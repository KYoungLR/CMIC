package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface ProducerWebService {

	public List<CMICContactDTO> getContacts(long producerId);

	public CMICProducerDTO getProducerById(long id);

	public List<CMICProducerDTO> getProducers(String agent, String division, String name, boolean payOutOfCdms);

}