package info.bicoin.credit.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 检查传入的Token
 *
 * @author mr.wang
 */
@Slf4j
@Configuration
public class TokenUtil {

    @Resource
    private ObjectRedisServiceImpl objectRedisService;

//	@Resource
//	private KafkaSend kafkaSend;
//
//	@Value("${application.kafka.userToken.topic}")
//	private String userToken;

    /**
     * 检查传入的Token值Radio中是否存入，如果不为空是正确
     *
     * @param token token
     * @return return
     */
    public boolean checkToken(String token) {
        //从缓存中获取Token信息
        Object object = objectRedisService.get(token);
        //判断获取值是否为空 为空返回false
        return !Objects.equals(null, object);
    }

    /**
     * 根据Token获取Cached的中存放的对象
     *
     * @param token key
     * @return return
     */
    public Object getTokenObject(String token) {
        return objectRedisService.get(token);
    }

//	/**
//	 * 设置Token对象放入Cached中
//	 *
//	 * @param token  key
//	 * @param object value
//	 */
//	public void setTokenObject(String token, Object object) {
//		objectRedisService.set(token, object);
//		//将用户的token放入kafka
//		String tokenkey = AESUtil.encrypt(token+"(userInfo)"+object.toString()+"(lightning-rod)");
//		kafkaSend.send(userToken,tokenkey);
//	}

//	/**
//	 * 根据传入值删除指定对象
//	 *
//	 * @param key key
//	 */
//	public void delObjectBykey(String key) {
//		objectRedisService.remove(key);
//	}
//
//	/**
//	 * 根据手机号和密码生成Token
//	 *
//	 * @param nickName 用户的昵称
//	 * @param password 密码
//	 * @return return
//	 */
//	public String createUserToken(String nickName, String password) {
//		return MD5Utils.encryptToken(nickName, password);
//	}

    //getTokenByAuthor
//    public  String getTokenByAuthor(String username){
//        try {
//        RestTemplate restTemplate=new RestTemplate();
//        Map params=new HashMap();
//        params.put("nickName",username);
//        JSONObject forObject = restTemplate.getForObject(getTokenByAuthorUrl, JSONObject.class, params);
//        return forObject.getString("data");
//        }catch (Exception e){
//        log.error("getTokenByAuthor error",e);
//        }
//        return null;
//    }

}
