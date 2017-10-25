package com.huihuitong.utils;

import com.ehking.sdk.entity.CustomsInfo;
import com.huihuitong.service.UpdateParkStatusService;
import com.huihuitong.service.impl.UpdateParkStatusServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yangz
 * @date 2017/10/18 10:29:58
 */
public class ExpireJobTask {
    private static final Logger logger = LoggerFactory.getLogger(ExpireJobTask.class);

    public static void doBiz() {
        // 执行业务逻辑
        UpdateParkStatusService service = new UpdateParkStatusServiceImpl();
        List<String> deliverIds = Utils.getMybatisDao().listDeliverId();
        for (String id : deliverIds) {
                logger.info("正在更新" + id);
                service.updateListStatus(id);
        }
    }
}
