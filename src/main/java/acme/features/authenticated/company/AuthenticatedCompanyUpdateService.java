
package acme.features.authenticated.company;

import acme.services.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AuthenticatedCompanyUpdateService extends AbstractService<Authenticated, Company> {

	// Constants -------------------------------------------------------------
	protected static final String[]				PROPERTIES	= {
		"name", "vatNumber", "summary", "link"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedCompanyRepository	repository;
	@Autowired
	protected SpamService spamDetector;


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
		final Company company;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);

		super.getBuffer().setData(company);
	}

	@Override
	public void bind(final Company company) {
		assert company != null;

		super.bind(company, AuthenticatedCompanyUpdateService.PROPERTIES);
	}

	@Override
	public void validate(final Company company) {
		assert company != null;

		// Spam validation
		if (!super.getBuffer().getErrors().hasErrors("name"))
			super.state(this.spamDetector.validateTextInput(company.getName()), "name", "authenticated.company.error.spam.name");
		if (!super.getBuffer().getErrors().hasErrors("vatNumber"))
			super.state(this.spamDetector.validateTextInput(company.getVatNumber()), "vatNumber", "authenticated.company.error.spam.vatNumber");
		if (!super.getBuffer().getErrors().hasErrors("summary"))
			super.state(this.spamDetector.validateTextInput(company.getSummary()), "summary", "authenticated.company.error.spam.summary");
		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(this.spamDetector.validateTextInput(company.getLink()), "link", "authenticated.company.error.spam.link");
	}

	@Override
	public void perform(final Company company) {
		assert company != null;

		this.repository.save(company);
	}

	@Override
	public void unbind(final Company company) {
		assert company != null;

		Tuple tuple;

		tuple = super.unbind(company, AuthenticatedCompanyUpdateService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
