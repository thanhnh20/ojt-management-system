<%-- 
    Document   : adminCompanyManagerPage
    Created on : Jun 6, 2022, 1:59:08 PM
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
        <title>Admin - Company Management</title>
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
                            <a href="AdminCompanyManagerController" class="nav__item--link link-active">
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
                        <a href="AdminCompanyManagerController" class="nav__item--link link-active">
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
                    <div class="main-body-aComManage ">
                        <c:set var="listCompany" value="${requestScope.LIST_COMPANY}"/>
                        <c:set var="page" value="${requestScope.page}"/>
                        <c:set var="sizePage" value="${requestScope.SIZE_PAGE}"/>
                        <div class="main-body-aComManage__header">
                            Company Management
                        </div>

                        <c:if test="${not empty requestScope.WARING_CHANGE_SIGNING_STATUS}">
                            <div class="all">
                                <div class="modal-cus" >
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h3 class="modal-title" id="exampleModalLabel">Warning</h3>
                                            </div>
                                            <h4 style="color: red">
                                                ${requestScope.WARING_CHANGE_SIGNING_STATUS}
                                            </h4>
                                            <div class="text-center">
                                                <div class="row row-cols-1">
                                                    <div class="col">
                                                        <form action="SearchCompanyAdminManagerController" method="Post">
                                                            <input type="hidden" name="selectCompany" value="${param.selectCompany}" />
                                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                            <input type="hidden" name="selectStatus" value="${param.selectStatus}" />
                                                            <input type="hidden" name="page" value="${param.page}" />
                                                            <input type="submit" value="Cancel" name="btAction" class="btn-regular-green"/>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="main-body-aComManage__search">
                            <form action="SearchCompanyAdminManagerController" method="POST">
                                <div class="row">
                                    <div class="col-4">
                                        <select id="city" name="selectCompany"  class="admin--select" >
                                            <option value="" hidden>Company</option>
                                            <option value="" >All Company</option>
                                            <c:forEach var="allCompany" items="${requestScope.LIST_ALL_COMPANY}">
                                                <option value="${allCompany.companyID}"
                                                        <c:if test="${allCompany.companyID eq param.selectCompany}">
                                                            selected="selected"
                                                        </c:if>
                                                        >
                                                    ${allCompany.account.name}                                                   
                                                </option>  
                                            </c:forEach>
                                        </select>  
                                    </div>

                                    <div class="col-4">
                                        <input type="text" name="txtEmail" placeholder="Email" value="${param.txtEmail}" class="admin--input">     
                                    </div>

                                    <div class="col-2">
                                        <select id="city" name="selectStatus"  class="admin--select" >
                                            <option value=""hidden >Status</option>
                                            <option value="">All Status</option>
                                            <option value="Success" class="text-success"
                                                    <c:if test="${param.selectStatus eq 'Success'}">
                                                        selected="selected"
                                                    </c:if>>
                                                Signed
                                            </option>
                                            <option value="Denied" class="text-danger"
                                                    <c:if test="${param.selectStatus eq 'Denied'}">
                                                        selected="selected"
                                                    </c:if>>
                                                Not Yet
                                            </option>
                                        </select>   
                                    </div>

                                    <div class="col-2">
                                        <input type="submit" value="Search" class=" admin-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="main-body-aComManage__content">
                            <div class="resultpage__header">
                                Result : ${sizePage}
                            </div>
                            <c:if test="${not empty listCompany}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>Company Name</th>
                                            <th>Email</th>
                                            <th>Phone</th>
                                            <th>Address</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="companyDTO" items="${listCompany}" varStatus="counter" >
                                        <form action="AdminUpdateStatusCompanyController" method="POST">

                                            <tr>
                                                <td>
                                                    ${my:counter(requestScope.page, counter.count)}
                                                </td>
                                                <td>${companyDTO.account.name}</td>
                                                <td>${companyDTO.account.email}</td>
                                                <td>${companyDTO.phone}</td>
                                                <td>${companyDTO.address}</td>                                           
                                                <td>
                                                    <select id="city" name="Status"  class="">                                                    
                                                        <option value="Success" class="text-success" <c:if  test="${companyDTO.is_Signed eq true}">
                                                                selected="selected"
                                                            </c:if>>
                                                            Signed
                                                        </option>                                                    
                                                        <option value="Denied" class="text-danger" <c:if  test="${companyDTO.is_Signed eq false}">
                                                                selected="selected"
                                                            </c:if>>
                                                            Not Yet
                                                        </option>
                                                    </select> 
                                                </td>

                                                <td>                                              
                                                    <input type="hidden" name="page" value="${param.page}" />
                                                    <input type="hidden" name="companyID" value="${companyDTO.companyID}" />
                                                    <input type="submit" value="Update" class="btn-update-green" />
                                                </td>
                                            </tr>
                                        </form>
                                    </c:forEach>
                                    </tbody>

                                </table>
                            </c:if>
                            <c:if test="${empty listCompany}">
                                <h3 class="text-center" style="margin-top: 20px">
                                    Company list does not have any result!
                                </h3>
                            </c:if>
                        </div>

                        <div class="main__pagination">
                            <ul class="pagination main_cus__pagination"> 
                                <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                <c:if test="${requestScope.page gt 5 }">
                                    <li class="page-item" >
                                        <form action="SearchCompanyAdminManagerController" method="POST">
                                            <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                            <input type="hidden" name="selectCompany" value="${param.selectCompany}"/>
                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                            <input type="hidden" name="selectStatus" value="${param.selectStatus}"/>
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
                                                <form action="SearchCompanyAdminManagerController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="selectCompany" value="${param.selectCompany}"/>
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                    <input type="hidden" name="selectStatus" value="${param.selectStatus}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                           pagination-active
                                                        </c:if>" />
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                            <li class="page-item" >
                                                <form action="SearchCompanyAdminManagerController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="selectCompany" value="${param.selectCompany}"/>
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                    <input type="hidden" name="selectStatus" value="${param.selectStatus}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                           pagination-active
                                                        </c:if>" />
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i eq requestScope.page and step le 0 }">
                                            <li class="page-item" >
                                                <form action="SearchCompanyAdminManagerController" method="POST">
                                                    <input type="hidden" name="page" value="${i}"/>
                                                    <input type="hidden" name="selectCompany" value="${param.selectCompany}"/>
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                    <input type="hidden" name="selectStatus" value="${param.selectStatus}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                           pagination-active
                                                        </c:if>" />
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${step le 0}">
                                                <li class="page-item" >
                                                    <form action="SearchCompanyAdminManagerController" method="POST">
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="hidden" name="selectCompany" value="${param.selectCompany}"/>
                                                        <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                        <input type="hidden" name="selectStatus" value="${param.selectStatus}"/>
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>" />
                                                    </form>
                                                </li>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${step lt 0}">
                                    <li class="page-item" >
                                        <form action="SearchCompanyAdminManagerController" method="POST">
                                            <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                            <input type="hidden" name="selectCompany" value="${param.selectCompany}"/>
                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                            <input type="hidden" name="selectStatus" value="${param.selectStatus}"/>
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
