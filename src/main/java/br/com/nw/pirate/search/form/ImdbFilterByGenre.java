package br.com.nw.pirate.search.form;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImdbFilterByGenre implements FormModifierInput {

    private String genreElementId;

    public ImdbFilterByGenre(String genreElementId) {
        this.genreElementId = genreElementId;
    }

    @Override
    public void apply(Document document) {
        document.getElementById(genreElementId).attr("checked", true);
    }

    @Override
    public Element selectInput(Document document) {
        return document.getElementById(genreElementId);
    }
}
