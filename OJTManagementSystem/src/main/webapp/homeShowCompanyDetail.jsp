<%-- 
    Document   : HomeShowCompanyDetail
    Created on : May 30, 2022, 12:23:51 AM
    Author     : Thanh Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home - Post Detail</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/home.css">
        <link rel="stylesheet" href="./assets/css/home-responsive.css">
    </head>
    <body>
        <header class="header ">
            <div class="navbar header__nav_cus">
                <a href="ShowStudentHomeController" class="header__logo">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>
                <c:set var="student" value="${sessionScope.STUDENT_ROLE}" />
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
        <c:set var="postDetail" value="${requestScope.POST_DETAIL}"/>

        <main class="main">
            <div class="main__search">
                <h2>Search Jobs</h2>
                <form action="SearchCompanyStudentHomeController" class="main__search-form" method="post">
                    <div class="row">
                        <div class="col-4">
                            <select name="nameCompany">
                                <option value=""hidden>Company Name</option>
                                <option value="">All Company </option>
                                <c:forEach items="${requestScope.COMPANY_NAME}" var="company">
                                    <option value="${company.companyID}" <c:if test="${company.companyID eq companyID}">
                                            selected="selected"
                                        </c:if> >${company.account.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <select name="nameMajor">
                                <option value=""hidden>Major</option>
                                <option value="">All Major</option>
                                <c:forEach items="${requestScope.LIST_NAME_MAJOR}" var="major">
                                    <option value="${major.majorID}" <c:if test="${major.majorID eq majorID}">
                                            selected="selected"
                                        </c:if> >${major.majorName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <select id="city" name="nameLocation"  class="main__search_select" >
                                <option value="" hidden>Location</option>
                                <option value="">All Location</option>
                                <option value="TP.HCM">TP.HCM</option>
                                <option value="Đồng Nai">Đồng Nai</option>
                                <option value="Tây Ninh">Tây Ninh</option>
                                <option value="Bình Dương">Bình Dương</option>
                            </select>
                        </div>
                        <div class="col-2">
                            <input type="submit" value="Search" class="primary-btn">
                        </div>
                    </div>
                </form>
            </div>


            <div class="hComApplDetail row">
                <div class="col-md-7">
                    <div class="hComApplDetail__left">
                        <div class="hComApplDetail--header">${postDetail.company.account.name}</div>
                        <div class="hComApplDetail__left--header">
                            Application Information:
                        </div>
                        <div class="hComApplDetail__left--content">
                            <p><strong>Job:</strong> ${postDetail.title_Post}</p>
                            <p><strong>Vacancy:</strong> ${postDetail.vacancy} </p>
                            <p><strong>Quantity:</strong> ${postDetail.quantityIterns}</p>
                            <p><strong>Job Description:</strong> <br>
                                ${postDetail.job_Description}
                            </p>
                            <p><strong>Job requirements:</strong> <br>
                                ${postDetail.job_Requirement}           
                            </p>
                            <p><strong>Remuneration:</strong> <br>
                                ${postDetail.remuneration}
                            </p>
                            <p><strong>Work location:</strong>
                                ${postDetail.workLocation}
                            </p>
                            <p><strong>Posting Date:</strong>
                                ${my:changeDateFormat(postDetail.postingDate)}
                            </p>
                            <p><strong>Expiration Date:</strong>
                                ${my:changeDateFormat(postDetail.expirationDate)}
                            </p>
                        </div>
                        <c:set var="errorCompanyPost" value="${requestScope.ERROR_COMPANY_POST}" />
                        <c:if test="${empty errorCompanyPost}" >
                            <div class="hComApplDetail-btn">
                                <a href="ShowApplyCVController?postID=${postDetail.postID}" class="primary-btn hComApplDetail-btn--app">Apply Now</a>
                                <c:set var="statusFollowing" value="${my:getStatusSaveJob(requestScope.LIST_FOLLOWING_POST, postDetail.postID)}" />
                                <c:if test="${statusFollowing eq true}">
                                    <form action="StudentDeleteSaveJobController" method="POST">
                                        <input type="hidden" name="page" value="${requestScope.page}">
                                        <input type="hidden" name="unSave" value="homeShowCompanyDetail" />
                                        <input type="hidden" name="postID" value="${postDetail.postID}" />
                                        <label for ="left-unsaveJob" class="far fa-heart hComApplDetail-btn-save save-btn save-btn-active "> </label>
                                        <input type="submit" value="Unsave Job" hidden id="left-unsaveJob" />
                                    </form>
                                </c:if>
                                <c:if test="${statusFollowing eq false}">
                                    <form action="StudentSaveJobController" method="POST">
                                        <input type="hidden" name="page" value="${requestScope.page}">
                                        <input type="hidden" name="save" value="homeShowCompanyDetail" />
                                        <input type="hidden" name="postID" value="${postDetail.postID}" />
                                        <label for ="left-saveJob" class="far fa-heart hComApplDetail-btn-save save-btn "> </label>
                                        <input type="submit" value="Save Job" id="left-saveJob" hidden/>
                                    </form>
                                </c:if>
                            </div>
                        </c:if>
                        <c:if test="${not empty errorCompanyPost}" >
                            <h5 class ="text-red"> 
                                <c:if test="${not empty errorCompanyPost.quantitytInternsNotEngough}" >
                                    <font>
                                    ${errorCompanyPost.quantitytInternsNotEngough}</br>
                                    </font>
                                </c:if>
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
                            </h5>
                            <a href="ShowApplyCVController?postID=${postDetail.postID}" class="primary-btn hComApplDetail-btn--app" 
                               style="pointer-events: none; opacity: 0.5">Apply Now</a>
                        </c:if>
                    </div>
                </div>
                <c:set var="listOtherCompanies" value="${requestScope.LIST_OTHER_COMPANIES}"/>
                <c:set var="sizeOfList" value="${requestScope.SIZE_OF_LIST}"/>
                <div class="col-md-5">
                    <div class="hComApplDetail__right">
                        <div class="hComApplDetail__right--header">
                            Other Company
                        </div>

                        <c:if test="${sizeOfList eq 1}">
                            <h4 class="text-center text-white" style="margin-top: 20px">
                                Currently, the system has only one job that you are looking at!!
                            </h4>
                        </c:if>    

                        <c:if test="${sizeOfList gt 1}">
                            <div class="hComApplDetail__right--body row row-cols-1">
                                <c:forEach items="${listOtherCompanies}" var="postOther" varStatus="counter">
                                    <c:if test="${postOther.postID ne postDetail.postID}">
                                        <c:url var="linkOther" value="HomeShowCompanyDetailController">
                                            <c:param name="postID" value="${postOther.postID}"/>
                                        </c:url>
                                        <div class="col" style="position: relative;">
                                            <a href="${linkOther}" class="hComApplDetail__right--card">
                                                <div class="row">
                                                    <div class="col-4 right-card_img">
                                                        <img src="./avatars/${postOther.company.account.avatar}" class="right--card-img"/>
                                                    </div>
                                                    <div class="col-8 right-card-content">
                                                        <div class="right--card-header">${postOther.company.account.name}</div>
                                                        <div class="right--card-body">
                                                            <p>Job: ${postOther.title_Post}</p>
                                                            <p>Quantity: ${postOther.quantityIterns}</p>
                                                            <p>Location: ${postOther.workLocation}</p>
                                                            <p>Date: ${my:changeDateFormat(postOther.expirationDate)}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </a>
                                            <div class="right--card-save">
                                                <c:set var="statusFollowing" value="${my:getStatusSaveJob(requestScope.LIST_FOLLOWING_POST, postOther.postID)}" />
                                                <c:if test="${statusFollowing eq true}">
                                                    <form action="StudentDeleteSaveJobController" method="POST">
                                                        <input type="hidden" name="page" value="${requestScope.page}">
                                                        <input type="hidden" name="unSave" value="homeShowCompanyDetail" />
                                                        <input type="hidden" name="postIDOther" value="${postOther.postID}" />
                                                        <input type="hidden" name="postID" value="${postDetail.postID}" />
                                                        <label for ="right-unsaveJob+${postOther.postID}" class="far fa-heart right--card-save save-btn save-btn-active "> </label>
                                                        <input type="submit" value="Unsave Job" hidden id ="right-unsaveJob+${postOther.postID}" />
                                                    </form>
                                                </c:if>
                                                <c:if test="${statusFollowing eq false}">
                                                    <form action="StudentSaveJobController" method="POST">
                                                        <input type="hidden" name="page" value="${requestScope.page}">
                                                        <input type="hidden" name="save" value="homeShowCompanyDetail" />
                                                        <input type="hidden" name="postIDOther" value="${postOther.postID}" />
                                                        <input type="hidden" name="postID" value="${postDetail.postID}" />
                                                        <label for ="right-saveJob+${postOther.postID}" class="far fa-heart right--card-save save-btn "> </label>
                                                        <input type="submit" value="Save Job" hidden id ="right-saveJob+${postOther.postID}"/>
                                                    </form>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>

                            <div class="main__pagination">
                                <ul class="pagination main_cus__pagination">
                                    <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                    <c:if test="${requestScope.page gt 5 }">
                                        <li class="page-item" >
                                            <form action="HomeShowCompanyDetailController" method="POST">
                                                <input type="hidden" name="page" value="${map['startNum'] - 1}">
                                                <input type="hidden" name="postID" value="${postDetail.postID}">
                                                <input type="submit" value="Previous" class="page-link <c:if test="${i eq requestScope.page}">
                                                       pagination-active
                                                    </c:if>">
                                            </form>
                                        </li>
                                        <!--đưa icon vào-->
                                    </c:if>

                                    <c:forEach var="i" begin="${ map['startNum']}" end="${ map['lastNum']}">
                                        <c:set var="step" value="${i - requestScope.numberPage}" />
                                        <c:choose>
                                            <c:when test="${ step le 0}">
                                                <li class="page-item" >
                                                    <form action="HomeShowCompanyDetailController" method="POST">
                                                        <input type="hidden" name="page" value="${i}">
                                                        <input type="hidden" name="postID" value="${postDetail.postID}">
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>">
                                                    </form>
                                                </li>
                                            </c:when>
                                            <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                                <li class="page-item" >  
                                                    <form action="HomeShowCompanyDetailController" method="POST">
                                                        <input type="hidden" name="page" value="${i}">
                                                        <input type="hidden" name="postID" value="${postDetail.postID}">
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>">
                                                    </form>
                                                </li>
                                            </c:when>
                                            <c:when test="${ i eq requestScope.page and step le 0 }">
                                                <li class="page-item" >    
                                                    <form action="HomeShowCompanyDetailController" method="POST">
                                                        <input type="hidden" name="page" value="${i}">
                                                        <input type="hidden" name="postID" value="${postDetail.postID}">
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>">
                                                    </form>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${step le 0}">
                                                    <li class="page-item" >
                                                        <form action="HomeShowCompanyDetailController" method="POST">
                                                            <input type="hidden" name="page" value="${i}">
                                                            <input type="hidden" name="postID" value="${postDetail.postID}">
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>">
                                                        </form>
                                                    </li>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${step lt 0}">
                                        <li class="page-item" >
                                            <form action="HomeShowCompanyDetailController" method="POST">
                                                <input type="hidden" name="page" value="${map['lastNum'] + 1}">
                                                <input type="hidden" name="postID" value="${postDetail.postID}">
                                                <input type="submit" value="Next" class="page-link">
                                            </form>
                                        </li>
                                        <!--đưa icon vào-->
                                    </c:if>
                                   
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>

            </div>
            <div class="hCompanyInfor">
                <div class="hCompanyInfor__header">
                    Company Information
                </div>
                <div class="hCompanyInfor__body">
                    <div class="row">
                        <div class="col-md-3">
                            <img src="./avatars/${postDetail.company.account.avatar}" alt="" >
                            <h3>${postDetail.company.account.name}</h3>
                        </div>
                        <div class="col-md-9 hCompanyInfor__content">
                            ${postDetail.company.company_Description}    
                        </div>
                    </div>
                </div>
            </div>
        </main>


        <footer class="footer">
            <div class="footer__content">
                <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
            </div>
        </footer>
    </body>
</html>
