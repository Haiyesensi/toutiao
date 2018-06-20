package com.amber.util;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import static java.util.stream.IntStream.range;

public class JedisSystaxLearn {

    public static void print(int index, Object obj) {
        System.out.println(String.format("%d ,%s", index, obj));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();
        jedis.set("hello", "world");
        jedis.setex("pv", 10, "yyy");
        jedis.set("increment", "10");
        jedis.incr("increment");
        jedis.incrBy("increment", 10);

        String lname = "listName";
        range(0, 10).forEach(i -> jedis.lpush(lname, String.valueOf(i)));
        print(1, jedis.lrange(lname, 0, 10));
        print(2, jedis.llen(lname));
        print(3, jedis.lpop(lname));
        print(4, jedis.llen(lname));
        print(5, jedis.lindex(lname, 3));

        print(6, jedis.linsert(lname, BinaryClient.LIST_POSITION.AFTER, "4", "a4"));
        print(7, jedis.linsert(lname, BinaryClient.LIST_POSITION.BEFORE, "1", "ar"));
        print(8, jedis.lrange(lname, 0, 100));


        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "19");
        jedis.hset(userKey, "phone", "1298982");
        print(9, jedis.hget(userKey, "name"));
        print(10, jedis.hgetAll(userKey));

        jedis.hdel(userKey, "phone");
        print(11, jedis.hgetAll(userKey));
        print(12, jedis.hkeys(userKey));
        print(13, jedis.hvals(userKey));
        print(14, jedis.hexists(userKey, "phone"));
        print(15, jedis.hexists(userKey, "age"));
        jedis.hsetnx(userKey, "school", "zasd");
        jedis.hsetnx(userKey, "name", "jpp");
        print(16, jedis.hgetAll(userKey));

        String likekey1 = "newlike1";
        String likekey2 = "newlike2";
        range(0, 10).forEach(i -> {
            jedis.sadd(likekey1, String.valueOf(i));
            jedis.sadd(likekey2, String.valueOf(i * 2));
        });
        print(1, jedis.smembers(likekey1));
        print(2, jedis.smembers(likekey2));
        print(3, jedis.sinter(likekey1, likekey2));
        print(4, jedis.sunion(likekey1, likekey2));
        print(5, jedis.sdiff(likekey1, likekey2));
        print(7, jedis.sismember(likekey1, "5"));
        jedis.srem(likekey1, "5");
        jedis.smove(likekey2, likekey1, "14");
        print(7, jedis.scard(likekey1));
        print(6, jedis.smembers(likekey1));


        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 60, "ajs");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 80, "Mei");
        jedis.zadd(rankKey, 75, "luc");
        print(1, jedis.zcard(rankKey));
        print(2, jedis.zcount(rankKey, 61, 100));
        print(3, jedis.zscore(rankKey, "Mei"));
        jedis.zincrby(rankKey, 2, "jim");
        print(4, jedis.zscore(rankKey, "jim"));
        print(5, jedis.zrange(rankKey, 0, 3));
        print(6, jedis.zrevrange(rankKey, 0, 3));

        for (Tuple tuple : jedis.zrevrangeByScoreWithScores(rankKey, "1000", "0")) {
            print(7, tuple.getElement() + " : " + String.valueOf(tuple.getScore()));
        }

        print(8, jedis.zrank(rankKey, "ajs"));
        print(9, jedis.zrevrank(rankKey, "ajs"));

        JedisPool jedisPool = new JedisPool();
        for (int i = 0; i < 100; i++) {
            Jedis j = jedisPool.getResource();
            j.get("a");
            System.out.println("POOL" + i);
        }

    }
}
