package com.dql.stu.utils;

import java.util.UUID;

/**
 * @author Dql
 * @date 2021/6/21   9:50
 * description:生成Cookie
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }
}
