package info.bicoin.credit.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "邀请加积分参数")
public class InviteRequestVO {
  @ApiModelProperty("邀请码")
  private  Long inviteCode;
}
