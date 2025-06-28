package org.dexflex.basicallystopwatch;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class StopwatchHud {
    public static void init() {
        HudRenderCallback.EVENT.register((MatrixStack ms, float tickDelta) -> render(ms));
    }

    private static void render(MatrixStack ms) {
        MinecraftClient mc = MinecraftClient.getInstance();
        TimerState t = StopwatchState.timer;

        if (!t.isRunning() && t.getElapsed() == 0) return;
        if (!t.isRunning() && System.currentTimeMillis() - t.getLastInteraction() > 10_000) return;

        String text = t.format(t.getElapsed());
        int color = TimerState.parseColor(StopwatchState.config.textColor);
        int x = (int) (mc.getWindow().getScaledWidth() * StopwatchState.config.x);
        int y = (int) (mc.getWindow().getScaledHeight() * StopwatchState.config.y);

        mc.textRenderer.draw(ms, text, x, y, color);
    }
}
