package com.huihuitong.meta;

import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class ListStatus {
    private int id;
    private String orgCode;
    private String copNo;
    private String listNo;
    private String logisticsNo;
    private String orderNo;
    private int status;
    private String formId;
    private String parkStatus;
    private Date date;

    public ListStatus() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListStatus that = (ListStatus) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (orgCode != null ? !orgCode.equals(that.orgCode) : that.orgCode != null) return false;
        if (copNo != null ? !copNo.equals(that.copNo) : that.copNo != null) return false;
        if (listNo != null ? !listNo.equals(that.listNo) : that.listNo != null) return false;
        if (logisticsNo != null ? !logisticsNo.equals(that.logisticsNo) : that.logisticsNo != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;
        if (formId != null ? !formId.equals(that.formId) : that.formId != null) return false;
        if (parkStatus != null ? !parkStatus.equals(that.parkStatus) : that.parkStatus != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orgCode != null ? orgCode.hashCode() : 0);
        result = 31 * result + (copNo != null ? copNo.hashCode() : 0);
        result = 31 * result + (listNo != null ? listNo.hashCode() : 0);
        result = 31 * result + (logisticsNo != null ? logisticsNo.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (formId != null ? formId.hashCode() : 0);
        result = 31 * result + (parkStatus != null ? parkStatus.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ListStatus{" +
                "id=" + id +
                ", orgCode='" + orgCode + '\'' +
                ", copNo='" + copNo + '\'' +
                ", listNo='" + listNo + '\'' +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", status=" + status +
                ", formId='" + formId + '\'' +
                ", parkStatus='" + parkStatus + '\'' +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCopNo() {
        return copNo;
    }

    public void setCopNo(String copNo) {
        this.copNo = copNo;
    }

    public String getListNo() {
        return listNo;
    }

    public void setListNo(String listNo) {
        this.listNo = listNo;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
