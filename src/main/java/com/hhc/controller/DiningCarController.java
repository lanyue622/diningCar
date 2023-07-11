package com.hhc.controller;

import com.hhc.service.DiningCarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * 导入Excel表格入口
 */
@RestController
public class DiningCarController {

    @Resource
    private DiningCarService diningCarService;

    @PostMapping("diningCar")
    public void importExcel(MultipartFile file) throws IOException {
        diningCarService.importExcel(file);
        //diningCarService.importExcel(file);
    }

}
