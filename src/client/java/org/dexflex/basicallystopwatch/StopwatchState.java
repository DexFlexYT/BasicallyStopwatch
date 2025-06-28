package org.dexflex.basicallystopwatch;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class StopwatchState {
    public static KeyBinding toggleKey;
    public static KeyBinding pauseKey;
    public static TimerState timer;
    public static StopwatchConfig config;

    public static void init() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.basicallystopwatch.toggle", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G, "category.basicallystopwatch"
        ));
        pauseKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.basicallystopwatch.pause", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H, "category.basicallystopwatch"
        ));
        config = StopwatchConfig.load();
        timer = new TimerState();
    }
}
