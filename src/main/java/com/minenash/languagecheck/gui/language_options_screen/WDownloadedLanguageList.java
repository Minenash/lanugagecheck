//package com.minenash.languagecheck.gui.option_scrren_2;
//
//import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
//import io.github.cottonmc.cotton.gui.widget.WWidget;
//import io.github.cottonmc.cotton.gui.widget.data.InputResult;
//import net.minecraft.client.util.math.MatrixStack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WDownloadedLanguageList extends WWidget {
//
//    private final List<Languages.Entry> entries = new ArrayList<>();
//    private final LanguageOptionsScreenDef mainScreen;
//
//    private int selected = 0;
//
//    private static final int SPACING = 11;
//
//    public WDownloadedLanguageList(LanguageOptionsScreenDef mainScreen) {
//        this.mainScreen = mainScreen;
//
//        for (var e : Languages.languages.values()) {
//            for (var e2 : e)
//                if (Languages.available.get(e2.language()))
//                    entries.add(e2);
//        }
//
//    }
//
//    @Override
//    public boolean canResize() {
//        return true;
//    }
//
//    @Override
//    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
//        super.paint(matrices, x, y, mouseX, mouseY);
//
//        for (int i = 0; i < entries.size(); i++, y+=SPACING)
//            paintGroup(matrices, x, y, mouseX, mouseY, entries.get(i), i);
//
//    }
//
//    public void paintGroup(MatrixStack matrices, int x, int y, int mouseX, int mouseY, Languages.Entry lang, int i) {
//        boolean hovered = mouseY >= i*SPACING && mouseY < i*SPACING+SPACING && mouseX >= 0 && mouseX <= width;
//
//        if (i == selected) {
//            ScreenDrawing.coloredRect(matrices, x + 1, y - 2, 1, SPACING, 0xFF000000);
//            ScreenDrawing.coloredRect(matrices, x + 2, y - 2, 1, SPACING, 0xFFAA0000);
//            ScreenDrawing.coloredRect(matrices, x + 3, y - 2, width-12, SPACING, 0xFFA6A6A6);
//        }
//        else if (hovered) {
//            ScreenDrawing.coloredRect(matrices, x + 1, y - 2, 1, SPACING, 0xFF000000);
//            ScreenDrawing.coloredRect(matrices, x + 2, y - 2, 1, SPACING, 0xFF218756);
//            ScreenDrawing.coloredRect(matrices, x + 3, y - 2, width-12, SPACING, 0xFFA6A6A6);
//        }
//
//        ScreenDrawing.drawString(matrices, lang.toShortString(), x+5, y, 0xFF000000);
//
//    }
//
//    @Override
//    public InputResult onClick(int x, int y, int button) {
//        int selected = y/SPACING;
//
//        if (x < 0 || x > width || selected >= entries.size())
//            return InputResult.IGNORED;
//
//        this.selected = selected;
////        mainScreen.setSelected( entries.get(selected) );
//        return InputResult.PROCESSED;
//    }
//
//}