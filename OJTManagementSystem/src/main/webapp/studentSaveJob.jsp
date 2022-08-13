<%-- 
    Document   : studentSaveJob
    Created on : May 29, 2022, 1:19:22 PM
    Author     : ThanhTy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Student - Save Jobs</title>
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
                            <a href="studentDashboardController" class="nav__item--link ">
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
                            <div  class="nav__item--link nav__item--dropdown link-active">
                                <i class="fas fa-pen"></i>
                                My Jobs
                                <i class="fas fa-angle-down icon-down"></i>
                            </div>
                            <div class="nav__item__dropdown">
                                <a href="SearchSaveJobController" class="nav__item__dropdown--link link-active">
                                    Saved Jobs
                                </a>
                                <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link">
                                    Applied Jobs
                                </a>
                            </div>
                        </li>

                        <li class="nav-item">
                            <a href="ReviewInternShipController" class="nav__item--link">
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
            <c:set var="page" value="${requestScope.page}"/>
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
                        <div  class="nav__item--link nav__item--dropdown link-active">
                            <i class="fas fa-pen"></i>
                            My Jobs
                            <i class="fas fa-angle-down icon-down"></i>
                        </div>
                        <div class="nav__item__dropdown">
                            <a href="SearchSaveJobController" class="nav__item__dropdown--link link-active">
                                Saved Jobs
                            </a>
                            <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link">
                                Applied Jobs
                            </a>
                        </div>
                    </li>
                    <li class="nav__items">
                        <a href="ReviewInternShipController" class="nav__item--link">
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

            <div class="main-body   offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-save ">
                        <div class="main-body-save__header">
                            Save Jobs
                        </div>

                        <div class="main-body-save__search">
                            <form action="SearchSaveJobController" method="POST" >
                                <div class="row">
                                    <div class="col-4">
                                        <input type="text" name="txtJob" value="${param.txtJob}" id="" placeholder="Job" class="student--input">
                                    </div>
                                    <div class="col-4">
                                        <select id="city" name="txtCompany"  class="student--select" >
                                            <option value="" hidden>Company</option>
                                            <c:if test="${requestScope.SIZE_OF_LIST eq 0}">
                                                <option value="" disabled="disabled">No Company to choose</option>
                                            </c:if>
                                            <c:if test="${requestScope.SIZE_OF_LIST gt 1}">
                                                <option value="" >All Company</option>
                                            </c:if>
                                            <c:forEach var="allCompany" items="${requestScope.LIST_ALL_COMPANY}">
                                                <option value="${allCompany.account.name}"
                                                        <c:if test="${allCompany.account.name eq param.txtCompany}">
                                                            selected="selected"
                                                        </c:if>
                                                        >
                                                    ${allCompany.account.name}                                                   
                                                </option>  
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-2">
                                        <select id="city" name="nameLocation" class="student--select"  >
                                            <option value=""hidden>Location</option>
                                            <option value="">All Location</option>
                                            <option value="TP.HCM" <c:if test="${param.nameLocation eq 'TP.HCM'}">
                                                    selected="selected"
                                                </c:if>>TP.HCM</option>
                                            <option value="Đồng Nai" <c:if test="${param.nameLocation eq 'Đồng Nai'}">
                                                    selected="selected"
                                                </c:if>>Đồng Nai</option>
                                            <option value="Tây Ninh" <c:if test="${param.nameLocation eq 'Tây Ninh'}">
                                                    selected="selected"
                                                </c:if>>Tây Ninh</option>
                                            <option value="Bình Dương" <c:if test="${param.nameLocation eq 'Bình Dương'}">
                                                    selected="selected"
                                                </c:if>>Bình Dương</option>
                                        </select>
                                    </div>
                                    <div class="col-2">
                                        <input type="submit" value="Search" class="student-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="main-body-save__content">
                            <div class="resultpage__header">
                                Result : ${requestScope.SIZE_OF_LIST}
                            </div>
                            <c:set var="result" value="${requestScope.LIST_SAVED_POSTS_RESULT}"/>
                            <c:if test="${not empty result}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>Title Job</th>
                                            <th>Company</th>
                                            <th>Location</th>
                                            <th>Creation Date</th>
                                            <th>Expiration Date</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach items="${result}" var="post" varStatus="counter">
                                            <c:set var="student1" value="${requestScope.STUDENT_CODE}" />
                                            <tr>
                                                <td>${my:counter(requestScope.page, counter.count)}</td>
                                                <td>
                                                    <a href="HomeShowCompanyDetailController?postID=${post.postID}">${post.getTittle_Post()}</a>
                                                </td>
                                                <td>${post.companyName}</td>
                                                <td>${post.workLocation}</td>
                                                <td>${my:changeDateFormat(post.postingDate)}</td>
                                                <td>${my:changeDateFormat(post.exprirationDate)}</td>

                                                <td>
                                                    <form action="StudentDeleteSaveJobController" method="Post">
                                                        <input type="hidden" name="postID" value="${post.postID}" />
                                                        <input type="submit" value="Unsave" class="btn-regular-red" />
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                
                                <div class="main__pagination">
                                    <ul class="pagination main_cus__pagination">
                                        <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                        <c:if test="${requestScope.page gt 5 }">
                                            <li class="page-item" >
                                                <form action="SearchSaveJobController" method="POST">
                                                    <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                                    <input type="hidden" name="txtJob" value="${param.txtJob}"/>
                                                    <input type="hidden" name="txtCompany" value="${param.txtCompany}"/>
                                                    <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                    <input type="submit" value="Previous" class="page-link"/>
                                                </form>
                                            </li>
                                            <!--đưa icon vào-->
                                        </c:if>

                                        <c:forEach var="i" begin="${ map['startNum']}" end="${ map['lastNum']}">
                                            <c:set var="step" value="${i - requestScope.numberPage}" />
                                            <c:choose>
                                                <c:when test="${ step le 0}">
                                                    <li class="page-item" >
                                                        <form action="SearchSaveJobController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="txtJob" value="${param.txtJob}"/>
                                                            <input type="hidden" name="txtCompany" value="${param.txtCompany}"/>
                                                            <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>"/>
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                                    <li class="page-item" >  
                                                        <form action="SearchSaveJobController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="txtJob" value="${param.txtJob}"/>
                                                            <input type="hidden" name="txtCompany" value="${param.txtCompany}"/>
                                                            <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>"/>
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:when test="${ i eq requestScope.page and step le 0 }">
                                                    <li class="page-item" >    
                                                        <form action="SearchSaveJobController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="txtJob" value="${param.txtJob}"/>
                                                            <input type="hidden" name="txtCompany" value="${param.txtCompany}"/>
                                                            <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>"/>
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${step le 0}">
                                                        <li class="page-item" >
                                                            <form action="SearchSaveJobController" method="POST">
                                                                <input type="hidden" name="page" value="${i}"/>
                                                                <input type="hidden" name="txtJob" value="${param.txtJob}"/>
                                                                <input type="hidden" name="txtCompany" value="${param.txtCompany}"/>
                                                                <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                                <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                       pagination-active
                                                                    </c:if>"/>
                                                            </form>
                                                        </li>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${step lt 0}">
                                            <li class="page-item" >
                                                <form action="SearchSaveJobController" method="POST">
                                                    <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                                    <input type="hidden" name="txtJob" value="${param.txtJob}"/>
                                                    <input type="hidden" name="txtCompany" value="${param.txtCompany}"/>
                                                    <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                    <input type="submit" value="Next" class="page-link"/>
                                                </form>
                                            </li>
                                            <!--đưa icon vào-->
                                        </c:if>
                                    </ul>
                                </div>
                            </c:if>
                            <c:if test="${empty result}">
                                <h3>
                                    You have not saved job yet!
                                </h3>
                            </c:if>
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

        <script src="./assets/font/bootstrap-5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>    
    </body>
</html>
