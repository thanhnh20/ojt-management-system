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
public class RegisterCompanyAccountError implements Serializable{
    private String emailFormatError;
    private String emailDuplicateError;
    private String passwordLengthError;
    private String passwordConfirmError;

    public RegisterCompanyAccountError() {
    }

    public RegisterCompanyAccountError(String emailFormatError, String emailDuplicateError, String passwordLengthError, String passwordConfirmError) {
        this.emailFormatError = emailFormatError;
        this.emailDuplicateError = emailDuplicateError;
        this.passwordLengthError = passwordLengthError;
        this.passwordConfirmError = passwordConfirmError;
    }

    /**
     * @return the emailFormatError
     */
    public String getEmailFormatError() {
        return emailFormatError;
    }

    /**
     * @param emailFormatError the emailFormatError to set
     */
    public void setEmailFormatError(String emailFormatError) {
        this.emailFormatError = emailFormatError;
    }

    /**
     * @return the emailDuplicateError
     */
    public String getEmailDuplicateError() {
        return emailDuplicateError;
    }

    /**
     * @param emailDuplicateError the emailDuplicateError to set
     */
    public void setEmailDuplicateError(String emailDuplicateError) {
        this.emailDuplicateError = emailDuplicateError;
    }

    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
        return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
        this.passwordLengthError = passwordLengthError;
    }

    /**
     * @return the passwordConfirmError
     */
    public String getPasswordConfirmError() {
        return passwordConfirmError;
    }

    /**
     * @param passwordConfirmError the passwordConfirmError to set
     */
    public void setPasswordConfirmError(String passwordConfirmError) {
        this.passwordConfirmError = passwordConfirmError;
    }
    
    
}
