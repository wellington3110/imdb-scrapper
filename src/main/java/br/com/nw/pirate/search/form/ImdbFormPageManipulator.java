package br.com.nw.pirate.search.form;

import br.com.nw.pirate.consts.ImdbConsts;
import br.com.nw.pirate.helper.JsoupHelper;
import br.com.nw.pirate.search.exceptions.WasNotFoundSubmitOfSearch;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

public class ImdbFormPageManipulator implements FormPageManipulator {

    private final Document document;
    private FormElement formElement;

    public ImdbFormPageManipulator() {
        this.document = JsoupHelper.getDocument(ImdbConsts.SEARCH_PAGE_URL);
    }

    @Override
    public FormElement getForm() {
        if(formElement == null) {
            formElement = document.select(ImdbConsts.SELECT_FORM)
                    .forms()
                    .stream()
                    .findFirst()
                    .orElseThrow(WasNotFoundSubmitOfSearch::new);
        }

        return formElement;
    }

    @Override
    public Connection submit() {
        return submit(getForm());
    }

    @Override
    public Connection submit(FormElement formElement) {
        return formElement.submit();
    }

    @Override
    public Document getSearchPage() {
        return document;
    }
}
