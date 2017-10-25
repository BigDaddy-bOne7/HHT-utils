package com.huihuitong.dao;

import com.huihuitong.meta.CustomsInfo;
import com.huihuitong.meta.Payer;
import com.huihuitong.meta.ProductDetail;
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
     * 获取所需时间段内需要发送支付报文的订单号
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 订单号列表
     */
    @Select("SELECT OrderCode FROM order_header WHERE TradeCreateDateTime BETWEEN '#{startDate} 00:00:00' " +
            "AND '#{endDate} 23:59:59' AND Status != 950")
    List<String> listOrders(@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 通过订单号获取商品信息
     * @param orderCode 订单号
     * @return 商品信息列表
     */
    @Select("SELECT " +
            "id.GoodsName AS `name`, " +
            "od.RequestQty AS quantity, " +
            "FORMAT(od.ItemListPrice,2)*100 AS amount, " +
            "'深圳市汇惠通电子商务有限公司' AS receiver, " +
            "od.ItemDesc AS description " +
            "FROM " +
            "order_header AS oh " +
            "RIGHT JOIN order_detail AS od ON oh.Id = od.OrderId " +
            "LEFT JOIN item_declare AS id ON id.DeclItemCode = od.UserDef5 " +
            "WHERE oh.Id = #{orderCode}")
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
            "oh.Tax AS tax, " +
            "4403660098 AS merchantCommerceCode, " +
            "'深圳市汇惠通电子商务有限公司' AS merchantCommerceName, " +
            "oh.WarehouseCode AS storeHouse, " +
            "5349 AS customsCode " +
            "471800 AS ciqCode " +
            "FROM " +
            "order_header AS oh " +
            "WHERE " +
            "oh.OrderCode = #{orderCode}")
    CustomsInfo getCustomsInfo(@Param("orderCode") String orderCode);
}
