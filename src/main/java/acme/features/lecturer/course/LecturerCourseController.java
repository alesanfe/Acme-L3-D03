
package acme.features.lecturer.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courses.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseController extends AbstractController<Lecturer, Course> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private LecturerCourseShowService		showService;
	@Autowired
	private LecturerCourseCreateService		createService;
	@Autowired
	private LecturerCourseUpdateService		updateService;
	@Autowired
	private LecturerCourseDeleteService		deleteService;
	@Autowired
	private LecturerCourseListAllService	listAllService;
	@Autowired
	private LecturerCourseListMineService	listMineService;
	@Autowired
	private LecturerCoursePublishService	publishService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);

	}
}
