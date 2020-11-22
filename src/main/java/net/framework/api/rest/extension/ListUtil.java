package net.framework.api.rest.extension;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * List Utility.
 */
public final class ListUtil {

    /** Default constructor */
    private ListUtil() {}

    /**
     * Return Optional
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Optional<List<T>> ofPresentable(List<T> list) {
        return Optional.ofNullable(list).filter(l -> l.size() != 0);
    }

    /**
     * Return Optional wrapping a list full of items.
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Optional<List<T>> ofAllPresentable(List<T> list) {
        return ofPresentable(list)
                .filter(l -> l.stream().filter(Objects::isNull).count() == 0);
    }
}
