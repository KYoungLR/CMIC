package com.churchmutual.rest.service;

import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.configuration.MockProducerWebServiceConfiguration;
import com.churchmutual.rest.model.CMICContact;
import com.churchmutual.rest.model.CMICProducer;
import com.churchmutual.rest.service.mock.MockProducerWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockProducerWebServiceConfiguration", immediate = true,
	service = ProducerWebService.class
)
public class ProducerWebServiceImpl implements ProducerWebService {

	@Override
	public List<CMICContact> getContacts(long producerId) {
		if (_mockProducerWebServiceConfiguration.enableMockGetContacts()) {
			return _mockProducerWebServiceClient.getContacts(producerId);
		}

		//TODO CMIC-199

		CMICContact contact = new CMICContact();

		contact.setFirstName("ACTUAL");

		return ListUtil.toList(contact);
	}

	@Override
	public CMICProducer getProducerById(long id) {
		if (_mockProducerWebServiceConfiguration.enableMockGetProducerById()) {
			return _mockProducerWebServiceClient.getProducerById(id);
		}

		//TODO CMIC-199

		CMICProducer producer = new CMICProducer();

		producer.setDivision("ACTUAL");

		return producer;
	}

	@Override
	public List<CMICProducer> getProducers(String agent, String division, String name, boolean payOutOfCdms) {
		if (_mockProducerWebServiceConfiguration.enableMockGetProducers()) {
			return _mockProducerWebServiceClient.getProducers(agent, division, name, payOutOfCdms);
		}

		//TODO CMIC-199

		CMICProducer producer = new CMICProducer();

		producer.setDivision("ACTUAL");

		return ListUtil.toList(producer);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockProducerWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockProducerWebServiceConfiguration.class, properties);
	}

	@Reference
	private MockProducerWebServiceClient _mockProducerWebServiceClient;

	private MockProducerWebServiceConfiguration _mockProducerWebServiceConfiguration;

}