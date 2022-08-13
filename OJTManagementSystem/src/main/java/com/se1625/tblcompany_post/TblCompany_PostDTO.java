/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblcompany_post;

import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblmajor.TblMajorDTO;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblCompany_PostDTO implements Serializable{
    private int postID;
    private String title_Post;
    private String job_Description;
    private String job_Requirement;
    private String remuneration;
    private String workLocation;
    private int quantityIterns;
    private Date postingDate;
    private Date expirationDate;
    private boolean school_confirm;
    private int statusPost;
    private String vacancy;
    private TblCompanyDTO company;
    private String majorName;
    private TblMajorDTO major;
    

    public TblCompany_PostDTO() {
    }

    public TblCompany_PostDTO(int postID, String title_Post, 
            String job_Description, String job_Requirement, String remuneration,
            String workLocation, int quantityIterns, Date postingDate, 
            Date expirationDate, boolean school_confirm, int statusPost, 
            TblCompanyDTO company, String majorName) {
        this.postID = postID;
        this.title_Post = title_Post;
        this.job_Description = job_Description;
        this.job_Requirement = job_Requirement;
        this.remuneration = remuneration;
        this.workLocation = workLocation;
        this.quantityIterns = quantityIterns;
        this.postingDate = postingDate;
        this.expirationDate = expirationDate;
        this.school_confirm = school_confirm;
        this.statusPost = statusPost;
        this.company = company;
        this.majorName = majorName;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    /**
     * @return the postID
     */
    public int getPostID() {
        return postID;
    }

    /**
     * @param postID the postID to set
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }

    /**
     * @return the title_Post
     */
    public String getTitle_Post() {
        return title_Post;
    }

    /**
     * @param title_Post the title_Post to set
     */
    public void setTitle_Post(String title_Post) {
        this.title_Post = title_Post;
    }

    /**
     * @return the job_Description
     */
    public String getJob_Description() {
        return job_Description;
    }

    /**
     * @param job_Description the job_Description to set
     */
    public void setJob_Description(String job_Description) {
        this.job_Description = job_Description;
    }

    /**
     * @return the job_Requirement
     */
    public String getJob_Requirement() {
        return job_Requirement;
    }

    /**
     * @param job_Requirement the job_Requirement to set
     */
    public void setJob_Requirement(String job_Requirement) {
        this.job_Requirement = job_Requirement;
    }

    /**
     * @return the remuneration
     */
    public String getRemuneration() {
        return remuneration;
    }

    /**
     * @param remuneration the remuneration to set
     */
    public void setRemuneration(String remuneration) {
        this.remuneration = remuneration;
    }

    /**
     * @return the workLocation
     */
    public String getWorkLocation() {
        return workLocation;
    }

    /**
     * @param workLocation the workLocation to set
     */
    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    /**
     * @return the quantityIterns
     */
    public int getQuantityIterns() {
        return quantityIterns;
    }

    /**
     * @param quantityIterns the quantityIterns to set
     */
    public void setQuantityIterns(int quantityIterns) {
        this.quantityIterns = quantityIterns;
    }

    /**
     * @return the postingDate
     */
    public Date getPostingDate() {
        return postingDate;
    }

    /**
     * @param postingDate the postingDate to set
     */
    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * @return the school_confirm
     */
    public boolean isSchool_confirm() {
        return school_confirm;
    }

    /**
     * @param school_confirm the school_confirm to set
     */
    public void setSchool_confirm(boolean school_confirm) {
        this.school_confirm = school_confirm;
    }

    /**
     * @return the statusPost
     */
    public int getStatusPost() {
        return statusPost;
    }

    /**
     * @param statusPost the statusPost to set
     */
    public void setStatusPost(int statusPost) {
        this.statusPost = statusPost;
    }

    /**
     * @return the company
     */
    public TblCompanyDTO getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(TblCompanyDTO company) {
        this.company = company;
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

    /**
     * @return the major
     */
    public TblMajorDTO getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(TblMajorDTO major) {
        this.major = major;
    }
    
    
    
}
