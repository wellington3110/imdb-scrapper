package br.com.nw.pirate.search.form;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

public interface FormPageManipulator {

    FormElement getForm();

    Connection submit();

    Connection submit(FormElement formElement);

    Document getSearchPage();
}
