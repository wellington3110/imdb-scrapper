package scrappers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.JsonBufferedWriter;
import search.configurator.SearchPageConfigurator;
import search.form.ImdbFilterByAscTitleRating;
import search.form.ImdbFilterByGenre;
import search.form.ImdbMaximumNumberOfItensFromSearchForm;
import search.parser.ImdbSearchPageResultsParser;
import helpers.JsoupHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import search.configurator.ImdbSearchPageConfigurator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
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
                .map(this::newSearchPageConfiguratorAndWriter)
                .collect(Collectors.toList());

        searchPagesAndWriters
                .parallelStream()
                .forEach(configuratorAndWriter ->
                        new ImdbSearchPageResultsParser(configuratorAndWriter.configurator).parser(configuratorAndWriter.jsonWritter::write));

        searchPagesAndWriters.forEach(configuratorAndWriter -> configuratorAndWriter.jsonWritter.close());

    }

    private SearchPageConfiguratorAndWriter newSearchPageConfiguratorAndWriter(Element element) {
        return new SearchPageConfiguratorAndWriter(newJsonBuferredWritter(element), newSearchPageExecutor(element));
    }

    private JsonBufferedWriter newJsonBuferredWritter(Element element) {
        File file = new File(getFilePath(element));
        try {
            return new JsonBufferedWriter(gson, new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFilePath(Element element) {
        return path + "/" + element.attr("value");
    }

    private SearchPageConfigurator<Document> newSearchPageExecutor(Element element) {
        return new ImdbSearchPageConfigurator(SEARCH_PAGE_URL).applyFormModifier(new ImdbMaximumNumberOfItensFromSearchForm())
                                                          .applyFormModifier(new ImdbFilterByGenre(JsoupHelper.getAttrId(element)))
                                                          .applyFormModifier(new ImdbFilterByAscTitleRating());
    }



    public static class SearchPageConfiguratorAndWriter {

        private final JsonBufferedWriter jsonWritter;
        private final SearchPageConfigurator<Document> configurator;

        public SearchPageConfiguratorAndWriter(JsonBufferedWriter json, SearchPageConfigurator<Document> configurator) {
            this.jsonWritter = json;
            this.configurator = configurator;
        }

    }

}
