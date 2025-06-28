package org.dexflex.basicallystopwatch;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientEvents {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isPaused()) return;

            while (StopwatchState.toggleKey.wasPressed()) {
                StopwatchState.timer.toggle();
            }

            // Pause/resume and copy logic inside pauseKey loop
            while (StopwatchState.pauseKey.wasPressed()) {
                boolean altHeld = InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT)
                        || InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_ALT);
                if (altHeld) {
                    StopwatchState.timer.copyCurrentToClipboard();
                } else {
                    StopwatchState.timer.pauseOrResume();
                }
            }
        });

    }

}
