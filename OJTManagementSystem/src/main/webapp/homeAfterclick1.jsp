<%-- 
    Document   : homeAfterckick1
    Created on : May 29, 2022, 7:49:15 AM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home - Student Information</title>
         <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/home.css">
    </head>
    <body>
        <c:set var="student" value="${sessionScope.STUDENT_ROLE}"/>
        <header class="header ">
            <div class="navbar header__nav_cus">
                <a href="ShowStudentHomeController" class="header__logo">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>
                <div class="header__name">
                    <div class="header__name--show">
                        Hi, ${student.account.name}
                        <i class="fas fa-angle-down icon-down"></i>
                    </div>
                    <div class="header__name--hidden">
                        <a href="studentDashboardController" class="header__name--hidden-content">Dashboard</a>
                        <a href="logoutController" class="header__name--hidden-content">Logout</a>
                    </div>
                </div>

            </div>

        </header>

        <form action="ApplyCVStudentConTroller" method="POST" enctype="multipart/form-data">
            <main class="main">
                <c:set var="companyPost" value="${requestScope.POST_COMPANY_INFOR}" />
                <div class="main-body">
                    <div class="main-body-cViewStu">
                        <c:if test="${not empty companyPost.postID}">
                            <input type="hidden" name="postID" value="${companyPost.postID}" />
                        </c:if>
                        <c:if test="${empty companyPost.postID}">
                            <input type="hidden" name="postID" value="${requestScope.POST_ID}" />
                        </c:if>
                        <h1 class="main-body-cViewStu__header">
                            ${companyPost.company.account.name} 
                        </h1>

                        <div class="row">
                            <div class="inforStu-left col-3">
                                <font class="inforStu-right__header">
                                Student Information
                                </font>
                                <c:if test="${not empty student.account.avatar}">
                                    <img src="./avatars/${student.account.avatar}" alt="" class="inforStu-left--img">
                                </c:if>
                                <c:if test="${empty student.account.avatar}"> 
                                    <img src="./assets/img/person.jpg" alt="" class="inforStu-left--img">
                                </c:if>
                                <div class="inforStu-left__content">
                                    <p>Name: ${student.account.name} </p>
                                    <p>Date of Birth: ${my:changeDateFormat(student.birthDay)}</p>
                                    <p>Gender: <c:if test="${student.gender eq true}">
                                            Male
                                        </c:if>
                                        <c:if test="${student.gender eq false}">
                                            Female
                                        </c:if>
                                    </p>
                                    <p>Address: ${student.address}</p>
                                    <p>Email: ${student.account.email}</p>    
                                </div>
                            </div>

                            <div class="inforStu-right col-9">
                                <div class="inforStu-right__header">
                                    Career Information
                                </div>
                                <c:set var="application" value="${requestScope.APPLICATION_INFORMATION}" />
                                <div class="inforStu-right__content">
                                    <c:set var="errors" value="${requestScope.ERRORS}"/>

                                    <div class ="row hPage-stuAppl-input"> 
                                        <label for="expectJob" class="col-3">Expected Job</label>
                                        <div class="col-9"> 
                                            <input type="text" name="txtExpectedJob" value="${application.expected_job}" id="expectJob" class="hPage-stuAppl--input"/>
                                            <c:if test="${not empty errors.expectedJobLengthError}" >
                                                <h5 class="text-red">
                                                    ${errors.expectedJobLengthError}
                                                </h5>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div class ="row hPage-stuAppl-input"> 
                                        <label for="technology" class="col-3">Main Skill</label>
                                        <div class="col-9"> 
                                            <input type="text" name="txtTechnology" value="${application.technology}" id="technology" class="hPage-stuAppl--input"/>
                                            <c:if test="${not empty errors.technologyLengthError}" >
                                                <h5 class="text-red">
                                                    ${errors.technologyLengthError}
                                                </h5>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div class ="row hPage-stuAppl-input"> 
                                        <label for="experience" class="col-3">Experience</label>
                                        <div class="col-9"> 
                                            <select name="txtExperience" id="experience" class="hPage-stuAppl--input">
                                                <option value="Chưa có kinh nghiệm" <c:if test="${application.experience eq 'Chưa có kinh nghiệm'}">
                                                        selected="selected"
                                                    </c:if>>Chưa có kinh nghiệm</option>
                                                <option value="1 năm" <c:if test="${application.experience eq '1 năm'}">
                                                        selected="selected"
                                                    </c:if>>1 năm</option>
                                                <option value="2 năm" <c:if test="${application.experience eq '2 năm'}">
                                                        selected="selected"
                                                    </c:if>>2 năm</option>
                                                <option value="Trên 3 năm" <c:if test="${application.experience eq 'Trên 3 năm'}">
                                                        selected="selected"
                                                    </c:if>>Trên 3 năm</option>
                                            </select> 
                                        </div>
                                    </div>

                                    <div class ="row hPage-stuAppl-input"> 
                                        <label for="foreign" class="col-3">Foreign Language</label>
                                        <div class="col-9"> 
                                            <input type="text" name="txtForeignLanguage" value="${application.foreign_Language}" id="foreign" class="hPage-stuAppl--input"/>
                                            <c:if test="${not empty errors.foreignLanguageLengthError}" >
                                                <h5 class="text-red">
                                                    ${errors.foreignLanguageLengthError}
                                                </h5>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div class ="row hPage-stuAppl-input"> 
                                        <label for="otherskill" class="col-3">Other Skills</label>
                                        <div class="col-9"> 
                                            <input type="text" name="txtOtherSkills" value="${application.otherSkills}" id="otherskill" class="hPage-stuAppl--input" />
                                            <c:if test="${not empty errors.otherSkillsLengthError}" >
                                                <h5 class="text-red">
                                                    ${errors.otherSkillsLengthError}
                                                </h5>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div class="file-input">
                                        <label for="inputFile" >
                                            Your CV: 
                                            <div class="input-file" for="inputFile"></div>
                                            <span id="displayResult"></span>
                                        </label>
                                        <input type="file" id="inputFile" name="myfile" value="" hidden="hidden">
                                        <h5 class="text-red">
                                            <br/>
                                            <c:if test="${not empty errors.fileUploadError}" >
                                                ${errors.fileUploadError}
                                            </c:if>
                                            <c:if test="${not empty errors.fileUploadTypeError}">
                                                ${errors.fileUploadTypeError}
                                            </c:if>
                                            <c:if test="${not empty errors.fileUploadLengthError}" >
                                                ${errors.fileUploadLengthError}
                                            </c:if>
                                        </h5>
                                    </div>

                                    <div class="text-center">
                                        <c:if test="${not empty errors.studentInformationError}" >
                                            <font class="text-danger">
                                            ${errors.studentInformationError}

                                            </font>                                           
                                            <div>
                                                <c:url value="ShowStudentProfileController" var="url">
                                                    <c:param name="postID" value="${requestScope.POST_ID}"/>
                                                </c:url> 
                                                <a href="${url}">Click here to update your information</a>
                                            </div>
                                        </c:if>
                                    </div>
                                    <c:set var="errorCompanyPost" value="${requestScope.ERROR_COMPANY_POST}"/>
                                    <c:if test="${not empty errorCompanyPost}" >
                                        <h3 class ="text-red text-center"> 
                                            <c:if test="${not empty errorCompanyPost.expirationDateError}" >
                                                <font>
                                                ${errorCompanyPost.expirationDateError}</br>
                                                </font>
                                            </c:if>
                                            <c:if test="${not empty errorCompanyPost.appliedTwoTimeError}" >
                                                <font>
                                                ${errorCompanyPost.appliedTwoTimeError}</br>
                                                </font>
                                            </c:if>
                                            <c:if test="${not empty errorCompanyPost.appliedJobStudentWorkingError}" >
                                                <font >
                                                ${errorCompanyPost.appliedJobStudentWorkingError}</br>
                                                </font>
                                            </c:if>
                                            <c:if test="${not empty errorCompanyPost.studentCompletedError}" >
                                                <font >
                                                ${errorCompanyPost.studentCompletedError}</br>
                                                </font>
                                            </c:if>
                                        </h3>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="fix--btn">
                    <c:if test="${not empty ERROR_RUN_OUT_QUANTITY_INTERNS}" >
                        ${ ERROR_RUN_OUT_QUANTITY_INTERNS}
                    </c:if>
                    <input type="submit" value="Apply Now" name="btAction" 
                           <c:if test="${not empty errorCompanyPost or not empty errors.studentInformationError}">
                               disabled style="opacity: 0.4" class="primary-btn upload-btn" 
                           </c:if>                              
                           <c:if test="${ empty errorCompanyPost and empty errors.studentInformationError}">
                               class="primary-btn edit-btn" 
                           </c:if>                              
                           >
                    <c:url var="linkOther" value="HomeShowCompanyDetailController">
                        <c:param name="postID" value="${companyPost.postID}"/>
                    </c:url>
                    <a href="${linkOther}" class= " primary-btn exit-btn">Exit</a>
                </div>
            </main>
        </form>
        <footer class="footer">
            <div class="footer__content">
                <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
            </div>
        </footer>
        <script src="./assets/js/inputfile.js"></script>
    </body>
</html>
