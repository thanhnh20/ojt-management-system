<%-- 
    Document   : studentProfilePage
    Created on : May 30, 2022, 10:08:37 AM
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
        <title>Student- Profile</title>
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
                            <a href="studentDashboardController" class="nav__item--link">
                                <i class="fas fa-palette "></i>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="ShowStudentProfileController" class="nav__item--link link-active">
                                <i class="fas fa-user-edit"></i>
                                My Profile
                            </a> 
                        </li>
                        <li class="nav-item nav__items">
                            <div  class="nav__item--link nav__item--dropdown">
                                <i class="fas fa-pen"></i>
                                My Jobs
                                <i class="fas fa-angle-down icon-down"></i>
                            </div>
                            <div class="nav__item__dropdown">
                                <a href="SearchSaveJobController" class="nav__item__dropdown--link">
                                    Saved Jobs
                                </a>
                                <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link">
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
                        <c:url var="studentProfile" value="ShowStudentProfileController">                           
                        </c:url>
                        <a href="${studentProfile}" class="nav__item--link link-active">
                            <i class="fas fa-user-edit"></i>
                            My Profile
                        </a>    
                    </li>
                    <li class="nav__items">
                        <div  class="nav__item--link nav__item--dropdown">
                            <i class="fas fa-pen"></i>
                            My Jobs
                            <i class="fas fa-angle-down icon-down"></i>
                        </div>
                        <div class="nav__item__dropdown">
                            <a href="SearchSaveJobController" class="nav__item__dropdown--link">
                                Saved Jobs
                            </a>
                            <a href="ShowStudentAppliedJobController" class="nav__item__dropdown--link">
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

            <div class="main-body  offset-xl-2 col-xl-10 offset-md-3 col-md-9 col-12">
                <div class="row">
                    <div class="main-body-profile offset-xl-3 col-xl-6 offset-2 col-8">
                        <div class="main-body-profile__header">
                            Personal Profile 
                        </div>

                        <c:set var="studentProfile" value="${requestScope.STUDENT_PROFILE}"/>

                        <form action="UpdateStudentProfileController" method="POST" enctype="multipart/form-data">
                            <c:set var="errorUpdate" value="${requestScope.ERROR_UPDATE_STUDENTPROFILE}" />

                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="studentCode">Student Code</label>
                                <div class="col-8  profile--input-none-hover ">${studentProfile.studentCode}</div>
                                <input type="hidden" name="studentCode" value="${studentProfile.studentCode}" />
                            </div>
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="fullName">Full Name</label>
                                <div class="col-8  profile--input-none-hover  ">${studentProfile.account.name}</div>
                            </div>
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="birthday">Date of Birth</label>
                                <input type="date" class="col-8 profile--input " name="dateUpdate" id="birthday" value="${studentProfile.birthDay}">
                                <h5 class="text-danger offset-4 col-8 text-start">
                                    <c:if test="${not empty errorUpdate}">
                                        ${errorUpdate.errorDateInvalid}
                                        ${errorUpdate.errorDateEmpty}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="gender">Gender</label>
                                <select name="genderUpdate" class="col-8 profile--input">
                                    <option <c:if test="${studentProfile.gender eq false}">
                                            selected="selected"
                                        </c:if>>Female</option>
                                    <option <c:if test="${studentProfile.gender eq true}">
                                            selected="selected"
                                        </c:if>>Male</option>
                                </select>
                            </div>
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="email">Email</label>
                                <input type="email" readonly class="col-8 profile--input-none-hover " name="email" id="email"
                                       value="${studentProfile.account.email}">
                            </div>
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="address">Address</label>
                                <input type="text" class="col-8 profile--input " name="addressUpdate" id="address" value="${studentProfile.address}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errorUpdate}">
                                        ${errorUpdate.errorAddressLength}
                                    </c:if>
                                </h5>
                            </div>
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="phone">Phone Number</label>
                                <input type="number" class="col-8 profile--input " name="phoneUpdate" id="phone" value="${studentProfile.phone}">
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errorUpdate}">
                                        ${errorUpdate.errorPhoneNumberLength}
                                    </c:if>
                                </h5>
                                <h5 class="text-danger offset-4 col-8 text-start ">
                                    <c:if test="${not empty errorUpdate}">
                                        ${errorUpdate.errorPhoneNumberFormat}
                                    </c:if>
                                </h5>
                            </div>
                            <input type="hidden" name="postID" value="${param.postID}" />   
                            <div class="profile__input row">
                                <label class="col-4 profile--label" for="major">Major</label>
                                <input type="text" readonly class="col-8 profile--input-none-hover " name="" id="major"
                                       value="${studentProfile.major}">
                            </div>
                            <div class="profile__input"> 
                                <label for="inputFile" class="profile--label">
                                    Avatar
                                    <div class="input-file" for="inputFile"></div>
                                    <span id="displayResult">${my:getAvatarName(studentProfile.account.avatar)}</span>
                                    <input type="file" id="inputFile" name="myfile" value="./avatars/${studentProfile.account.avatar}" hidden="hidden">

                                </label>
                            </div>
                            <c:if test="${not empty errorUpdate.errorFileLength}">
                                <h5 class="text-danger offset-4 col-8 text-start">
                                    ${errorUpdate.errorFileLength}
                                </h5>
                            </c:if>
                            <c:if test="${not empty errorUpdate.errorFileType}">
                                <h5 class="text-danger offset-4 col-8 text-start">
                                    ${errorUpdate.errorFileType}
                                </h5>
                            </c:if>


                            <div >
                                <label for="editProfile" class="profile-edit-btn primary-btn">
                                    <i class="fas fa-edit"></i>
                                    <input type="submit" class="profile-edit--input" value="Edit" id="editProfile">
                                </label>
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
        <script src="./assets/js/inputfile.js"></script>
    </body>
</html>
