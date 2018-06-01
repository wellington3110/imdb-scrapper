package br.com.nw.pirate.search.url;

import br.com.nw.pirate.helper.JsoupHelper;
import org.jsoup.nodes.Element;

public class ParametersFromElement implements UrlParameter {

    private String name;
    private String value;

    public ParametersFromElement(Element element) {
        name = JsoupHelper.getAttrName(element);
        value = JsoupHelper.getAttrValue(element);

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
