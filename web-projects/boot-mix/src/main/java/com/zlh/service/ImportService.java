package com.zlh.service;

import com.zlh.mapper.ImportMapper;
import com.zlh.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @package com.zlh.service
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/7/27
 */
@Service
public class ImportService {
    @Autowired
    ImportMapper importMapper;
    @Autowired
    ExcelUtils excelUtils;

    /**
     * 导入excel,使用apache.poi解析
     * @param file
     */
    public void importExcel(MultipartFile file) throws Exception {
        //校验文件信息
        String filePath = file.getOriginalFilename();
        String suffix = ".xls";

        if (filePath.endsWith(suffix)){
            List<Map<String,Object>> resMap = excelUtils.importXlsFile(file.getInputStream());
            return;
        }
        suffix = ".xlsx";
        if (filePath.endsWith(suffix)) {
            return;
        }
        throw new Exception("授权文件格式错误");
    }
}
