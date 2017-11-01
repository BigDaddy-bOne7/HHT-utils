package com.huihuitong.meta;

import org.springframework.stereotype.Repository;

/**
 * 订单详情：从ECM数据库获取，用于园区版报关
 * @author yangz
 */
@Repository
public class OrderDetail {
    private String itemRecordNo;
    private String gcode;
    private String gname;
    private String gmodel;
    private String qty;
    private String unit;
    private String totalPrice;
    private String qty1;
    private String oriCountry;

    public OrderDetail() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetail that = (OrderDetail) o;

        if (itemRecordNo != null ? !itemRecordNo.equals(that.itemRecordNo) : that.itemRecordNo != null) {
            return false;
        }
        if (gcode != null ? !gcode.equals(that.gcode) : that.gcode != null) {
            return false;
        }
        if (gname != null ? !gname.equals(that.gname) : that.gname != null) {
            return false;
        }
        if (gmodel != null ? !gmodel.equals(that.gmodel) : that.gmodel != null) {
            return false;
        }
        if (qty != null ? !qty.equals(that.qty) : that.qty != null) {
            return false;
        }
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) {
            return false;
        }
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) {
            return false;
        }
        if (qty1 != null ? !qty1.equals(that.qty1) : that.qty1 != null) {
            return false;
        }
        return oriCountry != null ? oriCountry.equals(that.oriCountry) : that.oriCountry == null;
    }

    @Override
    public int hashCode() {
        int result = itemRecordNo != null ? itemRecordNo.hashCode() : 0;
        result = 31 * result + (gcode != null ? gcode.hashCode() : 0);
        result = 31 * result + (gname != null ? gname.hashCode() : 0);
        result = 31 * result + (gmodel != null ? gmodel.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (qty1 != null ? qty1.hashCode() : 0);
        result = 31 * result + (oriCountry != null ? oriCountry.hashCode() : 0);
        return result;
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

    public String getOriCountry() {
        return oriCountry;
    }

    public void setOriCountry(String oriCountry) {
        this.oriCountry = oriCountry;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "itemRecordNo='" + itemRecordNo + '\'' +
                ", gcode='" + gcode + '\'' +
                ", gname='" + gname + '\'' +
                ", gmodel='" + gmodel + '\'' +
                ", qty='" + qty + '\'' +
                ", unit='" + unit + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", qty1='" + qty1 + '\'' +
                ", oriCountry='" + oriCountry + '\'' +
                '}';
    }
}
