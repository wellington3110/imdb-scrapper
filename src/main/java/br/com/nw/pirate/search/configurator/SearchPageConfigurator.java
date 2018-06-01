package br.com.nw.pirate.search.configurator;

import br.com.nw.pirate.search.url.UrlParameter;

import java.util.List;

public interface SearchPageConfigurator<T> {

    Iterable<T> search(String URL);

    SearchPageConfigurator<T> addFormAttrValue(UrlParameter parameter);

    SearchPageConfigurator<T> addFormAttrValue(List<UrlParameter> parameters);
}
