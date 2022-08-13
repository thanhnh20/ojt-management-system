/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblapplication;

import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDTO;
import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblApplicationDTO implements Serializable{
    private int applicationID;
    private String attachmentPath;
    private String expected_job;
    private String technology;
    private String experience;
    private String foreign_Language;
    private String otherSkills;
    private String evaluation;
    private float grade;
    private int isPass;
    private boolean studentConfirm;
    private int schoolConfirm;
    private int companyConfirm;
    private TblStudentDTO student;
    private TblCompany_PostDTO companyPost;
    private TblSemesterDTO semester;

    public TblApplicationDTO() {
    }

    public TblApplicationDTO(int applicationID, String attachmentPath, String expected_job, 
            String technology, String experience, String foreign_Language, String otherSkills, 
            String evaluation, float grade, int isPass, boolean studentConfirm, 
            int schoolConfirm, int companyConfirm) {
        this.applicationID = applicationID;
        this.attachmentPath = attachmentPath;
        this.expected_job = expected_job;
        this.technology = technology;
        this.experience = experience;
        this.foreign_Language = foreign_Language;
        this.otherSkills = otherSkills;
        this.evaluation = evaluation;
        this.grade = grade;
        this.isPass = isPass;
        this.studentConfirm = studentConfirm;
        this.schoolConfirm = schoolConfirm;
        this.companyConfirm = companyConfirm;
    }

    /**
     * @return the applicationID
     */
    public int getApplicationID() {
        return applicationID;
    }

    /**
     * @param applicationID the applicationID to set
     */
    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    /**
     * @return the attachmentPath
     */
    public String getAttachmentPath() {
        return attachmentPath;
    }

    /**
     * @param attachmentPath the attachmentPath to set
     */
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    /**
     * @return the expected_job
     */
    public String getExpected_job() {
        return expected_job;
    }

    /**
     * @param expected_job the expected_job to set
     */
    public void setExpected_job(String expected_job) {
        this.expected_job = expected_job;
    }

    /**
     * @return the technology
     */
    public String getTechnology() {
        return technology;
    }

    /**
     * @param technology the technology to set
     */
    public void setTechnology(String technology) {
        this.technology = technology;
    }

    /**
     * @return the experience
     */
    public String getExperience() {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * @return the foreign_Language
     */
    public String getForeign_Language() {
        return foreign_Language;
    }

    /**
     * @param foreign_Language the foreign_Language to set
     */
    public void setForeign_Language(String foreign_Language) {
        this.foreign_Language = foreign_Language;
    }

    /**
     * @return the otherSkills
     */
    public String getOtherSkills() {
        return otherSkills;
    }

    /**
     * @param otherSkills the otherSkills to set
     */
    public void setOtherSkills(String otherSkills) {
        this.otherSkills = otherSkills;
    }

    /**
     * @return the evaluation
     */
    public String getEvaluation() {
        return evaluation;
    }

    /**
     * @param evaluation the evaluation to set
     */
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * @return the grade
     */
    public float getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getIsPass() {
        return isPass;
    }

    /**
     * @param isPass
     */
    public void setIsPass(int isPass) {    
        this.isPass = isPass;
    }

    /**
     * @return the studentConfirm
     */
    public boolean isStudentConfirm() {
        return studentConfirm;
    }

    /**
     * @param studentConfirm the studentConfirm to set
     */
    public void setStudentConfirm(boolean studentConfirm) {
        this.studentConfirm = studentConfirm;
    }

    /**
     * @return the schoolConfirm
     */
    public int getSchoolConfirm() {
        return schoolConfirm;
    }

    /**
     * @param schoolConfirm the schoolConfirm to set
     */
    public void setSchoolConfirm(int schoolConfirm) {
        this.schoolConfirm = schoolConfirm;
    }

    /**
     * @return the companyConfirm
     */
    public int getCompanyConfirm() {
        return companyConfirm;
    }

    /**
     * @param companyConfirm the companyConfirm to set
     */
    public void setCompanyConfirm(int companyConfirm) {
        this.companyConfirm = companyConfirm;
    }

    /**
     * @return the student
     */
    public TblStudentDTO getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(TblStudentDTO student) {
        this.student = student;
    }

    /**
     * @return the companyPost
     */
    public TblCompany_PostDTO getCompanyPost() {
        return companyPost;
    }

    /**
     * @param companyPost the companyPost to set
     */
    public void setCompanyPost(TblCompany_PostDTO companyPost) {
        this.companyPost = companyPost;
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
    
    
    
    
}
