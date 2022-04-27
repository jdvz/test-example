package ru.dzera.tractive.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestItemTest {
    private TestItem classUnderTest = new TestItem();

    /**
     * the 'mapping' parameter in the example looks suspiciously like json, but let's pretend that we're transformed it
     * @param codes     list of string codes
     * @param mapping   mapping object
     */
    @ParameterizedTest
    @MethodSource("provideData")
    void testProductMap(int expectedSize, int expectedQuantityForTheFirst, List<String> codes, Map<String, TestItem.Description> mapping) {
        var res = classUnderTest.testProductMap(codes, mapping);
        assertEquals(expectedSize, res != null ? res.size() : 0);
        assertEquals(expectedQuantityForTheFirst, res != null ? res.get(0).quantity : 0);
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(0, 0, null, null),
                Arguments.of(3, 1, Arrays.asList("CVCD", "SDFD", "DDDF", "SDFD"), new HashMap<>() {{
                    put("CVCD", new TestItem.Description(1, "X"));
                    put("SDFD", new TestItem.Description(1));
                    put("DDDF", new TestItem.Description(2, "Z"));
                }}),
                Arguments.of(3, 4, Arrays.asList("CVCD", "SDFD", "DDDF", "CVCD", "SDFD", "CVCD", "SDFD", "CVCD"), new HashMap<>() {{
                    put("CVCD", new TestItem.Description(1, "X"));
                    put("SDFD", new TestItem.Description(1));
                    put("DDDF", new TestItem.Description(2, "Z"));
                }}),
                Arguments.of(2, 1, Arrays.asList("CVCD", "SDFD"), new HashMap<>() {{
                    put("CVCD", new TestItem.Description(1, "X"));
                    put("SDFD", new TestItem.Description(1));
                    put("DDDF", new TestItem.Description(2, "Z"));
                }})
        );
    }




}