
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select l from Lecturer l where l.id = ?1")
	Lecturer findOneLecturerById(int activeRoleId);

	@Query("select c from Course c where c.code = ?1")
	Collection<Course> findManyCoursesByCode(String code);

	@Query("select c from Course c where c.id = ?1")
	Course findOneCourseById(int masterId);

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findManyCourses();

	@Query("select c from Course c where c.lecturer.id = ?1")
	Collection<Course> findManyCoursesByLecturerId(int activeRoleId);

}
