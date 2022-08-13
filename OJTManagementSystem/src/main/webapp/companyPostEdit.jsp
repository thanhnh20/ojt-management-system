<%-- 
    Document   : companyPostEdit
    Created on : Jun 15, 2022, 8:37:36 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
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
        <title>Company - Post</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">
        <script src="./assets/ckeditor/ckeditor.js"></script>
    </head>
    <body>
    <body>
        <header></header>
            <c:set var="company" value="${sessionScope.COMPANY_ROLE_INFO}"/>
            <c:set var="companyPost" value="${requestScope.COMPANY_POST_DETAIL}"/>
            <c:set var="errors" value="${requestScope.ERROR_UPDATE}"/>
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
                        <font>   ${company.account.name} </font>
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
                            <a href="CompanyShowPostController" class="nav__item--link link-active">
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
                            <a href="CompanyShowIntershipApplicationController" class="nav__item--link">
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
                    ${company.account.name}
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
                        <a href="CompanyShowPostController" class="nav__item--link link-active">
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
                        <a href="CompanyShowIntershipApplicationController" class="nav__item--link">
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
            <div class="main-body offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-cPostEdit  offset-2 col-8">
                        <div class="main-body-cPostEdit__header">
                            Company Post

                        </div>
                        <form action="CompanyUpdatePostController" method="POST">
                            <input type="hidden" name="postID" value="${companyPost.postID}" />
                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="titlePost">Title Post</label>
                                <input type="text" class="col-8 cPostEdit--input " name="titlePost" id="titlePost"
                                       value="${companyPost.title_Post}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors}">
                                        ${errors.titlePostLenghtError}
                                    </c:if>
                                </h5>
                            </div>

                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="major">Major</label>
                                <select name="majorID" class="col-8 cPostEdit--input">
                                    <option value="" hidden>Major</option>
                                    <c:forEach items="${requestScope.LIST_NAME_MAJOR}" var="major">
                                        <option value="${major.majorID}" <c:if test="${companyPost.major.majorID eq major.majorID}">
                                                selected="selected"
                                            </c:if> >${major.majorName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="quantity">Vacancy</label>
                                <input type="text" class="col-8 cPostEdit--input " name="vacancy" id="quantity" value="${companyPost.vacancy}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors}">
                                        ${errors.vacancyLengthError}
                                    </c:if>
                                </h5>
                            </div>

                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="quantity">Quantity Interns</label>
                                <input type="text" class="col-8 cPostEdit--input " name="quantityIterns" id="quantity" value="${companyPost.quantityIterns}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors}">
                                        ${errors.quantitytInternsNotEngough}
                                    </c:if>
                                </h5>
                            </div>

                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="date">Expiration Date</label>
                                <input type="date" class="col-8 cPostEdit--input " name="expirationDate" id="date" value="${companyPost.expirationDate}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors}">
                                        <%--${errors.expirationDateError}--%>
                                        ${errors.expirationDateEmptyError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="city">Work Location</label>
                                <select id="city" name="workLocation" class="col-8 cPostEdit--input ">
                                    <option value="" hidden>City</option>

                                    <option value="${companyPost.workLocation}"<c:if test="${companyPost.workLocation eq 'TP.HCM'}">
                                            selected="selected"
                                        </c:if>>TP.HCM</option>
                                    <option value="${companyPost.workLocation}"<c:if test="${companyPost.workLocation eq 'Đồng Nai'}">
                                            selected="selected"
                                        </c:if>>Đồng Nai</option>
                                    <option value="${companyPost.workLocation}"<c:if test="${companyPost.workLocation eq 'Tây Ninh'}">
                                            selected="selected"
                                        </c:if>>Tây Ninh</option>
                                    <option value="${companyPost.workLocation}"<c:if test="${companyPost.workLocation eq 'Bình Dương'}">
                                            selected="selected"
                                        </c:if>>Bình Dương</option>
                                </select>
                            </div>
                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="descript">Job Description</label>
                                <div class="col-12">
                                    <textarea name="job_Description" class="col-12 cPostEdit--input cPostEdit--input-textarea" id="descript"
                                              cols="30" rows="4">${companyPost.job_Description}</textarea>
                                </div>
                                <h5 class="text-danger  text-start ">
                                    <c:if test="${not empty errors}">
                                        ${errors.descriptionLenghtError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="requirements">Job Requirements</label>
                                <div class="col-12">
                                    <textarea name="job_Requirement" class="col-12 cPostEdit--input cPostEdit--input-textarea" id="requirements"
                                              cols="30" rows="4">${companyPost.job_Requirement}</textarea>
                                </div>
                                <h5 class="text-danger  text-start ">
                                    <c:if test="${not empty errors}">
                                        ${errors.requirementLenghtError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="cPostEdit__input row">
                                <label class="col-4 cPostEdit--label" for="remuneration">Remuneration</label>
                                <div class="col-12">
                                    <textarea name="remuneration" class="col-12 cPostEdit--input cPostEdit--input-textarea" id="remuneration"
                                              cols="30" rows="4">${companyPost.remuneration}</textarea>
                                </div>
                                <h5 class="text-danger text-start ">
                                    <c:if test="${not empty errors}">
                                        ${errors.remunerationLenghtError}
                                    </c:if>
                                </h5>
                            </div>
                            <input type="hidden" name="statusPost" value="${companyPost.statusPost}" />
                            <div>
                                <label class="cPostEdit-edit-btn primary-btn" for="ebtaction"><i class="fas fa-edit"></i>
                                    <input type="submit" id="ebtaction" class="cPostEdit-edit--input " name="btAction" value="Edit" />
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
        <script>
            CKEDITOR.replace('requirements');
            CKEDITOR.replace('remuneration');
            CKEDITOR.replace('descript');
        </script>
    </body>
</html>
