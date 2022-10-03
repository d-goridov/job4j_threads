package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchIndexTest {

    @Test
    public void whenSearchIndexOfNumber() {
        Integer[] integers = {133, 543, 3, 43, 456, 987, 21, 32, 32, 55, 6, 8, 0, 54};
        Integer rsl = SearchIndex.search(integers, 32);
        assertThat(rsl).isEqualTo(7);
    }

    @Test
    public void whenSearchIndexOfString() {
        String[] strings = {"Oleg", "Igor", "Maria", "Alexey", "Misha", "Nikolay", "Dmitriy",
                "Gennadiy", "Fedor", "Dasha", "Boris", "Sasha"};
        Integer rsl = SearchIndex.search(strings, "Fedor");
        assertThat(rsl).isEqualTo(8);
    }
}