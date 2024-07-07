package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

	public long getMaxPage(long dataSize, long pageSize) {
		long maxPages = dataSize / pageSize;
        if (dataSize % pageSize != 0 || maxPages == 0) {
            maxPages++; 
        }
        return maxPages;
    }
}
