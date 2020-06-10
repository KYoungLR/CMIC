package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICUserDTO;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface PortalUserWebService {

	public List<CMICUserDTO> getCMICOrganizationUsers(long producerId);

	public List<CMICProducerDTO> getCMICUserProducers(String uuid);

	public void inviteUserToCMICOrganization(String emailAddress, long producerId);

	public boolean isUserRegistered(String uuid);

	public boolean isUserValid(String agentNumber, String divisionNumber, String registrationCode, String uuid);

	public void removeUserFromCMICOrganization(String cmicUUID, long producerId);

	public CMICUserDTO validateUser(String registrationCode);

	public CMICUserDTO validateUserRegistration(String registrationCode);

}