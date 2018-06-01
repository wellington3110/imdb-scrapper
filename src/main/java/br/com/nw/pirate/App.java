package br.com.nw.pirate;

import br.com.nw.pirate.scrapers.imdb.ImdbGenresWebScraper;

public class App {

    public static void main(String[] args) {
        System.out.println("Starting...");
        try {
            System.out.println("Scraping IMDB site, please wait!");
            new ImdbGenresWebScraper(getPath(args)).scrapper();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Done!!");
    }

    private static String getPath(String[] args) {
        return args.length > 0 ? args[0] : "./";
    }
}
