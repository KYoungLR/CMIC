package com.churchmutual.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kayleen Lim
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.rest.configuration.MockTransactionWebServiceConfiguration",
	localization = "content/Language", name = "mock-transaction-web-service"
)
public interface MockTransactionWebServiceConfiguration {

	@Meta.AD(
		deflt = "true", description = "Enable mock for Transaction Service method /v1/transactions", required = false
	)
	public boolean enableMockGetTransaction();

	@Meta.AD(
		deflt = "true",
		description = "Enable mock for Transaction Service method /v1/transaction-account-summary/accounts",
		required = false
	)
	public boolean enableMockGetTransactionAccountSummaryByAccounts();

	@Meta.AD(
			deflt = "true", description = "Enable mock for Transaction Service method /v1/transactions/on-policy",
			required = false
	)
	public boolean enableMockGetTransactionOnPolicy();

	@Meta.AD(
			deflt = "true",
			description = "Enable mock for Transaction Service method /v1/transaction-policy-summary/policies",
			required = false
	)
	public boolean enableMockGetTransactionPolicySummaryByPolicies();

	@Meta.AD(
		deflt = "true",
		description = "Enable mock for Transaction Service method /v1/transaction-policy-summary/on-account",
		required = false
	)
	public boolean enableMockGetTransactionPolicySummaryOnAccount();

}