package com.code.repository.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileReader {

    public static JSONArray readCsvFile(MultipartFile file) {
        JSONArray rtnArr = new JSONArray();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String firstLine = br.readLine();
            String[] lines = firstLine.split(",");
            List<String> list = new ArrayList<>();
            for (String line : lines) {
                list.add(line);
            }
            String line = null;
            while((line = br.readLine())!=null){
                lines = line.split(",");
                JSONObject jsonObject = new JSONObject();
                int i = 0;
                for (String s : list) {
                    if(list.contains(s))
                        continue;
                    jsonObject.put(list.get(i),s);
                    i++;
                }
                rtnArr.add(jsonObject);
            }
            return rtnArr;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }

    public static JSONArray readExcelFile(MultipartFile file) {
        Workbook workbook = null;
        Sheet sheet = null;
        InputStream is = null;
        JSONArray rtnArr = new JSONArray();
        try {
            is = file.getInputStream();
            String filename = file.getOriginalFilename();
            String type = filename.substring(filename.lastIndexOf(".") + 1);
            if (!(type.equals("xls") || type.equals("xlsx"))) {
                return null;
            }
            if (type.equals("xls")) {
                workbook = new XSSFWorkbook(is);
            }
            if (type.equals("xlsx")) {
                workbook = new HSSFWorkbook(is);
            }
            int nums = workbook.getNumberOfSheets();
            for (int i = 0; i < nums; i++) {
                sheet = workbook.getSheetAt(i);
                int rowNum = sheet.getLastRowNum();

                Row r = sheet.getRow(0);
                int cellnum = r.getLastCellNum();
                List<String> list = new ArrayList<>();
                for (int k = 0; k < cellnum; k++) {
                    Cell cell = r.getCell(k);
                    String cellv = getCellValue(cell).toString();
                    list.add(cellv);
                }
                for (int j = 1; j < rowNum; j++) {
                    JSONObject jsonObject = new JSONObject();
                    for (int k = 0; k < cellnum; k++) {
                        Cell cell = r.getCell(k);
                        String cellv = getCellValue(cell).toString();
                        jsonObject.put(list.get(k),cellv);
                    }
                    rtnArr.add(jsonObject);
                }
            }
            return rtnArr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray readTxtFile(MultipartFile file) {
        JSONArray rtnArr = new JSONArray();
        try{
            InputStreamReader isr = new InputStreamReader(file.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String firstLine = br.readLine();
            String[] lines = firstLine.split("\t",-1);
            List<String> list = new ArrayList<>();
            Collections.addAll(list, lines);

            String line = null;
            int i=0;
            while((line=br.readLine())!=null){
                if(i>0){
                    String[] columns = firstLine.split("\t",-1);
                    JSONObject jo = new JSONObject();
                    int k=0;
                    for (String s : columns) {
                        jo.put(list.get(k),s);
                        k++;
                    }
                    if(columns.length<lines.length){
                        for(int j=columns.length;j<lines.length;j++){
                            jo.put(list.get(j),"");
                        }
                    }
                    rtnArr.add(jo);
                }
                i++;
            }
            return rtnArr;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static Object getCellValue(Cell cell) throws Exception {
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
