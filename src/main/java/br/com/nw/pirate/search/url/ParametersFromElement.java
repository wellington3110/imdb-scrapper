package br.com.nw.pirate.search.form;

import br.com.nw.pirate.helper.JsoupHelper;
import org.jsoup.nodes.Element;

public class AttrValueFromElement implements FormAttrValue {

    private String name;
    private String value;

    public AttrValueFromElement(Element element) {
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
