/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblaccount;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblAccountDTO implements Serializable{
    private String email;
    private String password;
    private String name;
    private String avatar;
    private int is_Admin;
    private String verifyCode;

    public TblAccountDTO() {
    }

    public TblAccountDTO(String email, String name, String avatar, int is_Admin) {
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.is_Admin = is_Admin;
    }
       
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the is_Admin
     */
    public int getIs_Admin() {
        return is_Admin;
    }

    /**
     * @param is_Admin the is_Admin to set
     */
    public void setIs_Admin(int is_Admin) {
        this.is_Admin = is_Admin;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the verifyCode
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    /**
     * @param verifyCode the verifyCode to set
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
    
    
}
