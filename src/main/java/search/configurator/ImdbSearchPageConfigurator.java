package search.configurator;

import search.form.FormModifierInput;
import consts.ImdbJsoupConsts;
import helpers.JsoupHelper;
import search.exceptions.WasNotFoundSubmitOfSearch;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import search.result.ImdbResultSearchPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImdbSearchPageConfigurator implements SearchPageConfigurator<Document> {

    private Document document;
    private List<FormModifierInput> modifierInputs = new ArrayList<>();
    public static final String SELECT_FORM = "[action=/search/title]";


    public ImdbSearchPageConfigurator(String searchPageUrl) {

        this.document = JsoupHelper.getDocument(searchPageUrl);
    }

    @Override
    public Iterable<Document> search() {
        try {

            FormElement formElement = document.select(SELECT_FORM)
                                              .forms()
                                              .stream()
                                              .findFirst()
                                              .orElseThrow(WasNotFoundSubmitOfSearch::new);

            justLeaveModifierInputs(formElement);
            document = formElement.submit().maxBodySize(ImdbJsoupConsts.MAX_BODY).get();

            return new ImdbResultSearchPage(document);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void justLeaveModifierInputs(FormElement formElement) {
        List<String> names = modifierInputs.stream()
                .map(input -> JsoupHelper.getAttrName(input.selectInput(document)))
                .collect(Collectors.toList());
        
        JsoupHelper.removeInputsFromFormDataExceptWithName(names, formElement);
    }

    @Override
    public SearchPageConfigurator<Document> applyFormModifier(FormModifierInput inputModifier) {
        inputModifier.apply(document);
        modifierInputs.add(inputModifier);
        return this;
    }
    
}
