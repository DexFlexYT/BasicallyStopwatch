package org.dexflex.basicallystopwatch;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class StopwatchHud {
    public static void init() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> render(drawContext));
    }

    private static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) return;

        TimerState timer = StopwatchState.timer;
        if (!timer.isRunning() && timer.getElapsed() == 0) return;

        if (!timer.isRunning()) {
            long since = System.currentTimeMillis() - timer.getLastInteraction();
            if (since > 10_000) return;
        }

        String display = format(timer.getElapsed());
        int color = parseColor(StopwatchState.config.textColor);
        int x = (int) (client.getWindow().getScaledWidth() * StopwatchState.config.x);
        int y = (int) (client.getWindow().getScaledHeight() * StopwatchState.config.y);
        context.drawText(client.textRenderer, display, x, y, color, true);
    }


    private static String format(long ms) {
        long seconds = ms / 1000;
        long minutes = seconds / 60;
        return String.format("%02d:%02d.%02d", minutes, seconds % 60, (ms % 1000) / 10);
    }

    private static int parseColor(String hex) {
        try {
            return Color.decode(hex).getRGB();
        } catch (Exception e) {
            return 0xFFFFFF;
        }
    }
}
