package ddp.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * 删除对应的value
   */
  public void remove(final String key) {
    if (exists(key)) {
      redisTemplate.delete(key);
    }
  }

  /**
   * 批量删除对应的value
   */
  public void remove(final String... keys) {
    for (String key : keys) {
      remove(key);
    }
  }

  /**
   * 批量删除key
   */
  public void removePattern(final String pattern) {
    Set<String> keys = redisTemplate.keys(pattern);
    if (!keys.isEmpty()) {
      redisTemplate.delete(keys);
    }
  }

  /**
   * 判断缓存中是否有对应的value
   */
  public boolean exists(final String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 读取缓存
   */
  public Object get(final String key) {
    Object result = null;
    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
    result = operations.get(key);
    return result;
  }

  /**
   * 写入缓存
   */
  public boolean set(final String key, Object value) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 写入缓存
   */
  public boolean set(final String key, Object value, Long expireTime) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取所有key数据
   */
  public List<String> getAllKeys() {
    List<String> list = new ArrayList<>();
    Set<String> keys = stringRedisTemplate.keys("*");
    Iterator<String> it1 = keys.iterator();
    while (it1.hasNext()) {
      list.add(it1.next());
    }
    return list;
  }

}
