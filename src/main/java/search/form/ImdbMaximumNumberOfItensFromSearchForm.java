package search.form;

import search.exceptions.WasNotFoundMaxOfPageOption;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImdbMaximumNumberOfItensFromSearchForm implements FormModifierInput {

    public static final String SEARCH_COUNT_ID = "search-count";

    @Override
    public void apply(Document document) {
        document.getElementById(SEARCH_COUNT_ID)
                .children()
                .stream()
                .max(this::maxValue)
                .orElseThrow(WasNotFoundMaxOfPageOption::new)
                .attr("selected", true);
    }

    @Override
    public Element selectInput(Document document) {
        return document.getElementById(SEARCH_COUNT_ID);
    }

    private int maxValue(Element e1, Element e2) {
        int v1 = Integer.parseInt(e1.attr("value"));
        int v2 = Integer.parseInt(e2.attr("value"));
        return v1 - v2;
    }
}
