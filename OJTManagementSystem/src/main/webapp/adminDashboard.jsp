<%-- 
    Document   : adminDashboard
    Created on : Jul 18, 2022, 4:43:01 PM
    Author     : Thanh Huy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Admin Dashboard</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/admin.css">
        <link rel="stylesheet" href="./assets/css/admin-responsive.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
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
                            <a href="AdminDashboardController" class="nav__item--link link-active">
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
                        <a href="ShowAdminStudentManagementController" class="nav__item--link link-active">
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

            </nav>        <div class="main-body  offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row  row-cols-xl-4 row-cols-2 ">
                    <div class="dashboard-card col">
                        <form action="AdminSearchCompanyPostController" method="post">
                            <input type="hidden" name="txtTitle" value ="">
                            <input type="hidden" name="txtCompanyName" value ="">
                            <input type="hidden" name="page" value ="1">
                            <input type="hidden" name="nameStatus" value="Waiting">
                            <label class="dashboard-card--link" for ="post">
                                <div class="pending-posts">
                                    ${requestScope.TOTAL_PENDING_POST}
                                </div>
                                <div class="dashboard-card__content">
                                    Posts waiting
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="post">
                        </form>
                    </div>
                    <div class="dashboard-card col">
                        <form action="ShowAdminStudentManagementController" method="post">
                            <label class="dashboard-card--link" for ="student">
                                <div class="total-student">
                                    ${requestScope.TOTAL_STUDENT}
                                </div>
                                <div class="dashboard-card__content">
                                    Total student
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id ="student">
                        </form>    
                    </div>
                    <div class="dashboard-card col">
                        <form action="SearchCompanyAdminManagerController" method="post">
                            <input type="hidden" name="selectCompany" value ="">
                            <input type="hidden" name="txtEmail" value ="">
                            <input type="hidden" name="selectStatus" value="Success">
                            <label class="dashboard-card--link" for="signed">
                                <div class="signed-company">
                                    ${requestScope.TOTAL_SIGNED_COMPANY}
                                </div>
                                <div class="dashboard-card__content">
                                    Signed company
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="signed">
                        </form>    
                    </div>
                    <div class="dashboard-card col">
                        <form action="SearchCompanyAdminManagerController" method="post">
                            <input type="hidden" name="selectCompany" value ="">
                            <input type="hidden" name="txtEmail" value ="">
                            <input type="hidden" name="selectStatus" value="Denied">
                            <label class="dashboard-card--link" for="unsigned">
                                <div class="unsigned-company">
                                    ${requestScope.TOTAL_UNSIGNED_COMPANY}
                                </div>
                                <div class="dashboard-card__content">
                                    Unsigned company
                                </div>
                            </label>
                            <input type="submit" value ="submit" hidden="hidden" id="unsigned">
                        </form>

                    </div>
                </div>

                <div class="admindashboard">
                    <div class="admindashboard__header">
                        <div class="row">
                            <div class="d-flex justify-content-between">
                                <h4><strong>Score Spectrum</strong></h4>
                                <div class="filterdashboard">
                                    <form action="AdminDashboardController" method="post">
                                        <select name="txtSemesterID" id="" class="filterdashboard__select">
                                            <c:forEach items="${requestScope.LIST_SEMESTERS}" var="semester">
                                                <option value="${semester.semesterID}"
                                                        <c:if test="${requestScope.SELECTED_SEMESTER eq semester.semesterID}">
                                                            selected="selected"
                                                        </c:if>
                                                        >${semester.semesterName}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="submit" value="Search" class="filterdashboard__button">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="admindashboard__content">
                        <canvas id="dashboard1" style="width:100%; height:400px"></canvas>

                        <script>
                            var xValues = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
                            var yValues = [${requestScope.LIST_GRADE}];

                            new Chart("dashboard1", {
                                type: "line",
                                data: {
                                    labels: xValues,
                                    datasets: [{
                                            fill: false,
                                            lineTension: 0,
                                            backgroundColor: "rgba(0,0,255,1.0)",
                                            borderColor: "rgba(0,0,255,0.1)",
                                            data: yValues
                                        }]
                                },
                                options: {
                                    legend: {display: false},
                                    scales: {
                                        yAxes: [{ticks: {min: 0, max: 10}}],
                                    }
                                }
                            });
                        </script>
                    </div>
                </div>

                <div class="admindashboard">
                    <div class="admindashboard__header">
                        <div class="row">
                            <div class="">
                                <h4><strong>Pass Rating</strong></h4>
                            </div>
                        </div>
                    </div>
                    <div class="admindashboard__content">
                        <canvas id="dashboard2" style="width:100%; height:400px"></canvas>

                        <script>
                            var xValues = [${requestScope.LIST_SEMESTER_STRING}];

                            new Chart("dashboard2", {
                                type: "line",
                                data: {
                                    labels: xValues,
                                    datasets: [{
                                            data: [${requestScope.LIST_FAILED}],
                                            borderColor: "red",
                                            fill: false,
                                            label: 'Not Pass'
                                        }, {
                                            data: [${requestScope.LIST_PASSED}],
                                            borderColor: "green",
                                            fill: false,
                                            label: 'Passed'
                                        }]
                                },
                                options: {
                                    legend: {display: true},
                                    scales: {
                                        yAxes: [{ticks: {min: 0, max: 20}}],
                                    }
                                }
                            });
                        </script>
                    </div>
                </div>
            </div>

        </main>

        <footer class="footer">
            <div class="footer__content">
                <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
            </div>
        </footer>

    </body>

</html>
