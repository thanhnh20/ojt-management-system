/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblmajor;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblMajorDTO implements Serializable{
    private int majorID;
    private String majorName;

    public TblMajorDTO() {
    }

    public TblMajorDTO(int majorID, String majorName) {
        this.majorID = majorID;
        this.majorName = majorName;
    }

    /**
     * @return the majorID
     */
    public int getMajorID() {
        return majorID;
    }

    /**
     * @param majorID the majorID to set
     */
    public void setMajorID(int majorID) {
        this.majorID = majorID;
    }

    /**
     * @return the majorName
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * @param majorName the majorName to set
     */
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
    
    
    
}
