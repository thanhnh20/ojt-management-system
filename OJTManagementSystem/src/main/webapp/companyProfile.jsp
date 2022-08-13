<%-- 
    Document   : companyProfile
    Created on : Jun 13, 2022, 9:04:37 AM
    Author     : ThanhTy
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
        <title>Company - Profile</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">
        <script src="./assets/ckeditor/ckeditor.js"></script>
    </head>

    <body>
        <c:set var="companyProfile" value="${requestScope.COMPANY_PROFILE}"/>
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
                        <font>  ${companyProfile.account.name} </font>
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a href="ShowCompanyDashBoardController" class="nav__item--link">
                                <i class="fas fa-palette "></i>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowProfileController" class="nav__item--link link-active">
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
                <a href="ShowCompanyDashBoardController
                   " class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="CompanyShowProfileController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${companyProfile.account.name}
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="ShowCompanyDashBoardController" class="nav__item--link">
                            <i class="fas fa-palette "></i>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowProfileController" class="nav__item--link link-active">
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

                <c:set var="errors" value="${requestScope.ERROR_UPDATE_COMPANYPROFILE}"/>
                <div class="row">
                    <div class="main-body-cprofile  offset-2 col-8">
                        <div class="main-body-cprofile__header">
                            Company Profile
                        </div>
                        <form action="CompanyUpdateProfileController" method="POST" enctype="multipart/form-data">
                            <div class="cprofile__input row">
                                <label class="col-4 cprofile--label" for="cName">Company's Name</label>
                                <div class="col-8  profile--input-none-hover ">${companyProfile.account.name}</div>
                                <!--cprofile--input--> 
                            </div>

                            <div class="cprofile__input row">
                                <label class="col-4 cprofile--label" for="address">Address</label>
                                <input type="text" class="col-8 cprofile--input " name="addressUpdate" id="address" value="${companyProfile.address}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors.companyAddressLengthError}">
                                        ${errors.companyAddressLengthError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="cprofile__input row">
                                <label class="col-4 cprofile--label" for="city">City</label>

                                <select id="city" name="cityUpdate"  class="col-8 cprofile--input " >
                                    <option value="" hidden>Choose City</option>
                                    <option value="TP.HCM" <c:if test="${companyProfile.city eq 'TP.HCM'}" >
                                            selected="selected"
                                        </c:if> >TP.HCM</option>
                                    <option value="Đồng Nai" <c:if test="${companyProfile.city eq 'Đồng Nai'}" >
                                            selected="selected"
                                        </c:if> >Đồng Nai</option>
                                    <option value="Tây Ninh" <c:if test="${companyProfile.city eq 'Tây Ninh'}" >
                                            selected="selected"
                                        </c:if> >Tây Ninh</option>
                                    <option value="Bình Dương" <c:if test="${companyProfile.city eq 'Bình Dương'}" >
                                            selected="selected"
                                        </c:if> >Bình Dương</option>
                                </select>
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors.companyCityError}">
                                        ${errors.companyCityError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="cprofile__input row">
                                <label class="col-4 cprofile--label" for="email">Contaxt Email</label>
                                <input type="email" readonly class="col-8  profile--input-none-hover " name="email" id="email" value="${companyProfile.account.email}">
                            </div>
                            <div class="cprofile__input row">
                                <label class="col-4 cprofile--label" for="phone">Phone</label>
                                <input type="text" class="col-8 cprofile--input " name="phoneUpdate" id="phone" value="${companyProfile.phone}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errors.companyPhoneLengthError}">
                                        ${errors.companyPhoneLengthError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="cprofile__input row">
                                <label class="col-12 cprofile--label" for="descript">Company Description</label>
                                <div class="col-12">
                                    <textarea name="descriptUpdate" 
                                              class=" cprofile--input cprofile--input-textarea" 
                                              id="descript" cols="30" rows="4" >${companyProfile.company_Description}</textarea>
                                </div>
                                <h5 class="text-danger  text-start ">
                                    <c:if test="${not empty errors.companyDescriptionLegthError}">
                                        ${errors.companyDescriptionLegthError}
                                    </c:if>
                                </h5>
                            </div>
                            <div class=" cprofile-file-input">
                                <label for="inputFile" >
                                    Logo

                                    <div class="input-file" for="inputFile"></div>
                                    <span id="displayResult">${my:getAvatarName(companyProfile.account.avatar)}</span>

                                    <input type="file" name="avatar" class="col-8" value="./avatars/${companyProfile.account.avatar}" id="inputFile" hidden="hidden"  />

                                </label>
                            </div>
                            <c:if test="${not empty errors.companyLogoLengthError}">
                                <h5 class="text-danger offset-4 col-8 text-start">
                                    ${errors.companyLogoLengthError}
                                </h5>
                            </c:if>
                            <c:if test="${not empty errors.companyLogoTypeError}">
                                <h5 class="text-danger offset-4 col-8 text-start">
                                    ${errors.companyLogoTypeError}
                                </h5>
                            </c:if>
                            <div>

                                <label class="cprofile-edit-btn primary-btn" for="ebtAction">
                                    <i class="fas fa-edit"></i>
                                    <input type="submit" class="cprofile-edit--input" value="Edit" id="ebtAction"FF>
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
        <script src="./assets/js/inputfile.js"></script>
        <script>
            CKEDITOR.replace('descript');
        </script>
    </body>
</html>
