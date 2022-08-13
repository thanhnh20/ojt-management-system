/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblaccount;

/**
 *
 * @author ASUS
 */
public class TblAccountError {

    private String accountError;
    private String userEmailEmpty;
    private String userEmailFormatError;
    private String userPasswordEmpty;
    private String userEmailNotAllow;
    
    public TblAccountError() {
    }

    public TblAccountError(String accountError, String userEmailEmpty, String userPasswordEmpty, String userEmailNotAllow) {
        this.accountError = accountError;
        this.userEmailEmpty = userEmailEmpty;
        this.userPasswordEmpty = userPasswordEmpty;
        this.userEmailNotAllow = userEmailNotAllow;
    }
  
    

    public String getAccountError() {
        return accountError;
    }

    public void setAccountError(String accountError) {
        this.accountError = accountError;
    }

    public String getUserEmailEmpty() {
        return userEmailEmpty;
    }

    public void setUserEmailEmpty(String userEmailEmpty) {
        this.userEmailEmpty = userEmailEmpty;
    }

    public String getUserPasswordEmpty() {
        return userPasswordEmpty;
    }

    public void setUserPasswordEmpty(String userPasswordEmpty) {
        this.userPasswordEmpty = userPasswordEmpty;
    }

    public String getUserEmailNotAllow() {
        return userEmailNotAllow;
    }

    public void setUserEmailNotAllow(String userEmailNotAllow) {
        this.userEmailNotAllow = userEmailNotAllow;
    }

    /**
     * @return the userEmailFormatError
     */
    public String getUserEmailFormatError() {
        return userEmailFormatError;
    }

    /**
     * @param userEmailFormatError the userEmailFormatError to set
     */
    public void setUserEmailFormatError(String userEmailFormatError) {
        this.userEmailFormatError = userEmailFormatError;
    }
    
    
}
