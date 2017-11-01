package com.huihuitong.meta;

import org.springframework.stereotype.Repository;

/**
 * @author yangz
 */
@Repository
public class User {
    private int id;
    private String orgCode;
    private String userName;
    private String userPassword;
    private String uniteCookie;
    private String downloadPath;
    private String parkCookie;

    public User() {

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUniteCookie() {
        return uniteCookie;
    }

    public void setUniteCookie(String uniteCookie) {
        this.uniteCookie = uniteCookie;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getParkCookie() {
        return parkCookie;
    }

    public void setParkCookie(String parkCookie) {
        this.parkCookie = parkCookie;
    }
}
