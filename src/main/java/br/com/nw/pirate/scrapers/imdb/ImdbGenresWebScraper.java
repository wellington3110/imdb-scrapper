package br.com.nw.pirate.scrapers.imdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.com.nw.pirate.json.JsonBufferedWriter;
import br.com.nw.pirate.connection.ConnectionConfigurator;
import br.com.nw.pirate.connection.ImdbSearchPageConnectionConfigurator;
import br.com.nw.pirate.search.configurator.SearchPageConfigurator;
import br.com.nw.pirate.search.form.ImdbFilterByAscTitleRating;
import br.com.nw.pirate.search.form.ImdbFilterByGenre;
import br.com.nw.pirate.search.form.ImdbMaximumNumberOfItensFromSearchForm;
import br.com.nw.pirate.search.parser.ImdbSearchPageResultsParser;
import br.com.nw.pirate.helpers.JsoupHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import br.com.nw.pirate.search.configurator.ImdbSearchPageConfigurator;

import java.util.List;
import java.util.stream.Collectors;

public class ImdbGenresWebScraper {

    private Document document;
    private String path = "";
    private Gson gson = new GsonBuilder().create();
    private final String SEARCH_PAGE_URL = "https://www.imdb.com/search/title";
    private final ConnectionConfigurator connectionConfigurator = new ImdbSearchPageConnectionConfigurator();

    public ImdbGenresWebScraper() {}

    public ImdbGenresWebScraper(String path) {
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
                        new ImdbSearchPageResultsParser(configuratorAndWriter.configurator).parser(configuratorAndWriter.jsonWritter::writeAndBreakLine));

        searchPagesAndWriters.forEach(configuratorAndWriter -> configuratorAndWriter.jsonWritter.close());

    }

    private SearchPageConfiguratorAndWriter newSearchPageConfiguratorAndJsonArrayWriter(Element element) {
        return new SearchPageConfiguratorAndWriter(newJsonBuferredWritter(element), newSearchPageExecutor(element));
    }

    private JsonBufferedWriter newJsonBuferredWritter(Element element) {
        return JsonBufferedWriter.from(gson, getFilePath(element));
    }

    private String getFilePath(Element element) {
        return path + "/" + element.attr("value") + ".jsonl";
    }

    private SearchPageConfigurator<Document> newSearchPageExecutor(Element element) {
        return new ImdbSearchPageConfigurator(SEARCH_PAGE_URL, connectionConfigurator).applyFormModifier(new ImdbMaximumNumberOfItensFromSearchForm())
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
