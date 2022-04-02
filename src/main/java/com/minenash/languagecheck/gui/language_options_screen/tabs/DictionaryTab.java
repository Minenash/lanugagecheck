package com.minenash.languagecheck.gui.language_options_screen.tabs;

import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreenDef;
import com.minenash.languagecheck.gui.language_options_screen.widgets.WTranslucentTextField;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class DictionaryTab {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private final LanguageOptionsScreenDef screen;

    private final WLabel name;

    public DictionaryTab(LanguageOptionsScreenDef mainScreen) {
        screen = mainScreen;

        name = new WLabel("§n" + screen.curLang.name);
    }

    public void update() {
        name.setText(new LiteralText("§n" + screen.curLang.name));
    }

    public WWidget get() {
        WPlainPanel panel = new WPlainPanel();
        panel.setSize(215,160);
        panel.add(name, 10, 8);

        WTextField field = new WTranslucentTextField(new LiteralText("Search"));
        field.setMaxLength(200);

        panel.add(field, 5, panel.getHeight()-25, 100, 2);
        panel.add(new WTranslucentTextField(new LiteralText("Add")), 110, panel.getHeight()-25, 100, 2);

        panel.setBackgroundPainter((matrices, left, top, panel1) -> {
            int x = panel.getX() + left + 7;
            int y = panel.getY() + top + 25;
            int width = panel.getWidth() - 14;
            int height = 110;
            ScreenDrawing.coloredRect(matrices, x-1, y-1, width+2, height+2, 0xFF_000000);
            ScreenDrawing.coloredRect(matrices, x, y, width, height, 0xFF_5D5D5D);

            int yy = y - 6;
            for (String str : new String[]{"Minecraft", "Hypixel"}) {
                client.textRenderer.drawWithShadow(matrices, "Del", x+4, yy+11, 0xFFFFFFFF);
                int strWidth = client.textRenderer.getWidth(str);
                client.textRenderer.drawWithShadow(matrices, str, panel.getX() + left + (panel.getWidth()/2) - (strWidth/2), yy+=11, 0xFFFFFFFF);
            }
        });

        return panel;
    }
}
