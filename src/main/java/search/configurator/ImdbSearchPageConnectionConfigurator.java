package search.configurator;

import org.jsoup.Connection;

public class ImdbSearchPageConnectionConfigurator implements ConnectionConfigurator {

    @Override
    public Connection configure(Connection connection) {
        return connection.maxBodySize(5_000_000).timeout(30_0000);
    }
}
