import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/11/30
 */
@Data
public class ResponseDateModel implements Serializable {
    private List<String> formatDateList;
    private LocalDate startDate;
    private LocalDate endDate;
}
