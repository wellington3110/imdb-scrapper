package br.com.nw.pirate.search.url;


public class ImdbAscTitleRatingParameter implements UrlParameter {

    private final String name = "sort";
    private final String value = "user_rating,asc";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
