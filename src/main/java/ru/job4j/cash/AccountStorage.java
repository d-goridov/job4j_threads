package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final Map<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public  synchronized boolean update(Account account) {
        return accounts.computeIfPresent(account.id(),
                (k, v) -> new Account(account.id(), account.amount())) != null;

    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;

    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if ((getById(fromId).isEmpty() && getById(toId).isEmpty()) || getById(fromId).get().amount() < amount) {
            return false;
        }
        Account fromNew = new Account(fromId, getById(fromId).get().amount() - amount);
        Account toNew = new Account(toId, getById(toId).get().amount() + amount);
        return update(fromNew) && update(toNew);
    }
}
