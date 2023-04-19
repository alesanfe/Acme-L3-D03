
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCourseListService extends AbstractService<Authenticated, Course> {

	// Constants --------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"code", "title", "courseAbstract", "courseType", "retailPrice", "link"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedCourseRepository	repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		status = principal.isAuthenticated();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Course> courses;

		courses = this.repository.findAllCourses();
		super.getBuffer().setData(courses);
	}

	@Override
	public void unbind(final Course course) {
		assert course != null;

		Tuple tuple;

		tuple = super.unbind(course, AuthenticatedCourseListService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

}
