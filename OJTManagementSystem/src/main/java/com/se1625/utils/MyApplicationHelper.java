/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblapplication.TblApplicationDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.usergoogle.UserGoogleDTO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class MyApplicationHelper {

    public static void getSiteMaps(ServletContext context) throws IOException {
        //1. get siteMaps file
        String siteMapsFile = context.getInitParameter("SITE_MAPS_FILE_PATH");
        //2. load properties from context and siteMapsFile to getResourceAsStream
        InputStream is = null;
        is = context.getResourceAsStream(siteMapsFile);
        Properties properties = new Properties();
        properties.load(is);

        //3. tạo attribute trong contextScope
        context.setAttribute("SITE_MAPS", properties);
    }

    public static void getSemesterDate(ServletContext context) throws IOException {
        //1. get siteMaps file
        String siteMapsFile = context.getInitParameter("SEMESTER_DATE_FILE_PATH");
        //2. load properties from context and siteMapsFile to getResourceAsStream
        InputStream is = null;
        is = context.getResourceAsStream(siteMapsFile);
        Properties properties = new Properties();
        properties.load(is);

        //3. tạo attribute trong contextScope
        context.setAttribute("SEMESTER_DATE", properties);
    }

    public static void getCheckingExpirationPost(ServletContext context) throws IOException {
        //1. get siteMaps file
        String siteMapsFile = context.getInitParameter("CHECKING_EXPIRATION_POST");
        //2. load properties from context and siteMapsFile to getResourceAsStream
        InputStream is = null;
        is = context.getResourceAsStream(siteMapsFile);
        Properties properties = new Properties();
        properties.load(is);

        //3. tạo attribute trong contextScope
        context.setAttribute("CHECKING_EXPIRATION_POST_TIME", properties);
    }

    public static String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static boolean sendEmail(TblAccountDTO toAccount, TblAccountDTO fromAccount, String message, String subject)
            throws AddressException, MessagingException {
        boolean test = false;

        String toEmail = toAccount.getEmail();
        final String fromEmail = fromAccount.getEmail().trim();
        final String password = fromAccount.getPassword().trim();
        Properties pr = new Properties();
        pr.setProperty("mail.smtp.host", "smtp.gmail.com");
        pr.setProperty("mail.smtp.port", "587");
        pr.setProperty("mail.smtp.auth", "true");
        pr.setProperty("mail.smtp.starttls.enable", "true");
        pr.put("mail.smtp.socketFactory.port", "587");
        pr.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //get session
        Session session = Session.getInstance(pr, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }

        });

        Message mess = new MimeMessage(session);
        mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mess.setFrom(new InternetAddress(fromEmail));

        mess.setSubject(subject);
        mess.setText(message);
        Transport.send(mess);
        test = true;

        return test;
    }

    public static String createIdCompany(String lastID) {
        if (lastID == null) {
            lastID = "FPT001";
            return lastID;
        } else {
            // tách chuỗi ID và số riêng
            String prefix = lastID.substring(0, 3);
            String subnumber = lastID.substring(lastID.indexOf("T") + 1);
            // convert chuỗi gồm 4 số về số 
            int number = Integer.parseInt(subnumber);
            // tăng 1 đơn vị rồi convert lại thành chuỗi  
            String nextNumber = "" + (number + 1);
            // nối với chuỗi prefix ban đầu
            int length = subnumber.length() - nextNumber.length();
            String newId = prefix;
            for (int i = 0; i < length; i++) {
                newId = newId + "0";
            }
            newId = newId + nextNumber;
            return newId;
        }
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(MyApplicationConstants.Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", MyApplicationConstants.Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", MyApplicationConstants.Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", MyApplicationConstants.Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", MyApplicationConstants.Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static UserGoogleDTO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = MyApplicationConstants.Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDTO googlePojo = new Gson().fromJson(response, UserGoogleDTO.class);

        return googlePojo;
    }

    public static String getStudentCode(String username) {
        int end = username.indexOf("@");
        int begin = end - 8;
        String StudentCode = username.substring(begin, end).toUpperCase();
        return StudentCode;
    }

    public static List<TblStudentDTO> readExcel(String excelFilePath)
            throws FileNotFoundException, IOException, IllegalArgumentException, Exception {
        List<TblStudentDTO> studentList = null;

        //get File
        InputStream inputStream = new FileInputStream(excelFilePath);

        //get wordbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
        if (workbook != null) {
            //get sheet
            Sheet sheet = workbook.getSheetAt(0);

            //get all rows
            Iterator<Row> interator = sheet.iterator();

            if (sheet.getLastRowNum() < 1) {
                workbook.close();
                inputStream.close();
                throw new Exception("Sheet is empty");
            } else {
                studentList = new ArrayList<>();
                while (interator.hasNext()) {
                    Row nextRow = interator.next();
                    if (nextRow.getRowNum() == 0) {
                        //ignore header
                        continue;
                    }
                    //get all cells
                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    //read cells and set value for tblStudentDTO object
                    TblStudentDTO student = new TblStudentDTO();
                    TblAccountDTO account = new TblAccountDTO();
                    TblStudentDAO studentDAO = new TblStudentDAO();
                    boolean foundError = false;
                    String patternNumberPhone = "^(03|05|07|08|09)([0-9]{8,8})$";
                    String patternNumber = "[0-9]{1,3}";
                    int NumberOfCells = 0;
                    while (cellIterator.hasNext()) {
                        //read cell
                        Cell cell = cellIterator.next();
                        //set value for tblaccountDTO object
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case 0:
                                //check chiều dài student code phải bằng 8
                                //check student không được tồn tại trước đó 
                                String studentCode = cell.toString();
                                if (studentCode.trim().length() != 8 || studentCode.trim().isEmpty()) {
                                    foundError = true;
                                } else {
                                    boolean existedStudent = studentDAO.checkExistedStudent(studentCode);

                                    if (existedStudent == false) {
                                        boolean existedStudentList = checkExistedStudentCode(studentCode, studentList);
                                        if (existedStudentList) {
                                            foundError = true;
                                        } else {
                                            student.setStudentCode(studentCode);
                                            NumberOfCells++;
                                        }
                                    } else {
                                        foundError = true;
                                    }
                                }
                                break;
                            case 1:
                                String fullName = cell.toString();
                                if (fullName.trim().isEmpty() || fullName.trim().length() > 50 || fullName.trim().length() < 6) {
                                    foundError = true;
                                } else {
                                    String patternName = "^[^\\p{L}\\s]*$";
                                    if (fullName.matches(patternName)) {
                                        foundError = true;
                                    } else {
                                        char[] nameArray = fullName.toCharArray();
                                        boolean isNumber = false;
                                        for (char c : nameArray) {
                                            if (Character.isDigit(c)) {
                                                isNumber = true;
                                                break;
                                            }
                                        }
                                        if (isNumber) {
                                            foundError = true;
                                        } else {
                                            String pattern = "[\\p{L}\\s]*";
                                            if (fullName.matches(pattern) == false) {
                                                foundError = true;
                                            } else {
                                                account.setName(fullName);
                                                NumberOfCells++;
                                            }
                                        }
                                    }
                                }
                                break;
                            case 2:
                                //check major này phải có trong system
                                String major = cell.toString();
                                TblMajorDAO majorDAO = new TblMajorDAO();
                                boolean existedMajor = majorDAO.checkExistedMajor(major);
                                if (existedMajor) {
                                    student.setMajor(major);
                                    NumberOfCells++;
                                } else {
                                    foundError = true;
                                }
                                break;
                            case 3:
                                //check chuỗi này không được rỗng
                                //check email này có tồn tại trong system chưa 
                                // check đúng định dạng email với đôi @fpt.edu.vn
                                String email = cell.toString();
                                TblAccountDAO accountDAO = new TblAccountDAO();
                                boolean existedAccount = accountDAO.checkExistedAccount(email);
                                if (email.trim().isEmpty()) {
                                    foundError = true;
                                } else {
                                    if (email.endsWith("@fpt.edu.vn") == false) {
                                        foundError = true;
                                    } else {
                                        if (existedAccount) {
                                            foundError = true;
                                        } else {
                                            boolean existedEmailStudentList = checkExistedEmail(email, studentList);
                                            if (existedEmailStudentList) {
                                                foundError = true;
                                            } else {
                                                account.setEmail(email);
                                                NumberOfCells++;
                                            }
                                        }
                                    }
                                }
                                break;
                            case 4:
                                //check length đủ 10 kí tự và phải là chuỗi số
                                String phone = cell.toString();
                                boolean existedNumberPhone = studentDAO.checkExistedNumberPhone(phone);
                                if (phone.trim().length() != 10 || phone.matches(patternNumberPhone) == false) {
                                    foundError = true;
                                } else {
                                    boolean existedNumberPhoneStudentList = checkExistedNumberPhone(phone, studentList);
                                    if (existedNumberPhone) {
                                        foundError = true;
                                    } else {
                                        if (existedNumberPhoneStudentList) {
                                            foundError = true;
                                        } else {
                                            student.setPhone(phone);
                                            NumberOfCells++;
                                        }
                                    }
                                }
                                break;
                            case 5:
                                //check chuỗi này phải là chuỗi số
                                String credit = String.valueOf((int) cell.getNumericCellValue());
                                if (credit.matches(patternNumber) == false || credit.trim().isEmpty()) {
                                    foundError = true;
                                } else {
                                    int numberCredit = Integer.parseInt(credit);
                                    if (numberCredit < 68 || numberCredit > 200) {
                                        foundError = true;
                                    } else {
                                        student.setNumberOfCredit(numberCredit);
                                        NumberOfCells++;
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                        //nếu mà lỗi không có thì add
                        // nếu có lỗi thoát khỏi vòng lặp và báo lỗi
                    }
                    if (foundError || NumberOfCells != 6) {
                        workbook.close();
                        inputStream.close();
                        throw new Exception("Error Import Excel File");
                    } else if (foundError == false && NumberOfCells == 6) {
                        student.setAccount(account);
                        studentList.add(student);
                    }
                }
            }
            workbook.close();
            inputStream.close();
        }
        return studentList;
    }

    private static boolean checkExistedStudentCode(String studentCode, List<TblStudentDTO> studentList) {
        if (studentList != null) {
            for (TblStudentDTO student : studentList) {
                if (student.getStudentCode().equals(studentCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkExistedEmail(String email, List<TblStudentDTO> studentList) {
        if (studentList != null) {
            for (TblStudentDTO student : studentList) {
                if (student.getAccount().getEmail().equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkExistedNumberPhone(String numberPhone, List<TblStudentDTO> studentList) {
        if (studentList != null) {
            for (TblStudentDTO student : studentList) {
                if (student.getPhone().equals(numberPhone)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath)
            throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            inputStream.close();
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }

    public static void writeHeaderLine(Sheet sheet, int rowIndex) {
        //create cellstyle 
        CellStyle cellStyle = createStyleForHeader(sheet);

        //create row 
        Row row = sheet.createRow(rowIndex);

        //create cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Stdudent Code");

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Major");

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Company Name");

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Job");

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Grade");

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Evaluation");

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Status");
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    public static void writeStudentEvaluation(TblApplicationDTO application, Row row) {
        short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
        // DataFormat df = workbook.createDataFormat();
        // short format = df.getFormat("#,##0");
        CellStyle cellStyleFormatNumber = null;
        //Create CellStyle
        Workbook workbook = row.getSheet().getWorkbook();
        cellStyleFormatNumber = workbook.createCellStyle();
        cellStyleFormatNumber.setDataFormat(format);

        Cell cell = row.createCell(0);
        cell.setCellValue(application.getStudent().getStudentCode());

        cell = row.createCell(1);
        cell.setCellValue(application.getStudent().getMajor());

        cell = row.createCell(2);
        cell.setCellValue(application.getCompanyPost().getCompany()
                .getAccount().getName());

        cell = row.createCell(3);
        cell.setCellValue(application.getCompanyPost().
                getMajor().getMajorName());

        cell = row.createCell(4);
        cell.setCellValue(application.getGrade());

        cell = row.createCell(5);
        cell.setCellValue(application.getEvaluation());

        if (application.getIsPass() == 1) {
            cell = row.createCell(6);
            cell.setCellValue("Passed");
        } else if (application.getIsPass() == -1) {
            cell = row.createCell(6);
            cell.setCellValue("Not Pass");
        }
    }

    public static void createExcelFile(Workbook workbook, String excelPath) throws FileNotFoundException, IOException {
        FileOutputStream outputStream = new FileOutputStream(excelPath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
