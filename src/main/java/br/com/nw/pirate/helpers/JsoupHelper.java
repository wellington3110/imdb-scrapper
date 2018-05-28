package br.com.nw.pirate.helpers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void removeInputsFromFormDataExceptWithName(List<String> names, FormElement formElement) {

        List<Connection.KeyVal> inputsWillBeRemoved = formElement
                .formData()
                .stream()
                .filter(keyVal -> !names.contains(keyVal.key()))
                .collect(Collectors.toList());

        Elements elementsFromForm = formElement.elements();

        inputsWillBeRemoved.forEach(keyVal -> formElement.select("[name=" + keyVal.key() + "]")
                                                         .forEach(e -> elementsFromForm.remove(e)));


    }

    public static String getAttrId(Element element) {
        return element.attr("id");
    }
}
