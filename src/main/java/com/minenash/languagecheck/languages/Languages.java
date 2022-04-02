package com.minenash.languagecheck.languages;

import java.util.*;

import com.minenash.languagecheck.languages.Language.Variant;

@SuppressWarnings("SameParameterValue")
public class Languages {

    public static Language current;
    public static final List<Language> all;
    public static List<Language> available = new ArrayList<>();
    public static List<Language> downloaded = new ArrayList<>();

    static {
        all = new ArrayList<>(30);
        register("English", "en", true, 6200.0F,
                new Ngram(8300_000.0F, "ngrams-en-20150817.zip"),
                Variant.of("United States", "US", "AmericanEnglish"),
                Variant.of("Great Britain", "GB", "BritishEnglish"),
                Variant.of("Australia", "AU", "AustralianEnglish"),
                Variant.of("Canada", "CA", "CanadianEnglish"),
                Variant.of("New Zealand", "NZ", "NewZealandEnglish"),
                Variant.of("South Africa", "SA", "SouthAfricanEnglish"));

        register("German", "de", true, 17600.0F,
                new Ngram(1700_000.0F, "ngrams-de-20150819.zip"),
                Variant.of("Austria", "AT", "AustrianGerman"),
                Variant.of("Germany", "DE", "GermanyGerman"),
                Variant.of("Swiss", "CH", "SwissGerman"));

        register("Spanish", "es", true, 405.4F,
                new Ngram(1600_000.0F, "ngrams-es-20150915.zip"),
                Variant.of("no voseo", null, "Spanish"),
                Variant.of("voseo", "vos", "SpanishVoseo"));

        register("Portuguese", "pt", true, 4300.0F,
                Variant.of("Brazil", "BR", "BrazilianPortuguese"),
                Variant.of("Portugal", "PT", "PortugalPortuguese")
//                Variant.of("Angola (preAO)", "AO", "AngolaPortuguese"),
//                Variant.of("Mozambique (pAO)", "MZ", "MozambiquePortuguese")
                );

        register("Dutch", "nl", true, 36100.0F,
                new Ngram(1100_000.0F, "ngrams-nl-20181229.zip"),
                Variant.of("Netherlands", "NL", "Dutch"),
                Variant.of("Belgian", "BE", "BelgianDutch"));

        register("Ukrainian", "uk", true, 571.5F);
//        register("French", "fr", true, 0F, Ngram.of(1700_000.0F, "ngrams-fr-20150913.zip"));
        register("Russian", "ru", true, 4400.0F, new Ngram(2200_000.0F, "untested/ngram-ru-20150914.zip"));
        register("Irish", "ga", true, 806.5F);
        register("Arabic", "ar", true, 12500.0F);
        register("Galician", "gl", true, 4200.0F);
        register("Persian", "fa", false, 89.8F);
        register("Polish", "pl", true, 5000.0F);
        register("Greek", "el", true, 580.3F);
        register("Khmer", "km", true, 560.1F);
        register("Breton", "br", true, 1700.0F);
        register("Italian", "it", true, 845.2F, new Ngram(1000_000.0F, "untested/ngram-it-20150915.zip"));
        register("Romanian", "ro", true, 1400.0F);
        register("Chinese", "zh", false, 326.8F, new Ngram(1600_000.0F, "untested/ngram-zh-20150916.zip"));
        register("Esperanto", "eo", true, 289.2F);
        register("Slovak", "sk", true, 2500.0F);
        register("Swedish", "sv", true, 1000.0F);
        register("Asturian", "ast", true, 423.7F);
        register("Belarusian", "be", true, 579.4F);
        register("Danish", "da", true, 1100.0F);
        register("Japanese", "ja", false, 52.4F);
        register("Slovenian", "sl", true, 265.5F);
        register("Tagalog", "tl", true, 141.4F);
        register("Tamil", "ta", false, 61.3F);

    }

    private static void register(String lang, String code, boolean spell, float size) {
        all.add(new Language(lang, code, spell, size, null, Collections.singletonList(Variant.of(null, null, lang))));
    }
    private static void register(String lang, String code, boolean spell, float size, Language.Variant... variants) {
        all.add(new Language(lang, code, spell, size, null, Arrays.asList(variants)));
    }

    private static void register(String lang, String code, boolean spell, float size, Ngram ngram) {
        all.add(new Language(lang, code, spell, size, ngram, Collections.singletonList(Variant.of(null, null, lang))));
    }
    private static void register(String lang, String code, boolean spell, float size, Ngram ngram, Language.Variant... variants) {
        all.add(new Language(lang, code, spell, size, ngram, Arrays.asList(variants)));
    }

}
