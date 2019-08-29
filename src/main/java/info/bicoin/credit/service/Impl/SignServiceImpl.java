package info.bicoin.credit.service.Impl;

import info.bicoin.credit.entity.CreditValueEntity;
import info.bicoin.credit.entity.SignEntity;
import info.bicoin.credit.mapper.InviteMapper;
import info.bicoin.credit.mapper.SignEntityMapper;
import info.bicoin.credit.service.SignService;
import info.bicoin.credit.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SignServiceImpl implements SignService {

    @Resource
    SignEntityMapper signEntityMapper;

    @Resource
    InviteMapper inviteMapper;

    @Resource
    CreditValueServiceImpl creditValueService;

    @Override
    @Transactional
    public void sign(Long uid) throws Exception {
        try {
            //查询邀请人Id
            Long inviteId = inviteMapper.getInviteIdByUid(uid);

            //查询签到记录
            Map selectMap = new HashMap<>();
            selectMap.put("uid", uid);
            List<SignEntity> list = signEntityMapper.selectByMap(selectMap);

            //打赏
            CreditValueEntity creditValueEntity = new CreditValueEntity();
            creditValueEntity.setUid(uid);
            creditValueEntity.setA(new BigDecimal(0.1));
            creditValueEntity.setB(new BigDecimal(0));
            creditValueEntity.setC1(new BigDecimal(0));
            creditValueEntity.setC2(new BigDecimal(0));
            creditValueEntity.setC3(new BigDecimal(0));
            creditValueEntity.setC4(new BigDecimal(0));
            creditValueEntity.setD1(new BigDecimal(0));
            creditValueEntity.setD2(new BigDecimal(0));
            creditValueEntity.setD3(new BigDecimal(0));
            creditValueEntity.setD4(new BigDecimal(0));
            creditValueEntity.setD5(new BigDecimal(0));
            creditValueEntity.setE(new BigDecimal(0));
            CreditValueEntity inviteEntity = new CreditValueEntity();
            BeanUtils.copyProperties(creditValueEntity,inviteEntity);
            inviteEntity.setUid(inviteId);

            if (null == list || list.size() == 0) {
                SignEntity signEntity = new SignEntity();
                signEntity.setUid(uid);
                signEntity.setUTime(new Date());
                signEntity.setContinueTimes(1L);
                signEntityMapper.insert(signEntity);
                creditValueService.addValue(creditValueEntity);
                if (inviteId != null) {
                    creditValueService.addValue(inviteEntity);
                }
                return;
            }

            Date date = new Date();
            if (DateUtil.differenceZeroDay(date, list.get(0).getUTime())) {
                //今天已经签到过了
                throw new Exception("4000");
            }

            if (DateUtil.differenceOneDay(date, list.get(0).getUTime())) {
                SignEntity updateEntity = list.get(0);
                updateEntity.setContinueTimes(updateEntity.getContinueTimes() + 1);
                updateEntity.setUTime(date);
                signEntityMapper.updateById(updateEntity);
                //根据不同值打赏积分不同
                creditValueEntity.setA(updateEntity.getContinueTimes() >= 3 ? new BigDecimal(0.3) : new BigDecimal(0.2));
                creditValueService.addValue(creditValueEntity);
                if (inviteId != null) {
                    creditValueService.addValue(inviteEntity);
                }

            } else {
                SignEntity updateEntity = list.get(0);
                updateEntity.setContinueTimes(updateEntity.getContinueTimes() + 1);
                updateEntity.setContinueTimes(1L);
                updateEntity.setUTime(date);
                signEntityMapper.updateById(updateEntity);
                //打赏
                creditValueService.addValue(creditValueEntity);
                if (inviteId != null) {
                    creditValueService.addValue(inviteEntity);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }


}
