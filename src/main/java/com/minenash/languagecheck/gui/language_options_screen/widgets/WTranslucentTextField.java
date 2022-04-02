package com.minenash.languagecheck.gui.language_options_screen.widgets;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class WTranslucentTextField extends WTextField {

    Text suggestion;

    public WTranslucentTextField(Text suggestion) {
        super(suggestion);
        this.suggestion = suggestion;
    }

    @Override
    protected void renderBox(MatrixStack matrices, int x, int y) {
        int borderColor = this.isFocused() ? 0xFF444444 : 0xFF000000;
        ScreenDrawing.coloredRect(matrices, x - 1, y - 1 + height, width + 2, 2, borderColor);
    }

    protected void renderText(MatrixStack matrices, int x, int y, String visibleText) {
       MinecraftClient.getInstance().textRenderer.draw(matrices, visibleText, x + TEXT_PADDING_X, y + TEXT_PADDING_Y, 0xFF000000);
    }

    protected void renderSuggestion(MatrixStack matrices, int x, int y) {
        MinecraftClient.getInstance().textRenderer.draw(matrices, this.suggestion, x + TEXT_PADDING_X, y + TEXT_PADDING_Y, 0xFF444444);
    }

}
