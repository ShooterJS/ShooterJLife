import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 产品系列深度
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdCateBreadthPo  {

    protected int id;
    protected String terminalCode;
    protected String terminalName;
    protected long breadthQty;
    protected int m1;
    protected int m2;
    protected int m3;
    protected int m4;
    protected int m4Chance;
    protected String userCode;
    protected String userName;

    @ApiModelProperty(value = "办事处编码")
    String ucOrgAgencyId;
    @ApiModelProperty(value = "办事处名称")
    String ucOrgAgencyName;
    @ApiModelProperty(value = "BU编码")
    String ucOrgBuId;
    @ApiModelProperty(value = "BU名称")
    String ucOrgBuName;
    @ApiModelProperty(value = "子大区编码")
    String ucOrgSubareaId;
    @ApiModelProperty(value = "子大区名称")
    String ucOrgSubareaName;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;
    protected int status;
    protected int deleted;



}
