package info.bicoin.credit.controller;

import com.alibaba.fastjson.JSONObject;
import info.bicoin.credit.app.WebResult;
import info.bicoin.credit.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@Slf4j
@Api(tags = "签到操作")
public class SignController extends BaseController {
    @Resource
    SignService signService;

    @ApiOperation("签到")
    @PostMapping("sign")
    public WebResult sign(HttpServletRequest request) {
        try {
            Long uid = getUserIdFromRedis(request);
            if (StringUtils.isEmpty(uid)) {
                return WebResult.failResult(4000, "uid不能为空");
            }
            signService.sign(uid);
            return WebResult.okResult();
        } catch (Exception e) {
            if (e.getMessage().equals("4000")) {
                return WebResult.failResult(4000, "你今天已经签到过了");
            }
            log.error("签到失败了：{}", e);
        }
        return WebResult.failResult(4000);
    }
}
