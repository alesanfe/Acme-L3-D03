package acme.features.authenticated.practicum;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

    // Constants --------------------------------------------------------------
    public static final String[] PROPERTIES = {"code", "title", "abstractPracticum", "goals", "estimatedTimeInHours"};

    // Internal state ---------------------------------------------------------
    @Autowired
    protected AuthenticatedPracticumRepository repository;

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
        Collection<Practicum> practicums;
        Principal principal;
        int userAccountId;

        principal = super.getRequest().getPrincipal();
        userAccountId = principal.getAccountId();
        practicums = this.repository.findManyByUserAccountId(userAccountId);

        super.getBuffer().setData(practicums);
    }

    @Override
    public void unbind(final Practicum practicum) {
        assert practicum != null;

        Tuple tuple;

        tuple = super.unbind(practicum, PROPERTIES);

        super.getResponse().setData(tuple);
    }
}
