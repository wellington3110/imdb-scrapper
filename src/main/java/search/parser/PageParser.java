package search.parser;

import java.util.function.Consumer;

public interface PageParser<T> {

    void parser(Consumer<T> callback);
}
