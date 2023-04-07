
package acme.features.authenticated.lecturer;

import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class AuthenticatedLecturerUpdateService extends AbstractService<Authenticated, Lecturer> {

}
