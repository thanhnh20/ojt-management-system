<%-- 
    Document   : register2
    Created on : May 25, 2022, 1:59:49 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/register.css">    
        <link rel="stylesheet" href="./assets/css/register-responsive.css">
        <script src="./assets/ckeditor/ckeditor.js"></script>
    </head>
    <body>
        <header class="header ">
            <div class="navbar">
                <a href="loginPage" class="header__logo ">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>
                <a href="loginPage" class="primary-btn ">Login</a>
            </div>
        </header>
        <c:set var="errors" value="${requestScope.ERROR_REGISTER_COMPANY}"/>
        <div class="main">
            <div class="container-fluid ">
                <div class="row ">
                    <div class="container-left offset-xl-1 col-xl-4 offset-md-0 col-md-4">
                        <img src="./assets/img/ojt.png" alt="" class="container-left--img">
                    </div>

                    <div class="container-right offset-1 col-10  offset-xl-1 col-xl-5 offset-md-0 col-md-8">
                        <div class="header-right">COMPANY REGISTRATION</div>
                        <div class="header-right--step">STEP 2: COMPANY INFORMATION </div>
                        <div class="right-form">
                            <form action="RegisterCompanyDetailsController" method="POST" enctype="multipart/form-data" accept-charset="UTF-8">
                                <input type="text" class="right-form__input" name="companyName" value="${requestScope.companyName}" placeholder="Company Name" /> 
                                <c:if test="${not empty errors.companyNameLengthError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyNameLengthError}</div>
                                    </font>
                                </c:if>

                                <select id="city" name="city"  class="right-form__input" >
                                    <option value="" hidden>Choose City</option>
                                    <option value="TP.HCM" <c:if test="${requestScope.city eq 'TP.HCM'}" >
                                            selected="selected"
                                        </c:if> >TP.HCM</option>
                                    <option value="Đồng Nai" <c:if test="${requestScope.city eq 'Đồng Nai'}" >
                                            selected="selected"
                                        </c:if> >Đồng Nai</option>
                                    <option value="Tây Ninh" <c:if test="${requestScope.city eq 'Tây Ninh'}" >
                                            selected="selected"
                                        </c:if> >Tây Ninh</option>
                                    <option value="Bình Dương" <c:if test="${requestScope.city eq 'Bình Dương'}" >
                                            selected="selected"
                                        </c:if> >Bình Dương</option>
                                </select>
                                <c:if test="${not empty errors.companyCityError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyCityError}</div>
                                    </font>
                                </c:if>
                                <input type="text" name="companyAddress" value="${requestScope.companyAddress}" class="right-form__input" placeholder="Company Address" /> 
                                <c:if test="${not empty errors.companyAddressLengthError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyAddressLengthError}</div>
                                    </font>
                                </c:if>
                                <h3 style="margin-top: 20px;color: gray;font-weight: 400">Company Description</h3>
                                <textarea name="companyDescription" class="right-form__input righ-form-textarea" 
                                          placeholder="Company Summary" id="descript" cols="30" rows="3">${requestScope.companyDescription}</textarea>
                                <c:if test="${not empty errors.companyDescriptionLegthError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyDescriptionLegthError}</div>
                                    </font>
                                </c:if>

                                <input type="text" name="companyPhone" value="${requestScope.companyPhone}" class="right-form__input" placeholder="Phone" />
                                <c:if test="${not empty errors.companyPhoneLengthError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyPhoneLengthError}</div>
                                    </font>
                                </c:if>

                                <div class="right-file-input">
                                    <label for="inputFile" >
                                        Logo:
                                        <div class="input-file" for="inputFile"></div>
                                        <span id="displayResult">${requestScope.companyLogo}</span>

                                        <input type="file" name="companyLogo" value="${requestScope.companyLogo}" id="inputFile" hidden="hidden"  />

                                    </label>
                                </div>
                                <c:if test="${not empty errors.companyLogoLengthError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyLogoLengthError}</div>
                                    </font>
                                </c:if>
                                <c:if test="${not empty errors.companyLogoTypeError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.companyLogoTypeError}</div>
                                    </font>
                                </c:if>


                                <div class="end-form-btn">
                                    <input type="hidden" name="companyAccount" value="${verifyEmail}" />
                                    <input type="hidden" name="email" value="${email}" />
                                    <input type="hidden" name="password" value="${password}" />
                                    <input type="submit" class=" primary-btn " name="btAction" value="Sign Up" />
                                </div>

                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <footer class="footer">
            <div class="footer__content">
                <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
            </div>
        </footer>
        <script src="./assets/js/inputfile.js"></script>
        <script>
            CKEDITOR.replace('descript');
        </script>
    </body>
</html>
