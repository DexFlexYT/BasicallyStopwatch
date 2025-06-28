package org.dexflex.basicallystopwatch;

import net.fabricmc.api.ClientModInitializer;

public class BasicallyStopwatchClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		StopwatchState.init();
		StopwatchHud.init();
		ClientEvents.init();
	}
}
