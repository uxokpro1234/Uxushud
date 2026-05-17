package me.uxokpro1234.uxushud.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static me.uxokpro1234.uxushud.UxusHud.*;

public class HudEditor extends Screen {

    private enum Dragging {
        NONE, SPRINT, FPS, TPS, PING
    }

    private Dragging dragging = Dragging.NONE;

    public HudEditor() {
        super(Text.literal("HUD Editor"));
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return false;

        if (inside(mouseX, mouseY, sprinthudx, sprinthudy, 90, 12)) {
            dragging = Dragging.SPRINT;
            return true;
        }

        if (inside(mouseX, mouseY, fpsx, fpsy, 50, 12)) {
            dragging = Dragging.FPS;
            return true;
        }

        if (inside(mouseX, mouseY, tpsx, tpsy, 50, 12)) {
            dragging = Dragging.TPS;
            return true;
        }

        if (inside(mouseX, mouseY, pingx, pingy, 60, 12)) {
            dragging = Dragging.PING;
            return true;
        }

        dragging = Dragging.NONE;
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging == Dragging.NONE) return false;

        switch (dragging) {
            case SPRINT -> {
                sprinthudx += deltaX;
                sprinthudy += deltaY;
            }
            case FPS -> {
                fpsx += deltaX;
                fpsy += deltaY;
            }
            case TPS -> {
                tpsx += deltaX;
                tpsy += deltaY;
            }
            case PING -> {
                pingx += deltaX;
                pingy += deltaY;
            }
        }

        return true;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = Dragging.NONE;
        return true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        context.fill(0, 0, width, height, 0x88000000);

        context.fill(sprinthudx, sprinthudy, sprinthudx + 90, sprinthudy + 12, 0xAA171817);
        context.fill(fpsx, fpsy, fpsx + 50, fpsy + 12, 0xAA171817);
        context.fill(tpsx, tpsy, tpsx + 50, tpsy + 12, 0xAA171817);
        context.fill(pingx, pingy, pingx + 60, pingy + 12, 0xAA171817);

        context.drawCenteredTextWithShadow(textRenderer, "HUD EDITOR", width / 2, 20, 0xFFFFFF);
    }

    private boolean inside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}