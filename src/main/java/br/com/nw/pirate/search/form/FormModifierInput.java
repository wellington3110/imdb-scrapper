package br.com.nw.pirate.search.form;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface FormModifierInput {

    void apply(Document document);
    Element selectInput(Document document);
}
