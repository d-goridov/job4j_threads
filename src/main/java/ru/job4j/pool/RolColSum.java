package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {

        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    private static Sums getSumsForIndex(int[][] matrix, int index) {
        int sumRow = 0;
        int sumCol = 0;
        for (int j = 0; j < matrix.length; j++) {
            sumRow += matrix[index][j];
            sumCol += matrix[j][index];
        }
        return new Sums(sumRow, sumCol);
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = getSumsForIndex(matrix, i);
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            rsl[i] = CompletableFuture.supplyAsync(() -> getSumsForIndex(matrix, index)).get();
        }
        return rsl;
    }
}
