/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import acme.framework.helpers.MomentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.repositories.BannerRepository;

@Service
public class AdministratorCreateBannerService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	protected static final String[] PROPERTIES = {
			"instant", "displayStart", "displayEnd", "picture", "slogan", "link"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected BannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, AdministratorCreateBannerService.PROPERTIES);
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		Date start;
		Date end;

		start = object.getDisplayStart();
		end = object.getDisplayEnd();

		if (!super.getBuffer().getErrors().hasErrors("displayStart"))
			super.state(MomentHelper.isAfterOrEqual(start, MomentHelper.getCurrentMoment()), "displayStart", "administrator.banner.error.startDate.beforeInstantiation");

		if (!super.getBuffer().getErrors().hasErrors("displayEnd")) {
			super.state(MomentHelper.isAfter(end, start), "displayEnd", "administrator.banner.error.finishDate.beforeStartDate");
			super.state(MomentHelper.isLongEnough(start, end, 7, ChronoUnit.DAYS), "displayEnd", "administrator.banner.error.finishDate.notLongEnough");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		Tuple tuple;

		tuple = super.unbind(object, AdministratorCreateBannerService.PROPERTIES);
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}