package info.bicoin.credit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description="积分表" )
@TableName("tb_credit_value")
public class CreditValueEntity {
    private Long id;
    @ApiModelProperty(value = "BiCoin ID")
    private Long uid;
    @ApiModelProperty(value = "签到：a类积分 ")
    private BigDecimal a;
    @ApiModelProperty(value = "邀请好友：b类积分")
    private BigDecimal b;
    @ApiModelProperty(value = "合约okex获得c1类积分")
    private BigDecimal c1;
    @ApiModelProperty(value = "合约ix获得c2类积分")
    private BigDecimal c2;
    @ApiModelProperty(value = "合约bfx获得c3类积分")
    private BigDecimal c3;
    @ApiModelProperty(value = "合约bitmex获得c4类积分")
    private BigDecimal c4;
    @ApiModelProperty(value = "期货okex获得d1类积分")
    private BigDecimal d1;
    @ApiModelProperty(value = "期货火币获得d2类积分")
    private BigDecimal d2;
    @ApiModelProperty(value = "期货币安获得d3类积分")
    private BigDecimal d3;
    @ApiModelProperty(value = "期货mxc获得d4类积分")
    private BigDecimal d4;
    @ApiModelProperty(value = "期货bibox获得d5类积分")
    private BigDecimal d5;
    @ApiModelProperty(value = "打赏积分")
    private BigDecimal e;

    public CreditValueEntity() {
        this.a=new BigDecimal(0);
        this.b=new BigDecimal(0);
        this.c1=new BigDecimal(0);
        this.c2=new BigDecimal(0);
        this.c3=new BigDecimal(0);
        this.c4=new BigDecimal(0);
        this.d1=new BigDecimal(0);
        this.d2=new BigDecimal(0);
        this.d3=new BigDecimal(0);
        this.d4=new BigDecimal(0);
        this.d5=new BigDecimal(0);
        this.e=new BigDecimal(0);
    }
}
