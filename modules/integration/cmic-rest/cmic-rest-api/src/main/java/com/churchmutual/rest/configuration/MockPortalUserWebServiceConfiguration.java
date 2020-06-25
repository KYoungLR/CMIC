package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration", localization = "content/Language",
	name = "mock-portal-user-web-service"
)
public interface MockPortalUserWebServiceConfiguration {

	@Meta.AD(
		deflt = "true", description = "Enable mock for Portal User Service method /v1/get-users/details",
		required = false
	)
	public boolean enableMockGetUserDetails();

	@Meta.AD(
		deflt = "true", description = "Enable mock for Portal User Service method /v1/isUserRegistered",
		required = false
	)
	public boolean enableMockIsUserRegistered();

	@Meta.AD(
		deflt = "true", description = "Enable mock for Portal User Service method /v1/isUserValid", required = false
	)
	public boolean enableMockIsUserValid();

	@Meta.AD(
		deflt = "true", description = "Enable mock for Portal User Service method /v1/validateUser", required = false
	)
	public boolean enableMockValidateUser();

	@Meta.AD(
		deflt = "true", description = "Enable mock for Portal User Service method /v1/validateUserRegistration",
		required = false
	)
	public boolean enableMockValidateUserRegistration();

}