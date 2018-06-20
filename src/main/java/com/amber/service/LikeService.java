package com.amber.service;

import com.amber.util.JedisAdapter;
import com.amber.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 喜欢返回1, 不喜欢返回-1, 错误返回0
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDislikeKey(entityId, entityType);
        if (jedisAdapter.sismember(disLikeKey, String.valueOf(userId))) {
            return -1;
        }
        return 0;
    }

    public long like(int userId, int eType, int eId) {

        String likeKey = RedisKeyUtil.getLikeKey(eId, eType);
        String disLikeKey = RedisKeyUtil.getDislikeKey(eId, eType);

        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int eType, int eId) {

        String likeKey = RedisKeyUtil.getLikeKey(eId, eType);
        String disLikeKey = RedisKeyUtil.getDislikeKey(eId, eType);

        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}
