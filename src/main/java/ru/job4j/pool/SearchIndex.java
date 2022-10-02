package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int indexFrom;
    private final int indexTo;
    private final T target;

    public SearchIndex(T[] array, int from, int to, T element) {
        this.array = array;
        this.indexFrom = from;
        this.indexTo = to;
        target = element;
    }


    @Override
    protected Integer compute() {
        if (indexTo - indexFrom <= 10) {
            return findIndex();
        }
        int mid = (indexTo - indexFrom) / 2;
        SearchIndex<T> leftSearch = new SearchIndex<>(array, indexFrom, mid, target);
        SearchIndex<T> rightSearch = new SearchIndex<>(array, mid + 1, indexTo, target);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left > right ? left : right;
    }

    private int findIndex() {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer[] integers = {133, 543, 3, 43, 456, 987, 21, 32, 32, 55, 6, 8, 0, 54};
        Integer rsl = forkJoinPool.invoke(new SearchIndex<>(integers, 0, integers.length - 1, 32));
        System.out.println(rsl);

        String[] strings = {"Oleg", "Igor", "Maria", "Alexey", "Misha", "Nikolay", "Dmitriy",
                           "Gennadiy", "Fedor", "Dasha", "Boris", "Sasha"};
        Integer res = forkJoinPool.invoke(new SearchIndex<>(strings, 0, strings.length - 1, "Fedor"));
        System.out.println(res);
    }
}
