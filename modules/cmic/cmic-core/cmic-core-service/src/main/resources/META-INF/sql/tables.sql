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
	agentNumber VARCHAR(75) null,
	divisionNumber VARCHAR(75) null,
	producerId LONG,
	producerType INTEGER,
	active_ BOOLEAN
);