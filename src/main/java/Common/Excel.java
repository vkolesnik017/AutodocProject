package Common;

import io.qameta.allure.Step;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Excel {

    // read from Excel cell setUpFromExcel(file, 0);
    public Object[] setUpFromExcel(String file, int cellNumber) {
        return readFromExcel(file, cellNumber).toArray();
    }

    // read from Excel cell with choosing sheet name setUpFromExcel(file, "first sheet", 0);
    public Object[] setUpFromExcel(String file, String sheetName, int cellNumber) {
        return readFromExcel(file, sheetName, cellNumber).toArray();
    }

    // read from Excel all rows and cells setUpFromExcel(file);
    //Example: 2#37292#1453#ALFA ROMEO#Stevlio (949_)
    // Use with method parseExcel. String text = parseExcel(str From DataProvider)[1];
    // You get: 37292
    public Object[] setUpAllCellFromExcel(String file) {
        return readAllCellFromExcel(file).toArray();
    }

    public Object[] setUpAllCellFromExcel(String file, String sheetName) {
        return readAllCellFromExcel(file, sheetName).toArray();
    }

    public static String[] parseExcel(String str) {
        return str.split("#");
    }

    public List<String> readFromExcel(String file, int cellNumber) {
        List<String> finalList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new HSSFWorkbook(fileInputStream);
            Sheet sheet1 = wb.getSheetAt(0);
            for (Row row : sheet1) {
                String cellValue = formatter.formatCellValue(row.getCell(cellNumber));
                finalList.add(cellValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalList;
    }

    public List<String> readFromExcel(String file, String sheetName, int cellNumber) {
        List<String> finalList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new HSSFWorkbook(fileInputStream);
            Sheet sheet1 = wb.getSheet(sheetName);
            for (Row row : sheet1) {
                String cellValue = formatter.formatCellValue(row.getCell(cellNumber));
                finalList.add(cellValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalList;
    }

    private List<String> readAllCellFromExcel(String file) {
        DataFormatter formatter = new DataFormatter();
        List<String> listCell = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new HSSFWorkbook(fileInputStream);
            Sheet sheet = wb.getSheetAt(0);
            listCell = new ArrayList<>();
            int rowNum = sheet.getLastRowNum();
            for (int i = 0; i <= rowNum; i++) {
                int cellNum = sheet.getRow(i).getLastCellNum();
                StringBuilder txt = new StringBuilder();
                for (int e = 0; e <= cellNum; e++) {
                    String getText = formatter.formatCellValue(sheet.getRow(i).getCell(e));
                    txt.append(getText).append("#");
                }
                listCell.add(txt.substring(0, txt.lastIndexOf("##")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCell;
    }

    private List<String> readAllCellFromExcel(String file, String sheetName) {
        DataFormatter formatter = new DataFormatter();
        List<String> listCell = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new HSSFWorkbook(fileInputStream);
            Sheet sheet = wb.getSheet(sheetName.toUpperCase());
            listCell = new ArrayList<>();
            int rowNum = sheet.getLastRowNum();
            for (int i = 0; i <= rowNum; i++) {
                int cellNum = sheet.getRow(i).getLastCellNum();
                StringBuilder txt = new StringBuilder();
                for (int e = 0; e <= cellNum; e++) {
                    String getText = formatter.formatCellValue(sheet.getRow(i).getCell(e));
                    txt.append(getText).append("#");
                }
                listCell.add(txt.substring(0, txt.lastIndexOf("##")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCell;
    }

    public String getCellValueFromExel(String file, String sheetName, int cellNumber, int rowNumber) {
        String value = null;
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new HSSFWorkbook(fileInputStream);
            Sheet sheet1 = wb.getSheet(sheetName);
            value = formatter.formatCellValue(sheet1.getRow(rowNumber).getCell(cellNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    //Set in the excel file data in the specific cell and row.
    @Step("Set the value in single cell using numbers of column and row. Excel")
    public static void writeInExcelSingleCell(String sheetName, String fileName, String cellValue, int col, int row) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheet(sheetName);
            sheet.createRow(row).createCell(col).setCellValue(cellValue);
            FileOutputStream outFile = new FileOutputStream(new File(fileName));
            workbook.write(outFile);
            workbook.close();
            outFile.close();
        } else {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(sheetName);
            sheet.createRow(row).createCell(col).setCellValue(cellValue);
            FileOutputStream outFile = new FileOutputStream(new File(fileName));
            workbook.write(outFile);
            workbook.close();
            outFile.close();
            System.out.println("Created file: " + file.getAbsolutePath());
        }
    }

    //Set the data in excel in the current file or created a new file
    @Step("Write the value in excel. Method can write in current file or, if file does not exist - create new file. Excel")
    public static void writeInExcel(String fileName, String nameSheet, String textOne, String textTwo, String textThree, long textFour, String textFive) throws IOException {
        java.io.File file = new File(fileName);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            // Get first sheet from the workbook
            HSSFSheet sheet = (workbook.getSheet(nameSheet) == null)
                    ? workbook.createSheet(nameSheet) : workbook.getSheet(nameSheet);
            int currentRow = sheet.getPhysicalNumberOfRows() + 1;
            Row row = sheet.createRow(currentRow);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);
            Cell cell4 = row.createCell(3);
            Cell cell5 = row.createCell(4);
            cell1.setCellValue(textOne);
            cell2.setCellValue(textTwo);
            cell3.setCellValue(textThree);
            cell4.setCellValue(textFour);
            cell5.setCellValue(textFive);
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            workbook.close();
        } else {
            //create form scratch document
            HSSFWorkbook workbook = new HSSFWorkbook();
            //create page
            HSSFSheet sheet = workbook.createSheet(nameSheet);
            int rowNum = 0;
            Row row = sheet.createRow(rowNum);
            Cell cell = row.createCell(0, CellType.STRING);
            Cell cellUrl = row.createCell(1, CellType.STRING);
            Cell cellTime = row.createCell(2, CellType.STRING);
            Cell cellPercent = row.createCell(3, CellType.STRING);
            Cell cellDate = row.createCell(4, CellType.STRING);
            cell.setCellValue("Route");
            cellUrl.setCellValue("Url");
            cellTime.setCellValue("Page Loading Time");
            cellPercent.setCellValue("Percent, %");
            cellDate.setCellValue("Date");

            int currentRow = sheet.getPhysicalNumberOfRows() + 1;
            Row rowOne = sheet.createRow(currentRow);
            rowOne.createCell(0).setCellValue(textOne);
            rowOne.createCell(1).setCellValue(textTwo);
            rowOne.createCell(2).setCellValue(textThree);
            rowOne.createCell(3).setCellValue(textFour);
            rowOne.createCell(4).setCellValue(textFive);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            workbook.close();
            System.out.println("Created file: " + file.getAbsolutePath());
        }
    }

    @Step("get values from file with from couple sheets. Exel")
    public List<String> getValuesFromFile(String file, int quantityOfPages, int cellNumber, String sheetName) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < quantityOfPages; i++) {
            int k = i + 1;
            values.addAll(new Excel().readFromExcel(file, sheetName + String.valueOf(k), cellNumber));
        }
        return values;
    }
}
