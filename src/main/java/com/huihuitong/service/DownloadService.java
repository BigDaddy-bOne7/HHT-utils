package com.huihuitong.service;

import java.time.LocalDate;

/**
 * Created by yangz on 2017/7/20 9:42.
 */
public interface DownloadService {
    public void downLoad(String userName, LocalDate startDate, LocalDate endDate);
}
