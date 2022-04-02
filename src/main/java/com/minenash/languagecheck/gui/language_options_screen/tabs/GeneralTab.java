package com.minenash.languagecheck.gui.language_options_screen.tabs;

import com.minenash.languagecheck.LanguageCheck;
import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreenDef;
import com.minenash.languagecheck.gui.language_options_screen.painters.Downloader;
import com.minenash.languagecheck.languages.Languages;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.nio.file.Files;
import java.nio.file.Path;

public class GeneralTab {
    private final LanguageOptionsScreenDef screen;

    private final WPlainPanel panel;

    private final WLabel name, variant, spellCheck, size, status;
    private final WToggleButton active, quickSet;

    private boolean downloadOrDeleteState;
    private final WButton downloadOrDelete;

    public GeneralTab(LanguageOptionsScreenDef mainScreen) {
        screen = mainScreen;

        panel = new WPlainPanel();

        name = new WLabel("§n" + screen.curLang.name);
        variant = new WLabel("Variant: §o" + (screen.curLang.currentVariant.name() == null ? "N/A" : screen.curLang.currentVariant.name()));
        status = new WLabel("Status: §o" + statusLabel());
        spellCheck = new WLabel("Spell Check: §o" + (screen.curLang.hasSpellCheck ? "Yes" : "No"));
        size = new WLabel("Size: §o" + LanguageOptionsScreenDef.getSizeStr(screen.curLang.size));

        active = new WToggleButton(new LiteralText("Active (deactivates others)"));
        active.setToggle(screen.curLang == Languages.current);
        active.setOnToggle( b -> {
            if (!b)
                LanguageCheck.makeActive(null);
            if (screen.curLang.available)
                LanguageCheck.makeActive(screen.curLang);
            else
                active.setToggle(false);
        });

        quickSet = new WToggleButton(new LiteralText("Show in quick set"));
        quickSet.setToggle(false);

        downloadOrDeleteState = !Languages.downloaded.contains(screen.curLang);
        downloadOrDelete = new WButton(new ItemIcon(downloadOrDeleteState ? Items.GREEN_CONCRETE : Items.RED_CONCRETE));
        downloadOrDelete.setSize(20, 20);
        downloadOrDelete.setOnClick(() -> {

            if (downloadOrDeleteState) {
                downloadOrDelete.setEnabled(false);
                panel.setBackgroundPainter( new Downloader(screen.curLang.link, LanguageCheck.MODS_PATH, false, (success) -> {
                    downloadOrDeleteState = !success;
                    downloadOrDelete.setEnabled(true);
                    downloadOrDelete.setIcon(new ItemIcon(downloadOrDeleteState ? Items.GREEN_CONCRETE : Items.RED_CONCRETE));
                    panel.setBackgroundPainter(null);
                    if (success && !Languages.downloaded.contains(screen.curLang))
                        Languages.downloaded.add(screen.curLang);
                    update();
                }) );
            } else {
                try {
                    Path file = LanguageCheck.MODS_PATH
                            .resolve(screen.curLang.link.substring(screen.curLang.link.lastIndexOf("/")+1));
                    Files.delete(file);

                    downloadOrDeleteState = true;
                    downloadOrDelete.setIcon(new ItemIcon(Items.GREEN_CONCRETE));
                    while (Languages.downloaded.contains(screen.curLang))
                        Languages.downloaded.remove(screen.curLang);
                    update();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void update() {
        name.setText(text("§n" + screen.curLang.name));
        variant.setText(text("Variant: §o" + (screen.curLang.currentVariant.name() == null ? "N/A" : screen.curLang.currentVariant.name())));
        status.setText(text("Status: §o" + statusLabel()));
        spellCheck.setText(text("Spell Check: §o" + (screen.curLang.hasSpellCheck ? "Yes" : "No")));
        size.setText(text("Size: §o" + LanguageOptionsScreenDef.getSizeStr(screen.curLang.size)));

        active.setToggle(screen.curLang == Languages.current);
        quickSet.setToggle(false);

        downloadOrDeleteState = !Languages.downloaded.contains(screen.curLang);
        downloadOrDelete.setIcon(new ItemIcon(downloadOrDeleteState ? Items.GREEN_CONCRETE : Items.RED_CONCRETE));
        downloadOrDelete.addTooltip(new TooltipBuilder().add(new LiteralText("Test")));
    }

    private Text text(String text) {
        return new LiteralText(text);
    }

    private String statusLabel() {
        boolean downloaded = Languages.downloaded.contains(screen.curLang);

        if (screen.curLang.available)
            return downloaded ? "Loaded" : "Loaded until restart";
        return downloaded ? "Will be loaded after restart" : "Not downloaded";
    }

    public WWidget get() {
        panel.setSize(215,160);

        panel.add(downloadOrDelete, panel.getWidth() - 25, 8);

        int y = 0;
        panel.add(name, 10, y += 8);
        panel.add(variant, 10, y += 15);
        panel.add(status, 10, y += 11);
        panel.add(spellCheck, 10, y += 11);
        panel.add(size, 10, y += 11);

        panel.add(active, 10, y+= 15);
        panel.add(quickSet, 10, y+= 11);

//        panel.setBackgroundPainter( new Downloader() );

        return panel;
    }

    int i = 1;
    int i2 = 0;

}
