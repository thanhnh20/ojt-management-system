<%-- 
    Document   : companyInternsManagement
    Created on : Jun 15, 2022, 3:15:08 PM
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
        <title>Company - Interns Management</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">
    </head>
    <body>
        <header></header>
            <c:set var="company" value="${sessionScope.COMPANY_ROLE}" />

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
                        <font>  ${company.name} </font>
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a href="ShowCompanyDashBoardController" class="nav__item--link ">
                                <i class="fas fa-palette "></i>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowProfileController" class="nav__item--link">
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
                            <a href="CompanyShowInternsManagermentController" class="nav__item--link link-active">
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
                <a href="ShowCompanyDashBoardController" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="CompanyShowProfileController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${company.name}
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="ShowCompanyDashBoardController" class="nav__item--link ">
                            <i class="fas fa-palette "></i>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowProfileController" class="nav__item--link">
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
                        <a href="CompanyShowInternsManagermentController" class="nav__item--link link-active">
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
            <div class="main-body  offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-cInternManage ">
                        <div class="main-body-cInternManage__header">
                            Intern Management
                        </div>

                        <div class="main-body-cInternManage__search">
                            <form action="CompanySearchInternsManagementController" method="POST">

                                <div class="row">
                                    <div class="col-3">
                                        <input type="text" name="txtFullName" value="${param.txtFullName}" id="" placeholder="Full Name" class="company--input">
                                    </div>
                                    <div class="col-3">
                                        <input type="email" name="txtEmail" value="${param.txtEmail}" id="" placeholder="Email" class="company--input">
                                    </div>
                                    <div class="col-3">
                                        <c:set value="${requestScope.LIST_COMPANY_POST}" var="listCompanyPost"/>
                                        <c:set var="page" value="${requestScope.PAGE}"/>
                                        <select id="title" name="selectCompanyPost" class="company--select">
                                            <option value="" >Title Job</option>
                                            <c:forEach var="companyPost" items="${listCompanyPost}">
                                                <option value="${companyPost.postID}"
                                                        <c:if test="${companyPost.postID eq  param.selectCompanyPost}">
                                                            selected="selected"
                                                        </c:if>        
                                                        >  
                                                    ${companyPost.title_Post}
                                                </option>   
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <c:set value="${requestScope.SELECTED}" var="selected"/>
                                    <div class="col-2">
                                        <select id="status" name="status" class="company--select">
                                            <option value=""hidden>Status</option>
                                            <option value="">All Status</option>
                                            <option value="Passed" class="text-success"
                                                    <c:if test="${selected eq 'Passed'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >                            
                                                Passed
                                            </option>
                                            <option value="Working" class="text-warning"
                                                    <c:if test="${selected eq 'Working'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >
                                                Working
                                            </option>
                                            <option value="NotPass" class="text-danger"
                                                    <c:if test="${selected eq 'NotPass'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >
                                                Not Pass
                                            </option>
                                        </select>
                                    </div>

                                    <div class="col-1">
                                        <input type="submit" value="Search" class=" company-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <c:set value="${requestScope.SIZE_PAGE}" var="total"/>
                        <div class="main-body-cInternManage__content">
                            <div class="resultpage__header">
                                Result : ${total}
                            </div>
                            <c:if test="${total ne 0}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>Full Name</th>
                                            <th>Email</th>
                                            <th>Job</th>
                                            <th>Status</th>
                                            <th>Mark</th>
                                            <th>Comment</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set value="${requestScope.APPLICATION_LIST_BYPAGE}" var="applicationList"/>
                                        <c:forEach var="applicationDTO" items="${applicationList}" varStatus="counter"> 
                                        <form action="CompanyUpdateInternsController" method="POST">
                                            <tr>
                                                <td>${my:counter(requestScope.PAGE, counter.count)}</td>
                                                <td>
                                                    ${applicationDTO.student.account.name}
                                                    <input type="hidden" name="studentCode" value="${applicationDTO.student.studentCode}" />
                                                </td>
                                                <td>${applicationDTO.student.account.email}</td>
                                                <td>
                                                    ${applicationDTO.companyPost.title_Post}
                                                    <input type="hidden" name="txtPostID" value="${applicationDTO.companyPost.postID}" />
                                                </td>
                                                <td>
                                                    <c:if test="${applicationDTO.isPass eq 0}">
                                                        <font class="text-warning">
                                                        Working
                                                        </font>                                                
                                                    </c:if>
                                                    <c:if test="${applicationDTO.isPass eq -1}">
                                                        <font class="text-danger">
                                                        Not Pass
                                                        </font>  
                                                    </c:if>
                                                    <c:if test="${applicationDTO.isPass eq 1}">
                                                        <font class="text-success">
                                                        Passed
                                                        </font>  
                                                    </c:if>                  
                                                </td>
                                                <td>
                                                    <c:set value="${requestScope.ERROR_MARK}" var="error"/>
                                                    <c:if test="${applicationDTO.isPass eq 0}">
                                                        <input type="number" step="any" min="0" max="10" class="cInterManage__mark" name="txtMark" value="${param.txtMark}"/>
                                                    </c:if>          
                                                    <c:if test="${applicationDTO.isPass eq 1}">
                                                        <input type="number" step="any" min="0" max="10" class="cInterManage__mark" name="txtMark" value="${applicationDTO.grade}" disabled="disabled">     
                                                    </c:if>    
                                                    <c:if test="${applicationDTO.isPass eq -1}">
                                                        <input type="number" step="any" min="0" max="10" class="cInterManage__mark" name="txtMark" value="${applicationDTO.grade}" disabled="disabled">     
                                                    </c:if>              
                                                    <c:if test="${not empty error}">
                                                        <c:if test="${param.studentCode eq applicationDTO.student.studentCode}">
                                                            <font class="text-danger">
                                                            ${error.errorEmptyMark}
                                                            ${error.errorInputInvalidMark}
                                                            </font>
                                                        </c:if>
                                                    </c:if>
                                                </td>
                                                <td>

                                                    <textarea name="txtEvaluation" id="" class="cInterManage__textarea" cols="30"
                                                              rows="2"
                                                              <c:if test="${applicationDTO.isPass ne 0}">disabled ="disabled"</c:if>
                                                              >${applicationDTO.evaluation}</textarea>

                                                </td>
                                                <td>
                                                    <!--param of search-->
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                    <input type="hidden" name="status" value="${param.status}" />
                                                    <input type="hidden" name="page" value="${param.page}" />
                                                    <c:if test="${applicationDTO.isPass eq 0}">
                                                        <input type="submit" class="btn-regular-green" value="Update">
                                                    </c:if>
                                                    <c:if test="${applicationDTO.isPass ne 0}">
                                                        <input type="submit" class="btn-regular-green-disable" value="Update" disabled="disabled" style="opacity: 0.4">
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </form>
                                    </c:forEach>    
                                    </tbody>

                                </table>
                            </c:if>
                            <c:if test="${total eq 0}">
                                <h3 class="text-center" style="margin-top: 20px">
                                    Interns management list does not has any result!
                                </h3>    
                            </c:if>
                        </div>

                        <div class="main__pagination">
                            <ul class="pagination main_cus__pagination">        
                                <c:set var="map" value="${my:paging(requestScope.PAGE, 10, requestScope.NUMBER_PAGE)}"/>
                                <c:if test="${requestScope.page gt 5 }">
                                    <li class="page-item" >
                                        <form action="CompanySearchInternsManagementController" method="POST">
                                            <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                            <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                            <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                            <input type="hidden" name="status" value="${param.status}" />
                                            <input type="submit" value="Previous" class="page-link"/>
                                        </form>
                                    </li>
                                    <!--đưa icon vào-->
                                </c:if>

                                <c:forEach var="i" begin="${ map['startNum']}" end="${ map['lastNum']}">
                                    <c:set var="step" value="${i - requestScope.NUMBER_PAGE}" />
                                    <c:choose>
                                        <c:when test="${ step le 0}">
                                            <li class="page-item" >
                                                <form action="CompanySearchInternsManagementController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                    <input type="hidden" name="status" value="${param.status}" />
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                            <li class="page-item" >  
                                                <form action="CompanySearchInternsManagementController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                    <input type="hidden" name="status" value="${param.status}" />
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i eq requestScope.page and step le 0 }">
                                            <li class="page-item" >    
                                                <form action="CompanySearchInternsManagementController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                    <input type="hidden" name="status" value="${param.status}" />
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${step le 0}">
                                                <li class="page-item" >
                                                    <form action="CompanySearchInternsManagementController" method="POST">
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                        <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                        <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                        <input type="hidden" name="status" value="${param.status}" />
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
                                        <form action="CompanySearchInternsManagementController" method="POST">
                                            <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                            <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                            <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                            <input type="hidden" name="status" value="${param.status}" />
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
        <script src="./assets/js/inputfile.js"></script>
    </body>
</html>
