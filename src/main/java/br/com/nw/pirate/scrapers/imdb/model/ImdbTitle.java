package br.com.nw.pirate.scrapers.imdb.model;


import org.jsoup.nodes.Element;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImdbTitle {

    public static final String NO_DESCRIPTION = "add a plot";
    private Double rating;
    private Integer totalVotes;

    private String yearDescription;
    private String title;
    private String description;

    private List<String> genres;

    public ImdbTitle(Element titleElement) {

        try {

            rating = extractRating(titleElement);
            totalVotes = extractTotalVotes(titleElement);
            yearDescription = extractYear(titleElement);
            title = extractTitle(titleElement);
            description = extractDescription(titleElement);
            genres = extractGenres(titleElement);

        } catch(Exception e) {
            throw new RuntimeException("error to convert from element html to ImdbTitle", e);
        }

    }

    private List<String> extractGenres(Element titleElement) {
        return Arrays.asList( titleElement.getElementsByClass("genre").text().split(","))
                     .stream()
                     .map(String::trim)
                     .collect(Collectors.toList());
    }

    private String extractTitle(Element titleElement) {
        return titleElement.getElementsByClass("lister-item-header").first().getElementsByTag("a").text();
    }

    private String extractYear(Element titleElement) {
        return titleElement.getElementsByClass("lister-item-yearDescription").text();
    }

    private String extractDescription(Element titleElement) {
        return Optional.of(titleElement.select(".lister-item-content > .text-muted")
                .last().text())
                .map(description -> description.toLowerCase().equals(NO_DESCRIPTION) ? "" : description)
                .orElse("");
    }

    private Double extractRating(Element titleElement) {
        return Double.parseDouble(titleElement.getElementsByClass("ratings-imdb-rating")
                                              .attr("data-value"));
    }

    private Integer extractTotalVotes(Element titleElement) {
        return titleElement.select("[name=nv]")
                    .stream()
                    .findFirst()
                    .map(this::convertTotalVotesToInteger)
                    .orElse(0);
    }

    private Integer convertTotalVotesToInteger(Element element) {
        return Integer.parseInt(element.text().replace(",", ""));
    }

    public Double getRating() {
        return rating;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public String getYearDescription() {
        return yearDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {
        return Collections.unmodifiableList(genres);
    }

}
