package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    public void whenSyncCalculateSum() {
        int[][] matrix = {{1, 2}, {3, 4}};

        assertThat(RolColSum.sum(matrix))
                .isEqualTo(new RolColSum.Sums[]{
                        new RolColSum.Sums(3, 4),
                        new RolColSum.Sums(7, 6)});
    }

    @Test
    public void whenAsyncCalculateSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        assertThat(RolColSum.asyncSum(matrix))
                .isEqualTo(new RolColSum.Sums[]{
                        new RolColSum.Sums(6, 12),
                        new RolColSum.Sums(15, 15),
                        new RolColSum.Sums(24, 18)});
    }
}