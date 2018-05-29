package br.com.nw.pirate.search.parser;

import br.com.nw.pirate.scrapers.imdb.model.ImdbTitle;
import br.com.nw.pirate.search.result.SearchPageResultManipulator;

import java.util.function.Consumer;

public interface PageParser<T> {


    void parser(Consumer<ImdbTitle> callback);
}
