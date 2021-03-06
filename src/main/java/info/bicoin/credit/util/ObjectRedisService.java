package info.bicoin.credit.util;


import org.springframework.data.redis.connection.RedisZSetCommands;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface ObjectRedisService {

    void set(String key, Object obj);


    /**
     * Reset the cache k v with the same expire.
     *
     * @param key key
     * @param obj value
     */
    void reSet(String key, Object obj);

    /**
     * Set kv with expire
     *
     * @param key    k
     * @param obj    v
     * @param expire expire
     */
    public void set(String key, Object obj, Long expire);

    void remove(String key);

    Object get(String key);

    void setHashModel(String key, String HK, Object HV);

    void setHashModel(String key, String HK);

    void setHashMap(String key, Map<String, Object> map);

    void deleteHashModel(String key, String price);

    Object getHashModel(String key, String hk);

    Object getHashModel(String key);

    Object getEntries(String key);

    Boolean hasKey(String key, String price);


    //-----------set

    long getSortSetNo(String key);

    /**
     * @author YiYi
     * @Description 对有序的set操作
     * @Date  2019/5/9 17:20
     * @Param [key, value, score]
     * @return void
     **/
    void setSortSet(String key, String value, Double score);

    /**
     * @author YiYi
     * @Description 批量添加值
     * @Date  2019/5/30 14:31
     * @Param [key, values]
     * @return void
     **/
//    void setSortSet(String key, Map<String,Double> values);

    /**
     * @author YiYi
     * @Description 获取排序的set
     * @Date  2019/5/9 17:20
     * @Param [sort, key]
     * @return java.lang.Object
     **/
    Set<Object> getAllSortSet(String sort, String key);

    /**
     * @author YiYi
     * @Description 获取分页的排序的set
     * @Date  2019/5/9 17:20
     * @Param [sort, key, pageSize, pageNum]
     * @return java.lang.Object
     **/
    Set<Object> getAllSortSetByPage(String sort, String key, Integer pageSize, Integer pageNum);

    //获取某个值的某个排序
    Double getSortSetScor(String key, Object value);

    //删除某些值
    Long removeSortSetByValue(String key, Object... values);


    //删除一定排序范围的数据,返回删除个数
    Long removeSortSetByScore(String key, Long min, Long max);

    //删除一定排序范围的数据,返回删除个数
    Long removeSortSetByIndex(String key, Long start, Long end);

    //合并两个set,注意相同值的score会相加,返回合并后的个数
    Long unionAndStore(String key, String otherKey, String destkey);

    //合并多个set,注意相同值的score会相加,返回合并后的个数
    //destkey,合并后的key
    Long unionAndStore(String key, List<String> otherKey, String destkey);

    //-----------------通用
    //设置过期时间
    Boolean expire(String key, long timeout, TimeUnit unit);

    //----------------批量
    List<Object> getKeysStringData(List<String> keys);

    void setKeysStringData(Map<String, String> map);

    void setKeysStringData(Map<String, String> map, Long time);

    List<Object> getKeysZSetData(List<String> keys, String getType);

    void setKeysZSetSomeData(Map<String, Set<RedisZSetCommands.Tuple>> map);

//    void setKeysZSetOneData(Map<String,AnswerVoInZset> map);

    void setKeysDelTime(List<String> keys, Long time);

    void removeKeysZSetOneData(Map<String, String> map);

    void removeBySore(String key, double score);

    void removeAllSet(String key, double min, double max);

    //根据分数获取单条数据
    Object getSetByScore(String key, double score);

}


