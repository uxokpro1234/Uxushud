package me.uxokpro1234.uxushud.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.uxokpro1234.uxushud.UxusHud.noguibg;

@Mixin(value = Screen.class, priority = 500)
public abstract class ScreenMixin {

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void uxushud$noBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (noguibg) ci.cancel();
    }
}
