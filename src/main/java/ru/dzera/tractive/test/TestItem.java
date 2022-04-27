package ru.dzera.tractive.test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestItem {
    public List<Purchase> testProductMap(List<String> codes, Map<String, Description> mapping) {
        if (codes == null || mapping == null) { // we don't use apache utils or something similar for simplification
            return null;
        } else {
            final Map<String, Long> collectedProducts = codes.stream().collect(
                    Collectors.groupingBy(Function.identity(),
                            Collectors.counting()
                    ));
            return collectedProducts.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey)) // according to the expected result in the example data looks sorted
                    .map(e -> new Purchase(mapping.getOrDefault(e.getKey(), TestItem.Description.EMPTY), e.getValue()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * mapping description for products
     */
    protected static class Description {
        public static final Description EMPTY = new Description(0);

        final int version;
        final String edition;

        protected Description(int version, String edition) {
            this.version = version;
            this.edition = edition;
        }

        public Description(int version) {
            this(version, null);
        }

        @Override
        public String toString() {
            return "{\"version\": " + version +
                    (edition == null ? "" : ", \"edition\": \"" + edition + '\"') +
                    '}';
        }
    }

    /**
     * as we added the Description class before we can add another one
     */
    protected static class Purchase {
        final Description description;
        final long quantity;

        protected Purchase(Description description, long quantity) {
            this.description = description;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "{\"version\": " + description.version +
                    (description.edition == null ? "" : ", \"edition\": \"" + description.edition + '\"') +
                    ", \"quantity\": " + quantity +
                    '}';
        }
    }
}
