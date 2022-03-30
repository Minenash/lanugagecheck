package com.minenash.languagecheck;

import java.util.*;

public class Languages {

    public static final LinkedHashMap<String, List<Entry>> languages = new LinkedHashMap<>();
    public static final LinkedHashMap<String, Boolean> available = new LinkedHashMap<>();

    static {

        languages.put("English", List.of(
                new Entry("English", null, null, path("English")), //Don't
                new Entry("English", "United States", "US", path("AmericanEnglish")),
                new Entry("English", "Great Britain", "GB", path("BritishEnglish")),
                new Entry("English", "Australia", "AU", path("AustralianEnglish")),
                new Entry("English", "Canada", "CA", path("CanadianEnglish")),
                new Entry("English", "New Zealand", "NZ", path("NewZealandEnglish")),
                new Entry("English", "South Africa", "SA", path("SouthAfricanEnglish"))
        ));

        languages.put("German", List.of(
                new Entry("German", null, null, path("German")), //Don't
                new Entry("German", "Austria", "AT", path("AustrianGerman")),
                new Entry("German", "Germany", "DE", path("GermanyGerman")),
                new Entry("German", "Swiss", "CH", path("SwissGerman"))
        ));

        languages.put("Spanish", List.of(
                new Entry("Spanish", null, null, path("Spanish")),
                new Entry("Spanish", "voseo", "ES", path("SpanishVoseo"))
        ));

        languages.put("Portuguese", List.of(
                new Entry("Portuguese", null, null, path("Portuguese")),
                new Entry("Portuguese", "Brazil", "BR", path("BrazilianPortuguese")),
                new Entry("Portuguese", "Portugal", "PT", path("PortugalPortuguese")),
                new Entry("Portuguese", "Angola (preAO)", "AO", path("AngolaPortuguese")),
                new Entry("Portuguese", "Mozambique (pAO)", "MZ", path("MozambiquePortuguese"))
        ));

        languages.put("Dutch", List.of(
                new Entry("Dutch", null, null, path("Dutch")),
                new Entry("Dutch", "Belgium", "BE", path("BelgianDutch"))
        ));

        noVariants("Ukrainian");
        noVariants("French");
        noVariants("Russian");
        noVariants("Irish");
        noVariants("Arabic");
        noVariants("Galician");
        noVariants("Persian");
        noVariants("Polish");
        noVariants("Greek");
        noVariants("Khmer");
        noVariants("Breton");
        noVariants("Italian");
        noVariants("Romanian");
        noVariants("Chinese");
        noVariants("Esperanto");
        noVariants("Slovak");
        noVariants("Swedish");
        noVariants("Asturian");
        noVariants("Belarusian");
        noVariants("Danish");
        noVariants("Japanese");
        noVariants("Slovenian");
        noVariants("Tagalog");
        noVariants("Tamil");

    }

    private static void noVariants(String lang) {
        languages.put(lang, Collections.singletonList(new Entry(lang, null, null, "org.languagetool.language." + lang)));
    }

    private static String path(String lang) {
        return "org.languagetool.language." + lang;
    }

    public static record Entry(String language, String variant, String variant_short, String classPath) {

        public String toString() {
            return variant == null ? language : language + " (" + variant + ")";
        }

        public String toShortString() {
            return variant == null ? language : language + " (" + variant_short + ")";
        }

    }


}
