package com.code.repository.email.info;

import com.code.repository.util.DateUtil;
import com.code.repository.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class MailInfo {

    private MimeMessage mimeMessage = null;
    private String attachmentSavePath = "";

    public MailInfo(MimeMessage message) {
        this.mimeMessage = message;
    }

    public String getFrom() {
        try {
            InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
            String from = address[0].getAddress();
            if (StringUtil.isNotNull(from)) {
                String personal = address[0].getPersonal();
                if (StringUtil.isNotNull(personal)) {
                    return personal + "[" + from + "]";
                }
                return from;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getSentTime() {
        try {
            Date d = mimeMessage.getSentDate();
            if (d != null) {
                return DateUtil.convertDateToString(d, "yyyy.MM.dd");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件并解析
     *
     * @param part
     */
    public void getAttchmentParseExcel(Part part) {
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
            String d = DateUtil.convertDateToString(c.getTime(), "yyyy.MM.dd");
            if (getSentTime().contains(d)) {
                String subject = mimeMessage.getSubject();
                if (getFrom().contains("2862539203@qq.com") || getFrom().contains("济南机场货运配载") || getFrom().contains("dispatch-jnia@163.com") || getFrom().contains("济南机场")) {
                    Multipart multipart = (Multipart) part.getContent();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        String disposition = bodyPart.getDisposition();
                        InputStream is = bodyPart.getInputStream();
                        if (StringUtil.isNotNull(disposition)) {
                            String filename = bodyPart.getFileName();
                            if (StringUtil.isNotNull(filename))
                                filename = MimeUtility.decodeText(MimeUtility.unfold(filename));
                            saveFile(filename, is, d);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFile(String filename, InputStream is, String d) {
        try {
            if ((filename.contains("毛重") || filename.contains("每日航班载量"))) {
//                String path = "D:\\temp\\sdairport";
//                File f = new File(path);
//                if (!f.exists())
//                    f.mkdirs();
//                FileOutputStream fos = new FileOutputStream(new File(path + "\\" + filename));
//                int c = 0;
//                while ((c = is.read()) != -1) {
//                    fos.write(c);
//                }
//                fos.close();
//                fos.flush();
//                FileInputStream fis = new FileInputStream(new File(path + "\\" + filename));

                Workbook workbook = null;
                if ((filename.contains("xlsx"))) {
                    workbook = new XSSFWorkbook(is);
                } else if (filename.contains("xls")) {
                    workbook = new HSSFWorkbook(is);
                }

                if (filename.contains("每日航班载量")) {
                    int cellCount = 27;
                    int month = Calendar.getInstance().get(Calendar.MONTH - 1);//如果当前是9月，则在excel中，sheet就是低八个
                    Sheet sheet = workbook.getSheetAt(month - 1);
                    for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                        Row r = sheet.getRow(rowNum);
                        Object cell0 = getCellValue(r.getCell(0));//判断
                        if (!cell0.toString().contains(d)) {
                            continue;
                        }
                    }
                } else if (filename.contains("毛重")) {
                    int cellCount = 50;
                    int month = Calendar.getInstance().get(Calendar.MONTH - 1);//如果当前是9月，则在excel中，sheet就是低八个
                    Sheet sheet = workbook.getSheetAt(month - 1);
                    for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                        Row r = sheet.getRow(rowNum);
                        if(rowNum>=1){//第二行起
                            if(rowNum==1){//获取第二行

                            }else{
                                for(int i = 0;i < cellCount;i++){
                                    Object cell  = getCellValue(r.getCell(i));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getCellValue(Cell cell) throws Exception {
        Object value = null;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                value = cell.getDateCellValue();
                value = DateUtil.convertDateToString((Date) value, "yyyy-MM-dd HH:mm");
            } else {
                value = cell.getNumericCellValue();
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            value = cell.getStringCellValue();
        }
        return value;
    }

}
