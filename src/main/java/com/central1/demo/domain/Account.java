package com.central1.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private Integer id;
    private String type;
    private float amount;

    private transient List<Promotion> promotions = new ArrayList<>();

    public Account(Integer id, String type, float amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public Account attachPromotion(Promotion promotion) {
        promotions.add(promotion);
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
