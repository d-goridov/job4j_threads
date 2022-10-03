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
        return Math.max(left, right);
    }

    private int findIndex() {
        int rsl = -1;
        for (int i = indexFrom; i <= indexTo; i++) {
            if (array[i].equals(target)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> Integer search(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndex<>(array, 0, array.length - 1, target));
    }
}
