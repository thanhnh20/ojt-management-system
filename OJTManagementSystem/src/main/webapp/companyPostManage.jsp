<%-- 
    Document   : companyPostManage
    Created on : Jun 13, 2022, 9:51:22 PM
    Author     : ThanhTy
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
        <title>Company - Post Management</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">

    </head>

    <body>
        <header></header>
            <c:set var="company" value="${sessionScope.COMPANY_ROLE_INFO}"/>

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
                        <font>  ${company.account.name} </font>
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
                            <a href="CompanyShowPostController" class="nav__item--link link-active">
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
                    ${company.account.name}
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
                        <a href="CompanyShowPostController" class="nav__item--link link-active">
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
            <div class="main-body   offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-cPostManage ">
                        <div class="main-body-cPostManage__header">
                            Post Management
                        </div>
                        <c:set  var="error" value="${requestScope.COMPANY_NOT_ALLOW_CREATE_POST}"/>
                        <div class="main-body-cPostManage__create">
                            <!-- Đặt điều kiện ở đây -->
                            <c:if test="${company.is_Signed eq true}">

                                <a href="ShowCreateNewCompanyPostController" class="main-body-cPostManage__create-btn">
                                    <i class="fa-solid fa-plus"></i>
                                    Create
                                </a>
                            </c:if>
                            <!-- hiển thị popup,title và nội dung tự viết, viết dài ra -->
                            <c:if test="${company.is_Signed eq false}">

                                <button type="button" class="main-body-cPostManage__create-btn1" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    <i class="fa-solid fa-plus"></i>
                                    Create
                                </button>
                            </c:if>
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h3 class="modal-title" id="exampleModalLabel">Error</h3>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body text-start" style="color: red">
                                            You can not create post. Because your company has not signed with FPT University.
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="main-body-cPostManage__search">
                            <form action="CompanySearchPostController" method="POST">

                                <div class="row">
                                    <input type="hidden" name="companyID" value="${company.companyID}"/>
                                    <div class="col-5">
                                        <input type="text" name="title_Post" value="${param.title_Post}" name="title_Post" id="" placeholder="Title Job" class="company--input">
                                    </div>
                                    <div class="col-3">
                                        <select name="nameMajor" class="company--select" id="title">
                                            <option value=""hidden>Major</option>
                                            <option value="">All Major</option>
                                            <c:forEach items="${requestScope.LIST_MAJOR_NAME}" var="major">
                                                <option value="${major.majorID}" <c:if test="${major.majorID eq param.nameMajor}">
                                                        selected="selected"
                                                    </c:if> >${major.majorName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-3">
                                        <select id="city" name="nameStatus" class="company--select" >
                                            <option value="" hidden>Status</option>
                                            <option value="" >All Status</option>
                                            <option value="Active" class="text-success" <c:if test="${param.nameStatus eq 'Active'}">
                                                    selected="selected"
                                                </c:if>>Active</option>
                                            <option value="Inactive" class="text-muted" <c:if test="${param.nameStatus eq 'Inactive'}">
                                                    selected="selected"
                                                </c:if>>Inactive</option>
                                            <option value="Pending" class="text-warning" <c:if test="${param.nameStatus eq 'Pending'}">
                                                    selected="selected"
                                                </c:if>>Pending</option>
                                            <option value="Expired" class="text-danger" <c:if test="${param.nameStatus eq 'Expired'}">
                                                    selected="selected"
                                                </c:if>>Expired</option>

                                        </select>
                                    </div>

                                    <div class="col-1">
                                        <input type="submit" value="Search" class=" company-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="main-body-cPostManage__content">
                            <c:set var="companyPostList" value="${requestScope.COMPANY_POST_LIST}"/>
                            <div class="resultpage__header">
                                Result : ${requestScope.SIZE_OF_LIST}
                            </div>
                            <c:if test="${not empty requestScope.COMPANY_POST_LIST}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>Title</th>
                                            <th>Posting Date</th>
                                            <th>Expiration Date</th>
                                            <th>Category</th>
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
                                                    <a href="CompanyViewPostDetailController?postID=${post.postID}">${post.title_Post}</a>
                                                </td>
                                                <td>${my:changeDateFormat(post.postingDate)}</td>
                                                <td>${my:changeDateFormat(post.expirationDate)}</td>
                                                <td>${post.major.majorName}</td>
                                                <c:if test="${post.statusPost eq 0}">
                                                    <td class="text-muted">
                                                        <strong>
                                                            Inactive
                                                        </strong>
                                                    </td>
                                                </c:if>  
                                                <c:if test="${post.statusPost eq 1}">
                                                    <td class="text-warning">
                                                        <strong>
                                                            Pending
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${post.statusPost eq 2}">
                                                    <td class="text-success">
                                                        <strong>
                                                            Active
                                                        </strong>
                                                    </td>
                                                </c:if>    
                                                <c:if test="${post.statusPost eq 3}">
                                                    <td class="text-danger">
                                                        <strong>
                                                            Expired
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <td>
                                                    <form action="CompanyShowPostDetailsController" method ="post">
                                                        <input type="hidden" name="postID" value="${post.postID}" >
                                                        <c:if test="${company.is_Signed eq false}">
                                                            <input type="submit" value="Edit" class="btn-update-green" disabled="disabled" />
                                                        </c:if>
                                                            <c:if test="${company.is_Signed eq true}">
                                                            <input type="submit" value="Edit" class="btn-update-green" />
                                                        </c:if>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>

                                </table>
                            </div>

                            <div class="main__pagination">
                                <ul class="pagination main_cus__pagination">
                                    <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                    <c:if test="${requestScope.page gt 5 }">
                                        <li class="page-item" >
                                            <form action="CompanySearchPostController" method="POST">
                                                <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                                <input type="hidden" name="companyID" value="${company.companyID}"/>
                                                <input type="hidden" name="title_Post" value="${param.title_Post}"/>
                                                <input type="hidden" name="nameMajor" value="${param.nameMajor}"/>
                                                <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                <input type="submit" value="Previous" class="page-link <c:if test="${i eq requestScope.page}">
                                                       pagination-active
                                                    </c:if>"/>
                                            </form>
                                        </li>
                                        <!--đưa icon vào-->
                                    </c:if>

                                    <c:forEach var="i" begin="${ map['startNum']}" end="${ map['lastNum']}">
                                        <c:set var="step" value="${i - requestScope.numberPage}" />
                                        <c:choose>
                                            <c:when test="${ step le 0}">
                                                <li class="page-item" >
                                                    <form action="CompanySearchPostController" method="POST">
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="hidden" name="companyID" value="${company.companyID}"/>
                                                        <input type="hidden" name="title_Post" value="${param.title_Post}"/>
                                                        <input type="hidden" name="nameMajor" value="${param.nameMajor}"/>
                                                        <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>"/>
                                                    </form>
                                                </li>
                                            </c:when>
                                            <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                                <li class="page-item" >  
                                                    <form action="CompanySearchPostController" method="POST">
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="hidden" name="companyID" value="${company.companyID}"/>
                                                        <input type="hidden" name="title_Post" value="${param.title_Post}"/>
                                                        <input type="hidden" name="nameMajor" value="${param.nameMajor}"/>
                                                        <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>"/>
                                                    </form>
                                                </li>
                                            </c:when>
                                            <c:when test="${ i eq requestScope.page and step le 0 }">
                                                <li class="page-item" >    
                                                    <form action="CompanySearchPostController" method="POST">
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="hidden" name="companyID" value="${company.companyID}"/>
                                                        <input type="hidden" name="title_Post" value="${param.title_Post}"/>
                                                        <input type="hidden" name="nameMajor" value="${param.nameMajor}"/>
                                                        <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                        <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                               pagination-active
                                                            </c:if>"/>
                                                    </form>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${step le 0}">
                                                    <li class="page-item" >
                                                        <form action="CompanySearchPostController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="companyID" value="${company.companyID}"/>
                                                            <input type="hidden" name="title_Post" value="${param.title_Post}"/>
                                                            <input type="hidden" name="nameMajor" value="${param.nameMajor}"/>
                                                            <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
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
                                            <form action="CompanySearchPostController" method="POST">
                                                <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                                <input type="hidden" name="companyID" value="${company.companyID}"/>
                                                <input type="hidden" name="title_Post" value="${param.title_Post}"/>
                                                <input type="hidden" name="nameMajor" value="${param.nameMajor}"/>
                                                <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                <input type="submit" value="Next" class="page-link"/>
                                            </form>
                                        </li>
                                        <!--đưa icon vào-->
                                    </c:if>
                                </ul>
                            </div>
                        </c:if>
                        <c:if test="${empty requestScope.COMPANY_POST_LIST}">
                            <p3>
                                You have not any post job yet!
                            </p3>
                        </c:if>

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
