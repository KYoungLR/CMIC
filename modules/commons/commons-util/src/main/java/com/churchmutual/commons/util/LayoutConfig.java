package com.churchmutual.commons.util;

import com.liferay.portal.kernel.model.LayoutConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class LayoutConfig {

	public String getName() {
		return name;
	}

	public LayoutConfig setName(String name) {
		this.name = name;
		return this;
	}

	public String getFriendlyURL() {
		return friendlyURL;
	}

	public LayoutConfig setFriendlyURL(String friendlyURL) {
		this.friendlyURL = friendlyURL;
		return this;
	}

	public List<String> getPortletsKeys() {
		return Collections.unmodifiableList(portletsKeys);
	}

	public LayoutConfig addPortletKey(String portletKey) {
		portletsKeys.add(portletKey);
		return this;
	}

	public LayoutConfig addPortletsKeys(String... portletsKeys) {
		this.portletsKeys.addAll(asList(portletsKeys));
		return this;
	}

	public boolean isHiddenPage() {
		return hiddenPage;
	}

	public LayoutConfig setHiddenPage(boolean hiddenPage) {
		this.hiddenPage = hiddenPage;
		return this;
	}

	public boolean isPrivatePage() {
		return privatePage;
	}

	public LayoutConfig setPrivatePage(boolean privatePage) {
		this.privatePage = privatePage;
		return this;
	}

	public long getParentLayoutId() {
		return parentLayoutId;
	}

	public LayoutConfig setParentLayoutId(long parentLayoutId) {
		this.parentLayoutId = parentLayoutId;
		return this;
	}

	private String name;
	private String friendlyURL;
	private boolean hiddenPage = false;
	private boolean privatePage = true;
	private List<String> portletsKeys = new ArrayList<>();
	private long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

}
