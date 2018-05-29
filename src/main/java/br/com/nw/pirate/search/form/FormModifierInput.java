package br.com.nw.pirate.search.form;

import org.jsoup.nodes.Element;

public interface FormModifierInput {

    void apply(Element document);
    Element selectInput(Element document);
}
