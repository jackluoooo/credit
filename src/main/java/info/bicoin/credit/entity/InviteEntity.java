package info.bicoin.credit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("user_invite_info")
@ApiModel(description = "用户邀请表")
public class InviteEntity {
    private Long id;
    @ApiModelProperty(value = "邀请人Id")
    private Long inviteUserId;
    @ApiModelProperty(value = "被邀请人Id")
    private Long userId;
    @ApiModelProperty(value = "邀请码")
    private Long inviteCode;
    @ApiModelProperty(value = "邀请码是否有效")
    private Long isValid;
}
