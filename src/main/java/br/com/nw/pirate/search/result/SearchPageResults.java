package br.com.nw.pirate.search.result;

import br.com.nw.pirate.helper.JsoupHelper;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import br.com.nw.pirate.connection.ConnectionConfigurator;

import java.util.Iterator;

public class SearchPageResults implements Iterable<Document> {

    private final Document document;
    private ConnectionConfigurator connectionConfigurator;
    private SearchPageResultManipulator searchPageResultManipulator;

    public SearchPageResults(Document document, ConnectionConfigurator connectionConfigurator, SearchPageResultManipulator searchPageResultManipulator) {
        this.document = document;
        this.connectionConfigurator = connectionConfigurator;
        this.searchPageResultManipulator = searchPageResultManipulator;
    }

    @Override
    public Iterator<Document> iterator() {
        return new ImdbResultSearchPageIterator(document, connectionConfigurator, searchPageResultManipulator);
    }

    public static class ImdbResultSearchPageIterator implements Iterator<Document> {

        private Document document;
        private final ConnectionConfigurator connectionConfigurator;
        private final SearchPageResultManipulator searchPageResultManipulator;

        private boolean isFirstIteration = true;


        public ImdbResultSearchPageIterator(Document document, ConnectionConfigurator connectionConfigurator, SearchPageResultManipulator searchPageResultManipulator) {
            this.document = document;
            this.connectionConfigurator = connectionConfigurator;
            this.searchPageResultManipulator = searchPageResultManipulator;
        }

        @Override
        public boolean hasNext() {

            return searchPageResultManipulator.hasNextPage(document);
        }

        @Override
        public Document next() {

            if (isFirstIteration) {
                isFirstIteration = false;
                return document;
            }

            Connection connection = searchPageResultManipulator.nextPageConnection(document);
            document = JsoupHelper.getDocument(connectionConfigurator.configure(connection));
            return document;
        }

    }
}
