package com.huihuitong.dao;

import com.huihuitong.meta.ListNoStatus;
import com.huihuitong.meta.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by yangz on 2017/7/20 9:37.
 */
public interface MybatisDao {
    @Insert("insert into listnostatus (orgCode,copno,listno,logisticsNo,orderNo,status,date) "
            + "values (#{lns.orgCode},#{lns.copNo},#{lns.listNo},#{lns.logisticsNo},#{lns.orderNo},#{lns.status}"
            + ",DATE_FORMAT(Now(),'%Y-%m-%d'))")
    void insertListNoStatus(@Param("lns") ListNoStatus lns);

    // 获取清单状态
    @Select("select * from listnostatus where status = #{status} and orgCode = #{orgCode} and date Between #{startDate} and #{endDate}")
    List<ListNoStatus> getListNoStatus(@Param("status") int status, @Param("startDate") String startDate,
                                       @Param("endDate") String endDate, @Param("orgCode") String orgCode);

    @Select("select status from listnostatus where copNo = #{copNo}")
    Integer getStatus(@Param("copNo") String copNo);

    // 更新清单状态
    @Update("update listnostatus set status = #{status} where copNo = #{copNo}")
    void updateListNoStatus(@Param("copNo") String copNo, @Param("status") int status);

    @Update("update listnostatus set formId = #{formId} where listNo = #{listNo}")
    void updateFormId(@Param("formId") String formId, @Param("listNo") String listNo);

    // 获取清单编号
    @Select("select listNo from listnostatus where copno = #{copNo}")
    String getListNo(@Param("copNo") String copNo);

    // 获取运单编号
    @Select("select logisticsNo from listnostatus where copno = #{copNo}")
    String getLogisticsNo(@Param("copNo") String copNo);

    // 获取订单编号
    @Select("select orderNo from listnostatus where copno = #{copNo}")
    String getOrderNo(@Param("copNo") String copNo);

    // 获取用户信息
    @Select("select * from user where userName = #{userName}")
    User getUser(@Param("userName") String userName);

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);

    // 更新统一版cookie
    @Update("update user set unitecookie = #{uniteCookie} where id = #{id}")
    void updateUniteCookie(@Param("id") int id, @Param("uniteCookie") String uniteCookie);

    // 更新园区版cookie
    @Update("update user set parkcookie = #{parkcookie} where id = 1")
    void updateParkCookie(@Param("parkcookie") String parkcookie);

    // 获取园区版所需企业备案名称
    @Select("select cusCode from user where orgCode = #{orgCode}")
    String getCusCode(@Param("orgCode") String orgCode);
}
