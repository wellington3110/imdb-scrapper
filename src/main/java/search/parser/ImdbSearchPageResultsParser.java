package search.parser;

import org.jsoup.nodes.Document;
import scrappers.model.ImdbTitle;
import search.configurator.SearchPageConfigurator;

import java.util.function.Consumer;

public class ImdbSearchPageResultsParser implements PageParser<ImdbTitle> {

    private int iterations = 0;
    private final SearchPageConfigurator<Document> configurator;

    public static final int MAX_OF_TITLE = 500;
    public static final String LISTER_FILTER_CLASS = "lister-item";


    public ImdbSearchPageResultsParser(SearchPageConfigurator<Document> configurator) {

        this.configurator = configurator;
    }

    @Override
    public void parser(Consumer<ImdbTitle> callback) {

        Iterable<Document> search = configurator.search();

        for (Document document: search) {
            document.getElementsByClass(LISTER_FILTER_CLASS).stream()
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
        return iterations == MAX_OF_TITLE;
    }

    private long maxOfIterations() {
        return MAX_OF_TITLE - iterations;
    }

}
