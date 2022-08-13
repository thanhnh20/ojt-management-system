<%-- 
    Document   : companyDashboard
    Created on : Jun 13, 2022, 2:58:07 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Company - Dashboard</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/company.css">
        <link rel="stylesheet" href="./assets/css/company-responsive.css">
    </head>
    <body>
        <header></header>

        <c:set var="company" value="${requestScope.COMPANY_INFORMATION}" />

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
                            <a href="ShowCompanyDashBoardController" class="nav__item--link link-active">
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
                        <a href="ShowCompanyDashBoardController" class="nav__item--link link-active">
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
                    <div class="card-visit offset-xl-2 col-xl-8 offset-1 col-10">
                        <div class="card-visit__body row">
                            <div class="card-visit--img  col-4">
                                <img src="./avatars/${company.account.avatar}" alt="${company.account.avatar}">
                            </div>
                            <div class="card-vist__content offset-1 col-7">
                                <h3>${company.account.name}</h3>
                                <p>Address: ${company.address}</p>
                                <p>Phone: ${company.phone}</p>
                                <p>Email: ${company.account.email}</p>
                            </div>

                        </div>
                        <a href="CompanyShowProfileController" class="card-visit-btn primary-btn">
                            Edit Profile
                        </a>

                    </div>
                </div>
                <c:set var="activeJobs" value="${requestScope.NUMBER_OF_ACTIVE_JOBS}" />
                <c:set var="inactiveJobs" value="${requestScope.NUMBER_OF_INACTIVE_JOBS}" />
                <c:set var="pedingJobs" value="${requestScope.NUMBER_OF_PENDING_JOBS}" />
                <c:set var="expiredJobs" value="${requestScope.NUMBER_OF_EXPIRED_JOBS}" />
                <div class="row  row-cols-xl-4 row-cols-2 ">
                    <div class="dashboard-card col">
                        <form action="CompanySearchPostController" method="post">
                            <input type="hidden" name="nameStatus" value="Active">
                            <input type="hidden" name="companyID" value="${company.companyID}">
                            <label for="Active" class="dashboard-card--link">
                                <div class="active-jobs">
                                    ${activeJobs}
                                </div>
                                <div class="dashboard-card__content">
                                    Active Jobs
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="Active">
                        </form>
                    </div>
                    <div class="dashboard-card col">
                        <form action="CompanySearchPostController" method="post">
                            <input type="hidden" name="nameStatus" value="Pending">
                            <input type="hidden" name="companyID" value="${company.companyID}">
                            <label for="Pending" class="dashboard-card--link">
                                <div class="pending-jobs">
                                    ${pedingJobs}
                                </div>
                                <div class="dashboard-card__content">
                                    Pending Jobs
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="Pending">
                        </form>
                    </div>
                    <div class="dashboard-card col">
                        <form action="CompanySearchPostController" method="post">
                            <input type="hidden" name="nameStatus" value="Inactive">
                            <input type="hidden" name="companyID" value="${company.companyID}">
                            <label for="Inactive"class="dashboard-card--link">
                                <div class="inactive-jobs">
                                    ${inactiveJobs}
                                </div>
                                <div class="dashboard-card__content">
                                    Inactive Jobs
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="Inactive">
                        </form>
                    </div>
                    <div class="dashboard-card col">
                        <form action="CompanySearchPostController" method="post">
                            <input type="hidden" name="nameStatus" value="Expired">
                            <input type="hidden" name="companyID" value="${company.companyID}">
                            <label for="Expired"class="dashboard-card--link">
                                <div class="expired-jobs">
                                    ${expiredJobs}
                                </div>
                                <div class="dashboard-card__content">
                                    Expired Jobs
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="Expired">
                        </form>
                    </div>
                </div>

                <c:set var="listActiveJobs" value="${requestScope.LIST_ACTIVE_JOBS}" />
                <div class="cActive-jobs">
                    <div class="cActive-header">
                        Active Jobs
                    </div>
                    <c:if test="${not empty listActiveJobs}">
                        <div class="row row-cols-xl-2 row-cols-1">
                            <c:forEach items="${listActiveJobs}" var="activePost" begin="0" end="5">
                                <div class="col">
                                    <div class="cActive-box row ">
                                        <a href="CompanyViewPostDetailController?postID=${activePost.postID}">
                                            <h3>
                                                ${activePost.title_Post}
                                            </h3>
                                        </a>
                                        <div class="cActive-box__img col-4">
                                            <img src="./avatars/${activePost.company.account.avatar}" alt="${activePost.company.account.avatar}" />
                                        </div>
                                        <div class="cActive-box-content col-8">
                                            <p>Vacancy: ${activePost.vacancy}</p>
                                            <p>Quantity: ${activePost.quantityIterns}</p>
                                            <p>Work Location: ${activePost.workLocation}</p>
                                            <p>Expired Date: ${my:changeDateFormat(activePost.expirationDate)}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="cActive__see-more--btn">
                            <form action="CompanySearchPostController" method="post">
                                <input type="hidden" name="companyID" value="${company.companyID}">
                                <input type="hidden" name="nameStatus" value="Active">
                                <label for="seemore" class="cActive--more--btn">
                                    See More
                                    <i class="fas fa-arrow-right"></i>
                                </label>
                                <input type="submit" value="submit" hidden="hidden" id ="seemore">
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${empty listActiveJobs}">
                        <c:if test ="${company.is_Signed eq true}">
                            <div class ="text-center" style="padding: 30px">
                                <h3>Add your new posts to recruit more candidates.</h3>
                                <a href="ShowCreateNewCompanyPostController">Add a new Post!</a>
                            </div>
                        </c:if>
                        <c:if test ="${company.is_Signed eq false}">
                            <div class ="text-center" style="padding: 30px">
                                <h4 style="color: red">* Currently, you do not have permission to add the post. <br>
                                    Please wait for confirmation about signed status from The Business Relations Department.</h4>
                            </div>
                        </c:if>
                    </c:if>
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
