package info.bicoin.credit.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ObjectRedisServiceImpl implements ObjectRedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }


    /**
     * Reset the cache k v with the same expire.
     *
     * @param key key
     * @param obj value
     */
    public void reSet(String key, Object obj) {
        Long expire = redisTemplate.getExpire(key);
        redisTemplate.opsForValue().set(key, obj);
        if (expire != null && expire.intValue() > 0) {
            this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * Set kv with expire
     *
     * @param key    k
     * @param obj    v
     * @param expire expire
     */
    public void set(String key, Object obj, Long expire) {
        redisTemplate.opsForValue().set(key, obj);
        if (expire != null && expire.intValue() > 0) {
            this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public Object get(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return obj;
    }

    public void setHashModel(String key, String HK, Object HV) {
        redisTemplate.opsForHash().put(key, HK, HV);
    }

    public void setHashModel(String key, String HK) {
        setHashModel(key, HK, HK);
    }

    public void setHashMap(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void deleteHashModel(String key, String price) {
        redisTemplate.opsForHash().delete(key, price);
    }


    @Override
    public Object getHashModel(String key, String hk) {
        Object obj = redisTemplate.opsForHash().get(key, hk);
        return obj;
    }

    @Override
    public Object getHashModel(String key) {
        return null;
    }

    public Object getEntries(String key) {
        Object obj = redisTemplate.opsForHash().entries(key);
        return obj;
    }

    public Boolean hasKey(String key, String price) {
        return redisTemplate.opsForHash().hasKey(key, price);
    }

    @Override
    public long getSortSetNo(String key) {
        return 0;
    }


    @Override
    public List<Object> getKeysStringData(List<String> keys){
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public void setKeysStringData(Map<String,String> map){
        redisTemplate.opsForValue().multiSet(map);
    }

    public void setKeysStringData(Map<String,String> map,Long time){
        redisTemplate.opsForValue().multiSet(map);
        setKeysDelTime(new ArrayList<>(map.keySet()),time);
    }


    /**
     * @author YiYi
     * @Description 获取多个key的数据
     * @Date  2019/5/31 13:08
     * @Param [keys, getType]
     * @return java.util.List<java.lang.Object>
     **/
    public List<Object> getKeysZSetData(List<String> keys,String getType){
        return redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                keys.forEach(key->{
                    key = "\"" + key +"\"";
                    switch (getType){
                        case "zRange":
                            connection.zRange(key.getBytes(),0,-1);
                            break;
//						case "All":
//							connection.zRange(key.getBytes(),0,-1);
//							break;
                    }
                });
                return null;
            }
        });
    }

    /**
     * @author
     * 					Set<RedisZSetCommands.Tuple> set = new HashSet<>();
     * 					DefaultTuple defaultTuple = new DefaultTuple(key.getBytes(),1.0);
     * 					set.add(defaultTuple);
     * @Description 一个key,多个值
     * @Date  2019/5/31 11:50
     * @Param [map]
     * @return void
     **/
    public void setKeysZSetSomeData(Map<String, Set<RedisZSetCommands.Tuple>> map){
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                map.forEach((key,var)->{
                    connection.zAdd(key.getBytes(),var);
                });
                connection.closePipeline();
                return null;
            }
        });
    }


//    /**
//     * @author YiYi
//     * @Description 一个key,一个值
//     * @Date  2019/5/31 13:05
//     * @Param [map]
//     * @return void
//     **/
//    public void setKeysZSetOneData(Map<String,AnswerVoInZset> map){
//        redisTemplate.executePipelined(new RedisCallback<Object>() {
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.openPipeline();
//                map.forEach((key,var)->{
//                    connection.zAdd(("\""+key+"\"").getBytes(),var.getScore(),("\""+var.getValue().toString()+"\"").getBytes());
//                });
//                connection.closePipeline();
//                return null;
//            }
//        });
//    }

    /**
     * @author YiYi
     * @Description 一个key,一个值
     * @Date  2019/5/31 13:05
     * @Param [map]
     * @return void
     **/
    @Override
    public void removeKeysZSetOneData(Map<String,String> map){
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                map.forEach((key, var) -> {
                    connection.zRem(("\"" + key + "\"").getBytes(), ("\"" + var + "\"").getBytes());
                });
                connection.closePipeline();
                return null;
            }
        });
    }

    @Override
    public void removeBySore(String key, double score) {

    }

    @Override
    public Object getSetByScore(String key, double score) {
        return null;
    }


    /**
     * @author YiYi
     * @Description 批量设置数据的失效时间
     * @Date  2019/5/31 13:05
     * @Param [map]
     * @return void
     **/
    @Override
    public void setKeysDelTime(List<String> keys,Long time){
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                keys.forEach(key -> {
                    connection.expire(key.getBytes(), time);
                });
                connection.closePipeline();
                return null;
            }
        });
    }

    @Override
    public void setSortSet(String key, String value, Double score) {
        redisTemplate.opsForZSet().add(key,value,score);
    }

//    @Override
//    public void setSortSet(String key, Map<String,Double> values) {
//        Set<ZSetOperations.TypedTuple<Object>> var = new HashSet<>();
//        values.forEach((value,sort)->{
//            ZSetOperations.TypedTuple<Object> answerVoInZset = new AnswerVoInZset(value, sort);
//            var.add(answerVoInZset);
//        });
//        redisTemplate.opsForZSet().add(key,var);
//    }

    @Override
    public Set<Object> getAllSortSet(String sort, String key) {
        if(sort==null){
            sort = "asc";
        }
        if(sort.equals("asc")){
            //正序
            return redisTemplate.opsForZSet().range(key, 0, -1);
        }else{
            //倒叙
            return redisTemplate.opsForZSet().reverseRange(key,0,-1);
        }
    }

    @Override
    public Double getSortSetScor(String key,Object value) {
        return redisTemplate.opsForZSet().score(key,value);
    }



    @Override
    public Set<Object> getAllSortSetByPage(String sort, String key, Integer pageSize, Integer pageNum) {
        if(sort==null){
            sort = "asc";
        }
        //Long size = redisTemplate.opsForZSet().size(key);
        Long start= (pageNum-1)*pageSize.longValue();
        Long end= start+pageSize-1;
        if(sort.equals("asc")){
            //正序
            return redisTemplate.opsForZSet().range(key, start, end);
        }else{
            //倒叙
            return redisTemplate.opsForZSet().reverseRange(key,start,end);
        }
    }

    @Override
    public Long removeSortSetByValue(String key,Object...values) {
        return redisTemplate.opsForZSet().remove(key,values);
    }

    @Override
    public Long removeSortSetByScore(String key, Long min, Long max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min.doubleValue(), max.doubleValue());
    }

    @Override
    public Long removeSortSetByIndex(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().removeRange(key,start,end);
    }


    @Override
    public Long unionAndStore(String key, String otherKey, String destkey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destkey);
    }

    @Override
    public Long unionAndStore(String key, List<String> otherKey, String destkey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destkey);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key,timeout,unit);
    }

    @Override
    public void removeAllSet(String key, double min, double max) {

    }
}
