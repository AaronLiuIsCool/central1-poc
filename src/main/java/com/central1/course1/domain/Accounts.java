package com.central1.course1.domain;

import java.util.ArrayList;
import java.util.List;

public class Accounts {

    private String key;
    private List<Account> accounts = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void add(String accountKey, String accountName) {
        accounts.add(new Account(accountKey, accountName));
    }
}
