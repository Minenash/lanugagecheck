package com.minenash.languagecheck.gui.language_options_screen.tabs;

import com.minenash.languagecheck.LanguageCheck;
import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreenDef;
import com.minenash.languagecheck.gui.language_options_screen.painters.Downloader;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.nio.file.Files;
import java.nio.file.Path;

public class NgramTab {
    private final LanguageOptionsScreenDef screen;

    private final WPlainPanel panel;

    private final WLabel name;
    private final WLabel sizeOrUnavaliable;

    private final WToggleButton enabled;

    boolean downloadOrDeleteState = false;
    WButton downloadOrDelete = new WButton(new ItemIcon(Items.GREEN_CONCRETE));

    public NgramTab(LanguageOptionsScreenDef mainScreen) {
        screen = mainScreen;
        panel = new WPlainPanel();

        name = new WLabel("§n" + screen.curLang.name);
        sizeOrUnavaliable = new WLabel("Size: §o" + LanguageOptionsScreenDef.getSizeStr(screen.curLang.ngram.size));

        enabled = new WToggleButton(new LiteralText("Disabled"));
        enabled.setToggle(false);
        enabled.setOnToggle( b -> {
            enabled.setLabel(new LiteralText(b ? "Enabled" : "Disabled"));
        });

        downloadOrDeleteState = !screen.curLang.ngram.downloaded;
        downloadOrDelete = new WButton(new ItemIcon(downloadOrDeleteState ? Items.GREEN_CONCRETE : Items.RED_CONCRETE));
        downloadOrDelete.setSize(20, 20);
        downloadOrDelete.setOnClick(() -> {

            if (downloadOrDeleteState) {
                downloadOrDelete.setEnabled(false);
                panel.setBackgroundPainter( new Downloader(screen.curLang.ngram.link, LanguageCheck.CONFIG_PATH.resolve("ngram"), true, (success) -> {
                    downloadOrDeleteState = !success;
                    downloadOrDelete.setEnabled(true);
                    downloadOrDelete.setIcon(new ItemIcon(downloadOrDeleteState ? Items.GREEN_CONCRETE : Items.RED_CONCRETE));
                    panel.setBackgroundPainter(null);
                }) );
            } else {
                try {
                    Path file = LanguageCheck.CONFIG_PATH.resolve("ngram")
                            .resolve(screen.curLang.ngram.link.substring(screen.curLang.ngram.link.lastIndexOf("/")+1));
                    Files.delete(file);

                    downloadOrDeleteState = true;
                    downloadOrDelete.setIcon(new ItemIcon(Items.GREEN_CONCRETE));

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    public void update() {
        name.setText(text("§n" + screen.curLang.name));

        if (screen.curLang.ngram == null) {
            sizeOrUnavaliable.setText(text("§lNo Ngrams For This Language"));
            downloadOrDelete.setEnabled(false);
            enabled.setToggle(false);
            enabled.setOnToggle( b -> {
                if (b)
                    enabled.setToggle(false);
            });
            return;
        }

        String beta = screen.curLang.ngram.untested ? " (beta)" : "";
        sizeOrUnavaliable.setText(text("Size: §o" + LanguageOptionsScreenDef.getSizeStr(screen.curLang.ngram.size) + beta));
        downloadOrDelete.setEnabled(true);
        enabled.setToggle(false);
        enabled.setOnToggle( b -> {});
        downloadOrDeleteState = !screen.curLang.ngram.downloaded;
        downloadOrDelete.setIcon(new ItemIcon(downloadOrDeleteState ? Items.GREEN_CONCRETE : Items.RED_CONCRETE));

    }

    private Text text(String text) {
        return new LiteralText(text);
    }

    public WWidget get() {
        panel.setSize(215,160);

        panel.add(downloadOrDelete, panel.getWidth() - 25, 8);

        int y = 0;
        panel.add(name, 10, y += 8);
        panel.add(sizeOrUnavaliable, 10, y += 15);
        panel.add(new WLabel("Uses context to detect errors"), 10, y += 15);
        panel.add(new WLabel("Use only if you have an §lSSD§r!"), 10, y += 11);
        panel.add(new WLabel("Ngram data can be §lvery large§r!"), 10, y += 11);
        panel.add(new WLabel("Can also slow down error checking"), 10, y += 11);
        panel.add(enabled, 10, y += 15);



        return panel;
    }

}
