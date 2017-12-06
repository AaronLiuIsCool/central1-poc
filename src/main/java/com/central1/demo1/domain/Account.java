package com.central1.demo1.domain;

public class Account {

    private int count;
    private String name;
    private float amount;

    public Account(int count, String name, float amount) {
        this.count = count;
        this.name = name;
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public Account increaseAmountBy(float amount) {
        this.amount=+amount;
        return this;
    }

    public Account decreaseAmountBy(float amount) {
        this.amount=-amount;
        return this;
    }
}
