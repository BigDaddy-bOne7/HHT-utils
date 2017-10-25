package com.huihuitong.meta;

import java.io.Serializable;

/**
 * Created by yangz on 2017/10/25 14:27:48.
 */
public class ProductDetail implements Serializable {
    private String name;
    private Long quantity;
    private Long amount;
    private String receiver;
    private String description;

    public ProductDetail() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
