package br.com.nw.pirate.connection;

import org.jsoup.Connection;

public interface ConnectionConfigurator {

    Connection configure(Connection connection);
}

