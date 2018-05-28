import br.com.nw.pirate.scrapers.imdb.ImdbGenresWebScraper;

public class main {

    public static void main(String[] args) {
        try {
            new ImdbGenresWebScraper("/home/wellington/result").scrapper();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
