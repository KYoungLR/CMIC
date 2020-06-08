create table cmic_CMICAccountEntry (
	cmicAccountEntryId LONG not null primary key,
	accountEntryId LONG,
	accountNumber VARCHAR(75) null,
	numExpiredPolicies INTEGER,
	numFuturePolicies INTEGER,
	numInForcePolicies INTEGER,
	totalBilledPremium VARCHAR(75) null
);