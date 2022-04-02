package com.minenash.languagecheck.gui.language_options_screen;

import com.minenash.languagecheck.gui.language_options_screen.tabs.DictionaryTab;
import com.minenash.languagecheck.gui.language_options_screen.tabs.GeneralTab;
import com.minenash.languagecheck.gui.language_options_screen.tabs.NgramTab;
import com.minenash.languagecheck.gui.language_options_screen.tabs.RulesTab;
import com.minenash.languagecheck.gui.language_options_screen.widgets.WLanguageList;
import com.minenash.languagecheck.languages.Language;
import com.minenash.languagecheck.languages.Languages;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;


public class LanguageOptionsScreenDef extends LightweightGuiDescription {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public Language curLang = Languages.current != null ? Languages.current : Languages.all.get(0);

    private final GeneralTab generalTab;
    private final NgramTab ngramTab;
    private final DictionaryTab dictionaryTab;
    private final RulesTab rulesTab;


    public LanguageOptionsScreenDef() {
        generalTab = new GeneralTab(this);
        ngramTab = new NgramTab(this);
        dictionaryTab = new DictionaryTab(this);
        rulesTab = new RulesTab(this);

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(client.getWindow().getScaledWidth()-50, client.getWindow().getScaledHeight()-50);
        root.setInsets(Insets.ROOT_PANEL);

        root.add(new WLabel(new LiteralText("Language Checker Options")), 6, 0, 2, 1);
        root.add(new WLabel(new LiteralText("Language")), 2, 1, 3, 1);

        WTabPanel tabs2 = new WTabPanel();
        tabs2.add(scrolling(new WLanguageList(this, Languages.all), 110, 160, 315), tab -> tab.title(new LiteralText("All")));
        tabs2.add(scrolling(new WLanguageList(this, Languages.available), 110, 160), tab -> tab.title(new LiteralText("Downloaded")));
        root.add(tabs2, 0, 2, 6,10);


        WTabPanel tabs = new WTabPanel();
        tabs.add(generalTab.get(), tab -> tab.title(new LiteralText("General")));
        tabs.add(ngramTab.get(), tab -> tab.title(new LiteralText("Ngram")));
        tabs.add(dictionaryTab.get(), tab -> tab.title(new LiteralText("Dictionary")));
        tabs.add(rulesTab.get(), tab -> tab.title(new LiteralText("Rules")));
        root.add(tabs, 7, 2, 9, 10);

        root.validate(this);
    }

    public static String getSizeStr(float size) {
        return size >= 1000_000 ? size / 1000_000 + " GB": size >= 1000 ? size / 1000 + " MB": size + " KB";
    }

    public void setCurLang(Language lang) {
        this.curLang = lang;
        generalTab.update();
        ngramTab.update();
        dictionaryTab.update();
        rulesTab.update();
    }

    public static WWidget scrolling(WWidget toWrap, int width, int height) {
       return scrolling(toWrap, width, height, height);
    }

    public static WWidget scrolling(WWidget toWrap, int width, int height, int insideHeight) {
        toWrap.setSize(width, insideHeight);
        WScrollPanel scrollPanel = new WScrollPanel(toWrap);
        scrollPanel.setScrollingHorizontally(TriState.FALSE);
        scrollPanel.setSize(width-10, height-10);

        WPlainPanel holder = new WPlainPanel();
        holder.add(scrollPanel, 5, 5, width-10, height-10);
        holder.setSize(width, height);

        return holder;
    }


}
