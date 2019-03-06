package io.study.util;

import java.util.UUID;

/**
 * Created by Administrator on 2019/2/19 0019.
 */
public class PKUtil {

    private static Snowflake snowflake = new Snowflake(0, 0);

    public static long nextId(){
        return snowflake.nextId();
    }

    public static String nextSid(){
        return String.valueOf(nextId());
    }

    public static String uuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
