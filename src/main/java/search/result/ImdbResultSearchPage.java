package search.result;

import consts.ImdbJsoupConsts;
import helpers.JsoupHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.Optional;

public class ImdbResultSearchPage implements Iterable<Document> {

    private final Document document;

    public ImdbResultSearchPage(Document document) {
        this.document = document;
    }

    @Override
    public Iterator<Document> iterator() {
        return new ImdbResultSearchPageIterator(document);
    }

    public static class ImdbResultSearchPageIterator implements Iterator<Document> {

        private Elements nextHrefCaught;
        private final Document document;

        private boolean isFirstIteration = true;
        private boolean wasCaughtNextHrefInThisPage = false;

        public static final String NEXT_HREF_SEARCH_PAGE_CLASS = "lister-page-next";

        public ImdbResultSearchPageIterator(Document document) {
            this.document = document;

        }

        @Override
        public boolean hasNext() {
            if (wasCaughtNextHrefInThisPage) {
                return hasHrefNext();
            }

            wasCaughtNextHrefInThisPage = true;
            nextHrefCaught = catchNextHref();

            return hasHrefNext();
        }

        private Elements catchNextHref() {
            return document.getElementsByClass(NEXT_HREF_SEARCH_PAGE_CLASS);
        }

        private boolean hasHrefNext() {
            return !nextHrefCaught.isEmpty();
        }

        @Override
        public Document next() {

            if (isFirstIteration) {
                isFirstIteration = false;
                return document;
            }

            if (wasCaughtNextHrefInThisPage) {
                return nextPage(nextHrefCaught);
            }

            wasCaughtNextHrefInThisPage = false;
            return nextPage(catchNextHref());
        }

        private Document nextPage(Elements caughtNextHref) {
            String URL = Optional.ofNullable(caughtNextHref.first())
                    .orElseThrow(ArrayIndexOutOfBoundsException::new)
                    .attr("abs:href");

            return JsoupHelper.getDocument(Jsoup.connect(URL).maxBodySize(ImdbJsoupConsts.MAX_BODY));

        }
    }
}
