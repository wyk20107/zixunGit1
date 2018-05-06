package com.wyh.zixun.util;

public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";

    public static String getLikeKey(int entityId,String entityType){
        return BIZ_LIKE + SPLIT + entityType+SPLIT+String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId,String entityType){
        return BIZ_DISLIKE + SPLIT + entityType+SPLIT+String.valueOf(entityId);
    }

    public static String getEventQueueKey(){
        return BIZ_EVENT;
    }
}
