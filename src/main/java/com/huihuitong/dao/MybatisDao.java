package com.huihuitong.dao;

import com.huihuitong.meta.ListStatus;
import com.huihuitong.meta.OrderDetail;
import com.huihuitong.meta.OrderHeader;
import com.huihuitong.meta.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MybatisDao {
    //插入清单状态
    @Insert("insert into spider_list_status (orgCode,copno,listno,logisticsNo,orderNo,status,date) "
            + "values (#{orgCode},#{copNo},#{listNo},#{logisticsNo},#{orderNo},#{status},#{date})")
    void insertListStatus(ListStatus lns);

    // 获取清单状态
    @Select("select * from spider_list_status where status = #{status} and orgCode = #{orgCode} and date Between #{startDate} and #{endDate}")
    List<ListStatus> getListStatus(@Param("status") int status, @Param("startDate") String startDate,
                                   @Param("endDate") String endDate, @Param("orgCode") String orgCode);

    @Select("SELECT DISTINCT oh.SecondaryWayBillCode " +
            "FROM order_header oh RIGHT JOIN spider_list_status sls ON oh.OrderCode = sls.orderNo " +
            "where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= sls.date")
    List<String> listDeliverId();

    @Select("SELECT sls.id, sls.orgCode, sls.copNo, sls.listNo, sls.logisticsNo, sls.orderNo, sls.status, sls.formId, sls.parkStatus, sls.date" +
            "FROM order_header oh LEFT JOIN spider_list_status sls ON oh.OrderCode = sls.orderNo " +
            "where oh.SecondaryWayBillCode = #{deliverId} and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= sls.date")
    List<ListStatus> getListStatusByDeliverId(@Param("deliverId") String deliverId);

    @Select("SELECT count(sls.id) FROM order_header oh LEFT JOIN spider_list_status sls ON oh.OrderCode = sls.orderNo " +
            "where oh.SecondaryWayBillCode = #{deliverId} and parkStatus = '海关审核通过'")
    int getPassNum(String deliverId);

    @Select("SELECT count(sls.id) FROM order_header oh LEFT JOIN spider_list_status sls ON oh.OrderCode = sls.orderNo " +
            "where oh.SecondaryWayBillCode = #{deliverId} and parkStatus = '已暂存'")
    int getTemporaryNum(String deliverId);

    @Select("select status from spider_list_status where copNo = #{copNo}")
    Integer getStatus(@Param("copNo") String copNo);

    //获取ECM订单信息
    @Select("SELECT " +
            "oh.Id AS Id, " +
            "cc.companyName AS CopName, " +
            "oh.TotalQty AS packNo, " +
            "odc.GrossWeight AS grossWeight, " +
            "odc.NetWeight AS netWeight, " +
            "ohc.BuyerID AS buyerName, " +
            "oh.ShipToCity AS consigneeCity, " +
            "ohc.PaperNumber AS buyerIdNumber, " +
            "ohc.BuyerTelNumber AS buyerTelephone, " +
            "cc.eCommerceCode AS ebpCode, " +
            "oh.OrderCode AS orderNo, " +
            "oh.PrimaryWayBillCode AS logisticsNo, " +
            "cc.declareCompanyCode AS payCode, " +
            "ohc.PayNumber AS payTransactionId, " +
            "oh.SecondaryWayBillCode AS tranfNo, " +
            "cc.eCommerceKey AS OrgCode " +
            "FROM " +
            "order_header AS oh " +
            "INNER JOIN company AS c ON oh.CompanyCode = c.CompanyCode " +
            "INNER JOIN company_custom AS cc ON c.Id = cc.Id " +
            "INNER JOIN order_detail AS od ON oh.Id = od.OrderId " +
            "INNER JOIN order_detail_custom AS odc ON odc.Id = od.Id " +
            "INNER JOIN order_header_custom AS ohc ON oh.Id = ohc.Id " +
            "WHERE " +
            "oh.OrderCode = #{orderNo} " +
            "AND oh.PrimaryWayBillCode = #{logisticsNo}")
    OrderHeader getOrderHeader(@Param("orderNo") String orderNo, @Param("logisticsNo") String logisticsNo);

    @Select("SELECT " +
            "od.UserDef5 AS itemRecordNo, " +
            "id.HsCode AS gcode, " +
            "id.GoodsName AS gname, " +
            "id.Models AS gmodel, " +
            "od.RequestQty AS qty, " +
            "i.UnitDesc AS unit, " +
            "od.ItemListPrice AS totalPrice, " +
            "id.Property AS qty1, " +
            "id.OriginCode AS oriCountry " +
            "FROM " +
            "order_detail AS od " +
            "INNER JOIN item_declare AS id ON od.UserDef5 = id.DeclItemCode " +
            "INNER JOIN item AS i ON od.UserDef5 = i.ParentCode " +
            "WHERE " +
            "od.OrderId = #{id} " +
            "GROUP BY " +
            "od.Id")
    List<OrderDetail> listOrderDetails(@Param("id") int id);

    // 更新清单状态
    @Update("update spider_list_status set status = #{status} where copNo = #{copNo}")
    void updateListStatus(@Param("copNo") String copNo, @Param("status") int status);

    @Update("update spider_list_status set status = #{status} where formId = #{formId}")
    void updateListStatusByFormId(@Param("formId") String formId, @Param("status") int status);

    @Update("update spider_list_status set formId = #{formId} where listNo = #{listNo}")
    void updateFormId(@Param("formId") String formId, @Param("listNo") String listNo);

    @Update("update spider_list_status set parkStatus = #{parkStatus} where formId = #{formId}")
    void updateParkStatus(@Param("parkStatus") String parkStatus, @Param("formId") String formId);

    // 获取清单状态
    @Select("select * from spider_list_status where copno = #{copNo}")
    ListStatus getListStatusByCopNo(@Param("copNo") String copNo);

    @Select("select * from spider_list_status where listNo = #{listNo}")
    ListStatus getListStatusByListNo(@Param("listNo") String listNo);

    //获取税金
    @Select("select CASE WHEN(COUNT(Tax)=0)THEN 0 ELSE Tax END AS Tax from order_header where ordercode = (SELECT orderNo FROM `spider_list_status` where copNo = #{copNo})")
    double getTax(@Param("copNo") String copNo);

    // 获取用户信息
    @Select("select * from spider_user where userName = #{userName}")
    User getUser(@Param("userName") String userName);

    @Select("select * from spider_user where id = #{id}")
    User getUserById(@Param("id") int id);

    // 更新统一版cookie
    @Update("update spider_user set unitecookie = #{uniteCookie} where id = #{id}")
    void updateUniteCookie(@Param("id") int id, @Param("uniteCookie") String uniteCookie);

    // 更新园区版cookie
    @Update("update spider_user set parkcookie = #{parkcookie} where id = 1")
    void updateParkCookie(@Param("parkcookie") String parkcookie);

    //获取园区版cookie
    @Select("select parkcookie from spider_user where id = 1")
    String getParkCookie();

    // 获取园区版所需企业备案名称
    @Select("select cusCode from spider_user where orgCode = #{orgCode}")
    String getCusCode(@Param("orgCode") String orgCode);
}
