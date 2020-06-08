create table cmic_CMICAccountEntry (
	cmicAccountEntryId LONG not null primary key,
	accountEntryId LONG,
	accountNumber VARCHAR(75) null,
	numExpiredPolicies INTEGER,
	numFuturePolicies INTEGER,
	numInForcePolicies INTEGER,
	totalBilledPremium VARCHAR(75) null
);

create table cmic_CMICOrganization (
	cmicOrganizationId LONG not null primary key,
	organizationId LONG,
	agent VARCHAR(75) null,
	division VARCHAR(75) null,
	producerId LONG,
	producerType INTEGER,
	active_ BOOLEAN
);