<%-- 
    Document   : adminPostManage
    Created on : Jun 4, 2022, 5:58:41 PM
    Author     : ThanhTy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Post Management</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/admin.css">
        <link rel="stylesheet" href="./assets/css/admin-responsive.css"/>
    </head>

    <body>
        <header></header>
            <c:set var="admin" value="${sessionScope.ADMIN_ROLE}" />

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
                        <font> ${admin.name} </font>
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a href="AdminDashboardController" class="nav__item--link">
                                <i class="fas fa-palette"></i>
                                Admin Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="ShowAdminStudentManagementController" class="nav__item--link">
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
                            <a href="AdminShowPostManagementController" class="nav__item--link link-active">
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
                    ${admin.name}
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="AdminDashboardController" class="nav__item--link ">
                            <i class="fas fa-palette"></i>
                            Admin Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="ShowAdminStudentManagementController" class="nav__item--link">
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
                        <a href="AdminShowPostManagementController" class="nav__item--link link-active">
                            <input type="hidden" name="page" value="1" />
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
            <div class="main-body offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-aPostManage ">
                        <div class="main-body-aPostManage__header">
                            Post Management
                        </div>


                        <div class="main-body-aPostManage__search">
                            <form action="AdminSearchCompanyPostController" method="POST">
                                <div class="row">

                                    <div class="col-2">
                                        <c:set var="currentSemester" value="${requestScope.CURRENT_SEMESTER}"/>
                                        <c:set var="nowSemester" value="${requestScope.NOW_SEMESTER}"/>
                                        <select id="city" name="semester"  class="admin--select" >
                                            <c:forEach var="listSemester" items="${requestScope.LIST_SEMESTER}">
                                                <option value="${listSemester.semesterID}"
                                                        <c:if test="${currentSemester.semesterID eq listSemester.semesterID}">
                                                            selected="selected"
                                                        </c:if>
                                                        >
                                                    ${listSemester.semesterName}                                                 
                                                </option>  
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-3">
                                        <input type="text" name="txtTitle" value="${param.txtTitle}" placeholder="Title" class="admin--input">
                                    </div>
                                    <div class="col-3">
                                        <select id="city" name="txtCompanyName"  class="admin--select" >
                                            <option value="" hidden>Company</option>
                                            <option value="" >All Company</option>
                                            <c:forEach var="allCompany" items="${requestScope.LIST_ALL_COMPANY}">
                                                <option value="${allCompany.account.name}"
                                                        <c:if test="${allCompany.account.name eq param.txtCompanyName}">
                                                            selected="selected"
                                                        </c:if>
                                                        >
                                                    ${allCompany.account.name}                                                   
                                                </option>  
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-2">
                                        <select id="city" name="nameStatus" class="admin--select" >
                                            <option value="" hidden>Status</option>
                                            <option value="">All Status</option>
                                            <option value="Accept" class="text-success" <c:if test="${param.nameStatus eq 'Accept'}">
                                                    selected="selected"
                                                </c:if>>Accept</option>
                                            <option value="Denied" class="text-danger" <c:if test="${param.nameStatus eq 'Denied'}">
                                                    selected="selected"
                                                </c:if>>Denied</option>
                                            <option value="Waiting" class="text-warning" <c:if test="${param.nameStatus eq 'Waiting'}">
                                                    selected="selected"
                                                </c:if>>Waiting</option>
                                        </select>
                                    </div>

                                    <div class="col-2">
                                        <input type="submit" value="Search" class=" admin-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>

                        <c:set var="companyPostList" value="${requestScope.COMPANY_POST_LIST}"/>
                        <div class="main-body-aPostManage__content">
                            <div class="resultpage__header">
                                Result : ${requestScope.SIZE_OF_LIST}
                            </div>
                            <c:if test="${not empty requestScope.COMPANY_POST_LIST}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>Title</th>
                                            <th>Vacancy</th>
                                            <th>Posting Date</th>
                                            <th>Company</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${companyPostList}" 
                                                   var="post" 
                                                   varStatus="counter">
                                            <tr>
                                                <td>${my:counter(requestScope.page, counter.count)}</td>
                                                <td>
                                                    <form action="AdminViewPostDetailController" method="POST">
                                                        <input type="hidden" name="postID" value="${post.postID}"/>
                                                        <input type="hidden" name="page" value="${requestScope.page}"/>
                                                        <input type="hidden" name="semester" value="${currentSemester.semesterID}"/>
                                                        <input type="hidden" name="txtTitle" value="${param.txtTitle}"/>
                                                        <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                        <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                        <label for="${post.postID}" class="a-link">${post.title_Post} </label>
                                                        <input type="submit" value="View" hidden id="${post.postID}"/>
                                                    </form>
                                                    <%--<c:url var="urlAdminViewPostDetail" value="AdminViewPostDetailController">
                                                        <input type="hidden" name="postID" value="${post.postID}"/>
                                                        <c:param name="postID" value="${post.postID}"/>
                                                        <c:param name="page" value="${requestScope.page}"/>
                                                        <c:param name="semester" value="${currentSemester.semesterID}"/>
                                                        <c:param name="txtTitle" value="${param.txtTitle}"/>
                                                        <c:param name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                        <c:param name="nameStatus" value="${param.nameStatus}"/>
                                                    </c:url>
                                                    <a href="${urlAdminViewPostDetail}">${post.title_Post}</a>--%>
                                                </td>
                                                <td>${post.vacancy}</td>
                                                <td>${my:changeDateFormat(post.postingDate)}</td>
                                                <td>${post.company.account.name}</td>

                                                <c:if test="${post.statusPost eq 2}">
                                                    <td class="text-success">
                                                        <strong>
                                                            Accept
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${post.statusPost eq 0 or post.statusPost eq 3}">
                                                    <td class="text-danger">
                                                        <strong>
                                                            Denied
                                                        </strong>
                                                    </td>
                                                </c:if>  
                                                <c:if test="${post.statusPost eq 1}">
                                                    <td class="text-warning">
                                                        <strong>
                                                            Waiting
                                                        </strong>
                                                    </td>
                                                </c:if>


                                                <td>
                                                    <c:set var="listCompanyPost" value="${requestScope.COMPANY_POST_LIST}"/>
                                                    <c:set var="statusAcceptCompanyPost" value="${my:getStatusAcceptCompanyPost(listCompanyPost, post.postID)}"/>

                                                    <div class="row">



                                                        <div class=" col">
                                                            <form action="AdminUpdatePostController" method="POST">
                                                                <div>
                                                                    <input type="hidden" name="save" value="adminPostManagePage" />
                                                                    <input type="hidden" name="school_confirm" value="true" />
                                                                    <input type="hidden" name="statusPost" value="2" />
                                                                    <input type="hidden" name="postID" value="${post.postID}" />
                                                                    <input type="hidden" name="page" value="${requestScope.page}"/>
                                                                    <input type="hidden" name="semester" value="${currentSemester.semesterID}"/>
                                                                    <input type="hidden" name="txtTitle" value="${param.txtTitle}"/>
                                                                    <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                                    <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                                    <c:if test="${post.statusPost eq 1}">
                                                                        <input type="submit" value="Accept" class="btn-regular-green" >
                                                                    </c:if>
                                                                    <c:if test="${post.statusPost ne 1}">
                                                                        <input type="submit" value="Accept" class="btn-regular-green-disable" disabled="disabled" style="opacity: 0.4" />
                                                                    </c:if>
                                                                </div>
                                                            </form>
                                                        </div>


                                                        <div class=" col">

                                                            <c:if test="${post.statusPost ne 1}">
                                                                <input type="submit" value="Reject" class="btn-regular-red-disable" disabled="disabled" style="opacity: 0.4" />
                                                            </c:if>
                                                            <c:if test="${post.statusPost eq 1}">       
                                                                <button type="button" class="btn-regular-red" data-bs-toggle="modal" data-bs-target="#exampleModal_${post.postID}">
                                                                    Reject
                                                                </button>
                                                            </c:if>



                                                            <!-- Modal -->
                                                            <div class="modal fade" id="exampleModal_${post.postID}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h3 class="modal-title" id="exampleModalLabel">Reason Reject</h3>
                                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                        </div>
                                                                        <form action="AdminUpdatePostController" method="POST"> 
                                                                            <div class="modal-body">
                                                                                <textarea name="txtReason"  cols="70" rows="5" style="resize:none"></textarea>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                                    <div>
                                                                                        <input type="hidden" name="save" value="adminPostManagePage" />
                                                                                        <input type="hidden" name="school_confirm" value="false" />
                                                                                        <input type="hidden" name="statusPost" value="0" />
                                                                                        <input type="hidden" name="postID" value="${post.postID}" />
                                                                                        <input type="hidden" name="page" value="${requestScope.page}"/>
                                                                                        <input type="hidden" name="semester" value="${currentSemester.semesterID}"/>
                                                                                        <input type="hidden" name="txtTitle" value="${param.txtTitle}"/>
                                                                                        <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                                                        <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                                                        <input type="submit" value="Reject" class="btn-regular-red" >

                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                </div>



                                                            </div>
                                                        </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>


                                <div  class="main__pagination">
                                    <ul class="pagination main_cus__pagination">
                                        <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                        <c:if test="${requestScope.page gt 5 }">
                                            <li class="page-item" >
                                                <form action="AdminSearchCompanyPostController" method="POST">
                                                    <input type="hidden" name="save" value="adminSearchCompanyPostPage" />
                                                    <input type="hidden" name="page" value="${map['startNum'] - 1}" />
                                                    <input type="hidden" name="semester" value="${currentSemester.semesterID}" />
                                                    <input type="hidden" name="txtTitle" value="${param.txtTitle}" />
                                                    <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}" />
                                                    <input type="hidden" name="nameStatus" value="${param.nameStatus}" />
                                                    <input type="submit" value="Previous" class="page-link" />
                                                </form>
                                            </li>
                                            <!--đưa icon vào-->
                                        </c:if>

                                        <c:forEach var="i" begin="${ map['startNum']}" end="${ map['lastNum']}">
                                            <c:set var="step" value="${i - requestScope.numberPage}" />
                                            <c:choose>
                                                <c:when test="${ step le 0}">
                                                    <li class="page-item" >
                                                        <form action="AdminSearchCompanyPostController" method="POST">
                                                            <input type="hidden" name="save" value="adminSearchCompanyPostPage" />
                                                            <input type="hidden" name="page" value="${i}" />
                                                            <input type="hidden" name="semester" value="${currentSemester.semesterID}" />
                                                            <input type="hidden" name="txtTitle" value="${param.txtTitle}" />
                                                            <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}" />
                                                            <input type="hidden" name="nameStatus" value="${param.nameStatus}" />
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>" />
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                                    <li class="page-item" >  
                                                        <form action="AdminSearchCompanyPostController" method="POST">
                                                            <input type="hidden" name="save" value="adminSearchCompanyPostPage" />
                                                            <input type="hidden" name="page" value="${i}" />
                                                            <input type="hidden" name="semester" value="${currentSemester.semesterID}" />
                                                            <input type="hidden" name="txtTitle" value="${param.txtTitle}" />
                                                            <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}" />
                                                            <input type="hidden" name="nameStatus" value="${param.nameStatus}" />
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>" />
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:when test="${ i eq requestScope.page and step le 0 }">
                                                    <li class="page-item" >    
                                                        <form action="AdminSearchCompanyPostController" method="POST">
                                                            <input type="hidden" name="save" value="adminSearchCompanyPostPage" />
                                                            <input type="hidden" name="page" value="${i}" />
                                                            <input type="hidden" name="semester" value="${currentSemester.semesterID}" />
                                                            <input type="hidden" name="txtTitle" value="${param.txtTitle}" />
                                                            <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}" />
                                                            <input type="hidden" name="nameStatus" value="${param.nameStatus}" />
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>" />
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${step lt 0}">
                                                        <li class="page-item" >
                                                            <form action="AdminSearchCompanyPostController" method="POST">
                                                                <input type="hidden" name="save" value="adminSearchCompanyPostPage" />
                                                                <input type="hidden" name="page" value="${i}" />
                                                                <input type="hidden" name="semester" value="${currentSemester.semesterID}" />
                                                                <input type="hidden" name="txtTitle" value="${param.txtTitle}" />
                                                                <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}" />
                                                                <input type="hidden" name="nameStatus" value="${param.nameStatus}" />
                                                                <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                       pagination-active
                                                                    </c:if>" />
                                                            </form>
                                                        </li>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${step le 0}">
                                            <li class="page-item" >
                                                <form action="AdminSearchCompanyPostController" method="POST">
                                                    <input type="hidden" name="save" value="adminSearchCompanyPostPage" />
                                                    <input type="hidden" name="page" value="${map['lastNum'] + 1}" />
                                                    <input type="hidden" name="semester" value="${currentSemester.semesterID}" />
                                                    <input type="hidden" name="txtTitle" value="${param.txtTitle}" />
                                                    <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}" />
                                                    <input type="hidden" name="nameStatus" value="${param.nameStatus}" />
                                                    <input type="submit" value="Next" class="page-link" />
                                                </form>
                                            </li>
                                            <!--đưa icon vào-->
                                        </c:if>
                                    </ul>
                                </div>
                            </c:if>
                            <c:if test="${empty requestScope.COMPANY_POST_LIST}">
                                <h3 class="text-center" style="margin-top: 20px">
                                    Post job list does not have any result!
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
