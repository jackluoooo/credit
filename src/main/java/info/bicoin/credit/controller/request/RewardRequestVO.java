package info.bicoin.credit.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "打赏积分请求参数")
public class RewardRequestVO {
   @ApiModelProperty("打赏金额数量")
   private BigDecimal value;
   @ApiModelProperty("打赏帖子对应人员userId")
   private Long uid;
}
