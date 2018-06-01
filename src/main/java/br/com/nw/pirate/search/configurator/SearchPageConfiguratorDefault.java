package br.com.nw.pirate.search.configurator;

import br.com.nw.pirate.connection.ConnectionConfigurator;
import br.com.nw.pirate.helper.JsoupHelper;
import br.com.nw.pirate.search.url.UrlParameter;
import br.com.nw.pirate.search.result.SearchPageResultManipulator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import br.com.nw.pirate.search.result.SearchPageResults;

import java.util.ArrayList;
import java.util.List;

public class SearchPageConfiguratorDefault implements SearchPageConfigurator<Document> {

    private final List<UrlParameter> parameters = new ArrayList<>();
    private final ConnectionConfigurator connectionConfigurator;
    private SearchPageResultManipulator searchPageResultManipulator;


    public SearchPageConfiguratorDefault(ConnectionConfigurator connectionConfigurator, SearchPageResultManipulator searchPageResultManipulator) {
        this.connectionConfigurator = connectionConfigurator;
        this.searchPageResultManipulator = searchPageResultManipulator;
    }

    @Override
    public Iterable<Document> search(String uri) {

        Connection connection = connectionConfigurator.configure(Jsoup.connect(uri));
        setFormValues(connection);

        Document searchResult = JsoupHelper.post(connection);

        return new SearchPageResults(searchResult, connectionConfigurator, searchPageResultManipulator);

    }

    private void setFormValues(Connection connection) {
        parameters.forEach(attr -> connection.data(attr.getName(), attr.getValue()));
    }

    @Override
    public SearchPageConfigurator<Document> addFormAttrValue(UrlParameter parameter) {
        parameters.add(parameter);
        return this;
    }

    @Override
    public SearchPageConfigurator<Document> addFormAttrValue(List<UrlParameter> parameters) {
        this.parameters.addAll(parameters);
        return this;
    }

}
