/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblcompany;

import com.se1625.tblaccount.TblAccountDTO;
import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblCompanyDTO implements Serializable{
    private String companyID;
    private String address;
    private String city;
    private String phone;
    private String company_Description;
    private boolean is_Signed;
    private TblAccountDTO account;

    public TblCompanyDTO() {
    }

    public TblCompanyDTO(String companyID, String address, String city, String phone, String company_Description, boolean is_Signed, TblAccountDTO account) {
        this.companyID = companyID;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.company_Description = company_Description;
        this.is_Signed = is_Signed;
        this.account = account;
    }

    /**
     * @return the companyID
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * @param companyID the companyID to set
     */
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the company_Description
     */
    public String getCompany_Description() {
        return company_Description;
    }

    /**
     * @param company_Description the company_Description to set
     */
    public void setCompany_Description(String company_Description) {
        this.company_Description = company_Description;
    }

    /**
     * @return the is_Signed
     */
    public boolean isIs_Signed() {
        return is_Signed;
    }

    /**
     * @param is_Signed the is_Signed to set
     */
    public void setIs_Signed(boolean is_Signed) {
        this.is_Signed = is_Signed;
    }

    /**
     * @return the account
     */
    public TblAccountDTO getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(TblAccountDTO account) {
        this.account = account;
    }
    
    
}
