package org.dexflex.basicallystopwatch;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class StopwatchHud {
    public static void init() {
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> render(matrices, MinecraftClient.getInstance()));
    }

    public static void render(MatrixStack matrices, MinecraftClient client) {
        TextRenderer textRenderer = client.textRenderer;

        int x = (int) (client.getWindow().getScaledWidth() * StopwatchState.config.x);
        int y = (int) (client.getWindow().getScaledHeight() * StopwatchState.config.y);

        if (!StopwatchState.timer.isIdle()) {
            String timerText = StopwatchState.timer.format(StopwatchState.timer.getElapsed());
            int color = StopwatchState.timer.parseColor(StopwatchState.config.textColor);
            textRenderer.drawWithShadow(matrices, timerText, x, y, color);
            y += 12;
        }

        // Speedometer (always visible)
        String speedText = String.format("%.2f b/s", ClientEvents.getCurrentSpeed());
        int color = StopwatchState.timer.parseColor(StopwatchState.config.textColor);
        textRenderer.drawWithShadow(matrices, speedText, x, y + 8, color);
    }
}
