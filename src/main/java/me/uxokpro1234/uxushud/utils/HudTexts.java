package me.uxokpro1234.uxushud.utils;

import net.minecraft.client.MinecraftClient;

public final class HudTexts {
    public static final String SPRINT = "Sprint [Toggled]";

    private HudTexts() {}

    public static String fps() {
        return MinecraftClient.getInstance().getCurrentFps() + " fps";
    }

    public static String ping() {
        return "Ping: " + Utils.getPing() + "ms";
    }

    public static String tps() {
        return "TPS: " + (TPSHud.tps < 0 ? "--" : String.valueOf(TPSHud.tps));
    }
}
