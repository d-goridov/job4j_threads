package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    public void whenSyncCalculateSum() {
        int[][] matrix = {{1, 2}, {3, 4}};
        RolColSum rsl = new RolColSum();
        assertThat(rsl.sum(matrix))
                .isEqualTo(new Sums[]{
                        new Sums(3, 4),
                        new Sums(7, 6)});
    }

    @Test
    public void whenAsyncCalculateSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum rsl = new RolColSum();
        assertThat(rsl.asyncSum(matrix))
                .isEqualTo(new Sums[]{
                        new Sums(6, 12),
                        new Sums(15, 15),
                        new Sums(24, 18)});
    }
}