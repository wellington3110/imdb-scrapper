package scrappers.imdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.JsonArrayBufferedWriter;
import search.configurator.SearchPageConfigurator;
import search.form.ImdbFilterByAscTitleRating;
import search.form.ImdbFilterByGenre;
import search.form.ImdbMaximumNumberOfItensFromSearchForm;
import search.parser.ImdbSearchPageResultsParser;
import helpers.JsoupHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import search.configurator.ImdbSearchPageConfigurator;

import java.util.List;
import java.util.stream.Collectors;

public class ImdbGenresWebScrapper {

    private Document document;
    private String path = "";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String SEARCH_PAGE_URL = "https://www.imdb.com/search/title";

    public ImdbGenresWebScrapper() {}

    public ImdbGenresWebScrapper(String path) {
        this.path = path;
    }

    public void scrapper() {

        document =  JsoupHelper.getDocument(SEARCH_PAGE_URL);

        List<SearchPageConfiguratorAndWriter> searchPagesAndWriters = document.getElementsByAttributeValueContaining("id", "genres")
                                                                              .parallelStream()
                                                                              .map(this::newSearchPageConfiguratorAndJsonArrayWriter)
                                                                              .collect(Collectors.toList());

        searchPagesAndWriters
                .parallelStream()
                .forEach(configuratorAndWriter ->
                        new ImdbSearchPageResultsParser(configuratorAndWriter.configurator).parser(configuratorAndWriter.jsonWritter::write));

        searchPagesAndWriters.forEach(configuratorAndWriter -> configuratorAndWriter.jsonWritter.close());

    }

    private SearchPageConfiguratorAndWriter newSearchPageConfiguratorAndJsonArrayWriter(Element element) {
        return new SearchPageConfiguratorAndWriter(newJsonArrayBuferredWritter(element), newSearchPageExecutor(element));
    }

    private JsonArrayBufferedWriter newJsonArrayBuferredWritter(Element element) {
        return JsonArrayBufferedWriter.from(gson, getFilePath(element));
    }

    private String getFilePath(Element element) {
        return path + "/" + element.attr("value") + ".json";
    }

    private SearchPageConfigurator<Document> newSearchPageExecutor(Element element) {
        return new ImdbSearchPageConfigurator(SEARCH_PAGE_URL).applyFormModifier(new ImdbMaximumNumberOfItensFromSearchForm())
                                                          .applyFormModifier(new ImdbFilterByGenre(JsoupHelper.getAttrId(element)))
                                                          .applyFormModifier(new ImdbFilterByAscTitleRating());
    }



    public static class SearchPageConfiguratorAndWriter {

        private final JsonArrayBufferedWriter jsonWritter;
        private final SearchPageConfigurator<Document> configurator;

        public SearchPageConfiguratorAndWriter(JsonArrayBufferedWriter json, SearchPageConfigurator<Document> configurator) {
            this.jsonWritter = json;
            this.configurator = configurator;
        }

    }

}
