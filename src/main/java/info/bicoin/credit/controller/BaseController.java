package info.bicoin.credit.controller;

import com.alibaba.fastjson.JSONObject;
import info.bicoin.credit.entity.User;
import info.bicoin.credit.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * file:BaseController
 * <p>
 * 文件简要说明  基础controller 主要为用户的登陆设置
 *
 * @author 2018-06-14 hgl 创建初始版本
 * @version V1.0  简要版本说明
 * @par 版权信息：
 * 2018 Copyright 河南艾鹿网络科技有限公司 All Rights Reserved.
 */
@Slf4j
public class BaseController {

    @Resource
    private TokenUtil tokenUtil;

//    @Resource
//    UserService UserService;


    /**
     * 用户放入redis
     *
     * @param user
     * @return java.lang.String
     * @date 2018-06-14
     * @author hgl
     * @see
     * @since
     */
//    protected String setUserToRedis(User user) {
//        String token = tokenUtil.createUserToken(user.getNickName(), user.getPassword());
//        tokenUtil.setTokenObject(token, JSONObject.toJSONString(user));
//        return token;
//    }

    /**
     * 从redis中获取用户 调用接口时 需要在 header 中 传 token值
     *
     * @param request
     * @return com.ailu.lightningrod.dao.domain.User
     * @date 2018-06-14
     * @author hgl
     * @see
     * @since
     */
    protected User getUserFromRedis(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            return null;
        }
        tokenUtil.getTokenObject(token);
        Object o = tokenUtil.getTokenObject(token);
        if (o == null) {
            return null;
        }
        return JSONObject.parseObject(o.toString(), User.class);
    }

    /**
     * 通过token传参获取用户信息
     *
     * @param token
     * @return
     */
    protected User getUserByToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        tokenUtil.getTokenObject(token);
        Object o = tokenUtil.getTokenObject(token);
        if (o == null) {
            return null;
        }
        return JSONObject.parseObject(o.toString(), User.class);
    }

    /**
     * 从redis中获取用户 调用接口时 需要在 header 中 传 token值
     *
     * @param request
     * @return com.ailu.lightningrod.dao.domain.User
     * @date 2018-06-14
     * @author hgl
     * @see
     * @since
     */
    public Long getUserIdFromRedis(HttpServletRequest request) {
        String token = request == null ? null : request.getHeader("token");
//        log.info("token@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",token);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        Object o = tokenUtil.getTokenObject(token);
        if (o == null) {
            return null;
        }
        User user = JSONObject.parseObject(o.toString(), User.class);
//        log.info("user@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",user);
        return user.getId();
    }

    /**
     * 从redis中获取用户 调用接口时 需要在 header 中 传 token值,如果没有登录，就使用临时用户的id
     *
     * @param request
     * @return com.ailu.lightningrod.dao.domain.User
     * @date 2018-06-14
     * @author hgl
     * @see
     * @since
     */
//    protected Long getUserIdFromRedisTemp(HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getHeader("token");
//        if (!StringUtils.hasText(token)) {
//            //未登录状态，获取前端传过来的临时用户的id
//            String userTempId = request.getHeader("userTempId");
//            if (StringUtils.hasText(userTempId)) {
//                //获取成功后，直接将临时用户id传入系统使用
//                Long userId = Long.parseLong(userTempId);
//                return userId;
//            } else {
//                //token和uid都没有，就根据设备id创建
//                String mobilId = request.getHeader("mobilId");
//                //判断设备id的可用性--盐值解密
//                String mobilKey = request.getHeader("mobilKey");
//                String myMobilKey = EncryptUtils.encryptMD5ToString(mobilId, mobilId);
//                if (myMobilKey.equals(mobilKey)) {
//                    //验证设备id的重复性,除了dervice_id
//                    if (!"dervice_id".equals(mobilId)) {
//                        User user = UserService.checkUserMobileId(mobilId);
//                        if (user != null) {
//                            //设备id存在的话，将临时id传入系统
//                            response.setHeader("userTempId", user.getId().toString());
//                            return user.getId();
//                        }
//                    }
//                    //如果没有就使用设备id,创建临时用户
//                    User user = new User();
//                    user.setMobilId(mobilId);
//                    user.setUserTempFlag(true);
//                    user.setPassword("initpwd");
//                    boolean b = UserService.saveUser(user);
//                    if (!b) {
//                        return null;
//                    }
//                    Long userId = UserService.findUserByMobilId(mobilId).getId();
//                    response.setHeader("userTempId", userId.toString());
//                    return userId;
//                }
//                return null;
//            }
//        }
//        Object o = tokenUtil.getTokenObject(token);
//        if (o == null) {
//            return -1L;
//        }
//        User user = JSONObject.parseObject(o.toString(), User.class);
//        return user.getId();
//    }


//    /**
//     * 从redis中移除用户
//     *
//     * @param request
//     * @return void
//     * @date 2018-06-14
//     * @author hgl
//     * @see
//     * @since
//     */
//    protected void removeUserFromRedis(HttpServletRequest request) {
//        String token = request.getHeader("token");
//        tokenUtil.delObjectBykey(token);
//    }

}
