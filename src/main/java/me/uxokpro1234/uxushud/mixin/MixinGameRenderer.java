package me.uxokpro1234.uxushud.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.uxokpro1234.uxushud.UxusHud.nohurtcam;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void noHurtCam(CallbackInfo ci) {
        if (nohurtcam) ci.cancel();
    }
}
