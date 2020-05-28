package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICContact;
import com.churchmutual.rest.model.CMICProducer;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockProducerWebServiceClient.class)
public class MockProducerWebServiceClient {

	public List<CMICContact> getContacts(long producerId) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getContacts.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICContact[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICContact[] cmicContacts = jsonDeserializer.deserialize(fileContent, CMICContact[].class);

		return ListUtil.fromArray(cmicContacts);
	}

	public CMICProducer getProducerById(long id) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getProducerById.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICProducer> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICProducer.class);
	}

	public List<CMICProducer> getProducers(String agent, String division, String name, boolean payOutOfCdms) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getProducers.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICProducer[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICProducer[] cmicProducers = jsonDeserializer.deserialize(fileContent, CMICProducer[].class);

		return ListUtil.fromArray(cmicProducers);
	}

	private static final String _PRODUCER_WEB_SERVICE_DIR = "producer-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}