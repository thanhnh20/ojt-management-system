<%-- 
    Document   : studentReviewPage
    Created on : May 31, 2022, 8:39:47 AM
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
        <title>Student - Review</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/student.css">
        <link rel="stylesheet" href="./assets/css/student-responsive.css">

    </head>
    <body>
        <header></header>

        <c:set var="student" value="${sessionScope.STUDENT_ROLE}"/>
        <div class="navbar navbar-expand-md navbar-dark text-center navbar-sm-cus">
            <div class="container-fluid">
                <a href="ShowStudentHomeController" class="header__logo ">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa-solid fa-bars nav__respo--btn"></i>
                </button>
                <div class="collapse navbar-collapse navbar-collapse-cus" id="navbarSupportedContent">
                    <a href="ShowStudentProfileController" class=" nav__infor--link text-truncate text-center">
                        <i class="fas fa-user-circle nav__infor--icon"></i>
                        ${student.account.name}
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a href="studentDashboardController" class="nav__item--link">
                                <i class="fas fa-palette "></i>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="ShowStudentProfileController" class="nav__item--link">
                                <i class="fas fa-user-edit"></i>
                                My Profile
                            </a> 
                        </li>
                        <li class="nav-item nav__items">
                            <div  class="nav__item--link nav__item--dropdown">
                                <i class="fas fa-pen"></i>
                                My Jobs
                                <i class="fas fa-angle-down icon-down"></i>
                            </div>
                            <div class="nav__item__dropdown">
                                <a href="SearchSaveJobController" class="nav__item__dropdown--link">
                                    Saved Jobs
                                </a>
                                <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link">
                                    Applied Jobs
                                </a>
                            </div>
                        </li>

                        <li class="nav-item">
                            <a href="ReviewInternShipController" class="nav__item--link link-active">
                                <i class="fas fa-poll-h"></i>
                                Review Internship
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
                <a href="ShowStudentHomeController" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="ShowStudentProfileController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${student.account.name}
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="studentDashboardController" class="nav__item--link">
                            <i class="fas fa-palette "></i>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="ShowStudentProfileController" class="nav__item--link">
                            <i class="fas fa-user-edit"></i>
                            My Profile
                        </a>
                    </li>
                    <li class="nav__items">
                        <div  class="nav__item--link nav__item--dropdown">
                            <i class="fas fa-pen"></i>
                            My Jobs
                            <i class="fas fa-angle-down icon-down"></i>
                        </div>
                        <div class="nav__item__dropdown">
                            <c:url var="urlSaveJob" value="SearchSaveJobController">
                                <c:param name="studentCode" value="${student.studentCode}"/>
                            </c:url>
                            <a href="${urlSaveJob}" class="nav__item__dropdown--link">
                                Saved Jobs
                            </a>
                            <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link">
                                Applied Jobs
                            </a>
                        </div>
                    </li>
                    <li class="nav__items">
                        <a href="ReviewInternShipController" class="nav__item--link link-active">
                            <i class="fas fa-poll-h"></i>
                            Review Internship
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

            <%--Get Student Profile--%>   
            <c:set var="studentProfile" value="${requestScope.STUDENT_PROFILE}"/>
            <%--Get Student Application--%>  
            <c:set var="studentApp" value="${requestScope.STUDENT_APPLICATION}"/>
            <%--Get Company profile --%>  
            <c:set var="companyProfile" value="${requestScope.COMPANY_PROFILE}"/>
            <%--Get Company Post --%>  
            <c:set var="companyPost" value="${requestScope.COMPANY_POST_APPLIED_BY_STUDENT}"/>                     


            <div class="main-body  offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-review offset-3 col-6">
                        <div class="main-body-review__header">
                            Personal Review
                        </div>
                        <c:if test="${not empty studentApp}">

                            <div class="review__input row">
                                <div class="col-4 review--label" >Student Code</div>
                                <div class="col-8 review--input " >${studentProfile.studentCode}</div>
                            </div>
                            <div class="review__input row">
                                <div class="col-4 review--label" >Full Name</div>
                                <div class="col-8 review--input " >${studentProfile.account.name}</div>
                            </div>
                            <div class="review__input row">
                                <div class="col-4 review--label" >Birthdate</div>
                                <div class="col-8 review--input" >${my:changeDateFormat(studentProfile.birthDay)}</div>
                            </div>
                            <div class="detail_internship" disabled="true">
                                <div class="review__input row">
                                    <div class="col-4 review--label" >Company Internship</div>
                                    <div class="col-8 review--input ">${companyProfile.account.name}</div>
                                </div>
                                <div class="review__input row">
                                    <div class="col-4 review--label" >Type Job</div>
                                    <div class="col-8 review--input ">${companyPost.title_Post}</div>
                                </div>
                               
                                <div class="review__input row ">
                                    <div class="col-4 review--label" >Comment</div> <br>
                                    <!-- review--input--evaluate-->
                                    <div class="col-8 review--input" >
                                        <c:if test="${studentApp.student.isIntern eq 2}" >
                                            ${studentApp.evaluation}
                                        </c:if>
                                    </div>
                                </div>
                                <div class="review__input row">

                                    <div class="col-4 review--label" >Mark</div>

                                    <div class="col-8 review--input">
                                        <c:if test="${studentApp.student.isIntern eq 2}">
                                            ${studentApp.grade}
                                        </c:if>
                                    </div>
                                </div>
                                <div class="review__input row">

                                    <div class="col-4 review--label" >Status</div>

                                    <div class="col-8 review--input">
                                        <c:if test="${studentApp.isPass eq -1 and studentApp.student.isIntern eq 2}">
                                            <strong class="text-danger">
                                                Not Pass
                                            </strong>
                                        </c:if>
                                        <c:if test="${studentApp.isPass eq 1 and studentApp.student.isIntern eq 2}">
                                            <strong class="text-success">
                                                Passed
                                            </strong>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                        </c:if>
                    </div>
                </div>
                <c:if test="${empty studentApp}">

                    <div class="text-center " style="font-size: 20px; margin: auto;" for="">
                        You are not intern at any company                           
                    </div>

                    <%--Recomment Jobs--%>

                    <div class="recom-jobs">
                        <div class="recom-header">
                            Recommended Jobs
                        </div>

                        <div class="row row-cols-xl-2 row-cols-1">
                            <c:forEach items="${requestScope.LIST_RECOMMEND_POST}" var="recommendPost" begin="0" end="5">
                                <c:set var="majorID" value="${recommendPost.major.majorID}"/>
                                <div class="col">
                                    <div class="recom-box row ">
                                        <a href="HomeShowCompanyDetailController?postID=${recommendPost.postID}">
                                            <span>

                                                <h3>${recommendPost.title_Post}</h3>
                                                <h4>${recommendPost.company.account.name}</h4>
                                            </span>
                                        </a>
                                        <div class="recom-box__img col-4">
                                            <img src="./avatars/${recommendPost.company.account.avatar}" alt="${recommendPost.company.account.avatar}">
                                        </div>
                                        <div class="recom-box-content col-8">
                                            <p>Vacancy: ${recommendPost.vacancy}</p>
                                            <p>Quantity: ${recommendPost.quantityIterns}</p>
                                            <p>Location: ${recommendPost.workLocation}</p>
                                            <p>Expiration Date: ${my:changeDateFormat(recommendPost.expirationDate)}</p>

                                            <c:set var="listFollowingPost" value="${requestScope.LIST_FOLLOWING_POST}"/>
                                            <c:set var="statusFollowingPost" value="${my:getStatusSaveJob(listFollowingPost, recommendPost.postID)}"/>
                                            <p>
                                                <c:if test="${statusFollowingPost eq true}">
                                                <form action="StudentDeleteSaveJobController" method="POST">
                                                    <input type="hidden" name="postID" value="${recommendPost.postID}" />
                                                    <input type="hidden" name="unSave" value="studentDashboardPage" />
                                                    <label for="unsaveJob+${recommendPost.postID}" class="far fa-heart save-btn save-btn-active"> </label>
                                                    <input type="submit" value="Unsave Job" id="unsaveJob+${recommendPost.postID}" hidden />
                                                </form>
                                            </c:if>
                                            <c:if test="${statusFollowingPost eq false}">
                                                <form action="StudentSaveJobController" method="POST">
                                                    <input type="hidden" name="postID" value="${recommendPost.postID}" />
                                                    <label for="saveJob+${recommendPost.postID}" class="far fa-heart save-btn "> </label>
                                                    <input type="submit" value="Save Job" id="saveJob+${recommendPost.postID}" hidden/>
                                                </form>
                                            </c:if>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="recom__see-more--btn">
                            <c:url var="urlSearchHome" value="SearchCompanyStudentHomeController">
                                <c:param name="nameCompany" value=""/>
                                <c:param name="nameMajor" value="${majorID}"/>
                                <c:param name="nameLocation" value=""/>
                            </c:url>
                            <a href="${urlSearchHome}" class="recom--more--btn ">
                                See More 
                                <i class="fas fa-arrow-right"></i>
                            </a> 
                        </div>

                    </div>
                </c:if>
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
