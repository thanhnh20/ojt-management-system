/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblfollowing_post;

import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblstudent.TblStudentDTO;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author ThanhTy
 */
public class TblFollowing_PostDTO implements Serializable {

    private String studentID;
    private int postID;
    private String tittle_Post;
    private String companyName;
    private Date postingDate;
    private Date exprirationDate;
    private String workLocation;

    public TblFollowing_PostDTO() {
    }

    public TblFollowing_PostDTO(String studentID, int postID, String tittle_Post, String companyName, Date postingDate, Date exprirationDate, String workLocation) {
        this.studentID = studentID;
        this.postID = postID;
        this.tittle_Post = tittle_Post;
        this.companyName = companyName;
        this.postingDate = postingDate;
        this.exprirationDate = exprirationDate;
        this.workLocation = workLocation;
    }

    public String getTittle_Post() {
        return tittle_Post;
    }

    public void setTittle_Post(String tittle_Post) {
        this.tittle_Post = tittle_Post;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public Date getExprirationDate() {
        return exprirationDate;
    }

    public void setExprirationDate(Date exprirationDate) {
        this.exprirationDate = exprirationDate;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

}
