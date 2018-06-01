package br.com.nw.pirate.helper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
public class JsoupHelper {

    /*just to not handle exception*/
    public static Document getDocument(Connection connect) {
        try {
            return connect.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Document getDocument(String URL) {
        return getDocument(Jsoup.connect(URL));
    }

    public static String getAttrName(Element element) {
        return element.attr("name");
    }

    public static String getAttrId(Element element) {
        return element.attr("id");
    }

    public static Document post(Connection connection) {
        try {
            return connection.post();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAttrValue(Element element) {
        return element.attr("value");
    }
}
