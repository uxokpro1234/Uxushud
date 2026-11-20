package me.uxokpro1234.uxushud.gui;

import me.uxokpro1234.uxushud.UxusHud;
import me.uxokpro1234.uxushud.utils.HudTexts;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditor extends Screen {

    private enum Element { SPRINT, FPS, TPS, PING }

    private Element dragging = null;
    private int grabX, grabY;

    public HudEditor() {
        super(Text.literal("HUD Editor"));
    }

    private String label(Element e) {
        return switch (e) {
            case SPRINT -> HudTexts.SPRINT;
            case FPS -> HudTexts.fps();
            case TPS -> HudTexts.tps();
            case PING -> HudTexts.ping();
        };
    }

    private int getX(Element e) {
        return switch (e) {
            case SPRINT -> UxusHud.sprinthudx;
            case FPS -> UxusHud.fpsx;
            case TPS -> UxusHud.tpsx;
            case PING -> UxusHud.pingx;
        };
    }

    private int getY(Element e) {
        return switch (e) {
            case SPRINT -> UxusHud.sprinthudy;
            case FPS -> UxusHud.fpsy;
            case TPS -> UxusHud.tpsy;
            case PING -> UxusHud.pingy;
        };
    }

    private void setPos(Element e, int x, int y) {
        switch (e) {
            case SPRINT -> { UxusHud.sprinthudx = x; UxusHud.sprinthudy = y; }
            case FPS -> { UxusHud.fpsx = x; UxusHud.fpsy = y; }
            case TPS -> { UxusHud.tpsx = x; UxusHud.tpsy = y; }
            case PING -> { UxusHud.pingx = x; UxusHud.pingy = y; }
        }
    }

    private boolean inside(double mx, double my, Element e) {
        int x = getX(e), y = getY(e), w = textRenderer.getWidth(label(e));
        return mx >= x - 2 && mx <= x + w + 2 && my >= y - 2 && my <= y + 10;
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() == 0) {
            for (Element e : Element.values()) {
                if (inside(click.x(), click.y(), e)) {
                    dragging = e;
                    grabX = (int) click.x() - getX(e);
                    grabY = (int) click.y() - getY(e);
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (dragging == null) return super.mouseDragged(click, offsetX, offsetY);
        setPos(dragging, (int) click.x() - grabX, (int) click.y() - grabY);
        return true;
    }

    @Override
    public boolean mouseReleased(Click click) {
        dragging = null;
        return super.mouseReleased(click);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x88000000);

        for (Element e : Element.values()) {
            int x = getX(e), y = getY(e), w = textRenderer.getWidth(label(e));
            context.fill(x - 2, y - 2, x + w + 2, y + 10, 0xAA171817);
            context.drawText(textRenderer, label(e), x, y, UxusHud.color, true);
        }

        context.drawCenteredTextWithShadow(textRenderer, "HUD EDITOR", width / 2, 20, 0xFFFFFFFF);
    }

    @Override
    public void removed() {
        UxusHud.saveConfig();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
