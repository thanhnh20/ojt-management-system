<%-- 
    Document   : companyViewStudentDetail
    Created on : Jun 14, 2022, 10:04:31 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Company View Student</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">
    </head>
    <body>
        <header></header>
            <c:set value="${sessionScope.COMPANY_ROLE}" var="company"/>

        <div class="navbar navbar-expand-md navbar-dark text-center navbar-sm-cus">
            <div class="container-fluid">
                <a href="ShowCompanyDashBoardController" class="header__logo ">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa-solid fa-bars nav__respo--btn"></i>
                </button>
                <div class="collapse navbar-collapse navbar-collapse-cus" id="navbarSupportedContent">
                    <a href="CompanyShowProfileController" class=" nav__infor--link text-truncate text-center">
                        <i class="fas fa-user-circle nav__infor--icon"></i>
                        <font> ${company.name} </font>
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a href="ShowCompanyDashBoardController" class="nav__item--link">
                                <i class="fas fa-palette "></i>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowProfileController" class="nav__item--link">
                                <i class="fas fa-user-edit"></i>
                                My Profile
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowPostController" class="nav__item--link">
                                <i class="fas fa-pen"></i>
                                My Posts
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowInternsManagermentController" class="nav__item--link">
                                <i class="fas fa-poll-h"></i>
                                Interns Management
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowIntershipApplicationController" class="nav__item--link link-active">
                                <i class="fas fa-poll-h"></i>
                                Internship Application
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="logoutController" class="nav__item--link">
                                <i class="fas fa-power-off"></i>
                                Logout
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>


        <main class="row">
            <nav class="col-xl-2  nav-fixed col-md-3">
                <a href="ShowCompanyDashBoardController" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="CompanyShowProfileController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${company.name}
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="ShowCompanyDashBoardController" class="nav__item--link">
                            <i class="fas fa-palette "></i>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowProfileController" class="nav__item--link">
                            <i class="fas fa-user-edit"></i>
                            My Profile
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowPostController" class="nav__item--link">
                            <i class="fas fa-pen"></i>
                            My Posts
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowInternsManagermentController" class="nav__item--link">
                            <i class="fas fa-poll-h"></i>
                            Interns Management
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowIntershipApplicationController" class="nav__item--link link-active">
                            <i class="fas fa-poll-h"></i>
                            Internship Application
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="logoutController" class="nav__item--link">
                            <i class="fas fa-power-off"></i>
                            Logout
                        </a>
                    </li>
                </ul>

            </nav>
            <c:set value="${requestScope.STUDENT_INFOR}" var="studentInfor"/>
            <c:set value="${requestScope.COMPANY_POST_INFOR}" var="companyPostInfor"/>
            <c:set value="${requestScope.APPLICATION_DTO}" var="applicationInfor"/>
            <c:set value="${requestScope.COMPANY_COMFIRM}" var="companyConfirm"/>
            <div class="main-body offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="main-body-cViewStu">
                    <div class="main-body-cViewStu__header">
                        Student Information
                    </div>
                    <div class="row">
                        <div class="inforStu-left  col-xl-4 col-md-4 col-12 row ">
                            <c:if test="${studentInfor.account.avatar eq null}">
                                <div class="col-md-12 col-4">
                                    <img src="./assets/img/person.jpg" alt="" class="inforStu-left--img  ">
                                </div>
                            </c:if>
                            <c:if test="${not empty studentInfor.account.avatar}">
                                <div class="col-md-12 col-4">
                                    <img src="./avatars/${studentInfor.account.avatar}" alt="" class="inforStu-left--img  ">
                                </div>
                            </c:if>
                            <div class="inforStu-left__content col-md-12 col-7 ">
                                <p>Name: ${studentInfor.account.name}</p>
                                <p>Birthday: ${my:changeDateFormat(studentInfor.birthDay)}</p>
                                <p> 
                                    <c:if test="${studentInfor.gender eq true}">
                                        Gender: Male
                                    </c:if>
                                    <c:if test="${studentInfor.gender eq false}">
                                        Gender: Female
                                    </c:if>
                                </p>
                                <p>Address: ${studentInfor.address}</p>
                                <p>Email: ${studentInfor.account.email}</p>    
                            </div>
                        </div>

                        <div class="inforStu-right col-xl-7 col-md-6">
                            <div class="inforStu-right__header">
                                Career Information
                            </div>
                            <div class="inforStu-right__content">
                                <p>Expected Job: ${applicationInfor.expected_job}</p>
                                <p>Main Skill: ${applicationInfor.technology} </p>
                                <p>Experience: ${applicationInfor.experience}</p>
                                <p>Foreign Language: ${applicationInfor.foreign_Language}</p>
                                <p>Other Skills: ${applicationInfor.otherSkills}</p>
                            </div>
                        </div>
                    </div>
                    <div class="file-pdf__cViewStu">
                        <embed src="./CVs/${applicationInfor.attachmentPath}" type="application/pdf" scrolling="auto" width="100%" height="900px"/>
                    </div>  
                </div>

            </div>

            <div class="fix--btn">

                <form action="CompanyUpdateStatusIntershipApplicationController" method="POST">
                    <input type="hidden" name="studentCode" value="${studentInfor.studentCode}" />
                    <input type="hidden" name="companyPostID" value="${companyPostInfor.postID}" />  
                    <input type="hidden" name="page" value="${requestScope.PAGE}" />
                    <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                    <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                    <input type="hidden" name="status" value="${param.status}" />
                    <c:if test="${companyConfirm eq 0}">
                        <input name="action" class="accept-btn primary-btn" type="submit" value="Interview" /> 
                        <input name="action" class="reject-btn primary-btn" type="submit" value="Reject Interview" />   

                    </c:if>  
                    <c:if test="${companyConfirm eq 2}">
                        <input name="action" class="accept-btn primary-btn" type="submit" value="Accept" /> 
                        <input name="action" class="reject-btn primary-btn" type="submit" value="Reject" />                            
                    </c:if>
                    <c:if test="${companyConfirm eq -2}">
                        <input name="action" class="accept-btn-nonhover primary-btn" type="submit" value="Interview" disabled="disabled" style="opacity: 0.4" /> 
                        <input name="action" class="reject-btn-nonhover primary-btn" type="submit" value="Reject Interview" disabled="disabled" style="opacity: 0.4" />                           
                    </c:if>
                    <c:if test="${companyConfirm eq 1}">
                        <input name="action" class="accept-btn-nonhover primary-btn" type="submit" value="Accept" disabled="disabled" style="opacity: 0.4" /> 
                        <input name="action" class="reject-btn-nonhover primary-btn" type="submit" value="Reject" disabled="disabled" style="opacity: 0.4" />                            
                    </c:if>
                    <c:if test="${companyConfirm eq -1}">
                        <input name="action" class="accept-btn-nonhover primary-btn" type="submit" value="Accept" disabled="disabled" style="opacity: 0.4" /> 
                        <input name="action" class="reject-btn-nonhover primary-btn" type="submit" value="Reject" disabled="disabled" style="opacity: 0.4" />                            
                    </c:if>
                </form>

            </div>


        </main>

        <footer class="footer">
            <div class="footer__content">
                <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
            </div>
        </footer>
        <script src="./assets/font/bootstrap-5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
