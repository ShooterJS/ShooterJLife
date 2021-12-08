import com.google.common.collect.Lists;
import com.shooterj.core.builder.Builder;

import java.time.LocalDate;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/11/30
 */
public class BuilderTest {

    public static void main(String[] args) {
        ResponseDateModel responseDateModel = Builder.of(ResponseDateModel::new)
                .with(ResponseDateModel::setFormatDateList, Lists.newArrayList("202107","202108","202109"))
                .with(ResponseDateModel::setStartDate, LocalDate.now())
                .with(ResponseDateModel::setEndDate, LocalDate.now().plusDays(7)).build();

        System.out.println(responseDateModel.toString());
    }



}
