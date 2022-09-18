package ru.job4j.cash;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenWrongTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 50));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(1, 2, 70)).isFalse();
    }

    @Test
    void whenWrongTransferDoesNonExistAccounts() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 50));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(3, 4, 20)).isFalse();
    }

    @Test
    void whenWrongTransferDoesNotOneExistAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 50));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(1, 3, 40)).isFalse();
    }
}