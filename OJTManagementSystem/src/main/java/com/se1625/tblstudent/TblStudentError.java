/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblstudent;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class TblStudentError implements Serializable{
    private String errorAddressLength;
    private String errorPhoneNumberLength;
    private String errorDateInvalid;
    private String errorFileLength;
    private String errorFileType;
    private String errorDateEmpty;
    private String errorPhoneNumberFormat;
    
    public TblStudentError() {
    }

    public TblStudentError(String errorAddressLength, String errorPhoneNumberLength, String errorDateInvalid) {
        this.errorAddressLength = errorAddressLength;
        this.errorPhoneNumberLength = errorPhoneNumberLength;
        this.errorDateInvalid = errorDateInvalid;
    }
    
    

    public String getErrorDateInvalid() {
        return errorDateInvalid;
    }

    public void setErrorDateInvalid(String errorDateInvalid) {
        this.errorDateInvalid = errorDateInvalid;
    }

    

    public String getErrorAddressLength() {
        return errorAddressLength;
    }

    public void setErrorAddressLength(String errorAddressLength) {
        this.errorAddressLength = errorAddressLength;
    }

    public String getErrorPhoneNumberLength() {
        return errorPhoneNumberLength;
    }

    public void setErrorPhoneNumberLength(String errorPhoneNumberLength) {
        this.errorPhoneNumberLength = errorPhoneNumberLength;
    }
    /**
     * @return the errorFileLength
     */
    public String getErrorFileLength() {
        return errorFileLength;
    }

    /**
     * @param errorFileLength the errorFileLength to set
     */
    public void setErrorFileLength(String errorFileLength) {
        this.errorFileLength = errorFileLength;
    }

    /**
     * @return the errorDateEmpty
     */
    public String getErrorDateEmpty() {
        return errorDateEmpty;
    }

    /**
     * @param errorDateEmpty the errorDateEmpty to set
     */
    public void setErrorDateEmpty(String errorDateEmpty) {
        this.errorDateEmpty = errorDateEmpty;
    }

    /**
     * @return the errorPhoneNumberFormat
     */
    public String getErrorPhoneNumberFormat() {
        return errorPhoneNumberFormat;
    }

    /**
     * @param errorPhoneNumberFormat the errorPhoneNumberFormat to set
     */
    public void setErrorPhoneNumberFormat(String errorPhoneNumberFormat) {
        this.errorPhoneNumberFormat = errorPhoneNumberFormat;
    }

    /**
     * @return the errorFileType
     */
    public String getErrorFileType() {
        return errorFileType;
    }

    /**
     * @param errorFileType the errorFileType to set
     */
    public void setErrorFileType(String errorFileType) {
        this.errorFileType = errorFileType;
    }
}
