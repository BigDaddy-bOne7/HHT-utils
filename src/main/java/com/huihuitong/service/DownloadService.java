package com.huihuitong.service;

import java.time.LocalDate;

public interface DownloadService {
    void downLoad(String userName, LocalDate startDate, LocalDate endDate);
}
