package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockWebServiceConfiguration",
	localization = "content/Language", name = "mock-web-service"
)
public interface MockWebServiceConfiguration {

	@Meta.AD(
		deflt = "true", description = "Enable mock for Account Service",
		required = false
	)
	public boolean enableMockGetAccounts();

	@Meta.AD(
		deflt = "true", description = "Enable mock for Account Service",
		required = false
	)
	public boolean enableMockGetAccountsSearchByProducer();

	@Meta.AD(
		deflt = "true", description = "Enable mock for Account Service",
		required = false
	)
	public boolean enableMockGetAddressAccount();

}