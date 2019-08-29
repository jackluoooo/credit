package info.bicoin.credit.service;

import info.bicoin.credit.entity.CreditValueEntity;

import java.math.BigDecimal;

public interface CreditValueService {
    //加积分
    void addValue(CreditValueEntity creditValueEntity);

    //Bicoin邀请加积分
    void addInviteValue(Long uid);

    //打赏积分
    void rewardValue(Long uid, String token, BigDecimal val) throws Exception;

    //消费积分
    void consumerValue(Long uid,BigDecimal rate) throws Exception;
}
