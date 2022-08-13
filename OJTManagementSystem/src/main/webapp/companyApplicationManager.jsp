<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Company - Application Management</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">
    </head>

    <body>
        <header></header>
            <c:set var="company" value="${sessionScope.COMPANY_ROLE}"/>

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
                            <a href="ShowCompanyDashBoardController" class="nav__item--link">
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
                            <a href="CompanyShowInternsManagermentController" class="nav__item--link">
                                <i class="fas fa-poll-h"></i>
                                Interns Management
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="CompanyShowIntershipApplicationController" class="nav__item--link link-active">
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
                <a href="#" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="CompanyShowProfileController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${company.name}
                </a>
                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="ShowCompanyDashBoardController" class="nav__item--link">
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
                        <a href="CompanyShowInternsManagermentController" class="nav__item--link">
                            <i class="fas fa-poll-h"></i>
                            Interns Management
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="CompanyShowIntershipApplicationController" class="nav__item--link link-active">
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
            <div class="main-body offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-cApplManage ">
                        <div class="main-body-cApplManage__header">
                            Application Management
                        </div>
                        <div class="main-body-cApplManage__search">
                            <form action="CompanySearchInternsController" method="POST">

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
                                            <option value="Success" class="text-success"
                                                    <c:if test="${selected eq 'Success'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >                            
                                                Accepted
                                            </option>
                                            <option value="Waiting" class="text-warning"
                                                    <c:if test="${selected eq 'Waiting'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >
                                                Waiting
                                            </option>
                                            <option value="Denied" class="text-danger"
                                                    <c:if test="${selected eq 'Denied'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >
                                                Denied
                                            </option>
                                            <option value="Interviewing" class="text-warning"
                                                    <c:if test="${selected eq 'Interviewing'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >
                                                Interviewing
                                            </option>
                                            <option value="Failed" class="text-danger"
                                                    <c:if test="${selected eq 'Failed'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >
                                                Failed
                                            </option>
                                        </select>
                                    </div>

                                    <div class="col-1">
                                        <input type="submit" value="Search" class=" company-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <c:set var="total" value="${requestScope.SIZE_PAGE}" />
                        <div class="main-body-cApplManage__content">
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
                                            <th colspan="2" >Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="applicationList" value="${requestScope.APPLICATION_LIST_BYPAGE}"/>
                                        <c:forEach var="applicationDTO" items="${applicationList}" varStatus="counter">
                                            <tr>
                                                <td>${my:counter(requestScope.PAGE, counter.count)}</td>
                                                <td>
                                                    <%-- <c:url var="viewStudentDetail" value="CompanyViewStudentController">
                                                        <c:param name="studentCode" value="${applicationDTO.student.studentCode}" />
                                                        <c:param name="companyPostID" value="${applicationDTO.companyPost.postID}" />
                                                        <c:param name="txtFullName" value="${param.txtFullName}"/>
                                                        <c:param name="txtEmail" value="${param.txtEmail}"/>
                                                        <c:param name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                                        <c:param name="status" value="${param.status}"/>
                                                        <c:param name="companyConfirm" value="${applicationDTO.companyConfirm}"/>
                                                    </c:url>
                                                    
                                                    <a href="${viewStudentDetail}">${applicationDTO.student.account.name}</a> --%>
                                                    
                                                    <form action="CompanyViewStudentController" method="POST">
                                                        <input type="hidden" name="studentCode" value="${applicationDTO.student.studentCode}" />
                                                        <input type="hidden" name="companyPostID" value="${applicationDTO.companyPost.postID}" />
                                                        <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                        <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                        <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                        <input type="hidden" name="status" value="${param.status}" />
                                                        <input type="hidden" name="companyConfirm" value="${applicationDTO.companyConfirm}" />
                                                        <input type="hidden" name="page" value="${requestScope.PAGE}" />
                                                        <label for ="${my:counter(requestScope.PAGE, counter.count)}" class="a-link">${applicationDTO.student.account.name}</label>
                                                        <input type="submit" value="View" hidden id="${my:counter(requestScope.PAGE, counter.count)}"/>
                                                    </form>
                                                </td>
                                                <td>${applicationDTO.student.account.email}</td>
                                                <td>${applicationDTO.companyPost.title_Post}</td>

                                                <td>
                                                    <c:if test="${applicationDTO.companyConfirm eq 1}">
                                                        <strong class="text-success">
                                                            Accepted
                                                        </strong>
                                                    </c:if>
                                                    <c:if test="${applicationDTO.companyConfirm eq -1}">
                                                        <strong class="text-danger">
                                                            Denied
                                                        </strong>
                                                    </c:if>
                                                    <c:if test="${applicationDTO.companyConfirm eq 0}">
                                                        <strong class="text-warning">
                                                            Waiting
                                                        </strong>
                                                    </c:if>
                                                    <c:if test="${applicationDTO.companyConfirm eq 2}">
                                                        <strong class="text-warning">
                                                            Interviewing
                                                        </strong>
                                                    </c:if>
                                                    <c:if test="${applicationDTO.companyConfirm eq -2}">
                                                        <strong class="text-danger">
                                                            Failed
                                                        </strong>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <!--                                                    <div class="d-flex justify-content-around">     -->
                                                    <%--           <c:if test="${applicationDTO.companyConfirm eq 0 or applicationDTO.companyConfirm eq 2}"> --%>
                                                    <form action="CompanyUpdateStatusIntershipApplicationController" method="POST">
                                                        <!--param of update-->
                                                        <input type="hidden" name="studentCode" value="${applicationDTO.student.studentCode}" />
                                                        <input type="hidden" name="companyPostID" value="${applicationDTO.companyPost.postID}" />  
                                                        <input type="hidden" name="page" value="${param.page}" />
                                                        <!--param of search-->
                                                        <input type="hidden" name="txtFullName" value="${param.txtFullName}" />
                                                        <input type="hidden" name="txtEmail" value="${param.txtEmail}" />
                                                        <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}" />
                                                        <input type="hidden" name="status" value="${param.status}" />
                                                        <!--button-->

                                                        <c:if test="${applicationDTO.companyConfirm eq -1}">
                                                            <input name="action" class="btn btn-outline-success" type="submit" value="Interview" disabled="disabled" /> 
                                                            <input name="action" class="btn btn-outline-danger" type="submit" value="Reject Interview" disabled="disabled" /> 
                                                        </c:if>
                                                        <c:if test="${applicationDTO.companyConfirm eq -2 or applicationDTO.companyConfirm eq 1}">
                                                            <input name="action" class="btn btn-outline-success" type="submit" value="Accept" disabled="disabled"/>  
                                                            <input name="action" class="btn btn-outline-danger" type="submit" value="Reject" disabled="disabled"/>               
                                                        </c:if>
                                                        <c:if test="${applicationDTO.companyConfirm eq 0}">
                                                            <input name="action" class="btn btn-outline-success" type="submit" value="Interview" /> 
                                                            <input name="action" class="btn btn-outline-danger" type="submit" value="Reject Interview" /> 
                                                        </c:if>

                                                        <c:if test="${applicationDTO.companyConfirm eq 2}">
                                                            <input name="action" class="btn btn-outline-success" type="submit" value="Accept" />  
                                                            <input name="action" class="btn btn-outline-danger" type="submit" value="Reject" />               
                                                        </c:if>
                                                    </form>
                                                    <c:set value="${requestScope.ERROR_QUANTITY_INTERNS}" var="quantityEnough"/>
                                                    <c:if test="${not empty quantityEnough}">
                                                        <c:if test="${param.studentCode eq applicationDTO.student.studentCode}">
                                                            ${quantityEnough.quantitytInternsNotEngough} 
                                                        </c:if>
                                                    </c:if>
                                                    <%-- </c:if> --%>
                                                    <!--                                                    </div>-->
                                                </td>              
                                            </tr>     
                                        </c:forEach>

                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${total eq 0}">
                                <h3 class="text-center" style="margin-top: 20px">
                                    Internship application list does not has any result!
                                </h3>    
                            </c:if>

                        </div>

                        <c:set value="${requestScope.NUMBER_PAGE}" var="numberpage"/>
                        <div class="main__pagination">
                            <ul class="pagination main_cus__pagination"> 
                                <c:set var="map" value="${my:paging(requestScope.PAGE, 10, requestScope.NUMBER_PAGE)}"/>
                                <c:if test="${requestScope.PAGE gt 5 }">
                                    <li class="page-item" >
                                        <form action="CompanySearchInternsController" method="POST">
                                            <input type="hidden" name="PAGE" value="${map['startNum'] - 1}"/>
                                            <input type="hidden" name="txtFullName" value="${param.txtFullName}"/>
                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                            <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                            <input type="hidden" name="status" value="${param.selectCompanyPost}"/>
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
                                                <form action="CompanySearchInternsController" method="POST">
                                                    <input type="hidden" name="PAGE" value="${i}"/>
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}"/>
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                                    <input type="hidden" name="status" value="${param.selectCompanyPost}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                            <li class="page-item" >  
                                                <form action="CompanySearchInternsController" method="POST">
                                                    <input type="hidden" name="PAGE" value="${i}"/>
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}"/>
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                                    <input type="hidden" name="status" value="${param.selectCompanyPost}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:when test="${ i eq requestScope.PAGE and step le 0 }">
                                            <li class="page-item" >    
                                                <form action="CompanySearchInternsController" method="POST">
                                                    <input type="hidden" name="PAGE" value="${i}"/>
                                                    <input type="hidden" name="txtFullName" value="${param.txtFullName}"/>
                                                    <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                    <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                                    <input type="hidden" name="status" value="${param.selectCompanyPost}"/>
                                                    <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
                                                           pagination-active
                                                        </c:if>"/>
                                                </form>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${step le 0}">
                                                <li class="page-item" >
                                                    <form action="CompanySearchInternsController" method="POST">
                                                        <input type="hidden" name="PAGE" value="${i}"/>
                                                        <input type="hidden" name="txtFullName" value="${param.txtFullName}"/>
                                                        <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                                        <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                                        <input type="hidden" name="status" value="${param.selectCompanyPost}"/>
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.PAGE}">
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
                                        <form action="CompanySearchInternsController" method="POST">
                                            <input type="hidden" name="PAGE" value="${map['lastNum'] + 1}"/>
                                            <input type="hidden" name="txtFullName" value="${param.txtFullName}"/>
                                            <input type="hidden" name="txtEmail" value="${param.txtEmail}"/>
                                            <input type="hidden" name="selectCompanyPost" value="${param.selectCompanyPost}"/>
                                            <input type="hidden" name="status" value="${param.selectCompanyPost}"/>
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