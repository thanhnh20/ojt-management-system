<%-- 
    Document   : adminAddStudent
    Created on : Jun 22, 2022, 9:40:35 PM
    Author     : Thanh Huy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Student Management</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/admin.css">
        <link rel="stylesheet" href="./assets/css/admin-responsive.css">
    </head>
    <body>
        <header></header>

        <c:set var="Admin" value="${sessionScope.ADMIN_ROLE}"/>
        <div class="navbar navbar-expand-md navbar-dark text-center navbar-sm-cus">
            <div class="container-fluid">
                <a href="AdminDashboardController" class="header__logo ">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa-solid fa-bars nav__respo--btn"></i>
                </button>
                <div class="collapse navbar-collapse navbar-collapse-cus" id="navbarSupportedContent">
                    <a href="AdminDashboardController" class=" nav__infor--link text-truncate text-center">
                        <i class="fas fa-user-circle nav__infor--icon"></i>
                        <font> ${Admin.name} </font>
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                         <li class="nav-item">
                            <a href="AdminDashboardController" class="nav__item--link">
                                <i class="fas fa-palette"></i>
                                Admin Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="ShowAdminStudentManagementController" class="nav__item--link link-active">
                                <i class="fas fa-university"></i>
                                Student Management
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="AdminCompanyManagerController" class="nav__item--link">
                                <i class="far fa-building"></i>
                                Company Management
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="AdminShowPostManagementController" class="nav__item--link">
                                <i class="fas fa-pen"></i>
                                Post Management
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="AdminShowInternApplicationController" class="nav__item--link">
                                <i class="fas fa-clipboard-check"></i>
                                Internship Application
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="ShowStudentEvaluationController" class="nav__item--link">
                                <i class="fas fa-poll-h"></i>
                                Evaluation
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
                <a href="AdminDashboardController" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="AdminDashboardController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    <font> ${Admin.name} </font>
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="AdminDashboardController" class="nav__item--link ">
                            <i class="fas fa-palette"></i>
                            Admin Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="ShowAdminStudentManagementController" class="nav__item--link link-active">
                            <i class="fas fa-university"></i>
                            Student Management
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="AdminCompanyManagerController" class="nav__item--link">
                            <i class="far fa-building"></i>
                            Company Management
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="AdminShowPostManagementController" class="nav__item--link">
                            <i class="fas fa-pen"></i>
                            Post Management
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="AdminShowInternApplicationController" class="nav__item--link">
                            <i class="fas fa-clipboard-check"></i>
                            Internship Application
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="ShowStudentEvaluationController" class="nav__item--link">
                            <i class="fas fa-poll-h"></i>
                            Evaluation
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

            <div class="main-body  offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-aAddStu  offset-2 col-8">
                        <div class="main-body-aAddStu__header">
                            Add Student
                        </div>
                        <c:set var="error" value="${requestScope.ERRORS}"/>
                        <form action="AdminAddStudentController" method="POST">
                            <input type="hidden" name="" value="" />
                            <div class="aAddStu__input row">
                                <label class="col-4 aAddStu--label" for="stuID">Student ID</label>
                                <input type="text" class="col-8 aAddStu--input " name="txtStudentID" id="stuID" value="${param.txtStudentID}">
                                <c:if test="${not empty error.studentIDLengthError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.studentIDLengthError}
                                    </h5>
                                </c:if>
                                <c:if test="${not empty error.existedStudentIDError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.existedStudentIDError}
                                    </h5>
                                </c:if>
                            </div>

                            <div class="aAddStu__input row">
                                <label class="col-4 aAddStu--label" for="stuName">Student Name</label>
                                <input type="text" class="col-8 aAddStu--input " name="txtStudentName" id="stuName" value="${param.txtStudentName}">
                                <c:if test="${not empty error.studentNameLengthError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.studentNameLengthError}
                                    </h5>
                                </c:if>
                                <c:if test="${not empty error.studentNameContainSpecialCharacter}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.studentNameContainSpecialCharacter}
                                    </h5>
                                </c:if>
                            </div>
                            <c:set var="listMajorName" value="${requestScope.LIST_MAJOR_NAME}"/>
                            <div class="aAddStu__input row">
                                <label class="col-4 aAddStu--label" for="major">Major</label>
                                <select name="majorID" class="col-8 aAddStu--input">
                                    <option value="" hidden>Major</option>
                                    <c:forEach items="${listMajorName}" var="major">
                                        <option value="${major.majorName}" <c:if test="${major.majorName eq param.majorID}" >
                                                selected="selected"
                                        </c:if>>${major.majorName}</option>
                                    </c:forEach>

                                </select>
                                <c:if test="${not empty error.majorLengthError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.majorLengthError}
                                    </h5>
                                </c:if>
                            </div>

                            <div class="aAddStu__input row">
                                <label class="col-4 aAddStu--label" for="mail">Email</label>
                                <input type="email" class="col-8 aAddStu--input " name="txtEmail" id="mail" value="${param.txtEmail}">
                                <c:if test="${not empty error.emailFormatError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.emailFormatError}
                                    </h5>
                                </c:if>
                                <c:if test="${not empty error.emailLengthError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.emailLengthError}
                                    </h5>
                                </c:if>
                                <c:if test="${not empty error.existedEmailError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.existedEmailError}
                                    </h5>
                                </c:if>
                            </div>

                            <div class="aAddStu__input row">
                                <label class="col-4 aAddStu--label" for="phone">Phone</label>
                                <input type="text" class="col-8 aAddStu--input " name="txtPhone" id="phone" value="${param.txtPhone}">
                                <c:if test="${not empty error.phoneFormatError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.phoneFormatError}
                                    </h5>
                                </c:if>
                                <c:if test="${not empty error.existedPhoneError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.existedPhoneError}
                                    </h5>
                                </c:if>
                            </div>

                            <div class="aAddStu__input row">
                                <label class="col-4 aAddStu--label" for="credit">Credit</label>
                                <input type="number" min="0" max="200" class="col-8 aAddStu--input " name="txtCredit" id="credit" value="${param.txtCredit}">
                                <c:if test="${not empty error.creditError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.creditError}
                                    </h5>
                                </c:if>
                                <c:if test="${not empty error.creditEmptyError}">
                                    <h5 class="text-danger offset-4 col-8 text-start ">
                                        ${error.creditEmptyError}
                                    </h5>
                                </c:if>
                            </div>

                            <div >    
                                <label class="aAddStu-edit-btn primary-btn" for="btaction"><i class="fas fa-edit"></i>
                                    <input type="submit" id="btaction" class="aAddStu-edit--input " name="btAction" value="Add" />
                                </label>
                            </div>
                        </form>
                    </div>
                </div>
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
