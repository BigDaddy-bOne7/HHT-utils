package com.huihuitong.dao;

import com.ehking.sdk.entity.CustomsInfo;
import com.ehking.sdk.entity.Payer;
import com.ehking.sdk.entity.ProductDetail;
import com.huihuitong.meta.QueryConfig;
import com.huihuitong.meta.YibaoPaymentOrderInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @author yangz
 * @date 2017/10/24 16:10:15
 */
public interface YibaoDao {

    /**
     * 获取需要发送支付报文的订单号
     * @param config 货主编码
     * @return 订单号列表
     */
    @Select("SELECT " +
            "oh.OrderCode AS orderCode, " +
            "oh.CompanyCode AS companyCode, " +
            "oh.SecondaryWayBillCode AS deliverId, " +
            "oh.PrimaryWayBillCode AS logisticsNo, " +
            "oh.TotalValue AS amount, " +
            "IFNULL(sys.`status`,'未发送') AS `status`, " +
            "oh.TradeCreateDateTime AS createTime " +
            "FROM " +
            "order_header AS oh " +
            "LEFT JOIN spider_yibao_status AS sys ON oh.OrderCode = sys.orderCode " +
            "WHERE " +
            "oh.`Status` != 950 AND " +
            "oh.`Status` != 100 AND " +
            "(oh.CompanyCode = #{companyCode} OR " +
            "IFNULL(" +
            "#{companyCode}, " +
            "'null' " +
            ") = 'null') AND " +
            "(oh.SecondaryWayBillCode=#{deliverId} OR " +
            "IFNULL(" +
            "#{deliverId}, " +
            "'null' " +
            ") = 'null') AND " +
            "(oh.OrderCode=#{orderCode} OR " +
            "IFNULL(" +
            "#{orderCode}, " +
            "'null' " +
            ") = 'null') AND " +
            "(oh.PrimaryWayBillCode=#{logisticsNo} OR " +
            "IFNULL(" +
            "#{logisticsNo}, " +
            "'null' " +
            ") = 'null') AND " +
            "(oh.TradeCreateDateTime > #{startDate} OR " +
            "IFNULL(" +
            "#{startDate}, " +
            "'null'" +
            ") = 'null') AND " +
            "(oh.TradeCreateDateTime < #{endDate} OR " +
            "IFNULL(" +
            "#{endDate}, " +
            "'null'" +
            ") = 'null')")
    List<YibaoPaymentOrderInfo> listOrders(QueryConfig config);

    /**
     * 通过订单号获取商品信息
     * @param orderCode 订单号
     * @return 商品信息列表
     */
    @Select("SELECT " +
            "id.GoodsName AS `name`, " +
            "od.RequestQty AS quantity, " +
            "ROUND(od.ItemListPrice * 100) AS amount, " +
            "'深圳市汇惠通电子商务有限公司' AS receiver, " +
            "od.ItemDesc AS description " +
            "FROM " +
            "order_header AS oh " +
            "RIGHT JOIN order_detail AS od ON oh.Id = od.OrderId " +
            "LEFT JOIN item_declare AS id ON id.DeclItemCode = od.UserDef5 " +
            "WHERE oh.orderCode = #{orderCode}")
    List<ProductDetail> getProductDetails(@Param("orderCode") String orderCode);

    /**
     * 通过订单号获取支付人信息
     * @param orderCode 订单号
     * @return 支付人信息
     */
    @Select("SELECT " +
            "odc.BuyerID AS `name`, " +
            "'IDCARD' AS idType, " +
            "odc.PaperNumber AS idNum, " +
            "oh.SourceUserAccount AS customerId, " +
            "odc.BuyerTelNumber AS phoneNum, " +
            "concat(odc.BuyerTelNumber,'@qq.com') AS email, " +
            "156 AS nationality " +
            "FROM " +
            "order_header_custom AS odc " +
            "INNER JOIN order_header AS oh ON odc.OrderCode = oh.OrderCode " +
            "WHERE " +
            "odc.OrderCode = #{orderCode} ")
    Payer getPayer(@Param("orderCode") String orderCode);

    /**
     * 通过订单号查询报关信息
     * @param orderCode 订单号
     * @return CustomsInfo 报关信息
     */
    @Select("SELECT " +
            "'OFFICAL' AS customsChannel, " +
            "ROUND(ohc.OrderGoodsAmount * 100) AS goodsAmount, " +
            "ROUND(oh.Tax * 100) AS tax, " +
            "cc.companyCode AS merchantCommerceCode, " +
            "cc.eCommerceName AS merchantCommerceName, " +
            "oh.WarehouseCode AS storeHouse, " +
            "'5349' AS customsCode, " +
            "'471800' AS ciqCode, " +
            "'BBC' AS functionCode, " +
            "'B2B2C' AS businessType " +
            "FROM " +
            "order_header AS oh " +
            "INNER JOIN order_header_custom AS ohc ON oh.OrderCode = ohc.OrderCode " +
            "INNER JOIN company AS c ON oh.CompanyCode = c.CompanyCode " +
            "INNER JOIN company_custom AS cc ON c.Id = cc.Id " +
            "WHERE " +
            "oh.OrderCode = #{orderCode}")
    CustomsInfo getCustomsInfo(@Param("orderCode") String orderCode);

    /**
     * 通过订单号查询订单金额
     * @param orderCode 订单号
     * @return 订单金额
     */
    @Select("SELECT ROUND(TotalAmount * 100) FROM `order_header_custom` WHERE OrderCode = #{orderCode}")
    Long getOrderAmount(@Param("orderCode") String orderCode);

}
