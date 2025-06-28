package org.dexflex.basicallystopwatch;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TimerState {
    private long startTime;
    private long elapsed;
    private boolean running = false;
    private boolean paused = false;
    private long lastInteractionTime;

    public void toggle() {
        if (running) {
            stop();
        } else {
            start();
        }
    }

    public void pauseOrResume() {
        if (!running) return;
        paused = !paused;
        lastInteractionTime = System.currentTimeMillis();
    }

    public void start() {
        elapsed = 0;
        startTime = System.currentTimeMillis();
        running = true;
        paused = false;
        lastInteractionTime = System.currentTimeMillis();
    }

    public void stop() {
        if (running) {
            updateElapsed();
            running = false;
            paused = false;
            lastInteractionTime = System.currentTimeMillis();
            if (isAltPressed()) copyToClipboard(format(getElapsed()));
        }
    }

    public long getElapsed() {
        if (running && !paused) updateElapsed();
        return elapsed;
    }

    private void updateElapsed() {
        elapsed = System.currentTimeMillis() - startTime;
    }

    public boolean isRunning() { return running; }
    public boolean isPaused() { return paused; }

    public void copyCurrentToClipboard() {
        long time = getElapsed();
        String formatted = format(time);
        MinecraftClient.getInstance().keyboard.setClipboard(formatted);
        lastInteractionTime = System.currentTimeMillis();
    }


    public long getLastInteraction() { return lastInteractionTime; }

    private boolean isAltPressed() {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT);
    }

    private void copyToClipboard(String text) {
        MinecraftClient.getInstance().keyboard.setClipboard(text);
    }

    private String format(long ms) {
        long seconds = ms / 1000;
        return String.format("%02d:%02d.%03d", seconds / 60, seconds % 60, ms % 1000);
    }
}

