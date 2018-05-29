package br.com.nw.pirate.search.form;

import br.com.nw.pirate.consts.ImdbConsts;
import br.com.nw.pirate.search.exceptions.WasNotFoundMaxOfPageOption;
import org.jsoup.nodes.Element;

public class ImdbMaximumNumberOfItensFromSearchForm implements FormModifierInput {

    @Override
    public void apply(Element document) {
        document.getElementById(ImdbConsts.SEARCH_COUNT_ID)
                .children()
                .stream()
                .max(this::maxValue)
                .orElseThrow(WasNotFoundMaxOfPageOption::new)
                .attr("selected", true);
    }

    @Override
    public Element selectInput(Element document) {
        return document.getElementById(ImdbConsts.SEARCH_COUNT_ID);
    }

    private int maxValue(Element e1, Element e2) {
        int v1 = Integer.parseInt(e1.attr("value"));
        int v2 = Integer.parseInt(e2.attr("value"));
        return v1 - v2;
    }
}
