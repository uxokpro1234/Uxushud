package me.uxokpro1234.uxushud;

import me.uxokpro1234.uxushud.gui.HudEditor;
import me.uxokpro1234.uxushud.utils.TPSHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class UxusHud implements ClientModInitializer {

    public static MinecraftClient mc;
    public static final Logger LOGGER = LoggerFactory.getLogger("UxusHud");

    public static boolean nohurtcam = true;
    public static boolean tps = true;
    public static boolean pothud = true;
    public static boolean ping = true;
    public static boolean fps = true;
    public static boolean sprinthud = true;
    public static boolean noloadingterrain = true;
    public static boolean betterhitboxes = true;
    public static boolean spectatorcameraclip = true;
    public static boolean nobackground = false;
    public static boolean noguibg = false;
    public static boolean saturationhud = false;

    public static int pothudy = 240;
    public static int pingx = 100, pingy = 40;
    public static int fpsx = 3, fpsy = 3;
    public static int sprinthudx = 2, sprinthudy = 60;
    public static int tpsx = 100, tpsy = 60;

    public static int color = 0xFF55FFFF;

    public static final KeyBinding GUI_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.uxus.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing UxusHud");
        mc = MinecraftClient.getInstance();
        loadConfig();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GUI_KEY.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new HudEditor());
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> saveConfig());
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> TPSHud.reset());
    }

    private static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("uxushud_config.txt");
    }

    public static void loadConfig() {
        Path path = configPath();
        if (!Files.exists(path)) {
            LOGGER.warn("Config not found. Creating default.");
            saveConfig();
            return;
        }
        try {
            for (String line : Files.readAllLines(path)) {
                int i = line.indexOf('=');
                if (i < 0) continue;
                try {
                    parseConfigValue(line.substring(0, i).trim(), line.substring(i + 1).trim());
                } catch (NumberFormatException e) {
                    LOGGER.warn("Bad config line: {}", line);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config", e);
        }
    }

    private static void parseConfigValue(String key, String v) {
        switch (key) {
            case "Nohurtcam" -> nohurtcam = Boolean.parseBoolean(v);
            case "PotHud" -> pothud = Boolean.parseBoolean(v);
            case "BetterHitboxes", "BetterHitbohes" -> betterhitboxes = Boolean.parseBoolean(v);
            case "NoLoadingTerrain" -> noloadingterrain = Boolean.parseBoolean(v);
            case "Tps" -> tps = Boolean.parseBoolean(v);
            case "Ping" -> ping = Boolean.parseBoolean(v);
            case "Fps" -> fps = Boolean.parseBoolean(v);
            case "SprintToggled" -> sprinthud = Boolean.parseBoolean(v);
            case "SpectatorCameraClip" -> spectatorcameraclip = Boolean.parseBoolean(v);
            case "NoBackground" -> nobackground = Boolean.parseBoolean(v);
            case "NoGuiBg" -> noguibg = Boolean.parseBoolean(v);
            case "SaturationHud" -> saturationhud = Boolean.parseBoolean(v);
            case "SprintToggledX" -> sprinthudx = Integer.parseInt(v);
            case "SprintToggledY" -> sprinthudy = Integer.parseInt(v);
            case "TpsX" -> tpsx = Integer.parseInt(v);
            case "TpsY" -> tpsy = Integer.parseInt(v);
            case "PingX" -> pingx = Integer.parseInt(v);
            case "PingY" -> pingy = Integer.parseInt(v);
            case "FpsX" -> fpsx = Integer.parseInt(v);
            case "FpsY" -> fpsy = Integer.parseInt(v);
            case "PotHudY" -> pothudy = Integer.parseInt(v);
            case "Color" -> color = Integer.parseInt(v);
            default -> LOGGER.warn("Unknown config key: {}", key);
        }
    }

    public static void saveConfig() {
        List<String> lines = List.of(
                "Nohurtcam=" + nohurtcam,
                "PotHud=" + pothud,
                "BetterHitboxes=" + betterhitboxes,
                "NoLoadingTerrain=" + noloadingterrain,
                "Tps=" + tps,
                "Ping=" + ping,
                "Fps=" + fps,
                "SprintToggled=" + sprinthud,
                "SpectatorCameraClip=" + spectatorcameraclip,
                "NoBackground=" + nobackground,
                "NoGuiBg=" + noguibg,
                "SaturationHud=" + saturationhud,
                "SprintToggledX=" + sprinthudx,
                "SprintToggledY=" + sprinthudy,
                "TpsX=" + tpsx,
                "TpsY=" + tpsy,
                "PingX=" + pingx,
                "PingY=" + pingy,
                "FpsX=" + fpsx,
                "FpsY=" + fpsy,
                "PotHudY=" + pothudy,
                "Color=" + color);
        try {
            Files.write(configPath(), lines);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }
}
