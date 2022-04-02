package com.minenash.languagecheck.gui.language_options_screen.tabs;

import com.minenash.languagecheck.LanguageCheck;
import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreenDef;
import io.github.cottonmc.cotton.gui.widget.*;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.text.LiteralText;

public class RulesTab {
    private final LanguageOptionsScreenDef screen;

    private final WLabel name;

    public RulesTab(LanguageOptionsScreenDef mainScreen) {
        screen = mainScreen;
        name = new WLabel("§n" + screen.curLang.name);
    }

    //TODO: Update Rules Panel
    public void update() {
        name.setText(new LiteralText("§n" + screen.curLang.name));
    }

    public WWidget get() {
        WPlainPanel panel = new WPlainPanel();
        panel.setSize(215,160);
        panel.add(name, 5, 3);

        if (LanguageCheck.langTool == null)
            return panel;

        int y = 5;
        for (String rule : LanguageCheck.langTool.getAllRules().stream().map(r -> r.getCategory().getName()).distinct().toList()) {
            WToggleButton b = new WToggleButton(new LiteralText(rule));
            b.setToggle(true);
            panel.add(b, 5, y+=11);
        }

        return LanguageOptionsScreenDef.scrolling(panel, 215, 160);
    }

}
