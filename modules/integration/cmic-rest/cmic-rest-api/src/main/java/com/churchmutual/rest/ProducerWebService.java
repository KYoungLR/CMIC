package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICContact;
import com.churchmutual.rest.model.CMICProducer;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface ProducerWebService {

	public List<CMICContact> getContacts(long producerId);

	public CMICProducer getProducerById(long id);

	public List<CMICProducer> getProducers(String agent, String division, String name, boolean payOutOfCdms);

}