package me.uxokpro1234.uxushud.mixin;

import me.uxokpro1234.uxushud.utils.HudTexts;
import me.uxokpro1234.uxushud.utils.TPSHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.uxokpro1234.uxushud.UxusHud.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", at = @At("TAIL"))
    private void uxushud$render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;
        TextRenderer tr = mc.textRenderer;

        if (fps) context.drawText(tr, HudTexts.fps(), fpsx, fpsy, color, true);
        if (ping) context.drawText(tr, HudTexts.ping(), pingx, pingy, color, true);
        if (tps) context.drawText(tr, HudTexts.tps(), tpsx, tpsy, color, true);

        if (sprinthud && mc.options.sprintKey.isPressed()) {
            int y = mc.currentScreen instanceof ChatScreen ? sprinthudy - 17 : sprinthudy;
            context.drawText(tr, HudTexts.SPRINT, sprinthudx, y, color, true);
        }

        if (saturationhud) {
            int sat = MathHelper.ceil(mc.player.getHungerManager().getSaturationLevel());
            float hue = MathHelper.clamp(sat / 20.0f, 0.0f, 1.0f);
            int rgb = java.awt.Color.HSBtoRGB(hue / 3.0f, 0.8f, 1.0f) | 0xFF000000;
            context.drawText(tr, String.valueOf(sat), context.getScaledWindowWidth() / 2 + 94, context.getScaledWindowHeight() - 39, rgb, true);
        }
    }
}
