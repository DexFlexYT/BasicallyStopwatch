package org.dexflex.basicallystopwatch;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ClientEvents {

    private static Vec3d lastPos = null;
    private static double currentSpeed = 0.0;

    public static void tick(MinecraftClient client) {
        if (client.player != null) {
            Vec3d pos = client.player.getPos();

            if (lastPos != null) {
                double dx = pos.x - lastPos.x;
                double dy = pos.y - lastPos.y;
                double dz = pos.z - lastPos.z;
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                currentSpeed = distance * 20.0; // b/tick -> b/sec
            }

            lastPos = pos;
        }
    }

    public static double getCurrentSpeed() {
        return currentSpeed;
    }


    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isPaused()) return;

            tick(client);

            while (StopwatchState.toggleKey.wasPressed()) {
                StopwatchState.timer.toggle();
            }
            while (StopwatchState.pauseKey.wasPressed()) {
                boolean alt = InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT)
                        || InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_ALT);
                if (alt) StopwatchState.timer.copyCurrentToClipboard();
                else StopwatchState.timer.pauseOrResume();
            }
        });
    }
}
