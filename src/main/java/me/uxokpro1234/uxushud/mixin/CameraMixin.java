package me.uxokpro1234.uxushud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.uxokpro1234.uxushud.UxusHud.spectatorcameraclip;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    private void onClipToSpace(float desiredCameraDistance, CallbackInfoReturnable<Float> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (spectatorcameraclip && client.player != null && client.player.isSpectator()) {
            cir.setReturnValue(desiredCameraDistance);
        }
    }
}
