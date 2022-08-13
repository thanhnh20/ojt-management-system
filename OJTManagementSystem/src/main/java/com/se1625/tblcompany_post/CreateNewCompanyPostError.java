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
public class CreateNewCompanyPostError implements Serializable{
    private String titlePostEmptyError;
    private String vacancyEmptyError;
    private String majorChooseError;
    private String quantityEmptyError;
    private String expirationdateIllegal;
    private String workLocationEmptyError;
    private String jobDescriptionEmptyError;
    private String jobRequirementsEmptyError;
    private String remunerationEmptyError;
    private String companyNotSignedError;
 
    public CreateNewCompanyPostError() {
    }

    public String getCompanyNotSignedError() {
        return companyNotSignedError;
    }

    public void setCompanyNotSignedError(String companyNotSignedError) {
        this.companyNotSignedError = companyNotSignedError;
    }

    /**
     * @return the titlePostEmptyError
     */
    public String getTitlePostEmptyError() {
        return titlePostEmptyError;
    }

    /**
     * @param titlePostEmptyError the titlePostEmptyError to set
     */
    public void setTitlePostEmptyError(String titlePostEmptyError) {
        this.titlePostEmptyError = titlePostEmptyError;
    }

    /**
     * @return the vacancyEmptyError
     */
    public String getVacancyEmptyError() {
        return vacancyEmptyError;
    }

    /**
     * @param vacancyEmptyError the vacancyEmptyError to set
     */
    public void setVacancyEmptyError(String vacancyEmptyError) {
        this.vacancyEmptyError = vacancyEmptyError;
    }

    /**
     * @return the majorChooseError
     */
    public String getMajorChooseError() {
        return majorChooseError;
    }

    /**
     * @param majorChooseError the majorChooseError to set
     */
    public void setMajorChooseError(String majorChooseError) {
        this.majorChooseError = majorChooseError;
    }

    /**
     * @return the quantityEmptyError
     */
    public String getQuantityEmptyError() {
        return quantityEmptyError;
    }

    /**
     * @param quantityEmptyError the quantityEmtyError to set
     */
    public void setQuantityEmptyError(String quantityEmptyError) {
        this.quantityEmptyError = quantityEmptyError;
    }

    /**
     * @return the expirationdateIllegal
     */
    public String getExpirationdateIllegal() {
        return expirationdateIllegal;
    }

    /**
     * @param expirationdateIllegal the expirationdateIllegal to set
     */
    public void setExpirationdateIllegal(String expirationdateIllegal) {
        this.expirationdateIllegal = expirationdateIllegal;
    }

    /**
     * @return the workLocationEmptyError
     */
    public String getWorkLocationEmptyError() {
        return workLocationEmptyError;
    }

    /**
     * @param workLocationEmptyError the workLocationEmptyError to set
     */
    public void setWorkLocationEmptyError(String workLocationEmptyError) {
        this.workLocationEmptyError = workLocationEmptyError;
    }

    /**
     * @return the jobDescriptionEmptyError
     */
    public String getJobDescriptionEmptyError() {
        return jobDescriptionEmptyError;
    }

    /**
     * @param jobDescriptionEmptyError the jobDescriptionEmptyError to set
     */
    public void setJobDescriptionEmptyError(String jobDescriptionEmptyError) {
        this.jobDescriptionEmptyError = jobDescriptionEmptyError;
    }

    /**
     * @return the jobRequirementsEmptyError
     */
    public String getJobRequirementsEmptyError() {
        return jobRequirementsEmptyError;
    }

    /**
     * @param jobRequirementsEmptyError the jobRequirementsEmptyError to set
     */
    public void setJobRequirementsEmptyError(String jobRequirementsEmptyError) {
        this.jobRequirementsEmptyError = jobRequirementsEmptyError;
    }

    /**
     * @return the RemunerationEmptyError
     */
    public String getRemunerationEmptyError() {
        return remunerationEmptyError;
    }

    /**
     * @param remunerationEmptyError the RemunerationEmptyError to set
     */
    public void setRemunerationEmptyError(String remunerationEmptyError) {
        this.remunerationEmptyError = remunerationEmptyError;
    }
    
    
    
}
