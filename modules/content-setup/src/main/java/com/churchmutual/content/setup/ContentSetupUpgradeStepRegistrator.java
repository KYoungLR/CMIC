package com.churchmutual.content.setup;

import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddBrokerSiteUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContentSetupUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {

		registry.register(
			"0.0.0", "1.0.0",
			new AddBrokerSiteUpgradeProcess());
	}
}
