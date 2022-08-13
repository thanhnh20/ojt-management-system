<%-- 
    Document   : register1
    Created on : May 25, 2022, 9:38:36 AM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/register.css">
        <link rel="stylesheet" href="./assets/css/register-responsive.css">
        <title>Register</title>
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

        <div class="main">
            <div class="container-fluid">
                <div class="row ">
                    <div class="container-left offset-xl-1 col-xl-4 offset-md-1 col-md-3">
                        <img src="./assets/img/ojt.png" alt="" class="container-left--img">
                    </div>

                    <div class="container-right offset-xl-1 col-xl-5 offset-md-0 col-md-7 offset-2 col-8">
                        <div class="header-right">COMPANY REGISTRATION</div>
                        <div class="header-right--step">STEP 1: LOGIN INFORMATION </div>
                        <div class="right-form">
                            <c:set var="errors" value="${requestScope.ERROR_REGISTER}" />
                            <form action="RegisterCompanyController" method="POST">
                                <input type="text" name="email" value="${param.email}" class="right-form__input" placeholder="Email" 
                                       <c:if test="${not empty sessionScope.ACCOUNT_COMPANY}" >
                                           disabled="disabled"
                                       </c:if>/> 
                                <c:if test="${not empty errors.emailFormatError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.emailFormatError}</div>
                                    </font>
                                </c:if>
                                <c:if test="${not empty errors.emailDuplicateError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.emailDuplicateError}</div>
                                    </font>
                                </c:if>
                                <c:set var="email" value="${param.email}" />
                                <input type="password" name="password" value="${param.password}" class="right-form__input" placeholder="Password" 
                                       <c:if test="${not empty sessionScope.ACCOUNT_COMPANY}" >
                                       disabled="disabled"
                                    </c:if> /> 
                                <c:set var="password" value="${param.password}" />
                                <c:if test="${not empty errors.passwordLengthError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.passwordLengthError}</div>
                                    </font>
                                </c:if>
                                    <input type="password" name="confirmPassword" value="${param.confirmPassword}" class="right-form__input" placeholder="Re-enter Password" 
                                       <c:if test="${not empty sessionScope.ACCOUNT_COMPANY}" >
                                           disabled="disabled"
                                       </c:if> /> 
                                <c:set var="confirmPassword" value="${param.confirmPassword}" />
                                <c:if test="${not empty errors.passwordConfirmError}">
                                    <font style="color: red">
                                    <div class="text-danger">${errors.passwordConfirmError}</div><br />
                                    </font>
                                </c:if>
                                <input type="submit" value="Verify Email" name="btAction" class="right-form__btn primary-btn" 
                                       <c:if test="${not empty sessionScope.ACCOUNT_COMPANY}" var="verify"> 
                                           hidden="hidden"
                                       </c:if>
                                       />
                            </form>
                            <c:set var="verifyEmail" value="${sessionScope.ACCOUNT_COMPANY}"/>
                            <c:if test="${not empty verifyEmail}">
                                <form action="EmailVerificationController" method="POST">
                                    <input type="text" name="varification" value="${param.varification}" class="right-form__input" placeholder="Verify Code" />
                                    <c:if test="${not empty requestScope.SUCCESS_VERIFY}">
                                        <font style="color: green"> 
                                        <div class="text-success">${requestScope.SUCCESS_VERIFY}</div>
                                        </font>
                                    </c:if>
                                    <c:if test="${not empty requestScope.FAIL_VERIFY}">
                                        <font style="color: red">
                                        <div class="text-danger">${requestScope.FAIL_VERIFY}</div><br />
                                        </font>
                                    </c:if>
                                    <input type="hidden" name="email" value="${email}" />
                                    <input type="hidden" name="password" value="${password}" />
                                    <input type="hidden" name="confirmPassword" value="${confirmPassword}" />
                                    <input type="submit" value="Confirm" name="btAction" class="right-form__btn primary-btn" />
                                </form>
                                <c:if test="${not empty requestScope.SUCCESS_VERIFY}">
                                    <form action="RegisterPage2JSP" method="POST">
                                        <input type="hidden" name="email" value="${email}" />
                                        <input type="hidden" name="password" value="${password}" />
                                        <input type="hidden" name="confirmPassword" value="${confirmPassword}" />
                                        <input type="submit" value="Next" name="btAction" class="right-form__btn primary-btn" />
                                    </form>
                                </c:if>
                            </c:if>
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
    </body>
</html>
