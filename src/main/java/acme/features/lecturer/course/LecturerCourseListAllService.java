
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseListAllService extends AbstractService<Lecturer, Course> {

	// Constants -------------------------------------------------------------
	protected static final String[]		PROPERTIES	= {
		"code", "title", "courseAbstract", "courseType", "retailPrice", "link"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected LecturerCourseRepository	repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Course> courses;

		courses = this.repository.findManyCourses();

		super.getBuffer().setData(courses);
	}

	@Override
	public void unbind(final Course course) {
		assert course != null;

		Tuple tuple;
		String payload;

		tuple = super.unbind(course, LecturerCourseListAllService.PROPERTIES);
		payload = String.format("%s; %s; %s", course.getCourseAbstract(), course.getRetailPrice(), course.getLink());
		tuple.put("payload", payload);

		super.getResponse().setData(tuple);
	}
}
