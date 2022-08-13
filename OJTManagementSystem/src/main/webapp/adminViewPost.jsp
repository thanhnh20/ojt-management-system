<%-- 
    Document   : adminViewPost
    Created on : Jun 4, 2022, 7:39:59 PM
    Author     : ThanhTy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - View Post</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/admin.css">
        <link rel="stylesheet" href="./assets/css/admin-responsive.css">
    </head>

    <body>
        <header></header>
            <c:set var="admin" value="${sessionScope.ADMIN_ROLE}" />
        <div class="navbar navbar-expand-md navbar-dark text-center navbar-sm-cus">
            <div class="container-fluid">
                <a href="ShowAdminStudentManagementController" class="header__logo ">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa-solid fa-bars nav__respo--btn"></i>
                </button>
                <div class="collapse navbar-collapse navbar-collapse-cus" id="navbarSupportedContent">
                    <a href="" class=" nav__infor--link text-truncate text-center">
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
                <a href="#" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="#" class=" nav__infor--link text-truncate">
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
            <c:set var="post" value="${requestScope.COMPANY_POST_DETAIL}"/>
            <div class="main-body  offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="main-body-aViewPost">
                    <div class="aViewPost__header">
                        ${post.title_Post}
                    </div>
                    <div class="aViewPost__content">
                        <p><strong>Job:</strong> ${post.major.majorName}</p>
                        <p><strong>Vacancy:</strong> ${post.vacancy}</p>
                        <p><strong>Quantity:</strong> ${post.quantityIterns}</p>
                        <p><strong>Job Description:</strong> <br/>
                            ${post.job_Description}
                        </p>
                        <p><strong>Job requirements:</strong> <br/>
                            ${post.job_Requirement}              
                        </p>
                        <p><strong>Remuneration:</strong><br/>
                            ${post.remuneration}
                        </p>
                        <p><strong>Work location:</strong>
                            ${post.workLocation}
                        </p>
                        <p><strong>Posting Date:</strong>
                            ${my:changeDateFormat(post.postingDate)}
                        </p>
                        <p><strong>Expiration Date:</strong>
                            ${my:changeDateFormat(post.expirationDate)}
                        </p>
                        <p><strong>Status: </strong>
                            <c:if test="${post.statusPost eq 2}">
                                <span class="text-success">
                                    <strong>
                                        Accept
                                    </strong>
                                </span>
                            </c:if>
                            <c:if test="${post.statusPost eq 0 or post.statusPost eq 3}">
                                <span class="text-danger">
                                    <strong>
                                        Denied
                                    </strong>
                                </span>
                            </c:if>  
                            <c:if test="${post.statusPost eq 1}">
                                <span class="text-warning">
                                    <strong>
                                        Waiting
                                    </strong>
                                </span>
                            </c:if>
                        </p>
                    </div>

                    <div class="aViewPost-btn">
                        <form action="AdminUpdatePostController" method="POST">
                            <div>
                                <input type="hidden" name="save" value="adminViewPostPage" />
                                <input type="hidden" name="school_confirm" value="true" />
                                <input type="hidden" name="statusPost" value="2"/>
                                <input type="hidden" name="postID" value="${post.postID}" />
                                <%-- lay param de back lai trang cu~ --%>
                                <input type="hidden" name="page" value="${requestScope.page}" />
                                <input type="hidden" name="semester" value="${param.semester}" />
                                <input type="hidden" name="txtTitle" value="${param.txtTitle}"/>
                                <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                <input type="submit" value="Accept" class="primary-btn accept-btn" 
                                       <c:if test="${post.statusPost ne 1}">
                                           autocomplete="off" hidden 
                                       </c:if> />
                            </div>

                        </form>
                        <div>
                            
                                <button type="button" class="primary-btn reject-btn" data-bs-toggle="modal" data-bs-target="#exampleModal" 
                                        <c:if test="${post.statusPost ne 1}">hidden="hidden"</c:if> >
                                    Reject
                                </button>
                            

                            <!-- Modal -->
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h3 class="modal-title" id="exampleModalLabel">Reason Reject</h3>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <form action="AdminUpdatePostController" method="POST"> 
                                            <div class="modal-body">
                                                <textarea name="" id="" cols="60" rows="5" style="resize:none"></textarea>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <div>
                                                        <input type="hidden" name="save" value="adminViewPostPage" />
                                                        <input type="hidden" name="school_confirm" value="false" />
                                                        <input type="hidden" name="statusPost" value="0"/>
                                                        <input type="hidden" name="postID" value="${post.postID}" />
                                                        <!-- lay param de back lai trang cu~ -->
                                                        <input type="hidden" name="page" value="${requestScope.page}" />
                                                        <input type="hidden" name="semester" value="${param.semester}" />
                                                        <input type="hidden" name="txtTitle" value="${param.txtTitle}"/>
                                                        <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                                        <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                        <input type="submit" value="Reject" class="btn-regular-red"
                                                               <c:if test="${post.statusPost ne 1}">
                                                                   autocomplete="off" hidden 
                                                               </c:if> />

                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-center">
                        <form action="AdminSearchCompanyPostController" method="POST">
                            <div>
                                <input type="hidden" name="page" value="${requestScope.page}"/>
                                <input type="hidden" name="semester" value="${param.semester}" />
                                <input type="hidden" name="txtTitle" value="${param.txtTitle}"/>
                                <input type="hidden" name="txtCompanyName" value="${param.txtCompanyName}"/>
                                <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                <input type="submit" value="Back" class="btn-regular-gray" />
                            </div>
                        </form>
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
