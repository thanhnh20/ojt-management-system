<%-- 
    Document   : home
    Created on : May 25, 2022, 4:42:15 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/home.css">
        <link rel="stylesheet" href="./assets/css/home-responsive.css">
        <script src="./assets/font/bootstrap-5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <header class="header ">
            <div class="navbar header__nav_cus">
                <a href="ShowStudentHomeController" class="header__logo">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>
                <div class="header__name">
                    <div class="header__name--show">
                        <c:set var="student" value="${sessionScope.STUDENT_ROLE}"/>
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

        <main class="main">
            <img src="./assets/img/main.png" alt="" class="main__img">
            <div class="main__search">
                <h2>Search Jobs</h2>
                <form action="SearchCompanyStudentHomeController" class="main__search-form" method="post">
                    <div class="row">
                        <div class="col-4">
                            <select name="nameCompany">
                                <option value="" hidden>Company Name</option>
                                <option value="">All Company</option>
                                <c:forEach items="${requestScope.COMPANY_NAME}" var="company">
                                    <option value="${company.companyID}">${company.account.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <select name="nameMajor">
                                <option value="" hidden="">Major</option>
                                <option value="">All Major</option>
                                <c:forEach items="${requestScope.LIST_NAME_MAJOR}" var="major">
                                    <option value="${major.majorID}">${major.majorName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2 ">
                            <select id="city" name="nameLocation" class="form__select"  >
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

            <div class="main__company">
                <h2>Company</h2>
                <div class="row row-cols-2 row-cols-lg-3 row-cols-md-3 row-cols-xl-4">
                    <c:forEach items="${requestScope.LIST_POST_HOME}" var="dto" begin="0" end="7">
                        <div class="col">
                            <div class="card-company">
                                <img src="./avatars/${dto.company.account.avatar}" alt="${dto.company.account.avatar}" class="card-company--img">
                                <a href="HomeShowCompanyDetailController?postID=${dto.postID}" class="card-company-header">
                                    ${dto.company.account.name}
                                </a>
                                <div class="card-company-body">
                                    <p>Jobs: ${dto.title_Post}</p>
                                    <p>Vacancy: ${dto.vacancy}</p>
                                    <p>Quantity: ${dto.quantityIterns}</p>
                                    <p>Location: ${dto.workLocation}</p>
                                    <p>Expiration Date: ${my:changeDateFormat(dto.expirationDate)}</p>
                                </div>
                                <div class="card-company-btn">
                                    <a href="ShowApplyCVController?postID=${dto.postID}" class="primary-btn hApply-btn">Apply Now</a>
                                </div>   

                                <c:set var="statusFollowing" value="${my:getStatusSaveJob(requestScope.LIST_FOLLOWING_POST, dto.postID)}" />
                                <c:if test="${statusFollowing eq true}">
                                    <form action="StudentDeleteSaveJobController" method="POST">
                                        <input type="hidden" name="unSave" value="homePage" />
                                        <input type="hidden" name="postID" value="${dto.postID}" />
                                        <label for="unsaveJob+${dto.postID}" class="far fa-heart card-company-btn-save save-btn save-btn-active">
                                            <input type="submit" value="Unsave Job"  id="unsaveJob+${dto.postID}" hidden/>
                                        </label>
                                    </form>
                                </c:if>
                                <c:if test="${statusFollowing eq false}">
                                    <form action="StudentSaveJobController" method="POST">
                                        <input type="hidden" name="save" value="homePage" />
                                        <input type="hidden" name="postID" value="${dto.postID}" />
                                        <label for="saveJob+${dto.postID}" class="far fa-heart card-company-btn-save save-btn">
                                            <input type="submit" value="" hidden id="saveJob+${dto.postID}"/>
                                        </label>
                                    </form>

                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="home_see-more">
                    <c:url var="urlSearchHome" value="SearchCompanyStudentHomeController">
                        <c:param name="nameCompany" value=""/>
                        <c:param name="nameMajor" value=""/>
                        <c:param name="nameLocation" value=""/>
                    </c:url>
                    <a href="${urlSearchHome}" class="home_see-more--btn ">
                        See More 
                        <i class="fas fa-arrow-right"></i>
                    </a>
                </div>
            </div>

            <div id="carouselExampleControls" class="carousel carousel-dark slide" data-bs-ride="carousel">
                <div class="carousel-inner" style="padding: 10px 70px;">
                    <c:forEach begin="1" end="${my:getIndexList(requestScope.LIST_AVATAR_SIGNED_COMPANY)}" var="index">
                        <div class="carousel-item <c:if test="${index eq 1}">active</c:if>">
                            <c:forEach items="${my:getList(requestScope.LIST_AVATAR_SIGNED_COMPANY, index)}" var="item">
                                <span style="margin:10px">
                                    <a href="SearchCompanyStudentHomeController?nameCompany=${item.companyID}&nameMajor=&nameLocation=" class="icon-company-link">
                                        <img src="./avatars/${item.account.avatar}" alt="${item.account.avatar}" class="main__company-img ">
                                    </a> 
                                </span>

                            </c:forEach> 
                        </div>
                    </c:forEach>
                </div>
                <button style="width:7%;" class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button style="width:7%;" class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            
        </main>

        <footer class="footer">
            <div class="footer__content">
                <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
            </div>

        </footer>
    </body>
</html>
