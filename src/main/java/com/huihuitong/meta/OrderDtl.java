package com.huihuitong.meta;

import org.springframework.stereotype.Repository;

/**
 * Created by yangz on 2017/7/20 9:39.
 */
@Repository
public class OrderDtl {
    private String itemRecordNo;
    private String gcode;
    private String gname;
    private String gmodel;
    private String qty;
    private String unit;
    private String totalPrice;
    private String qty1;
    private String unit1;
    private String oriCountry;

    public OrderDtl() {
    }

    public String getItemRecordNo() {
        return itemRecordNo;
    }

    public void setItemRecordNo(String itemRecordNo) {
        this.itemRecordNo = itemRecordNo;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGmodel() {
        return gmodel;
    }

    public void setGmodel(String gmodel) {
        this.gmodel = gmodel;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getQty1() {
        return qty1;
    }

    public void setQty1(String qty1) {
        this.qty1 = qty1;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getOriCountry() {
        return oriCountry;
    }

    public void setOriCountry(String oriCountry) {
        this.oriCountry = oriCountry;
    }
}
