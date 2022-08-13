<%-- 
    Document   : adminEvaluation
    Created on : Jun 8, 2022, 5:21:14 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Evaluation</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/admin.css">
        <link rel="stylesheet" href="./assets/css/admin-responsive.css.css">
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
                        <a href="AdminShowPostManagementController" class="nav__item--link">
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
                        <a href="ShowStudentEvaluationController" class="nav__item--link link-active">
                            <i class="fas fa-poll-h"></i>
                            Comment
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
                    <div class="main-body-aEval ">
                        <div class="main-body-aEval__header">
                            Evaluation Management
                        </div>

                        <div class="export__file-student">
                            <a href="ExportEvalutionExcelFileController?semesterID=${requestScope.CURRENT_SEMESTER.semesterID}">
                                Export Excel:
                                <i class="fa-solid fa-cloud-arrow-down"></i>
                            </a>
                        </div>
                        <div class="main-body-aEval__search">
                            <form action="SearchStudentEvaluationController" method="POST">
                                <div class="row">
                                    <div class="col-2">
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
                                        <input type="text" name="studentCode" value="${param.studentCode}" placeholder="ID" class="admin--input" />

                                    </div>
                                    <div class="col-4">
                                        <select name="txtCompanyName" class="admin--select">
                                            <option value="" hidden >Company Name</option>
                                            <option value="">All Company</option>
                                            <c:forEach items="${requestScope.COMPANY_NAME}" var="companyName">
                                                <option value="${companyName.companyID}" <c:if test="${param.txtCompanyName eq companyName.companyID}">
                                                        selected="selected"
                                                    </c:if>>${companyName.account.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-1">
                                        <input type="number" step="any" min="0" max="10" name="garde" value="${param.garde}" placeholder="Grade" class="admin--input"/>     
                                    </div>
                                    <div class="col-2">
                                        <select id="city" name="isPass"  class="admin--select" >
                                            <option value=""hidden >Status</option>
                                            <option value="">All Status</option>
                                            <option value="true" class="text-success" <c:if test="${param.isPass eq 'true'}">
                                                    selected="selected"
                                                </c:if>>
                                                Passed
                                            </option>
                                            <option value="false" class="text-danger" <c:if test="${param.isPass eq 'false'}">
                                                    selected="selected"
                                                </c:if>>
                                                Not Pass
                                            </option>
                                        </select>              
                                    </div>
                                    <div class="col-1">
                                        <input type="submit" value="Search" class="admin-search-btn">
                                    </div>
                                </div>           
                            </form>
                        </div>
                        <div class="main-body-aEval__content">
                            <div class="resultpage__header">
                                Result : ${requestScope.SIZE_OF_LIST}
                            </div>
                            <c:if test="${not empty requestScope.LIST_APPLICATION_RESULT}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>ID</th>
                                            <th>Full Name</th>
                                            <th>Company Name</th>
                                            <th>Title Job</th>
                                            <th>Evaluation</th>
                                            <th>Grade</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.LIST_APPLICATION_RESULT}" var="application" varStatus="counter">
                                            <tr>
                                                <td>
                                                    ${counter.count}
                                                </td>
                                                <td>
                                                    ${application.student.studentCode}
                                                </td>
                                                <td>
                                                    ${application.student.account.name}
                                                </td>
                                                <td>
                                                    ${application.companyPost.company.account.name}
                                                </td>
                                                <td>
                                                    ${application.companyPost.vacancy}
                                                </td>
                                                <td>
                                                    ${application.evaluation}
                                                </td>
                                                <td>
                                                    ${application.grade}
                                                </td>
                                                <td>
                                                    <c:if test="${application.isPass eq 1}">
                                                        <strong class="text-success" >
                                                            Passed
                                                        </strong>
                                                    </c:if>
                                                    <c:if test="${application.isPass eq -1}">
                                                        <strong class="text-danger" >
                                                            Not Pass
                                                        </strong>
                                                    </c:if>                                                   
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>

                        <c:if test="${empty requestScope.LIST_APPLICATION_RESULT}" >
                            <h3 class="text-center" style="margin-top: 20px">
                                Evaluation list does not have any result!
                            </h3>
                        </c:if>
                    </div>

                    <div  class="main__pagination">
                        <ul class="pagination main_cus__pagination">
                            <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                            <c:if test="${requestScope.page gt 5 }">
                                <li class="page-item" >
                                    <form action="SearchStudentEvaluationController" method="POST">
                                        <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                        <input type="hidden" name="semester" value="${requestScope.CURRENT_SEMESTER.semesterID}"/>
                                        <input type="hidden" name="studentCode" value="${param.studentCode}"/>
                                        <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                        <input type="hidden" name="garde" value="${param.garde}"/>
                                        <input type="hidden" name="isPass" value="${param.isPass}"/>
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
                                            <form action="SearchStudentEvaluationController" method="POST">
                                                <input type="hidden" name="page" value="${i}"/>
                                                <input type="hidden" name="semester" value="${requestScope.CURRENT_SEMESTER.semesterID}"/>
                                                <input type="hidden" name="studentCode" value="${param.studentCode}"/>
                                                <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                <input type="hidden" name="garde" value="${param.garde}"/>
                                                <input type="hidden" name="isPass" value="${param.isPass}"/>
                                                <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                       pagination-active
                                                    </c:if>"/>
                                            </form>    
                                        </li>
                                    </c:when>
                                    <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                        <li class="page-item" >
                                            <form action="SearchStudentEvaluationController" method="POST">
                                                <input type="hidden" name="page" value="${i}"/>
                                                <input type="hidden" name="semester" value="${requestScope.CURRENT_SEMESTER.semesterID}"/>
                                                <input type="hidden" name="studentCode" value="${param.studentCode}"/>
                                                <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                <input type="hidden" name="garde" value="${param.garde}"/>
                                                <input type="hidden" name="isPass" value="${param.isPass}"/>
                                                <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                       pagination-active
                                                    </c:if>"/>
                                            </form>    
                                        </li>
                                    </c:when>
                                    <c:when test="${ i eq requestScope.page and step le 0 }">
                                        <li class="page-item" >
                                            <form action="SearchStudentEvaluationController" method="POST">
                                                <input type="hidden" name="page" value="${i}"/>
                                                <input type="hidden" name="semester" value="${requestScope.CURRENT_SEMESTER.semesterID}"/>
                                                <input type="hidden" name="studentCode" value="${param.studentCode}"/>
                                                <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                <input type="hidden" name="garde" value="${param.garde}"/>
                                                <input type="hidden" name="isPass" value="${param.isPass}"/>
                                                <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                       pagination-active
                                                    </c:if>"/>
                                            </form>    
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${step le 0}">
                                            <li class="page-item" >
                                                <form action="SearchStudentEvaluationController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="semester" value="${requestScope.CURRENT_SEMESTER.semesterID}"/>
                                                    <input type="hidden" name="studentCode" value="${param.studentCode}"/>
                                                    <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                    <input type="hidden" name="garde" value="${param.garde}"/>
                                                    <input type="hidden" name="isPass" value="${param.isPass}"/>
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
                                    <form action="SearchStudentEvaluationController" method="POST">
                                        <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                        <input type="hidden" name="semester" value="${requestScope.CURRENT_SEMESTER.semesterID}"/>
                                        <input type="hidden" name="studentCode" value="${param.studentCode}"/>
                                        <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                        <input type="hidden" name="garde" value="${param.garde}"/>
                                        <input type="hidden" name="isPass" value="${param.isPass}"/>
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
