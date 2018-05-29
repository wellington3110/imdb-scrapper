package br.com.nw.pirate.search.configurator;

import br.com.nw.pirate.connection.ConnectionConfigurator;
import br.com.nw.pirate.helper.JsoupHelper;
import br.com.nw.pirate.search.form.FormModifierInput;
import br.com.nw.pirate.search.form.FormPageManipulator;
import br.com.nw.pirate.search.result.SearchPageResultManipulator;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import br.com.nw.pirate.search.result.SearchPageResults;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPageConfiguratorDefault implements SearchPageConfigurator<Document> {

    private final List<FormModifierInput> modifierInputs = new ArrayList<>();
    private final FormPageManipulator formPageManipulator;
    private final ConnectionConfigurator connectionConfigurator;
    private SearchPageResultManipulator searchPageResultManipulator;


    public SearchPageConfiguratorDefault(FormPageManipulator formPageManipulator, ConnectionConfigurator connectionConfigurator, SearchPageResultManipulator searchPageResultManipulator) {

        this.formPageManipulator = formPageManipulator;
        this.connectionConfigurator = connectionConfigurator;
        this.searchPageResultManipulator = searchPageResultManipulator;
    }

    @Override
    public Iterable<Document> search() {

        FormElement formElement = formPageManipulator.getForm();
        modifierInputs.forEach(formModifierInput -> formModifierInput.apply(formElement));

        justLeaveModifierInputs(formElement);

        Connection connection = formPageManipulator.submit(formElement);
        Document searchResult = JsoupHelper.getDocument(connectionConfigurator.configure(connection));

        return new SearchPageResults(searchResult, connectionConfigurator, searchPageResultManipulator);

    }

    private void justLeaveModifierInputs(FormElement formElement) {
        List<String> names = modifierInputs.stream()
                                            .map(input -> JsoupHelper.getAttrName(input.selectInput(formElement)))
                                            .collect(Collectors.toList());
        
        JsoupHelper.removeInputsFromFormDataExceptWithName(names, formElement);
    }

    @Override
    public SearchPageConfigurator<Document> addFormModifier(FormModifierInput inputModifier) {
        modifierInputs.add(inputModifier);
        return this;
    }
    
}
