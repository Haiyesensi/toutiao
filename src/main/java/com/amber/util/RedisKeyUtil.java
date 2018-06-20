package com.amber.util;

public class RedisKeyUtil {
    public static final String SPLIT = ":";
    public static final String BIZ_LIKE = "LIKE";
    public static final String BIZ_DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDislikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

}
