package com.minenash.languagecheck.gui.language_options_screen;

import com.minenash.languagecheck.Languages;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
import java.util.Map;

public class WLanguageList extends WWidget {

    private final Object[] entries = Languages.languages.entrySet().toArray();
    private final LanguageOptionsScreenDefinition mainScreen;
    private int open = -1;
    private int open_length = 0;

    private static final int SPACING = 9;

    public WLanguageList(LanguageOptionsScreenDefinition mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        super.paint(matrices, x, y, mouseX, mouseY);

        for (int i = 0; i < entries.length; i++) {
            Map.Entry<String, List<Languages.Entry>> entry = getEntry(i);
            y += paintGroup(matrices, x, y, mouseX, mouseY, entry, i);
        }

    }

    public int paintGroup(MatrixStack matrices, int x, int y, int mouseX, int mouseY, Map.Entry<String,List<Languages.Entry>> group, int i) {
        ScreenDrawing.coloredRect(matrices,x+1,y+1,5,5, 0xFF000000);

        int hover = i > open ? (i+open_length)*SPACING : i*SPACING;

        if (mouseY >= hover && mouseY < hover+SPACING && mouseX >= 0 && mouseX <= width)
            ScreenDrawing.coloredRect(matrices,x+2,y+2,3,3, 0xFF218756);
        else if (open == i)
            ScreenDrawing.coloredRect(matrices,x+2,y+2,3,3, 0xFFAA0000);
        else if (group.getValue().size() > 1)
            ScreenDrawing.coloredRect(matrices,x+2,y+2,3,3, 0xFFC6C6C6);
        ScreenDrawing.drawString(matrices, group.getKey(), x+8, y, 0xFF000000);

        int y_offset = SPACING;

        if (open == i && group.getValue().size() > 1) {
            List<Languages.Entry> entries = group.getValue();
            for (int e = 1; e < entries.size(); e++, y_offset+=SPACING)
                ScreenDrawing.drawString(matrices, "   " + entries.get(e).variant(), x+8, y+=9, 0xFF000000);
        }


        return y_offset;
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        if (x >= 0 && x <= width) {
            int selected = y/SPACING;
            if (selected >= entries.length + open_length)
                return InputResult.IGNORED;
            if (open != -1 && selected > open && selected <= open + open_length) {
                var e = getEntry(open);
                mainScreen.setSelected( e.getValue().get(selected-open) );
                return InputResult.PROCESSED;
            }
            if (selected > open + open_length)
                selected -= open_length;
            mainScreen.setSelected( getEntry(selected).getValue().get(0) );

            if (selected == open) {
                open_length = 0;
                open = -1;
            }
            else {
                open_length = getEntry(selected).getValue().size()-1;
                open = selected;
            }
            return InputResult.PROCESSED;
        }

        open = -1;
        return InputResult.IGNORED;
    }

    private Map.Entry<String,List<Languages.Entry>> getEntry(int i) {
        return (Map.Entry<String,List<Languages.Entry>>) entries[i];
    }
}