package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    private Sums getSumsForIndex(int[][] matrix, int index) {
        int sumRow = 0;
        int sumCol = 0;
        for (int j = 0; j < matrix.length; j++) {
            sumRow += matrix[index][j];
            sumCol += matrix[j][index];
        }
        return new Sums(sumRow, sumCol);
    }

    public Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = getSumsForIndex(matrix, i);
        }
        return rsl;
    }

    public  Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            rsl[i] = CompletableFuture.supplyAsync(() -> getSumsForIndex(matrix, index)).get();
        }
        return rsl;
    }
}
