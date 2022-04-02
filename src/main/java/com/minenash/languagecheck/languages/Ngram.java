package com.minenash.languagecheck.languages;

public class Ngram {

    public final float size;
    public final boolean untested;
    public final String link;

    public boolean downloaded;

    public Ngram(float size, String file) {
        this.size = size;
        this.untested = file.startsWith("untested/");
        this.link = "https://languagetool.org/download/ngram-data/" + file;

        this.downloaded = false;
    }

}
