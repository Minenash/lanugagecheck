package com.minenash.languagecheck.languages;

import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

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

}
