/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblapplication;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class TblApplicationError implements Serializable{
    private String errorEmptyMark;
    private String errorInputInvalidMark;

    public TblApplicationError() {
    }

    public TblApplicationError(String errorEmptyMark, String errorInputInvalidMark) {
        this.errorEmptyMark = errorEmptyMark;
        this.errorInputInvalidMark = errorInputInvalidMark;
    }

    public String getErrorEmptyMark() {
        return errorEmptyMark;
    }

    public void setErrorEmptyMark(String errorEmptyMark) {
        this.errorEmptyMark = errorEmptyMark;
    }

    public String getErrorInputInvalidMark() {
        return errorInputInvalidMark;
    }

    public void setErrorInputInvalidMark(String errorInputInvalidMark) {
        this.errorInputInvalidMark = errorInputInvalidMark;
    }
   
    
}
