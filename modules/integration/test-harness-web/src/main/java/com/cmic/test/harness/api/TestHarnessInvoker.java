package com.cmic.test.harness.api;

import com.cmic.test.harness.model.HarnessDescriptor;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Eric Chin
 */
public interface TestHarnessInvoker {

	public List<HarnessDescriptor> getHarnessDescriptors();

	public String invoke(PortletRequest portletRequest);

}