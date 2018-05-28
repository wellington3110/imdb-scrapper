package br.com.nw.pirate.search.form;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImdbFilterByAscTitleRating implements FormModifierInput {



    @Override
    public void apply(Document document) {

        getFilterByAscTitleRatingElement(document)
                .children()
                .stream()
                .forEach(element -> {
                    if (isAscRatingOption(element)) {
                        element.attr("selected", true);
                    } else {
                        element.attr("selected", false);
                    }
                });
    }

    @Override
    public Element selectInput(Document document) {
        return getFilterByAscTitleRatingElement(document);
    }

    private Element getFilterByAscTitleRatingElement(Document document) {
        return document.getElementsByAttributeValue("name", "sort")
                .first();
    }

    private boolean isAscRatingOption(Element element) {
            return element.attr("value").equals("user_rating,asc");
        }


}
