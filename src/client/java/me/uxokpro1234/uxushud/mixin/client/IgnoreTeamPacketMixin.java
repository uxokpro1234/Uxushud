package me.uxokpro1234.uxushud.mixin.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class IgnoreTeamPacketMixin {

    @Inject(method = "onTeam", at = @At("HEAD"), cancellable = true)
    private void ignoreTeamPacket(TeamS2CPacket packet, CallbackInfo ci) {
            ci.cancel();
    }
}