<%-- 
    Document   : adminInterAppl
    Created on : Jun 6, 2022, 11:56:45 PM
    Author     : Thanh Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Application Management</title>
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
                            <a href="AdminShowPostManagementController" class="nav__item--link">
                                <i class="fas fa-pen"></i>
                                Post Management
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="AdminShowInternApplicationController" class="nav__item--link link-active">
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


        <c:set var="studentID" value="${param.txtStudentID}"/>
        <c:set var="companyID" value="${param.txtCompanyID}"/>
        <c:set var="schoolStatus" value="${param.txtSchoolStatus}"/>
        <c:set var="titleJob" value="${param.txtTitleJob}" />



        <main class="row">
            <nav class="col-xl-2  nav-fixed col-md-3">
                <a href="AdminDashboardController" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="AdminDashboardController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${Admin.name}
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
                        <a href="AdminShowPostManagementController" class="nav__item--link">
                            <i class="fas fa-pen"></i>
                            Post Management
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="AdminShowInternApplicationController" class="nav__item--link link-active">
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
                    <div class="main-body-aInterAppl ">
                        <div class="main-body-aInterAppl__header">
                            Intern Application
                        </div>



                        <div class="main-body-aInterAppl__search">
                            <form action="AdminShowInternApplicationController" method="POST">
                                <div class="row">
                                    <div class="col-3">
                                        <c:set var="nowSemester" value="${requestScope.CURRENT_SEMESTER.semesterID}" />
                                        <select name="semester" class="admin--select">
                                            <c:forEach items="${requestScope.LIST_SEMESTER}" var="semester">
                                                <option value="${semester.semesterID}" <c:if test="${requestScope.CURRENT_SEMESTER.semesterID eq semester.semesterID}" >
                                                        selected="selected"
                                                    </c:if>>${semester.semesterName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>    
                                    <div class="col-2">
                                        <input type="text" name="txtStudentID" id="" placeholder="ID" class="admin--input" value="${studentID}">
                                    </div>
                                    <div class="col-3">
                                        <input type="text" placeholder="Title Job" name="txtTitleJob" class="admin--input" value="${param.txtTitleJob}">
                                    </div>
                                    <div class="col-3">
                                        <select name="txtCompanyID" class="admin--select">
                                            <option value=""hidden >Company Name</option>
                                            <option value="">All Company </option>
                                            <c:forEach items="${requestScope.COMPANY_NAME}" var="company">
                                                <option value="${company.companyID}" <c:if test="${company.companyID eq companyID}">
                                                        selected="selected"
                                                    </c:if> >${company.account.name}</option>
                                            </c:forEach>
                                        </select>

                                    </div>

                                        <div class="col-1">
                                            <input type="submit" value="Search" class="admin-search-btn">
                                        </div>
                                    </div>
                                </form>
                            </div>

                        <c:set var="sizeOfList" value="${requestScope.SIZE_OF_LIST}" />
                        <div class="main-body-aInterAppl__content">                         
                            <div class="resultpage__header">
                                Result : ${requestScope.SIZE_OF_LIST}
                            </div>
                            <c:set var= "listIntern" value="${requestScope.INTERN_APPLICATION}"/>
                            <c:if test="${not empty listIntern}" >
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>ID</th>
                                            <th>Company Name</th>
                                            <th>Title Job</th>
                                            <th>Student Applied</th>
                                            <th>Company Accepted</th>

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="intern" items="${listIntern}" varStatus="counter">
                                            <tr>
                                                <td>${my:counter(requestScope.page, counter.count)}</td>
                                                <td>${intern.student.studentCode}</td>
                                                <td>${intern.companyPost.company.account.name}</td>
                                                <td>${intern.companyPost.title_Post}</td>

                                                <c:if test="${intern.studentConfirm eq true}">
                                                    <td class="text-success">
                                                        <strong>
                                                            Accepted
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${intern.studentConfirm eq false}">
                                                    <td class="text-danger">
                                                        <strong>
                                                            Cancel
                                                        </strong>
                                                    </td>
                                                </c:if>

                                                <c:if test="${intern.companyConfirm eq 0}">
                                                    <td class="text-warning">
                                                        <strong>
                                                            Waiting
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${intern.companyConfirm eq -1}">
                                                    <td class="text-danger">
                                                        <strong>
                                                            Denied
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${intern.companyConfirm eq 1}">
                                                    <td class="text-success">
                                                        <strong>
                                                            Accepted
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${intern.companyConfirm eq -2}">
                                                    <td class="text-gray">
                                                        <strong>
                                                            Failed interview
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${intern.companyConfirm eq 2}">
                                                    <td class="text-orange">
                                                        <strong>
                                                            Success interview
                                                        </strong>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>      

                                    </tbody>

                                </table>

                            </c:if>
                            <c:if test="${empty listIntern}">
                                <h3 class="text-center" style="margin-top: 20px">
                                    Internship application list does not have any result!
                                </h3>
                            </c:if>
                        </div>

                        <div  class="main__pagination">
                            <ul class="pagination main_cus__pagination">
                                <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                <c:if test="${requestScope.page gt 5 }">
                                    <li class="page-item" >
                                        <form action="AdminShowInternApplicationController" method="POST">
                                            <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                            <input type="hidden" name="txtStudentID" value="${studentID}"/>
                                            <input type="hidden" name="semester" value="${nowSemester}" />
                                            <input type="hidden" name="txtCompanyID" value="${companyID}"/>
                                            <input type="hidden" name="txtSchoolStatus" value="${schoolStatus}"/>
                                            <input type="hidden"name="txtTitleJob" value="${titleJob}"/>
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
                                                <form action="AdminShowInternApplicationController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="semester" value="${nowSemester}" />
                                                    <input type="hidden" name="txtStudentID" value="${studentID}"/>
                                                    <input type="hidden" name="txtCompanyID" value="${companyID}"/>
                                                    <input type="hidden" name="txtSchoolStatus" value="${schoolStatus}"/>
                                                    <input type="hidden"name="txtTitleJob" value="${titleJob}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                            <li class="page-item" >  
                                                <form action="AdminShowInternApplicationController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="semester" value="${nowSemester}" />
                                                    <input type="hidden" name="txtStudentID" value="${studentID}"/>
                                                    <input type="hidden" name="txtCompanyID" value="${companyID}"/>
                                                    <input type="hidden" name="txtSchoolStatus" value="${schoolStatus}"/>
                                                    <input type="hidden"name="txtTitleJob" value="${titleJob}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i eq requestScope.page and step le 0 }">
                                            <li class="page-item" >    
                                                <form action="AdminShowInternApplicationController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="semester" value="${nowSemester}" />
                                                    <input type="hidden" name="txtStudentID" value="${studentID}"/>
                                                    <input type="hidden" name="txtCompanyID" value="${companyID}"/>
                                                    <input type="hidden" name="txtSchoolStatus" value="${schoolStatus}"/>
                                                    <input type="hidden"name="txtTitleJob" value="${titleJob}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${step le 0}">
                                                <li class="page-item" >
                                                    <form action="AdminShowInternApplicationController" method="POST">
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="hidden" name="semester" value="${nowSemester}" />
                                                        <input type="hidden" name="txtStudentID" value="${studentID}"/>
                                                        <input type="hidden" name="txtCompanyID" value="${companyID}"/>
                                                        <input type="hidden" name="txtSchoolStatus" value="${schoolStatus}"/>
                                                        <input type="hidden"name="txtTitleJob" value="${titleJob}"/>
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
                                        <form action="AdminShowInternApplicationController" method="POST">
                                            <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                            <input type="hidden" name="semester" value="${nowSemester}" />
                                            <input type="hidden" name="txtStudentID" value="${studentID}"/>
                                            <input type="hidden" name="txtCompanyID" value="${companyID}"/>
                                            <input type="hidden" name="txtSchoolStatus" value="${schoolStatus}"/>
                                            <input type="hidden"name="txtTitleJob" value="${titleJob}"/>
                                            <input type="submit" value="Next" class="page-link"/>
                                        </form>
                                    </li>
                                    <!--đưa icon vào-->
                                </c:if>
                            </ul>
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
