package com.huihuitong.service.impl;

import com.huihuitong.service.SlideShowService;
import com.huihuitong.utils.Utils;

public class SlideShowServiceImpl implements SlideShowService {


    @Override
    public int getPassNum(String deliverId) {
        return Utils.getMybatisDao().getPassNum(deliverId);
    }

    @Override
    public int getTemporaryNum(String deliverId) {
        return Utils.getMybatisDao().getTemporaryNum(deliverId);
    }
}
