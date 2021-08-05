package com.zlh.util;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * excel读写工具类
 * @package com.zlh.util
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/7/27
 */
@Component
public class ExcelUtils {
    /**
     * 读取excel
     * @param inputStream
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> importXlsFile(InputStream inputStream) throws IOException {
        //通过构造函数传参
        HSSFWorkbook workbook = new HSSFWorkbook(new BOMInputStream(inputStream));
        //获取工作表
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取行,行号作为参数传递给getRow方法,第一行从0开始计算
        HSSFRow row = sheet.getRow(0);
        //获取单元格,row已经确定了行号,列号作为参数传递给getCell,第一列从0开始计算
        HSSFCell cell = row.getCell(2);
        //获取单元格的值,即C1的值(第一行,第三列)
        String cellValue = cell.getStringCellValue();
        System.out.println("第一行第三列的值是"+cellValue);
        workbook.close();
        return null;
    }
}
