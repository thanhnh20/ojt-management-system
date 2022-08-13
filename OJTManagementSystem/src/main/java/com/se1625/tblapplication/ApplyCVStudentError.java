/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblapplication;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class ApplyCVStudentError implements Serializable {

    private String expectedJobLengthError;
    private String technologyLengthError;
    private String foreignLanguageLengthError;
    private String otherSkillsLengthError;
    private String fileUploadError;
    private String fileUploadTypeError;
    private String fileUploadLengthError;
    private String studentInformationError;
    public ApplyCVStudentError() {
    }

    public ApplyCVStudentError(String expectedJobLengthError, String technologyLengthError, String foreignLanguageLengthError, String otherSkillsLengthError, String studentInformationError) {
        this.expectedJobLengthError = expectedJobLengthError;
        this.technologyLengthError = technologyLengthError;
        this.foreignLanguageLengthError = foreignLanguageLengthError;
        this.otherSkillsLengthError = otherSkillsLengthError;
        this.studentInformationError = studentInformationError;
    }

    /**
     * @return the expectedJobLengthError
     */
    public String getExpectedJobLengthError() {
        return expectedJobLengthError;
    }

    /**
     * @param expectedJobLengthError the expectedJobLengthError to set
     */
    public void setExpectedJobLengthError(String expectedJobLengthError) {
        this.expectedJobLengthError = expectedJobLengthError;
    }

    /**
     * @return the technologyLengthError
     */
    public String getTechnologyLengthError() {
        return technologyLengthError;
    }

    /**
     * @param technologyLengthError the technologyLengthError to set
     */
    public void setTechnologyLengthError(String technologyLengthError) {
        this.technologyLengthError = technologyLengthError;
    }

    /**
     * @return the foreignLanguageLengthError
     */
    public String getForeignLanguageLengthError() {
        return foreignLanguageLengthError;
    }

    /**
     * @param foreignLanguageLengthError the foreignLanguageLengthError to set
     */
    public void setForeignLanguageLengthError(String foreignLanguageLengthError) {
        this.foreignLanguageLengthError = foreignLanguageLengthError;
    }

    /**
     * @return the otherSkillsLengthError
     */
    public String getOtherSkillsLengthError() {
        return otherSkillsLengthError;
    }

    /**
     * @param otherSkillsLengthError the otherSkillsLengthError to set
     */
    public void setOtherSkillsLengthError(String otherSkillsLengthError) {
        this.otherSkillsLengthError = otherSkillsLengthError;
    }

    /**
     * @return the fileUploadError
     */
    public String getFileUploadError() {
        return fileUploadError;
    }

    /**
     * @param fileUploadError the fileUploadError to set
     */
    public void setFileUploadError(String fileUploadError) {
        this.fileUploadError = fileUploadError;
    }

    /**
     * @return the fileUploadTypeError
     */
    public String getFileUploadTypeError() {
        return fileUploadTypeError;
    }

    /**
     * @param fileUploadTypeError the fileUploadTypeError to set
     */
    public void setFileUploadTypeError(String fileUploadTypeError) {
        this.fileUploadTypeError = fileUploadTypeError;
    }

    /**
     * @return the fileUploadLengthError
     */
    public String getFileUploadLengthError() {
        return fileUploadLengthError;
    }

    /**
     * @param fileUploadLengthError the fileUploadLengthError to set
     */
    public void setFileUploadLengthError(String fileUploadLengthError) {
        this.fileUploadLengthError = fileUploadLengthError;
    }

    public String getStudentInformationError() {
        return studentInformationError;
    }

    public void setStudentInformationError(String studentInformationError) {
        this.studentInformationError = studentInformationError;
    }
   
}
