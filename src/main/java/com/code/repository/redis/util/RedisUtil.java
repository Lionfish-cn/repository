package com.code.repository.redis.util;

import com.code.repository.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.stringRedisTemplate = this.stringRedisTemplate;
    }

    /**
     * type ['nx','ex','bit']
     *
     * @param k
     * @param v
     * @param l
     * @param type
     */
    public static void stringSetOperations(String k, Object v, Long l, String type) {
        ValueOperations<String, String> valueOperations = redisUtil.stringRedisTemplate.opsForValue();
        if (l != null) {
            Long t = TTL(k);
            if(t==null)
                t = l;
            Duration duration = Duration.ofMillis(t);
            if ("nx".equals(type)) {
                valueOperations.setIfAbsent(k, v.toString(), duration);//如果不存在则set
            } else if ("ex".equals(type)) {
                valueOperations.setIfPresent(k, v.toString(), duration);//如果存在则set
            } else if ("bit".equals(type)) {
                valueOperations.setBit(k, l, Boolean.parseBoolean(v.toString()));
            } else {
                valueOperations.set(k, v.toString(), duration);//设置k超时时间
            }
        } else {
            if ("nx".equals(type)) {
                valueOperations.setIfAbsent(k, v.toString());
            } else if ("ex".equals(type)) {
                valueOperations.setIfPresent(k, v.toString());
            } else {
                valueOperations.set(k, v.toString());
            }
        }
    }

    /**
     * type ['getAndSet','bit','multi']
     *
     * @param k
     * @param v
     * @param type
     * @return
     */
    public static Object stringGetOperations(Object k, String v, String type) {
        ValueOperations<String, String> valueOperations = redisUtil.stringRedisTemplate.opsForValue();
        if ("getAndSet".equals(type)) {
            return valueOperations.getAndSet(k.toString(), v);
        } else if ("bit".equals(type)) {
            Long t = TTL(k.toString());
            if(t==null)
                t = Long.parseLong(v);
            Duration duration = Duration.ofMillis(t);
            return valueOperations.getBit(k.toString(), t);
        } else if ("multi".equals(type)) {
            return valueOperations.multiGet((Collection<String>) k);
        } else {
            return valueOperations.get(k);
        }
    }

    /**
     * h -> key,hk-> field or Map<Hk,Hv>,hv->value
     * type ['nx','all']
     *
     * @param h
     * @param hk
     * @param hv
     * @param type
     */
    public static void hashSetOperations(String h, Object hk, Object hv, String type) {
        HashOperations<String, Object, Object> hashOperations = redisUtil.stringRedisTemplate.opsForHash();
        if ("nx".equals(type)) {
            hashOperations.putIfAbsent(h, hk, hv);
        } else if ("all".equals(type)) {
            hashOperations.putAll(h, (Map<Object, Object>) hk);
        } else {
            hashOperations.put(h, hk, hv);
        }
    }

    public static Object hashGetOperations(String h, Object o) {
        HashOperations<String, Object, Object> hashOperations = redisUtil.stringRedisTemplate.opsForHash();
        if (o instanceof Collection) {
            return hashOperations.multiGet(h, (Collection) o);
        } else {
            return hashOperations.get(h, o);
        }
    }

    /**
     * type ['keys','values']
     *
     * @param h
     * @param type
     * @return
     */
    public static Object hashGetStream(String h, String type) {
        HashOperations<String, Object, Object> hashOperations = redisUtil.stringRedisTemplate.opsForHash();
        if ("keys".equals(type)) {
            return hashOperations.keys(h);
        } else if ("values".equals(type)) {
            return hashOperations.values(h);
        } else {
            return hashOperations.entries(h);
        }
    }

    /**
     * list set
     * l是偏移量，往list的哪个地方设值
     *
     * @param k
     * @param l
     * @param v
     */
    public static void listSetOperations(String k, long l, Object v, String direction) {
        ListOperations<String, String> listOperations = redisUtil.stringRedisTemplate.opsForList();
        if ("left".equals(direction)) {
            if (v instanceof Collection) {
                listOperations.leftPushAll(k, (Collection) v);
            } else {
                listOperations.leftPush(k, v.toString());
            }
        } else if ("right".equals(direction)) {
            if (v instanceof Collection) {
                listOperations.rightPushAll(k, (Collection) v);
            } else {
                listOperations.rightPush(k, v.toString());
            }
        } else {
            Long t = TTL(k);
            if(t==null)
                t = l;
            listOperations.set(k, t, v.toString());
        }
    }

    /**
     * list获取数据，若阻塞，默认20s后释放
     * direction ['right','left']
     *
     * @param k
     * @param v
     * @param direction
     * @return
     */
    public static Object listGetOperations(String k, String v, String direction) {
        ListOperations<String, String> listOperations = redisUtil.stringRedisTemplate.opsForList();
        if ("right".equals(direction)) {
            if (StringUtil.isNotNull(v)) {
                return listOperations.rightPopAndLeftPush(k, v, 20000l, TimeUnit.MILLISECONDS);
            } else {
                return listOperations.rightPop(k);
            }
        } else if ("left".equals(direction)) {
            return listOperations.leftPop(k);
        }
        return null;
    }

    public static Long TTL(String k) {
        return redisUtil.stringRedisTemplate.getExpire(k);
    }

    /**
     * 删除key，若是hash类型则删除指定key的field
     *
     * @param k
     * @param objs
     */
    public static void delete(String k, Object... objs) {
        if (objs.length > 0) {
            HashOperations<String, Object, Object> hashOperations = redisUtil.stringRedisTemplate.opsForHash();
            hashOperations.delete(k, objs);
        } else {
            redisUtil.stringRedisTemplate.delete(k);
        }
    }

    public static void expire(String key,Object l){
        if(l instanceof Long){
            redisUtil.stringRedisTemplate.expire(key,Long.parseLong(l.toString()),TimeUnit.MILLISECONDS);
        }else if(l instanceof Date){
            redisUtil.stringRedisTemplate.expireAt(key,(Date)l);
        }
    }


}
