package me.uxokpro1234.uxushud;

import me.uxokpro1234.uxushud.gui.HudEditor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class UxusHud implements ClientModInitializer {

    public static MinecraftClient mc;
    public static final Logger LOGGER =
            LoggerFactory.getLogger("UxusHud");

    static boolean a = false;
    static boolean b = false;

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

    public static int pingx = 100;
    public static int pingy = 40;

    public static int fpsx = 3;
    public static int fpsy = 3;

    public static int sprinthudx = 2;
    public static int sprinthudy = 60;

    public static int tpsx = 100;
    public static int tpsy = 60;

    public static int color = 0xFF55FFFF;
    public static final KeyBinding GUI_KEY =
            KeyBindingHelper.registerKeyBinding(new KeyBinding("key.uxus.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));
    @Override
    public void onInitializeClient() {

        LOGGER.info("Initializing UxusHud");
        mc = MinecraftClient.getInstance();
        loadConfig();
    /*
     * Config loading
     */
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (GUI_KEY.wasPressed()) {
                client.setScreen(new HudEditor());
            }

            if (mc.options.sprintKey.isPressed()) {

                if (mc.currentScreen instanceof ChatScreen) {
                    a = true;
                    b = false;
                } else {
                    b = true;
                    a = false;

                }
            }
            if (!mc.options.sprintKey.isPressed()) {
                a = false;
                b = false;
            }

        });
    }
    private void loadConfig() {

        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("uxushud_config.txt");

        if (!Files.exists(configPath)) {

            LOGGER.warn("Config not found. Creating default.");
            saveConfig();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configPath.toFile()))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length != 2)
                    continue;

                parseConfigValue(parts[0].trim(), parts[1].trim());
            }

        } catch (IOException e) {

            LOGGER.error("Failed to load config", e);
        }
    }

    private void parseConfigValue(String key, String value) {

        switch (key) {

            case "Nohurtcam" -> nohurtcam =
                    Boolean.parseBoolean(value);

            case "PotHud" -> pothud =
                    Boolean.parseBoolean(value);

            case "BetterHitboxes" -> betterhitboxes =
                    Boolean.parseBoolean(value);

            case "NoLoadingTerrain" -> noloadingterrain =
                    Boolean.parseBoolean(value);

            case "Tps" -> tps =
                    Boolean.parseBoolean(value);

            case "Ping" -> ping =
                    Boolean.parseBoolean(value);

            case "Fps" -> fps =
                    Boolean.parseBoolean(value);

            case "SprintToggled" -> sprinthud =
                    Boolean.parseBoolean(value);

            case "SpectatorCameraClip" -> spectatorcameraclip =
                    Boolean.parseBoolean(value);

            case "NoBackground" -> nobackground =
                    Boolean.parseBoolean(value);

            case "NoGuiBg" -> noguibg =
                    Boolean.parseBoolean(value);

            case "SaturationHud" -> saturationhud =
                    Boolean.parseBoolean(value);

            case "SprintToggledX" -> sprinthudx =
                    Integer.parseInt(value);

            case "SprintToggledY" -> sprinthudy =
                    Integer.parseInt(value);

            case "TpsX" -> tpsx =
                    Integer.parseInt(value);

            case "TpsY" -> tpsy =
                    Integer.parseInt(value);

            case "PingX" -> pingx =
                    Integer.parseInt(value);

            case "PingY" -> pingy =
                    Integer.parseInt(value);

            case "FpsX" -> fpsx =
                    Integer.parseInt(value);

            case "FpsY" -> fpsy =
                    Integer.parseInt(value);

            case "PotHudY" -> pothudy =
                    Integer.parseInt(value);

            case "Color" -> color =
                    Integer.parseInt(value);

            default ->
                    LOGGER.warn("Unknown config key: {}", key);
        }
    }

    private void saveConfig() {

        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("uxushud_config.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configPath.toFile()))) {

            writer.write("Nohurtcam=" + nohurtcam);
            writer.newLine();

            writer.write("PotHud=" + pothud);
            writer.newLine();

            writer.write("BetterHitboxes=" + betterhitboxes);
            writer.newLine();

            writer.write("NoLoadingTerrain=" + noloadingterrain);
            writer.newLine();

            writer.write("Tps=" + tps);
            writer.newLine();

            writer.write("Ping=" + ping);
            writer.newLine();

            writer.write("Fps=" + fps);
            writer.newLine();

            writer.write("SprintToggled=" + sprinthud);
            writer.newLine();

            writer.write("SpectatorCameraClip=" + spectatorcameraclip);
            writer.newLine();

            writer.write("NoBackground=" + nobackground);
            writer.newLine();

            writer.write("NoGuiBg=" + noguibg);
            writer.newLine();

            writer.write("SaturationHud=" + saturationhud);
            writer.newLine();

            writer.write("SprintToggledX=" + sprinthudx);
            writer.newLine();

            writer.write("SprintToggledY=" + sprinthudy);
            writer.newLine();

            writer.write("TpsX=" + tpsx);
            writer.newLine();

            writer.write("TpsY=" + tpsy);
            writer.newLine();

            writer.write("PingX=" + pingx);
            writer.newLine();

            writer.write("PingY=" + pingy);
            writer.newLine();

            writer.write("FpsX=" + fpsx);
            writer.newLine();

            writer.write("FpsY=" + fpsy);
            writer.newLine();

            writer.write("PotHudY=" + pothudy);
            writer.newLine();

            writer.write("Color=" + color);

        } catch (IOException e) {

            LOGGER.error("Failed to save config", e);
        }
    }

    public static boolean geta() {
        return a;
    }

    public static boolean getb() {
        return b;
    }
}
