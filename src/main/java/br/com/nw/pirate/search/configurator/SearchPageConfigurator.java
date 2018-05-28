package br.com.nw.pirate.search.configurator;

import br.com.nw.pirate.search.form.FormModifierInput;

public interface SearchPageConfigurator<T> {

    Iterable<T> search();

    SearchPageConfigurator<T> applyFormModifier(FormModifierInput inputModifier);
}
