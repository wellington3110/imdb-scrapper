package br.com.nw.pirate.search.result;

import br.com.nw.pirate.consts.ImdbConsts;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ImdbSearchPageResultManipulator implements SearchPageResultManipulator {

    @Override
    public boolean hasNextPage(Document document) {
        return !getNextHrefElement(document).isEmpty();
    }

    private Elements getNextHrefElement(Document document) {
        return document.getElementsByClass(ImdbConsts.NEXT_HREF_SEARCH_PAGE_CLASS);
    }

    @Override
    public Connection nextPageConnection(Document document) {
        if(hasNextPage(document)) {
            String URL = getNextHrefElement(document).stream()
                                                    .findFirst()
                                                    .orElseThrow(() -> new RuntimeException("Error to acess next page of search"))
                                                    .attr("abs:href");

            return Jsoup.connect(URL);
        }

        throw new RuntimeException("Error to acess next page of search");
    }


}
