package br.com.nw.pirate.search.form;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImdbFilterByGenre implements FormModifierInput {

    private String genreElementId;

    public ImdbFilterByGenre(String genreElementId) {
        this.genreElementId = genreElementId;
    }

    @Override
    public void apply(Element element) {
        element.getElementById(genreElementId).attr("checked", true);
    }

    @Override
    public Element selectInput(Element element) {
        return element.getElementById(genreElementId);
    }
}
