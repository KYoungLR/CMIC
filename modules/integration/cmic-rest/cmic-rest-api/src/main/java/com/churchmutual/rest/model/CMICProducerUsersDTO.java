package com.churchmutual.rest.model;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public class CMICProducerUsersDTO extends CMICObjectDTO {

	public long getProducerId() {
		return _producerId;
	}

	public List<CMICUserDTO> getProducerUsers() {
		return _producerUsers;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public void setProducerUsers(List<CMICUserDTO> producerUsers) {
		_producerUsers = producerUsers;
	}

	private long _producerId;
	private List<CMICUserDTO> _producerUsers;

}