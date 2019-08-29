package info.bicoin.credit.service.Impl;

import com.alibaba.fastjson.JSONObject;
import info.bicoin.credit.entity.CreditValueEntity;
import info.bicoin.credit.mapper.CreditValueMapper;
import info.bicoin.credit.service.CreditValueService;
import info.bicoin.credit.util.OkhttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CreditValueServiceImpl implements CreditValueService {

    @Value("${isAdminUrl}")
    private String isAdminUrl;

    @Resource
    CreditValueMapper creditValueMapper;

    @Override
    public void addValue(CreditValueEntity creditValueEntity) {
        Map selectMap = new HashMap<>();
        selectMap.put("uid", creditValueEntity.getUid());
        List<CreditValueEntity> list = creditValueMapper.selectByMap(selectMap);
        if (null == list || list.size() == 0) {
            creditValueMapper.insert(creditValueEntity);
        } else {
            CreditValueEntity updateEntity = list.get(0);
            updateEntity.setA(updateEntity.getA().add(creditValueEntity.getA()));
            updateEntity.setB(updateEntity.getB().add(creditValueEntity.getB()));
            updateEntity.setC1(updateEntity.getC1().add(creditValueEntity.getC1()));
            updateEntity.setC2(updateEntity.getC2().add(creditValueEntity.getC2()));
            updateEntity.setC3(updateEntity.getC3().add(creditValueEntity.getC3()));
            updateEntity.setC4(updateEntity.getC4().add(creditValueEntity.getC4()));
            updateEntity.setD1(updateEntity.getD1().add(creditValueEntity.getD1()));
            updateEntity.setD2(updateEntity.getD2().add(creditValueEntity.getD2()));
            updateEntity.setD3(updateEntity.getD3().add(creditValueEntity.getD3()));
            updateEntity.setD4(updateEntity.getD4().add(creditValueEntity.getD4()));
            updateEntity.setD5(updateEntity.getD5().add(creditValueEntity.getD5()));
            updateEntity.setE(updateEntity.getE().add(creditValueEntity.getE()));
            creditValueMapper.updateById(updateEntity);
        }
    }

    @Override
    public void addInviteValue(Long uid) {
        CreditValueEntity creditValueEntity = new CreditValueEntity();
        creditValueEntity.setUid(uid);
        creditValueEntity.setB(new BigDecimal(20));
        this.addValue(creditValueEntity);
    }

    @Override
    public void rewardValue(Long uid, String token, BigDecimal val) throws Exception{
        //掉接口判断是否为管理员
        try {
            JSONObject forObject = OkhttpUtil.doGet(isAdminUrl, "5213cc2c8654f73e29d677d2adee22be");
            if (forObject.getLong("code") == 0 && forObject.getJSONObject("data").getLong("isAdmin") == 1) {
                //添加e类积分
                CreditValueEntity creditValueEntity = new CreditValueEntity();
                creditValueEntity.setUid(uid);
                creditValueEntity.setE(val);
                this.addValue(creditValueEntity);
            } else {
                throw new Exception("adminerror");
            }
        } catch (Exception e) {
            log.error("掉接口判断是否为管理员失败", e);
            throw e;
        }
    }

    @Override
    public void consumerValue(Long uid, BigDecimal rate) throws Exception {
        Map selectMap = new HashMap<>();
        selectMap.put("uid", uid);
        List<CreditValueEntity> list = creditValueMapper.selectByMap(selectMap);
        if (null == list || list.size() == 0) {
            throw new Exception("adminerror");
        }
        CreditValueEntity updateEntity = list.get(0);
        if (updateEntity.getA().intValue()==0&&
            updateEntity.getB().intValue()==0&&
            updateEntity.getC1().intValue()==0&&
            updateEntity.getC2().intValue()==0&&
            updateEntity.getC3().intValue()==0&&
            updateEntity.getC4().intValue()==0&&
            updateEntity.getD1().intValue()==0&&
            updateEntity.getD2().intValue()==0&&
            updateEntity.getD3().intValue()==0&&
            updateEntity.getD4().intValue()==0&&
            updateEntity.getD5().intValue()==0&&
            updateEntity.getE().intValue()==0
        ) {
            throw new Exception("adminerror");
        }
        updateEntity.setA(updateEntity.getA().multiply(rate));
        updateEntity.setB(updateEntity.getB().multiply(rate));
        updateEntity.setC1(updateEntity.getC1().multiply(rate));
        updateEntity.setC2(updateEntity.getC2().multiply(rate));
        updateEntity.setC3(updateEntity.getC3().multiply(rate));
        updateEntity.setC4(updateEntity.getC4().multiply(rate));
        updateEntity.setD1(updateEntity.getD1().multiply(rate));
        updateEntity.setD2(updateEntity.getD2().multiply(rate));
        updateEntity.setD3(updateEntity.getD3().multiply(rate));
        updateEntity.setD4(updateEntity.getD4().multiply(rate));
        updateEntity.setD5(updateEntity.getD5().multiply(rate));
        creditValueMapper.updateById(updateEntity);
    }
}
