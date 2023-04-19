
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findAllCourses();

	@Query("select u from UserAccount u where u.id = ?1")
	UserAccount findOneUserAccountById(int userAccountId);

	@Query("select c from Course c where c.id = ?1")
	Course findOneCourseById(int id);

}
