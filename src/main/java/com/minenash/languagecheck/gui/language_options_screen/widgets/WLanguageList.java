package com.minenash.languagecheck.gui.language_options_screen.widgets;

import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreenDef;
import com.minenash.languagecheck.languages.Language;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class WLanguageList extends WWidget {

    private static final int BLACK_COLOR = 0xFF000000;
    private static final int HOVER_COLOR = 0xFF218756;
    private static final int SELECTED_COLOR = 0xFFAA0000;
    private static final int EXPANDABLE_COLOR = 0xFFC6C6C6;
    private static final int NO_COLOR = -1;

    private final LanguageOptionsScreenDef mainScreen;
    private final List<Language> languages;
    private int langSelected;
    private int open_length;

    private static final int SPACING = 9;

    public WLanguageList(LanguageOptionsScreenDef mainScreen, List<Language> languages) {
        this.mainScreen = mainScreen;
        this.languages = languages;
        this.langSelected = languages.indexOf(mainScreen.curLang);
        this.open_length = langSelected == -1 ? 0 : languages.get(langSelected).variants.size();
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        super.paint(matrices, x, y, mouseX, mouseY);

        for (int i = 0; i < languages.size(); i++)
            y += paintGroup(matrices, x, y, mouseX, mouseY, languages.get(i), i);

        updateSize();

    }

    public int paintGroup(MatrixStack matrices, int x, int y, int mouseX, int mouseY, Language lang, int index) {

        int ypos = index > langSelected ? (index+open_length)*SPACING : index*SPACING;
        boolean isHover = mouseY >= ypos && mouseY < ypos+SPACING && mouseX >= 0 && mouseX <= width;
        int color = isHover ? HOVER_COLOR : langSelected == index ? SELECTED_COLOR : lang.variants.size() > 1 ? EXPANDABLE_COLOR : NO_COLOR;

        ScreenDrawing.coloredRect(matrices,x+1,y+1,5,5, BLACK_COLOR);
        if (color != NO_COLOR)
            ScreenDrawing.coloredRect(matrices,x+2,y+2,3,3, color);
        ScreenDrawing.drawString(matrices, lang.name, x+8, y, BLACK_COLOR);


        int y_offset = SPACING;
        if (langSelected == index && lang.variants.size() > 1)
            for (int i = 0; i < lang.variants.size(); i++, y_offset+=SPACING) {
                Language.Variant variant = lang.variants.get(i);
                ScreenDrawing.drawString(matrices, "   " + variant.name(), x + 8, y += SPACING, 0xFF000000);

                int yposIn = ypos + (i+1)*SPACING;
                boolean isHoverIn = mouseY >= yposIn && mouseY < yposIn+SPACING && mouseX >= 0 && mouseX <= width;

                if (variant == lang.currentVariant || isHoverIn)
                    ScreenDrawing.coloredRect(matrices,x+15,y+2,3,3, BLACK_COLOR);
                if (isHoverIn)
                    ScreenDrawing.coloredRect(matrices,x+16,y+3,1,1, EXPANDABLE_COLOR);

            }


        return y_offset;
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        if (x < 0 || x > width)
            return InputResult.IGNORED;

        int selected = y/SPACING;
        if (selected >= languages.size() + open_length)
            return InputResult.IGNORED;

        if (this.langSelected != -1 && selected > this.langSelected && selected <= this.langSelected + open_length) {
            var e = languages.get(this.langSelected);
            e.currentVariant = e.variants.get(selected - this.langSelected - 1);
            mainScreen.setCurLang(e);
            return InputResult.PROCESSED;
        }
        if (selected > this.langSelected + open_length)
            selected -= open_length;
        mainScreen.setCurLang( languages.get(selected) );

        if (selected == this.langSelected) {
            open_length = 0;
            this.langSelected = -1;
        }
        else {
            open_length = languages.get(selected).variants.size();
            if (open_length == 1)
                open_length = 0;
            this.langSelected = selected;
        }
        updateSize();
        return InputResult.PROCESSED;

    }

    private void updateSize() {
        setSize(getWidth(), languages.size()*SPACING + open_length*SPACING);
    }

}