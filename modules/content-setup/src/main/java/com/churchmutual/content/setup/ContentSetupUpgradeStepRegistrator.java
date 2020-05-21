package com.churchmutual.content.setup;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContentSetupUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {

	}
}
