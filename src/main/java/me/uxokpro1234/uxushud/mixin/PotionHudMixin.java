package me.uxokpro1234.uxushud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static me.uxokpro1234.uxushud.UxusHud.pothud;
import static me.uxokpro1234.uxushud.UxusHud.pothudy;

@Mixin(InGameHud.class)
public abstract class PotionHudMixin {
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void uxushud$effects(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (!pothud) return;          // off -> vanilla overlay
        ci.cancel();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        List<StatusEffectInstance> effects = new ArrayList<>(client.player.getStatusEffects());
        if (effects.isEmpty()) return;
        effects.sort(Collections.reverseOrder());

        TextRenderer tr = client.textRenderer;
        int i = 0;
        for (StatusEffectInstance effect : effects) {
            int x = 3;
            int y = 18 * i + pothudy;
            i++;

            Identifier icon = effect.getEffectType().getKey()
                    .map(k -> k.getValue().withPrefixedPath("mob_effect/"))
                    .orElse(null);
            if (icon != null) {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, icon, x, y, 18, 18);
            }

            int duration = effect.getDuration() / 20;
            long mins = TimeUnit.SECONDS.toMinutes(duration);
            long secs = duration - TimeUnit.MINUTES.toSeconds(mins);

            String text;
            if (effect.isInfinite()) text = "\u221E";
            else if (mins == 0) text = secs + " sec";
            else text = String.format("%d min, %d sec", mins, secs);

            int c = duration <= 5 ? 0xFFFF5555 : duration <= 15 ? 0xFFFFAA00 : duration <= 25 ? 0xFFFFFF55 : 0xFFFFFFFF;

            context.drawText(tr, text, x + 21, y + 5, c, true);
        }
    }
}
