package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICUser;

/**
 * @author Kayleen Lim
 */
public interface PortalUserWebService {

	public boolean isUserRegistered(String uuid);

	public boolean isUserValid(String agentNumber, String divisionNumber, String registrationCode, String uuid);

	public CMICUser validateUser(String registrationCode);

	public CMICUser validateUserRegistration(String registrationCode);

}