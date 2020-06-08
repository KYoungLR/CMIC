package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICUserDTO;

/**
 * @author Kayleen Lim
 */
public interface PortalUserWebService {

	public boolean isUserRegistered(String uuid);

	public boolean isUserValid(String agentNumber, String divisionNumber, String registrationCode, String uuid);

	public CMICUserDTO validateUser(String registrationCode);

	public CMICUserDTO validateUserRegistration(String registrationCode);

}