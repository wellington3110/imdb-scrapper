package br.com.nw.pirate.search.form;

public class ImdbMaximumNumberOfItensFromSearchFormAttr implements FormAttrValue {

    private final String name = "count";
    private final String value = "250";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
