import scrappers.imdb.ImdbGenresWebScrapper;

public class main {

    public static void main(String[] args) {
        try {
            new ImdbGenresWebScrapper("/home/wellington/result").scrapper();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
