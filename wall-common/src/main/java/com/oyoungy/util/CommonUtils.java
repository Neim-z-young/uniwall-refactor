package com.oyoungy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Supplier;


/**
 * 公共工具类
 *
 * @author tjq
 * @since 2020/3/18
 */
public class CommonUtils {

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 将大小格式化为 2的N次
     * @param cap 初始大小
     * @return 格式化后的大小，2的N次
     */
    public static int formatSize(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * 生成 UUID
     * @return uuid
     */
    public static String genUUID() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

    public static void easySleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
        }
    }
}
