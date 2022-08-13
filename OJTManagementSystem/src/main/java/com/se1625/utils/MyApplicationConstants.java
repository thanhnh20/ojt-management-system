/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.utils;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class MyApplicationConstants {

    public class ShowStudentHomeFeature {
        public static final String STUDENT_HOME_PAGE = "StudentHomePage";
        public static final String LOGIN_PAGE = "";

    }
    public class CompanyFeatures {
        public static final String COMPANY_PROFILE_CONTROLLER = "CompanyShowProfileController";
        public static final String COMPANY_SHOW_POST_CONTROLLER = "CompanyShowPostController";
        public static final String COMPANY_UPDATE_PROFILE_CONTROLLER = "CompanyUpdateProfileController";
        public static final String COMPANY_PROFILE_PAGE_JSP = "companyProfileJSP";
        public static final String COMPANY_POST_MANAGE_PAGE = "companyPostManagePage";
        public static final String COMPANY_POST_EDIT_CONTROLLER = "CompanyUpdatePostController";
        public static final String COMPANY_POST_EDIT_PAGE = "companyPostEditPage";
        public static final String LOGIN_PAGE = "";
    }

    public class AdminInternApplication{
        public static final String ADMIN_SHOW_INTERN_APPLICATION_CONTROLLER = "AdminShowInternApplicationController";
        public static final String ADMIN_SHOW_INTERN_APPLICATION_JSP = "AdminShowInternApplicationJSP";
        public static final String ADMMIN_CHANGE_STATUS_INTERN_APPLICATION_CONTROLLER ="AdminChangeStatusInternApplicationController";
    }
    public class AdminShowPostManagementFeature {
        public static final String  ADMIN_POST_MANAGE_PAGE="AdminPostManagePage";
        public static final String  ADMIN_DASHBOARD_MANAGE_PAGE="adminDashboardPage";
        public static final String  ADMIN_SHOW_POST_MANAGE_CONTROLLER="AdminShowPostManagementController";
        public static final String  ADMIN_VIEW_POST_MANAGE_PAGE="AdminViewPostPage";
        public static final String  ADMIN_VIEW_POST_DETAIL_CONTROLLER="AdminViewPostDetailController";
        public static final String  ADMIN_SEARCH_POST_MANAGE_CONTROLLER="AdminSearchCompanyPostController";
        public static final String  ADMIN_UPDATE_POST_MANAGE_CONTROLLER="AdminUpdatePostController";
        public static final String LOGIN_PAGE = "";
        
    }
    
    public class StudentSaveJobFeature {
        public static final String STUDENT_SAVE_JOB_PAGE = "StudentSaveJobJSP";
        public static final String STUDENT_DASHBOARD_CONTROLLER = "studentDashboardController";
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_REVIEW_INTERNSHIP_CONTROLLER = "ReviewInternShipController";
        public static final String STUDENT_HOME_CONTROLLER = "ShowStudentHomeController";
        public static final String STUDENT_HOME_SHOW_COMPANY_DETAIL_CONTROLLER = "HomeShowCompanyDetailController";
    }
    
    public class studentDeleteSaveJobFeature {
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_REVIEW_INTERNSHIP_CONTROLLER = "ReviewInternShipController";
        public static final String STUDENT_DASHBOARD_CONTROLLER = "studentDashboardController";
        public static final String STUDENT_HOME_CONTROLLER = "ShowStudentHomeController";
        public static final String STUDENT_SEARCH_SAVE_JOB_CONTROLLER = "SearchSaveJobController";
        public static final String STUDENT_HOME_SHOW_COMPANY_DETAIL_CONTROLLER = "HomeShowCompanyDetailController";
    }   
    

    public class StudentDasboardFeature {

        public static final String STUDENT_DASHBOARD_PAGE = "studentDashboardPage";
        public static final String LOGIN_PAGE = "";
    }

    public class SearchComanyStudentHomeFeature {

        // trang home, xem thông tin company detail (servelet,jsp)
        public static final String HOME_SHOW_COMPANY_DETAIL_JSP = "HomeShowCompanyDetailJSP";
        public static final String HOME_SHOW_COMPANY_DETAIL_CONTROLLER = "HomeShowCompanyDetail";
        public static final String LOGIN_PAGE = "";

        public static final String STUDENT_HOME_PAGE = "ShowStudentHomeController";
        public static final String SEARCH_COMPANY_POST_PAGE = "HomeResultPageJSP";

    }

    public class RegisterCompanyFeature {

        public static final String REGISTER_COMPANY_PAGE_1_JSP = "RegisterPage1JSP";
        public static final String REGISTER_COMPANY_PAGE_1 = "RegisterPage1";
        public static final String LOGIN_PAGE = "";
        public static final String REGISTER_COMPANY_PAGE_2_JSP = "RegisterPage2JSP";

    }

    public class Constants {

        public static final String GOOGLE_CLIENT_ID = "206603271676-8up4i9nf258hmlcol6khi7hcfkf7ibcn.apps.googleusercontent.com";

        public static final String GOOGLE_CLIENT_SECRET = "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";

        public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/OJTManagementSystem/LoginGoogleServlet";

        public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

        public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

        public static final String GOOGLE_GRANT_TYPE = "authorization_code";
    }

    public class LoginFeture {

        public static final String COMPANY_DASHBOARD_CONTROLLER = "ShowCompanyDashBoardController";
        public static final String LOGIN_PAGE = "loginPage";
    }

    public class LogoutFeture {
        public static final String LOGIN_PAGE = "";
    }

    public class LoginGoogleFeture {

        public static final String ADMIN_DASHBOARD_PAGE = "AdminDashboardController";
        public static final String LOGIN_PAGE = "loginPage";

        public static final String SHOW_STUDENT_HOME_PAGE = "ShowStudentHomeController";

        public static final String STUDENT_DASHBOARD_CONTROLLER = "studentDashboardController";
        public static final String ADD_STUDENT_CONTROLLER = "addStudentController";
    }

    public class AddStudentFeture {

        public static final String STUDENT_HOME_PAGE = "StudentHomePage";
        public static final String STUDENT_DASHBOARD_PAGE = "studentDashboardPage";
    }

    public class ImportStudentExcelFileFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_STUDENT_MANAGEMENT_PAGE = "ShowAdminStudentManagementController";
    }
    
    public class LoadApplyCVStudentFeature{
        public static final String HOME_AFTER_CLICK1_PAGE = "homeAfterclick1PageJSP";
    }
    
    public class ApplyCVStudentFeature{
        public static final String LOGIN_PAGE = "";
        public static final String HOME_AFTER_CLICK1_PAGE = "homeAfterclick1PageJSP";
        public static final String STUDENT_APPLIED_JOB_PAGE = "ShowStudentAppliedJobController";
        public static final String SHOW_APPLY_CV_CONTROLLER = "ShowApplyCVController";
    }

    public class ShowStudentProfileFeature {

        public static final String STUDENT_PROFILE_PAGE = "StudentProfilePage";
        public static final String LOGIN_PAGE = "loginPage";
    }

    public class UpdateStudentProfileFeature {

        public static final String SHOW_STUDENT_PROFILE_SERVLET = "ShowStudentProfileController";
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_DASHBOARD_CONTROLLER = "studentDashboardController";
        public static final String SHOW_APPLY_CV_CONTROLLER = "ShowApplyCVController";
    }

    public class ReviewInternShipFeature {
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_REVIEW_INTERNSHIP_PAGE = "StudentReviewPage";
    }
    
    public class ShowStudentAppliedJobFeature {
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_APPLIED_JOBS_PAGE  = "StudentAppliedJobPage";
    }
    
    public class ShowApplyCVFeature {
        public static final String SHOW_JOB_DETAIL_COMPANY = "HomeShowCompanyDetailController";
        public static final String LOGIN_PAGE = "";
        public static final String APPLY_CV_PAGE_JSP = "homeAfterclick1PageJSP";
    }
    
    public class CancleApplyCVFeature {
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_APPLIED_JOB_PAGE = "ShowStudentAppliedJobController";
    }
    
    public class SearchStudentAppliedJobFeature {
        public static final String LOGIN_PAGE = "";
        public static final String STUDENT_APPLIED_JOB_PAGE = "ShowStudentAppliedJobController";
        public static final String STUDENT_APPLIED_JOBS_PAGE  = "StudentAppliedJobPage";
    }
    
    public class ShowAdminStudentManagementFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_MANAGEMENT_STUDENT_PAGE = "adminManagementStudentPage";
    }
    public class SearchStudentByAdminFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_MANAGEMENT_STUDENT_PAGE = "adminManagementStudentPage";
        public static final String ADMIN_MANAGEMENT_STUDENT_CONTROLLER = "ShowAdminStudentManagementController";
    }
    
    public class UpdateStudentInforMationFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_MANAGEMENT_STUDENT_CONTROLLER = "ShowAdminStudentManagementController";
    }
    
    
    public class ShowStudentEvaluationFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_EVALUATION_PAGE = "adminEvaluationPage";
    }
    
    public class SearchStudentEvaluationFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_STUDENT_EVALUATION_CONTROLLER ="ShowStudentEvaluationController";
        public static final String ADMIN_STUDENT_EVALUATION_PAGE = "adminEvaluationPage";
    }
    
    public class AdminCompanyManagerFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_COMPANY_MANAGER_PAGE ="AdminCompanyManagerPage";
    }
    
    public class AdminUpdateStatusCompanyFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_COMPANY_MANAGER_CONTROLLER = "AdminCompanyManagerController";
    }
    
    public class SearchCompanyAdminManagerFeature {
        public static final String LOGIN_PAGE = "";
        public static final String ADMIN_COMPANY_MANAGER_PAGE ="AdminCompanyManagerPage";
    }
    public class ShowCompanyDashBoardFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_DASHBOARD_PAGE = "CompanyDashBoardPage";
    }
    
    public class CreateNewCompanyPostFeature {
        public static final String LOGIN_PAGE = "";
        public static final String SHOW_CREATE_COMPANY_POST_PAGE = "companyCreateNewPostPage";
        public static final String SHOW_COMPANY_DASHBOARD_CONTROLLER = "ShowCompanyDashBoardController";
        public static final String SHOW_COMPANY_POST_MANAGER_CONTROLLER = "CompanyShowPostController";
    }
    
    public class ShowCreateNewCompanyPostFeature {
        public static final String LOGIN_PAGE = "";
        public static final String SHOW_CREATE_COMPANY_POST_PAGE = "companyCreateNewPostPage";
        
    }
    
    public class CompanyViePostDetailFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_VIEW_POST_PAGE = "companyViewPostPage";
    }
    public class CompanyShowIntershipApplicationFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_APPLICATION_MANAGER_PAGE ="CompanyApplicationManagerPage";
    }
    
    public class CompanyUpdateStatusIntershipApplicationFeature{
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_SHOW_INTERNSHIP_APPLICATION_CONTROLLER ="CompanyShowIntershipApplicationController";
        public static final String COMPANY_SEARCH_INTERNS_CONTROLLER = "CompanySearchInternsController";
    }
    
    public class CompanySearchInternsFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_APPLICATION_MANAGER_PAGE ="CompanyApplicationManagerPage";
    }
    
    public class CompanyViewStudentDetailFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_VIEW_STUDENT_DETAILE_PAGE = "CompanyViewStudentDetailPage";
    }
    
    public class CompanyShowInternsManagermentFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_INTERNS_MANAGEMENT_PAGE = "CompanyInternsManagementPage";
    }
    
    public class CompanyUpdateInternsFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_SEARCH_INTERNS_MANAGEMENT_CONTROLLER = "CompanySearchInternsManagementController";
    }
    
    public class CompanySearchInternsManagementFeature {
        public static final String LOGIN_PAGE = "";
        public static final String COMPANY_APPLICATION_MANAGER_PAGE ="CompanyInternsManagementPage";
    }
    
    public class AdminShowAddingStudentFeature {
        public static final String LOGIN_PAGE ="";
        public static final String ADMIN_ADD_STUDENT_PAGE = "adminAddStudentPage";
    }
    
    public class AdminAddStudentFeature {
        public static final String LOGIN_PAGE ="";
        public static final String ADMIN_ADD_STUDENT_PAGE = "adminAddStudentPage";
        public static final String ADMIN_SHOW_ADDING_STUDENT_CONTROLLER = "AdminShowAddingStudentController";
        public static final String SHOW_ADMIN_STUDENT_MANAGEMENT_CONTROLLER = "ShowAdminStudentManagementController";
    }
    
    public class AdminDashboardFeature{
        public static final String ADMIN_DASHBOARD_PAGE = "AdminDashboardPage";
        public static final String ADMIN_DASHBOARD_CONTROLLER = "AdminDashboardController";
        public static final String LOGIN_PAGE = "";
    }
    
    public class ExportEvalutionExcelFileFeature {
        public static final String LOGIN_PAGE = "";
    }
    
    public class DownloadFileModelFeature {
        public static final String LOGIN_PAGE = "";
    }
}
