package com.minenash.languagecheck.gui.language_options_screen;

import com.minenash.languagecheck.Languages;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

//import static com.minenash.languagecheck.LanguageCheck.langTool;


public class LanguageOptionsScreenDefinition extends LightweightGuiDescription {

    MinecraftClient client = MinecraftClient.getInstance();
//        WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));

    Languages.Entry selectedLanguage = Languages.languages.get("English").get(0);

    public LanguageOptionsScreenDefinition() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(client.getWindow().getScaledWidth()-50, client.getWindow().getScaledHeight()-50);
        root.setInsets(Insets.ROOT_PANEL);

        root.add(new WLabel(new LiteralText("Language Checker Options")), 6, 0, 2, 1);
        root.add(new WLabel(new LiteralText("Language")), 2, 1, 3, 1);
//        root.add(new WLabel(new LiteralText("Options")), 12, 1, 2, 1);

        WTabPanel tabs2 = new WTabPanel();
        tabs2.add(scrolling(new WLanguageList(this), 110, 160, 315), tab -> tab.title(new LiteralText("All")));
        tabs2.add(scrolling(new WDownloadedLanguageList(this), 110, 160), tab -> tab.title(new LiteralText("Downloaded")));

        root.add(tabs2, 0, 2, 6,10);


        WTabPanel tabs = new WTabPanel();
        tabs.add(new WPlainPanel(), tab -> tab.title(new LiteralText("General")));
        tabs.add(new WPlainPanel(), tab -> tab.title(new LiteralText("Dictionary")));
        tabs.add(getRules(), tab -> tab.title(new LiteralText("Rules")));

        root.add(tabs, 7, 2, 9, 10);

        root.validate(this);
    }

    public WWidget getRules() {
        WPlainPanel panel = new WPlainPanel();
        panel.setSize(215,160);
        panel.add(new WDynamicLabel(() -> selectedLanguage.toString()), 5, 5);

//        int y = 5;
//        for (String rule : langTool.getAllRules().stream().map(r -> r.getCategory().getName()).distinct().toList()) {
//            WToggleButton b = new WToggleButton(new LiteralText(rule));
//            b.setToggle(true);
//            panel.add(b, 5, y+=11);
//        }

        return scrolling(panel, 215, 160);
    }

    public void setSelected(Languages.Entry lang) {
        this.selectedLanguage = lang;
    }

    public WWidget scrolling(WWidget toWrap, int width, int height) {
       return scrolling(toWrap, width, height, height);
    }

    public WWidget scrolling(WWidget toWrap, int width, int height, int insideHeight) {
        WPlainPanel root = new WPlainPanel();
        root.add(toWrap, 0, 0, width-10, insideHeight);

        WScrollPanel scrollPanel = new WScrollPanel(toWrap);
        scrollPanel.setScrollingHorizontally(TriState.FALSE);
        scrollPanel.setSize(width-10, height-10);

        WPlainPanel holder = new WPlainPanel();
        holder.add(scrollPanel, 5, 5, width-10, height-10);
        holder.setSize(width, height);

        return holder;
    }


}
