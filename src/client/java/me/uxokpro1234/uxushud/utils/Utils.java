package me.uxokpro1234.uxushud.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;

public class Utils {

    public static int getPing() {

        MinecraftClient mc = MinecraftClient.getInstance();

        ClientPlayerEntity player = mc.player;

        if (player == null || mc.getNetworkHandler() == null) {
            return 0;
        }

        PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());

        return entry != null ? entry.getLatency() : 0;
    }
}