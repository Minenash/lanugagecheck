package com.minenash.languagecheck.languages;

import com.minenash.languagecheck.LanguageCheck;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;
import org.languagetool.JLanguageTool;
import org.languagetool.broker.DefaultResourceDataBroker;

public class Language {

	public record Variant(String name, String code, String path) {
		public static Variant of(String name, String code, String path) {
			return new Variant(name, code, "org.languagetool.language." + path);
		}
	}

	public final String name;
	public final String code;
	public final boolean hasSpellCheck;
	public final float size;
	public final String link;
	public final Ngram ngram;
	public final List<Variant> variants;

	public boolean available;

	public Variant currentVariant;
	public List<String> disabledRuleIds;

	public Language(String name, String code, boolean hasSpellCheck, float size, Ngram ngram, List<Variant> variants) {
		this.name = name;
		this.code = code;
		this.hasSpellCheck = hasSpellCheck;
		this.size = size;
		this.link = "https://cdn.modrinth.com/data/GbR1oZVN/versions/" + code + "/language-" + code + "-5.6.jar";
		this.ngram = ngram;
		this.variants = variants;

		available = FabricLoader.getInstance().isModLoaded("org_languagetool_language-" + code);
		currentVariant = variants.get(0);
		disabledRuleIds = new ArrayList<>();
	}

	@SuppressWarnings({"AccessStaticViaInstance", "Convert2MethodRef"})
	public void loadLangTool() {

		Class classLoader = getClass();
		try {
			JLanguageTool.setClassBrokerBroker(qualifiedName ->classLoader.forName(qualifiedName));
			JLanguageTool.setDataBroker(new DefaultResourceDataBroker() {
				@Nullable
				@Override
				public InputStream getAsStream(String path) {
					return classLoader.getResourceAsStream(path);
				}

				@Nullable
				@Override
				public URL getAsURL(String path) {
					return classLoader.getResource(path);
				}

				@Nullable
				@Override
				public List<URL> getAsURLs(String path) {
					try {
						return Collections.list(classLoader.getClassLoader().getResources(path));
					} catch (IOException e) {
						return null;
					}
				}
			});

			LanguageCheck.langTool = new JLanguageTool(org.languagetool.Languages.getOrAddLanguageByClassName(this.currentVariant.path));
			Languages.current = this;
			LanguageCheck.LOGGER.info("[Language Checker]: " + this.name + (this.currentVariant.name() == null ? "" : " (" + this.currentVariant.code() + ")") + " is active");
		} catch (Exception e) {
			LanguageCheck.LOGGER.info("[Language Checker]: " + this.name + " failed to load");
		}
	}
}
