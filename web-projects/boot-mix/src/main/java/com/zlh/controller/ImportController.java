package com.zlh.controller;

import com.zlh.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @package com.zlh.controller
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/7/27
 */
@RestController
@RequestMapping("/import")
public class ImportController {
    @Autowired
    ImportService importService;

    /**
     * 导入excel
     * @param file
     * @throws Exception
     */
    @PostMapping(value = "/importExcel")
    public void importExcel(MultipartFile file) throws Exception {
        importService.importExcel(file);
    }
}
