package com.zz.multithread.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BlockingQueue 方法
 *        抛出异常     特殊值      阻塞         超时
 * 插入   add(e)      offer(e)    put(e)      offer(e, time, unit)
 * 移除   remove()    poll()      take()      poll(time, unit)
 * 检查   element()   peek()      不可用       不可用
 */
public class Utils {

    private ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();

    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(Integer.MAX_VALUE);
}
