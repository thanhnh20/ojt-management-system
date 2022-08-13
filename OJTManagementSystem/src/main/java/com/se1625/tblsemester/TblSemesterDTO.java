package com.se1625.tblsemester;


import java.io.Serializable;
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblSemesterDTO implements Serializable{
    private int semesterID;
    private String semesterName;
    private Date startDate;
    private Date endDate;

    public TblSemesterDTO() {
    }

    public TblSemesterDTO(int semesterID, String semesterName, Date startDate, Date endDate) {
        this.semesterID = semesterID;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the semesterID
     */
    public int getSemesterID() {
        return semesterID;
    }

    /**
     * @param semesterID the semesterID to set
     */
    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    /**
     * @return the semesterName
     */
    public String getSemesterName() {
        return semesterName;
    }

    /**
     * @param semesterName the semesterName to set
     */
    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
}
