package br.com.nw.pirate.search.configurator;

import br.com.nw.pirate.connection.ConnectionConfigurator;
import br.com.nw.pirate.helpers.JsoupHelper;
import br.com.nw.pirate.search.form.FormModifierInput;
import br.com.nw.pirate.search.exceptions.WasNotFoundSubmitOfSearch;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import br.com.nw.pirate.search.result.ImdbSearchPageResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImdbSearchPageConfigurator implements SearchPageConfigurator<Document> {

    private final Document document;
    private final ConnectionConfigurator connectionConfigurator;
    private final List<FormModifierInput> modifierInputs = new ArrayList<>();
    public static final String SELECT_FORM = "[action=/search/title]";


    public ImdbSearchPageConfigurator(String searchPageUrl, ConnectionConfigurator connectionConfigurator) {

        this.document = JsoupHelper.getDocument(searchPageUrl);
        this.connectionConfigurator = connectionConfigurator;
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
            Document searchResult = connectionConfigurator.configure(formElement.submit()).get();

            return new ImdbSearchPageResults(searchResult, connectionConfigurator);

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
