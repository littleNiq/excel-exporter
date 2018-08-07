package com.example.demo.service;

import com.company.project.args.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Naq on 2018/8/6.
 */
public class ExcelOne {
    public static void main(String[] args) throws IOException {

        File file = new File("C:/soft/word/package/One.xls");
        List<Hotel> hotelList = new ArrayList<>();

        // 读取表格并生成新数据
        hotelList = getData(file, 0);

        // 导出新生成的表格
        ExportExcelUtil<Hotel> util = new ExportExcelUtil<Hotel>();
        String[] columnNames = {"省", "市", "词包列一", "词包列二", "合并后的词包列"};
        util.exportExcel("Hotel", columnNames, hotelList, new FileOutputStream("C:/soft/hotel.xls"), ExportExcelUtil.EXCEl_FILE_2007);
    }

    public static List<Hotel> getData(File file, int ignoreRows)
            throws FileNotFoundException, IOException {

        List<Hotel> hotels = new ArrayList<>();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                file));

        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFCell cell = null;

        HSSFSheet st = wb.getSheetAt(0);
        HSSFSheet sts = wb.getSheetAt(1);

        int rowsOfSheet = st.getPhysicalNumberOfRows(); // 获取当前Sheet的总行数
        int rowsOfSheets = sts.getPhysicalNumberOfRows(); // 获取当前Sheet的总行数

        Map<Integer, List<String>> map = new HashMap<>();
        Map<Integer, List<Word>> maps = new HashMap<>();

        List<String> list = new ArrayList<>();

        for (int r = 0, j = 0; r < rowsOfSheet; r++) {// 第二词包 map
            Row rownext = null;
            if (r < rowsOfSheet - 1) {
                rownext = st.getRow(r + 1);
            }
            Row rownow = st.getRow(r);
            if (r < rowsOfSheet - 1 && rownext.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && rownow.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK) {
                list.add(rownow.getCell(3).getStringCellValue());
            } else if (r < rowsOfSheet - 1 && rownext.getCell(0).getCellType() == Cell.CELL_TYPE_STRING || r == rowsOfSheet - 1) {
                if (rownow.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK)
                    list.add(rownow.getCell(3).getStringCellValue());
                map.put(j, list);
                list = new ArrayList<>();
                j++;
            }
        }

        String[] privince = new String[10000];
        String[] city = new String[10000];
        List<Word> wordList = new ArrayList<>();

        for (int r = 0, j = 0; r < rowsOfSheets; r++) {// 前三项 map
            Row row = sts.getRow(r);
            String wordone = null;
            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                privince[r] = row.getCell(0).getStringCellValue(); // 省
                city[r] = row.getCell(1).getStringCellValue(); // 市
            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                privince[r] = privince[r - 1];
                city[r] = city[r - 1];
            }

            if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                wordone = row.getCell(2).getStringCellValue(); // 第一词包
            }

            wordList.add(new Word(privince[r], city[r], wordone));
            if (r == rowsOfSheets - 1 || r < rowsOfSheets - 1 && sts.getRow(r + 1).getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
                maps.put(j, wordList);
                wordList = new ArrayList<>();
                j++;
            }
        }

        for (Integer i : map.keySet()) { // map
            for (int m = 0; m < maps.get(i).size(); m++) {// 省,市,第一词包
                {
                    Word word = maps.get(i).get(m);
                    for (int j = 0; j < map.get(i).size(); j++) {// 第二词包
                        String wordtwo = map.get(i).get(j);
                        String finalword = word.getWordone() + "+" + wordtwo;
                        hotels.add(new Hotel(word.getProvince(), word.getCity(), word.getWordone(), wordtwo, finalword));
                    }
                }
            }
        }

        return hotels;
    }
}
