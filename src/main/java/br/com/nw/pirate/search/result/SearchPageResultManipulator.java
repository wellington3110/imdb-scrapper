package br.com.nw.pirate.search.result;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

public interface SearchPageResultManipulator {

    boolean hasNextPage(Document document);

    Connection nextPageConnection(Document document);
}
