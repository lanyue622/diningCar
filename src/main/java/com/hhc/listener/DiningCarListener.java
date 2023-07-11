package com.hhc.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.hhc.domain.DiningCar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class DiningCarListener implements ReadListener<DiningCar>{

    private static final Log logger = LogFactory.getLog(DiningCarListener.class);

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private ThreadLocal<ArrayList<DiningCar>> DiningCarList = ThreadLocal.withInitial(ArrayList::new);

    private static AtomicInteger count = new AtomicInteger(1);

    @Resource
    private DiningCarListener diningCarListener;

    /**
     * 执行插入数据操作
     * @param data
     * @param context
     */
    @Override
    public void invoke(DiningCar data, AnalysisContext context) {
        DiningCarList.get().add(data);
        asyncSaveData();
    }

    public void asyncSaveData() {
        if (!DiningCarList.get().isEmpty()) {
            ArrayList<DiningCar> diningCar = (ArrayList<DiningCar>) DiningCarList.get().clone();
            executorService.execute(new SaveTask(diningCar, diningCarListener));
            DiningCarList.get().clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) { }

    static class SaveTask implements Runnable {

        private List<DiningCar> diningCarList;

        private DiningCarListener diningCarListener;

        public SaveTask(List<DiningCar> diningCarList, DiningCarListener diningCarListener) {
            this.diningCarList = diningCarList;
            this.diningCarListener = diningCarListener;
        }

        //过滤数据并输出
        @Override
        public void run() {
            //diningCarListener.saveBatch(diningCarList);
            List<DiningCar> push_cart = diningCarList.stream().filter(e -> e.getFacilityType().equals("Push Cart")).collect(Collectors.toList());
            logger.info(push_cart);
            //logger.info("第" + count.getAndAdd(1) + "次插入" + diningCarList.size() + "条数据");
        }
    }
}
