package br.com.nw.pirate.search.form;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImdbFilterByAscTitleRating implements FormModifierInput {


    @Override
    public void apply(Element element) {

        getFilterByAscTitleRatingElement(element)
                .children()
                .stream()
                .forEach(e -> {
                    if (isAscRatingOption(e)) {
                        e.attr("selected", true);
                    } else {
                        e.attr("selected", false);
                    }
                });
    }

    @Override
    public Element selectInput(Element element) {
        return getFilterByAscTitleRatingElement(element);
    }

    private Element getFilterByAscTitleRatingElement(Element element) {
        return element.getElementsByAttributeValue("name", "sort")
                .first();
    }

    private boolean isAscRatingOption(Element element) {
            return element.attr("value").equals("user_rating,asc");
        }


}
