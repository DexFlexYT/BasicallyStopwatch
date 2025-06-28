package org.dexflex.basicallystopwatch;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Clipboard;

public class TimerState {
    private long startTime, elapsed = 0;
    private boolean running = false, paused = false;
    private long lastInteraction = 0;

    public void toggle() {
        if (running) stop();
        else start();
    }

    public void start() {
        elapsed = 0;
        startTime = System.currentTimeMillis();
        running = true;
        paused = false;
        lastInteraction = startTime;
    }

    public void stop() {
        if (!running) return;
        if (!paused) elapsed = System.currentTimeMillis() - startTime;
        running = false;
        paused = false;
        lastInteraction = System.currentTimeMillis();
    }

    public void pauseOrResume() {
        if (!running) return;
        paused = !paused;
        if (paused) elapsed = System.currentTimeMillis() - startTime;
        else startTime = System.currentTimeMillis() - elapsed;
        lastInteraction = System.currentTimeMillis();
    }

    public void copyCurrentToClipboard() {
        String s = format(getElapsed());
        long window = MinecraftClient.getInstance().getWindow().getHandle();
        new Clipboard().setClipboard(window, s);
        lastInteraction = System.currentTimeMillis();
    }

    public long getElapsed() {
        if (running && !paused) {
            elapsed = System.currentTimeMillis() - startTime;
        }
        return elapsed;
    }

    public long getLastInteraction() { return lastInteraction; }
    public boolean isRunning() { return running; }
    public boolean isPaused() { return paused; }

    public String format(long ms) {
        long sec = ms / 1000, mins = sec / 60;
        return String.format("%02d:%02d.%03d", mins, sec % 60, ms % 1000);
    }

    public static int parseColor(String hex) {
        try { return Integer.decode(hex); }
        catch (Exception e) { return 0xFFFFFF; }
    }
}
