import com.shooterj.core.util.BigDataInsert;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

/*
    测试大数据插入mysql
 */
@Component
public class BreadthBigDataInsertRepository extends BigDataInsert<ProdCateBreadthPo> {


    @Override
    public PreparedStatement pstmToSetValue(PreparedStatement pstm, ProdCateBreadthPo po) {
        try {
            pstm.setInt(1,po.getM4Chance());
            pstm.setLong(2,po.getBreadthQty());
            pstm.setTimestamp(3, Timestamp.valueOf(po.getCreateTime()));
            pstm.setInt(4,po.getM1());
            pstm.setInt(5,po.getM2());
            pstm.setInt(6,po.getM3());
            pstm.setInt(7,po.getM4());
            pstm.setString(8,po.getTerminalCode());
            pstm.setString(9,po.getTerminalName());
            pstm.setString(10,po.getUcOrgAgencyId());
            pstm.setString(11,po.getUcOrgAgencyName());
            pstm.setString(12,po.getUcOrgBuId());
            pstm.setString(13,po.getUcOrgBuName());
            pstm.setString(14,po.getUcOrgSubareaId());
            pstm.setString(15,po.getUcOrgSubareaName());
            pstm.setString(16,po.getUserCode());
            pstm.setString(17,po.getUserName());
        }catch (Exception e){
            e.printStackTrace();
        }


        return pstm;
    }

    @Override
    public void init() {
        this.driverClassName = "com.mysql.cj.jdbc.Driver";
        this.url = "jdbc:mysql://172.18.1.141:3306/cm?useServerPrepStmts=false&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8";
        this.user = "cm_dev";
        this.password = "Cm#2020D@v";
        this.sql = "INSERT INTO cm_prodcate_breadth (m4_chance, breadth_qty, create_time , m1, m2, m3, m4 , terminal_code, terminal_name, uc_org_agency_id, uc_org_agency_name," +
                " uc_org_bu_id , uc_org_bu_name, uc_org_subarea_id, uc_org_subarea_name, user_code , user_name) VALUES (    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    }


}
