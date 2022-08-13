/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.customtag;

import com.se1625.tblapplication.TblApplicationDAO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
import com.se1625.tblfollowing_post.TblFollowing_PostDTO;
import com.se1625.tblstudent.TblStudentDTO;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class CustomTag {

    public static Boolean getStatusSaveJob(List<TblFollowing_PostDTO> listFollowingPost, Integer postID) {
        if (listFollowingPost != null) {
            for (TblFollowing_PostDTO tblFollowing_PostDTO : listFollowingPost) {
                if (tblFollowing_PostDTO.getPostID() == postID) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean getStatusAcceptCompanyPost(List<TblCompany_PostDTO> listCompanyPost, Integer postID) {
        if (listCompanyPost != null) {
            for (TblCompany_PostDTO tblCompany_PostDTO : listCompanyPost) {
                if (tblCompany_PostDTO.getPostID() == postID) {
                    if (tblCompany_PostDTO.getStatusPost() == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String changeDateFormat(java.sql.Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = dateFormat.format(date);
            return dateString;
        }
        return null;
    }

    public static String getError(String Error, String studentCode) {
        if (Error != null || "".equals(Error) == false) {
            StringTokenizer stk = new StringTokenizer(Error, "_");
            if (stk.hasMoreTokens()) {
                String message = stk.nextToken();
                String studentRoll = stk.nextToken();
                if (studentRoll.equals(studentCode)) {
                    return message;
                }
            }
        }
        return null;
    }

    public static TblApplicationDTO getApplicationOfStudentByID(TblStudentDTO student, ServletContext context) {
        TblApplicationDAO applicationDAO = new TblApplicationDAO();
        TblApplicationDTO application = null;
        try {
            application = applicationDAO.getApplication(student.getStudentCode(), student.getSemester().getSemesterID());
        } catch (SQLException ex) {
            context.log("SQLException at CustomTag " + ex.getMessage());
        } catch (NamingException ex) {
            context.log("NamingException at CustomTag " + ex.getMessage());
        }
        return application;
    }

    public static Integer getIndexList(List<TblCompanyDTO> listAvatar) {
        int numberRowsPerPage = 10;
        int sizeOfList = listAvatar.size();
        int numberPage = sizeOfList % numberRowsPerPage;

        if (numberPage == 0) {
            numberPage = sizeOfList / numberRowsPerPage;
        } else {
            numberPage = (sizeOfList / numberRowsPerPage) + 1;
        }
        return numberPage;
    }

    public static List<TblCompanyDTO> getList(List<TblCompanyDTO> listCompany, Integer index) {
        int numberRowsPerPage = 10;
        int start;
        int end;
        int sizeOfList = listCompany.size();
        start = (index - 1) * numberRowsPerPage;
        end = Math.min(index * numberRowsPerPage, sizeOfList);
        List<TblCompanyDTO> listAvatarOnSlide = new ArrayList<>();
        for (int i = start; i < end; i++) {
            listAvatarOnSlide.add(listCompany.get(i));
        }
        return listAvatarOnSlide;
    }

    static int remain;

    public static HashMap<String, Integer> paging(Integer CurrentPage, Integer numberPerPage, Integer numberPages) {
        final int pageCount = 5;
        int startNum = 0;
        int lastNum = 0;
        int lastPageNum = 0;

        startNum = (int) Math.floor((CurrentPage - 1) / pageCount) * pageCount + 1;
        lastNum = startNum + (pageCount - 1);
        //6
        //1-5 | 2
        //6 
        if (numberPerPage % pageCount == 0) {
            lastPageNum = (int) Math.floor((numberPerPage / pageCount));
        } else {
            lastPageNum = (int) Math.floor((numberPerPage / pageCount)) + 1;

        }
        HashMap<String, Integer> pagingList = new HashMap<>();
        pagingList.put("startNum", startNum);
        pagingList.put("lastNum", lastNum);
        pagingList.put("lastPageNum", lastPageNum);

        return pagingList;
    }

    public static Integer counter(Integer pageNumber, Integer index) {
        return ((pageNumber - 1) * 10) + index;
    }

    public static String getAvatarName(String avartarName) {
        if (avartarName != null) {
            StringTokenizer stk = new StringTokenizer(avartarName, "_");
            if (stk.countTokens() == 2) {
                String prefix = stk.nextToken();
                String name = stk.nextToken();
                return name;
            } else {
                return avartarName;
            }
        }

        return null;
    }
}
