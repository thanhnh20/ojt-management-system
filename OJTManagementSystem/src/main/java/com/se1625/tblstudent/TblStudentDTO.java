/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblstudent;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblsemester.TblSemesterDTO;
import java.io.Serializable;
import java.sql.Date;

/**

 @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblStudentDTO implements Serializable{
    private String studentCode;
    private Date birthDay;
    private String address;
    private boolean gender;
    private String phone;
    private int isIntern;
    private int numberOfCredit;
    private String major;
    private TblSemesterDTO semester;
    private TblAccountDTO account;  
    private boolean isDisabled;


    public TblStudentDTO(String studentCode, Date birthDay, String address, boolean gender, String phone, int isIntern, int numberOfCredit, String major) {
        this.studentCode = studentCode;
        this.birthDay = birthDay;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.isIntern = isIntern;
        this.numberOfCredit = numberOfCredit;
        this.major = major;
    }
        
 
    public TblStudentDTO() {
    }

    public TblStudentDTO(Date birthDay, String major) {
        this.birthDay = birthDay;
        this.major = major;
    }
    
    

    public TblAccountDTO getAccount() {
        return account;
    }

    public void setAccount(TblAccountDTO account) {
        this.account = account;
    }
    /**
     * @return the studentCode
     */
    public String getStudentCode() {
        return studentCode;
    }
    /**
     * @param studentCode the studentCode to set
     */
    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
    /**
     * @return the birthDay
     */
    public Date getBirthDay() {
        return birthDay;
    }
    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
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
     * @return the gender
     */
    public boolean isGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(boolean gender) {
        this.gender = gender;
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
    public int getIsIntern() {
        return isIntern;
    }

    public void setIsIntern(int isIntern) {
        this.isIntern = isIntern;
    }

    public int getNumberOfCredit() {
        return numberOfCredit;
    }

    public void setNumberOfCredit(int numberOfCredit) {
        this.numberOfCredit = numberOfCredit;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return the semester
     */
    public TblSemesterDTO getSemester() {
        return semester;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(TblSemesterDTO semester) {
        this.semester = semester;
    }

    /**
     * @return the isDisabled
     */
    public boolean isIsDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled the isDisabled to set
     */
    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    
}
