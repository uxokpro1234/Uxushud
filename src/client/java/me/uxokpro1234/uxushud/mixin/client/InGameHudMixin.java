package me.uxokpro1234.uxushud.mixin.client;

import me.uxokpro1234.uxushud.utils.TPSHud;
import me.uxokpro1234.uxushud.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.uxokpro1234.uxushud.UxusHud.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", at = @At("HEAD"))
    private void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

        MinecraftClient mc = MinecraftClient.getInstance();

        context.drawText(mc.textRenderer, mc.getCurrentFps() + " fps", fpsx, fpsy, 0xFF55FFFF, true);

        context.drawText(mc.textRenderer, "Ping: " + Utils.getPing() + "ms", pingx, pingy, 0xFF55FFFF, true);
        context.drawText(mc.textRenderer, "TPS: " + TPSHud.tps, tpsx, tpsy, 0xFF55FFFF, true);

        if(geta() && sprinthud){context.drawText(mc.textRenderer, "Sprint [Toggled]", sprinthudx, sprinthudy-17, 0xFF55FFFF, true);
        }
        if(getb() && sprinthud){context.drawText(mc.textRenderer, "Sprint [Toggled]", sprinthudx, sprinthudy, 0xFF55FFFF, true);
        }
    }
}