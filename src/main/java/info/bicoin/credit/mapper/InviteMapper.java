package info.bicoin.credit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import info.bicoin.credit.entity.InviteEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface InviteMapper extends BaseMapper<InviteEntity> {
    @Select("select invite_user_id from user_invite_info  where user_id=#{uid} and is_valid=1")
    Long getInviteIdByUid(@Param("uid") Long uid);
}
