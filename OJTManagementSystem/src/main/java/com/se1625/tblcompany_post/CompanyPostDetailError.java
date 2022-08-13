/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblcompany_post;

import java.io.Serializable;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class CompanyPostDetailError implements Serializable{
    private String vacancyLengthError;
    private String quantitytInternsNotEngough;
    private String expirationDateError;
    private String expirationDateEmptyError;
    private String titlePostLenghtError;
    private String descriptionLenghtError;
    private String requirementLenghtError;
    private String remunerationLenghtError;
    private String appliedTwoTimeError;
    private String appliedJobStudentWorkingError;
    private String studentCompletedError;
    
    public CompanyPostDetailError() {
    }

    public CompanyPostDetailError(String quantitytInternsNotEngough, String expirationDateError, String expirationDateEmptyError, String titlePostLenghtError, String descriptionLenghtError, String requirementLenghtError, String remunerationLenghtError, String vacancyLengthError, String studentCompletedError) {
        this.quantitytInternsNotEngough = quantitytInternsNotEngough;
        this.expirationDateError = expirationDateError;
        this.expirationDateEmptyError = expirationDateEmptyError;
        this.titlePostLenghtError = titlePostLenghtError;
        this.descriptionLenghtError = descriptionLenghtError;
        this.requirementLenghtError = requirementLenghtError;
        this.remunerationLenghtError = remunerationLenghtError;
        this.vacancyLengthError = vacancyLengthError;
        this.studentCompletedError = studentCompletedError;
    }

    
    /**
     * @return the quantitytInternsNotEngough
     */
    public String getQuantitytInternsNotEngough() {
        return quantitytInternsNotEngough;
    }

    /**
     * @param quantitytInternsNotEngough the quantitytInternsNotEngough to set
     */
    public void setQuantitytInternsNotEngough(String quantitytInternsNotEngough) {
        this.quantitytInternsNotEngough = quantitytInternsNotEngough;
    }

    /**
     * @return the expirationDateError
     */
    public String getExpirationDateError() {
        return expirationDateError;
    }

    /**
     * @param expirationDateError the expirationDateError to set
     */
    public void setExpirationDateError(String expirationDateError) {
        this.expirationDateError = expirationDateError;
    }

    /**
     * @return the expirationDateEmptyError
     */
    public String getExpirationDateEmptyError() {
        return expirationDateEmptyError;
    }

    /**
     * @param expirationDateEmptyError the expirationDateEmptyError to set
     */
    public void setExpirationDateEmptyError(String expirationDateEmptyError) {
        this.expirationDateEmptyError = expirationDateEmptyError;
    }

    /**
     * @return the titlePostLenghtError
     */
    public String getTitlePostLenghtError() {
        return titlePostLenghtError;
    }

    /**
     * @param titlePostLenghtError the titlePostLenghtError to set
     */
    public void setTitlePostLenghtError(String titlePostLenghtError) {
        this.titlePostLenghtError = titlePostLenghtError;
    }

    /**
     * @return the descriptionLenghtError
     */
    public String getDescriptionLenghtError() {
        return descriptionLenghtError;
    }

    /**
     * @param descriptionLenghtError the descriptionLenghtError to set
     */
    public void setDescriptionLenghtError(String descriptionLenghtError) {
        this.descriptionLenghtError = descriptionLenghtError;
    }

    /**
     * @return the requirementLenghtError
     */
    public String getRequirementLenghtError() {
        return requirementLenghtError;
    }

    /**
     * @param requirementLenghtError the requirementLenghtError to set
     */
    public void setRequirementLenghtError(String requirementLenghtError) {
        this.requirementLenghtError = requirementLenghtError;
    }

    /**
     * @return the remunerationLenghtError
     */
    public String getRemunerationLenghtError() {
        return remunerationLenghtError;
    }

    /**
     * @param remunerationLenghtError the remunerationLenghtError to set
     */
    public void setRemunerationLenghtError(String remunerationLenghtError) {
        this.remunerationLenghtError = remunerationLenghtError;
    }

    public String getVacancyLengthError() {
        return vacancyLengthError;
    }

    public void setVacancyLengthError(String vacancyLengthError) {
        this.vacancyLengthError = vacancyLengthError;
    }

    /**
     * @return the appliedTwoTimeError
     */
    public String getAppliedTwoTimeError() {
        return appliedTwoTimeError;
    }

    /**
     * @param appliedTwoTimeError the appliedTwoTimeError to set
     */
    public void setAppliedTwoTimeError(String appliedTwoTimeError) {
        this.appliedTwoTimeError = appliedTwoTimeError;
    }

    /**
     * @return the appliedJobStudentWorkingError
     */
    public String getAppliedJobStudentWorkingError() {
        return appliedJobStudentWorkingError;
    }

    /**
     * @param appliedJobStudentWorkingError the appliedJobStudentWorkingError to set
     */
    public void setAppliedJobStudentWorkingError(String appliedJobStudentWorkingError) {
        this.appliedJobStudentWorkingError = appliedJobStudentWorkingError;
    }

    public String getStudentCompletedError() {
        return studentCompletedError;
    }

    public void setStudentCompletedError(String studentCompletedError) {
        this.studentCompletedError = studentCompletedError;
    }
    
}
