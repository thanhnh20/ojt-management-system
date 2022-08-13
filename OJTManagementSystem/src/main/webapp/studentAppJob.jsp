<%-- 
    Document   : studentAppJob
    Created on : Jun 2, 2022, 9:27:42 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/myapplicationlib.tld" prefix="my"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Student - Applied Jobs</title>
        <link rel="stylesheet" href="./assets/font/fontawesome-free-6.1.1-web/css/all.min.css">
        <link rel="stylesheet" href="./assets/font/bootstrap-5.2.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/css/base.css">
        <link rel="stylesheet" href="./assets/css/student.css">
        <link rel="stylesheet" href="./assets/css/student-responsive.css">
    </head>
    <body>
        <header></header>


        <c:set var="student" value="${sessionScope.STUDENT_ROLE}"/>
        <div class="navbar navbar-expand-md navbar-dark text-center navbar-sm-cus">
            <div class="container-fluid">
                <a href="ShowStudentHomeController" class="header__logo ">
                    <img src="./assets/img/logo.png" alt="" class="logo">
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa-solid fa-bars nav__respo--btn"></i>
                </button>
                <div class="collapse navbar-collapse navbar-collapse-cus" id="navbarSupportedContent">
                    <a href="ShowStudentProfileController" class=" nav__infor--link text-truncate text-center">
                        <i class="fas fa-user-circle nav__infor--icon"></i>
                        ${student.account.name}
                    </a>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a href="studentDashboardController" class="nav__item--link ">
                                <i class="fas fa-palette "></i>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="ShowStudentProfileController" class="nav__item--link">
                                <i class="fas fa-user-edit"></i>
                                My Profile
                            </a> 
                        </li>
                        <li class="nav-item nav__items">
                            <a href="" class="nav__item--link nav__item--dropdown link-active">
                                <i class="fas fa-pen"></i>
                                My Jobs
                                <i class="fas fa-angle-down icon-down"></i>
                            </a>
                            <div class="nav__item__dropdown">
                                <a href="" class="nav__item__dropdown--link ">
                                    Saved Jobs
                                </a>
                                <a href="" class="nav__item__dropdown--link link-active">
                                    Applied Jobs
                                </a>
                            </div>
                        </li>

                        <li class="nav-item">
                            <a href="ReviewInternShipController" class="nav__item--link">
                                <i class="fas fa-poll-h"></i>
                                Review Internship
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
                <a href="ShowStudentHomeController" class="nav__logo ">
                    <img src="./assets/img/logo.png" alt="" class="nav--logo">
                </a>
                <a href="ShowStudentProfileController" class=" nav__infor--link text-truncate">
                    <i class="fas fa-user-circle nav__infor--icon"></i>
                    ${student.account.name}
                </a>

                <ul class="nav__content">
                    <li class="nav__items">
                        <a href="studentDashboardController" class="nav__item--link">
                            <i class="fas fa-palette "></i>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav__items">
                        <a href="ShowStudentProfileController" class="nav__item--link">
                            <i class="fas fa-user-edit"></i>
                            My Profile
                        </a>
                    </li>
                    <li class="nav__items">
                        <div  class="nav__item--link nav__item--dropdown link-active">
                            <i class="fas fa-pen"></i>
                            My Jobs
                            <i class="fas fa-angle-down icon-down"></i>
                        </div>
                        <div class="nav__item__dropdown">
                            <a href="SearchSaveJobController" class="nav__item__dropdown--link">
                                Saved Jobs
                            </a>
                            <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link link-active">
                                Applied Jobs
                            </a>
                        </div>
                    </li>
                    <li class="nav__items">
                        <a href="ReviewInternShipController" class="nav__item--link">
                            <i class="fas fa-poll-h"></i>
                            Review Internship
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
                    <div class="main-body-appl ">
                        <div class="main-body-appl__header">
                            Applied Jobs
                        </div>


                        <div class="main-body-appl__search">
                            <form action="SearchStudentAppliedJobController" method="POST">
                                <div class="row">
                                    <div class="col-3">
                                        <input type="text" name="nameTypeJob" class="student--input" value="${param.nameTypeJob}" id="" placeholder="Type Job">
                                    </div>
                                    <div class="col-3">                             
                                        <!--<input type="text" name="nameCompany" class="student--input"  value="${param.nameCompany}" id="" placeholder="Company">-->
                                        <c:set var="listCompanyName" value="${requestScope.List_COMPANY_NAME}"/>
                                        <select id="city" name="nameCompany"  class="student--select" >
                                            <option value="">All Company</option>
                                            <c:forEach var="companyName" items="${listCompanyName}">
                                                <option value="${companyName}" <c:if test="${param.nameCompany eq companyName}">
                                                        selected="selected"
                                                    </c:if>>${companyName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-2">
                                        <select id="city" name="nameLocation"  class="student--select" >
                                            <option value="" hidden>Location</option>
                                            <option value="">All Location</option>
                                            <option value="TP.HCM" <c:if test="${param.nameLocation eq 'TP.HCM'}">
                                                    selected="selected"
                                                </c:if>>TP.HCM</option>
                                            <option value="Đồng Nai" <c:if test="${param.nameLocation eq 'Đồng Nai'}">
                                                    selected="selected"
                                                </c:if>>Đồng Nai</option>
                                            <option value="Tây Ninh" <c:if test="${param.nameLocation eq 'Tây Ninh'}">
                                                    selected="selected"
                                                </c:if>>Tây Ninh</option>
                                            <option value="Bình Dương" <c:if test="${param.nameLocation eq 'Bình Dương'}">
                                                    selected="selected"
                                                </c:if>>Bình Dương</option>
                                        </select>
                                    </div>
                                    <div class="col-2">
                                        <select id="city" name="nameStatus"  class="student--select" >
                                            <option value="" hidden>Status</option>
                                            <option value="" >All Status</option>
                                            <option value="Denied" class="text-danger" <c:if test="${param.nameStatus eq 'Denied'}">
                                                    selected="selected"
                                                </c:if>>Denied</option>
                                            <option value="Waiting" class="text-warning" <c:if test="${param.nameStatus eq 'Waiting'}">
                                                    selected="selected"
                                                </c:if>>Waiting</option>
                                            <option value="Success" class="text-success" <c:if test="${param.nameStatus eq 'Success'}">
                                                    selected="selected"
                                                </c:if>>Success</option>
                                            <option value="Interviewing" class="text-warning" <c:if test="${param.nameStatus eq 'Interviewing'}">
                                                    selected="selected"
                                                </c:if>>Interviewing</option>
                                            <option value="Failed" class="text-danger" <c:if test="${param.nameStatus eq 'Failed'}">
                                                    selected="selected"
                                                </c:if>>Failed</option>
                                            <option value="Canceled" class="text-gray" <c:if test="${param.nameStatus eq 'Canceled'}">
                                                    selected="selected"
                                                </c:if>>Canceled</option>
                                        </select>
                                    </div>
                                    <div class="col-2">
                                        <input type="submit" name="btAction" value="Search" class="student-search-btn">
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="main-body-appl__content">
                            <div class="resultpage__header">
                                Result : ${requestScope.SIZE_OF_LIST}
                            </div>
                            <c:if test="${not empty requestScope.LIST_APPLIED_JOB_RESULT}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>NO.</th>
                                            <th>Type Job</th>
                                            <th>Company</th>
                                            <th>Location</th>
                                            <th>Expiration Date</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.LIST_APPLIED_JOB_RESULT}" var="appliedJob" varStatus="counter">
                                            <tr>
                                                <td>${my:counter(requestScope.page, counter.count)}</td>
                                                <td>
                                                    <a href="HomeShowCompanyDetailController?postID=${appliedJob.companyPost.postID}">${appliedJob.companyPost.title_Post}</a>
                                                </td>
                                                <td>${appliedJob.companyPost.company.account.name}</td>
                                                <td>${appliedJob.companyPost.workLocation}</td>
                                                <td>${my:changeDateFormat(appliedJob.companyPost.expirationDate)}</td>
                                                <%--<c:if test="${(appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 0 and appliedJob.companyConfirm eq 0)
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 1 and appliedJob.companyConfirm eq 2) }">
                                                      <td class="text-warning">
                                                          <strong>
                                                              Waiting
                                                          </strong>
                                                      </td>
                                                </c:if>--%>
                                                <c:if test="${appliedJob.studentConfirm eq true and appliedJob.companyConfirm eq 0}">
                                                    <td class="text-warning">
                                                        <strong>
                                                            Waiting
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${appliedJob.studentConfirm eq true and appliedJob.companyConfirm eq -1}" >
                                                    <td class="text-danger">
                                                        <strong>
                                                            Denied
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <%--<c:if test="${(appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq -1)
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq -2)}" >
                                                      <td class="text-danger">
                                                          <strong>
                                                              Denied
                                                          </strong>
                                                      </td>
                                                </c:if>---%>
                                                <c:if test="${appliedJob.studentConfirm eq true and appliedJob.companyConfirm eq 1}" >
                                                    <td class="text-success">
                                                        <strong>
                                                            Accepted
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${appliedJob.studentConfirm eq true and appliedJob.companyConfirm eq 2}" >
                                                    <td class="text-warning">
                                                        <strong>
                                                            Interviewing
                                                        </strong>
                                                    </td>
                                                </c:if>
                                                <c:if test="${appliedJob.studentConfirm eq true and appliedJob.companyConfirm eq -2}" >
                                                    <td class="text-danger">
                                                        <strong>
                                                            Failed
                                                        </strong>
                                                    </td>
                                                </c:if>

                                                <c:if test="${appliedJob.studentConfirm eq false}">
                                                    <td class="text-gray">
                                                        <strong>
                                                            Canceled
                                                        </strong>
                                                    </td>
                                                </c:if>

                                                <c:url var="url" value="CancleApplyCVController" >
                                                    <c:param name="applicationID" value="${appliedJob.applicationID}" />
                                                </c:url>
                                                <c:if test="${(appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 0 and appliedJob.companyConfirm eq 0)
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 1 and appliedJob.companyConfirm eq 2)}">
                                                      <td>
                                                          <form action="${url}" method="POST">
                                                              <input type="submit" name="btAction" value="Cancel" class="btn-regular-red" />
                                                          </form>

                                                      </td>
                                                </c:if>
                                                <c:if test="${appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 1 and appliedJob.companyConfirm eq 0}">
                                                    <td>
                                                        <form action="${url}" method="POST">
                                                            <input type="submit" name="btAction" value="Cancel" class="btn-regular-red"/>
                                                        </form>

                                                    </td>
                                                </c:if>
                                                <c:if test="${(appliedJob.studentConfirm eq false)
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq -1) 
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 1 and appliedJob.companyConfirm eq -2)
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 1 and appliedJob.companyConfirm eq -1)
                                                              or (appliedJob.studentConfirm eq true and appliedJob.schoolConfirm eq 1 and appliedJob.companyConfirm eq 1)}">
                                                      <td>
                                                          <form action="${url}" method="POST">
                                                              <input type="submit" name="btAction" value="Cancel"  class="btn-regular-red-disable" disabled="disabled" style="opacity: 0.4" />
                                                          </form>
                                                      </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </tbody>

                                </table>
                                <div class="main__pagination">
                                    <ul class="pagination main_cus__pagination">
                                        <c:set var="map" value="${my:paging(requestScope.page, 10, requestScope.numberPage)}"/>
                                        <c:if test="${requestScope.page gt 5 }">
                                            <li class="page-item" >
                                                <form action="SearchStudentAppliedJobController" method="POST">
                                                    <input type="hidden" name="page" value="${map['startNum'] - 1}"/>
                                                    <input type="hidden" name="nameTypeJob" value="${param.txtJob}"/>
                                                    <input type="hidden" name="nameCompany" value="${param.txtCompany}"/>
                                                    <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                    <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
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
                                                        <form action="SearchStudentAppliedJobController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="nameTypeJob" value="${param.txtJob}"/>
                                                            <input type="hidden" name="nameCompany" value="${param.txtCompany}"/>
                                                            <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                            <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>"/>
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:when test="${ i > map['lastPageNum'] and step le 0}">
                                                    <li class="page-item" >  
                                                        <form action="SearchStudentAppliedJobController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="nameTypeJob" value="${param.txtJob}"/>
                                                            <input type="hidden" name="nameCompany" value="${param.txtCompany}"/>
                                                            <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                            <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                            <input type="submit" value="${i}" class="page-link <c:if test="${i eq requestScope.page}">
                                                                   pagination-active
                                                                </c:if>"/>
                                                        </form>
                                                    </li>
                                                </c:when>
                                                <c:when test="${ i eq requestScope.page and step le 0 }">
                                                    <li class="page-item" >    
                                                        <form action="SearchStudentAppliedJobController" method="POST">
                                                            <input type="hidden" name="page" value="${i}"/>
                                                            <input type="hidden" name="nameTypeJob" value="${param.txtJob}"/>
                                                            <input type="hidden" name="nameCompany" value="${param.txtCompany}"/>
                                                            <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
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
                                                            <form action="SearchStudentAppliedJobController" method="POST">
                                                                <input type="hidden" name="page" value="${i}"/>
                                                                <input type="hidden" name="nameTypeJob" value="${param.txtJob}"/>
                                                                <input type="hidden" name="nameCompany" value="${param.txtCompany}"/>
                                                                <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
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
                                                <form action="SearchStudentAppliedJobController" method="POST">
                                                    <input type="hidden" name="page" value="${map['lastNum'] + 1}"/>
                                                    <input type="hidden" name="nameTypeJob" value="${param.txtJob}"/>
                                                    <input type="hidden" name="nameCompany" value="${param.txtCompany}"/>
                                                    <input type="hidden" name="nameLocation" value="${param.nameLocation}"/>
                                                    <input type="hidden" name="nameStatus" value="${param.nameStatus}"/>
                                                    <input type="submit" value="Next" class="page-link"/>
                                                </form>
                                            </li>
                                            <!--đưa icon vào-->
                                        </c:if>
                                    </ul>
                                </div>

                            </c:if>
                            <c:if test="${empty requestScope.LIST_APPLIED_JOB_RESULT}">
                                <p3>
                                    You have not any applied job yet!
                                </p3>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

        </main>
        <footer class="footer">
            <div class="container-fluid">
                <div class="footer__content">
                    <i class="fa-regular fa-copyright"></i> Copyright 2022,  Developed by <strong> OJT-Team </strong>
                </div>
            </div>
        </footer>

        <script src="./assets/font/bootstrap-5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
