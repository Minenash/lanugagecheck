package com.minenash.languagecheck;

import com.minenash.languagecheck.troblesome.LanguageClassLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.languagetool.JLanguageTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class LanguageCheck implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("languagecheck");

	public static JLanguageTool langTool;
	public static final Path LANGUAGES_PATH = FabricLoader.getInstance().getConfigDir().resolve("languagecheck");


	@Override
	public void onInitializeClient() {

		LanguageClassLoader.load(FabricLoader.getInstance().getConfigDir().resolve("languagecheck/language-en-5.6.jar"));
		try {
			System.out.println(ClassLoader.getSystemClassLoader().loadClass("org.languagetool.language.AmericanEnglish"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
