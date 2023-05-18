
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Constants -------------------------------------------------------------
	protected static final String[]		PROPERTIES	= {
		"code", "title", "courseAbstract", "courseType", "retailPrice", "link"
	};

	// Internal state --------------------------------------------------------
	@Autowired
	protected LecturerCourseRepository	repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;
		Lecturer lecturer;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && principal.hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course course;
		int courseId;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.getBuffer().setData(course);
	}

	@Override
	public void bind(final Course course) {
		assert course != null;

		super.bind(course, LecturerCoursePublishService.PROPERTIES);
	}

	@Override
	public void validate(final Course course) {
		assert course != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;
			boolean noChangeCode;
			Course oldCourse;
			int courseId;

			courseId = super.getRequest().getData("id", int.class);
			oldCourse = this.repository.findOneCourseById(courseId);

			isUnique = this.repository.findManyCoursesByCode(course.getCode()).isEmpty();
			noChangeCode = oldCourse.getCode().equals(course.getCode()) && oldCourse.getId() == course.getId();
			super.state(!isUnique || noChangeCode, "code", "authenticated.lecturer.course.form.error.not-unique-code");

		}
	}

	@Override
	public void perform(final Course course) {
		assert course != null;

		course.setDraftMode(false);

		this.repository.save(course);
	}

	@Override
	public void unbind(final Course course) {
		assert course != null;

		Tuple tuple;

		tuple = super.unbind(course, LecturerCoursePublishService.PROPERTIES);
		tuple.put("draftMode", course.isDraftMode());

		super.getRequest().setData(tuple);
	}

}
