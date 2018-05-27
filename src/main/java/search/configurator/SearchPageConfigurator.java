package search.configurator;

import search.form.FormModifierInput;

public interface SearchPageConfigurator<T> {

    Iterable<T> search();

    SearchPageConfigurator<T> applyFormModifier(FormModifierInput inputModifier);
}
