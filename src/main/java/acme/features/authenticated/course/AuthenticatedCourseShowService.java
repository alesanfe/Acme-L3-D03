
package acme.features.authenticated.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCourseShowService extends AbstractService<Authenticated, Course> {

	// Constants --------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"code", "title", "courseAbstract", "courseType", "retailPrice", "link"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedCourseRepository	repository;


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
		Principal principal;

		courseId = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();
		course = this.repository.findOneCourseById(courseId);
		status = !course.isDraftMode() && principal.isAuthenticated();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		int courseId;
		Course course;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.getBuffer().setData(course);
	}

	@Override
	public void unbind(final Course course) {
		assert course != null;

		Tuple tuple;

		tuple = super.unbind(course, AuthenticatedCourseShowService.PROPERTIES);
		tuple.put("lecturer", course.getLecturer().getIdentity().getFullName());

		super.getResponse().setData(tuple);
	}

}
