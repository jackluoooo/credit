package info.bicoin.credit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description="签到数据记录实体类" )
@TableName("tb_sign")
public class SignEntity {
    private Long id;
    @ApiModelProperty(value = "BiCoin ID")
    private Long uid;
    @ApiModelProperty(value = "创建时间")
    private Date cTime;
    @ApiModelProperty(value = "签到最新时间")
    private Date uTime;
    @ApiModelProperty(value = "持续天数")
    private Long continueTimes;
}
