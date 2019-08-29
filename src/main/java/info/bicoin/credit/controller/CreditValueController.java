package info.bicoin.credit.controller;

import com.alibaba.fastjson.JSONObject;
import info.bicoin.credit.app.WebResult;
import info.bicoin.credit.controller.request.ComsumerRequestVO;
import info.bicoin.credit.controller.request.InviteRequestVO;
import info.bicoin.credit.controller.request.RewardRequestVO;
import info.bicoin.credit.entity.UserEntity;
import info.bicoin.credit.mapper.UserMapper;
import info.bicoin.credit.service.CreditValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "积分相关操作")
public class CreditValueController {

    @Resource
    UserMapper userMapper;

    @Resource
    CreditValueService creditValueService;

    @ApiOperation("币COIN邀请添加b类积分")
    @PostMapping("addInviteValue")
    public WebResult addInviteValue(@RequestBody InviteRequestVO inviteRequestVO) {
        try {
            Long inviteCode = inviteRequestVO.getInviteCode();
            if (StringUtils.isEmpty(inviteCode)) {
                return WebResult.failResult(4000, "邀请码不能为空");
            }

            Map selectMap = new HashMap();
            selectMap.put("invite_code", inviteCode);
            List<UserEntity> list = userMapper.selectByMap(selectMap);
            if (null == list || list.size() == 0) {
                return WebResult.failResult(4000, "邀请码不正确");
            }
            creditValueService.addInviteValue(list.get(0).getId());
            return WebResult.okResult();
        } catch (Exception e) {
            log.error("币COIN邀请添加b类积分失败：{}", e.getMessage());
        }
        return WebResult.failResult(4000);
    }

    @ApiOperation("论坛管理员打赏积分")
    @PostMapping("rewardValue")
    public WebResult rewardValue(HttpServletRequest servletRequest, @RequestBody RewardRequestVO rewardRequestVO) {
        try {
            String token = servletRequest.getHeader("token");
            BigDecimal value = rewardRequestVO.getValue();
            //根据token获取id
            Long uid = rewardRequestVO.getUid();
            creditValueService.rewardValue(uid, token, value);
            return WebResult.okResult();
        } catch (Exception e) {
            if (e.getMessage().equals("adminerror")){
                return WebResult.failResult(4000,"该用户不是管理员");
            }
            log.error("论坛管理员打赏积分：{}", e.getMessage());
        }
        return WebResult.failResult(4000);
    }

    @ApiOperation("消费积分")
    @PostMapping("consumerValue")
    public WebResult consumerValue( @RequestBody ComsumerRequestVO comsumerRequestVO) {
        try {
            creditValueService.consumerValue(comsumerRequestVO.getUid(),comsumerRequestVO.getRate());
            return WebResult.okResult();
        } catch (Exception e) {
            log.error("消费积分失败：{}", e.getMessage());
        }
        return WebResult.failResult(4000);
    }

}

