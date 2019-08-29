package info.bicoin.credit.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "消费积分请求参数")
public class ComsumerRequestVO {
    @ApiModelProperty("用户ID")
    private Long uid;
    @ApiModelProperty("消费比例")
    private BigDecimal rate;
}
