package br.com.nw.pirate.search.parser;

import br.com.nw.pirate.consts.ImdbConsts;
import br.com.nw.pirate.scrapers.imdb.model.ImdbTitle;
import br.com.nw.pirate.search.configurator.SearchPageConfigurator;
import org.jsoup.nodes.Document;

import java.util.function.Consumer;

public class ImdbSearchPageResultsParser implements PageParser<ImdbTitle> {

    private int iterations = 0;
    private final SearchPageConfigurator<Document> configurator;

    private final int maxOfTitle = 500;


    public ImdbSearchPageResultsParser(SearchPageConfigurator<Document> configurator) {

        this.configurator = configurator;
    }

    @Override
    public void parser(Consumer<ImdbTitle> callback) {

        Iterable<Document> search = configurator.search();

        for (Document document: search) {
            document.getElementsByClass(ImdbConsts.LISTER_FILTER_CLASS).stream()
                    .limit(maxOfIterations())
                    .forEach(titleElement -> {
                        iterations ++;
                        callback.accept(new ImdbTitle(titleElement));
                    });

            if (hasMaxOfTitles()) {
                break;
            }

        }

    }

    private boolean hasMaxOfTitles() {
        return iterations == maxOfTitle;
    }

    private long maxOfIterations() {
        return maxOfTitle - iterations;
    }

}
