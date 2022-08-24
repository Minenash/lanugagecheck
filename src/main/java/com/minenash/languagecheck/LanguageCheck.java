package com.minenash.languagecheck;

import com.minenash.languagecheck.languages.Languages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class LanguageCheck implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("languagecheck");

	public static JLanguageTool langTool;

	public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("languagecheck");
	public static final Path MODS_PATH = FabricLoader.getInstance().getGameDir().resolve("mods");


	@Override
	public void onInitializeClient() {
		try {
			Files.createDirectories(CONFIG_PATH.resolve("ngram"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (var lang : Languages.all) {
			if (FabricLoader.getInstance().isModLoaded("org_languagetool_language-" + lang.code)) {
				lang.available = true;
				Languages.available.add(lang);
				Languages.downloaded.add(lang);
			}
		}

		for (var lang : Languages.available) {
			lang.loadLangTool();
		}

	}

	public boolean isDownloaded(String code) {
		return Files.exists(MODS_PATH.resolve("language-" + code + "-5.6.jar"));
	}

	public static void makeActive(com.minenash.languagecheck.languages.Language lang) {
		if (lang == null) {
			langTool = null;
			Languages.current = null;
			System.out.println("we fucked shit");
		} else {
			lang.loadLangTool();
		}
	}

}
