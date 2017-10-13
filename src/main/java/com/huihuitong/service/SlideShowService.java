package com.huihuitong.service;

import com.huihuitong.meta.ListStatus;

import java.util.List;

public interface SlideShowService {

    int getPassNum(String deliverId);

    int getTemporaryNum(String deliverId);
}
