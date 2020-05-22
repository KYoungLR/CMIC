package com.churchmutual.test.harness.model;

import com.liferay.portal.kernel.util.Http;

import java.util.List;

/**
 * @author Eric Chin
 */
public class HarnessDescriptor {

	public HarnessDescriptor(
		String description, String endpoint, Http.Method method) {

		_description = description;
		_endpoint = endpoint;
		_method = method;
	}

	public String getDescription() {
		return _description;
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public Http.Method getMethod() {
		return _method;
	}

	public List<Parameter> getParameters() {
		return _parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		_parameters = parameters;
	}

	public static class Parameter {

		public Parameter(
			String description, String name, boolean required,
			Object sampleValue, String type) {

			_description = description;
			_name = name;
			_required = required;
			_sampleValue = sampleValue;
			_type = type;
		}

		public Parameter(
			String description, String name, boolean required, String type) {

			_description = description;
			_name = name;
			_required = required;
			_type = type;
		}

		public String getDescription() {
			return _description;
		}

		public String getName() {
			return _name;
		}

		public Object getSampleValue() {
			return _sampleValue;
		}

		public String getType() {
			return _type;
		}

		public boolean isRequired() {
			return _required;
		}

		private String _description;
		private String _name;
		private boolean _required;
		private Object _sampleValue;
		private String _type;

	}

	private String _description;
	private String _endpoint;
	private Http.Method _method;
	private List<Parameter> _parameters;

}