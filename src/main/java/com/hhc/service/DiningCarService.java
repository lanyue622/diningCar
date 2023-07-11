package com.hhc.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hhc.domain.DiningCar;
import com.hhc.listener.DiningCarListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DiningCarService {
    @Resource
    private DiningCarListener diningCarListener;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 普通导入
     * @param file
     * @throws IOException
     */
    public void importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DiningCar.class, diningCarListener).excelType(ExcelTypeEnum.CSV).doReadAll();
    }


    /**
     * 多线程导入
     * @param file
     */
    public void importExcelAsync(MultipartFile file) {
        // 开多个线程分别处理sheet
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int num = i;
            tasks.add(() -> {
                EasyExcel.read(file.getInputStream(), DiningCar.class, diningCarListener).excelType(ExcelTypeEnum.CSV)
                        .sheet(num).doRead();
                return null;
            });
        }

        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
