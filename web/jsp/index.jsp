<%@ page language="java" import="java.time.LocalDate"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>pachongbaoguan</title>
    <link rel="stylesheet" href="css/baoguanxitong.css">

</head>
<body>
<div class="waibukuang main">
    <div id="dingbu">
        <div class="gongsimingcheng">深圳市汇惠通电子商务有限公司报关系统</div>
    </div>

    <div id="toubu">
        <div class="gsxuanzhe">
            <span class="span tongyibanbiaoti">统一版 获取清单号</span>
            <%
                LocalDate date = LocalDate.now();
                out.print(
                        "<span class='startDateKuang'>开始时间：<input type='date' class='start_date startDate' name='startDate' value="
                                + date.toString() + "></span>");
                out.print(
                        "<span class='startDateKuang'>结束时间：<input type='date' class='start_date endDate' name='endDate' value="
                                + date.toString() + "></span>");
            %>

            <input class="anniu_huoquqingdanhao" name="anniu_huoquqingdanhao"
                   type="button" value="获取清单号">
            <input type="button" class="anniu_yuanquban_shenbaoliebiao" name="anniu_yuanquban_shenbaoliebiao" value="获取可申报列表">

        </div>

    </div>

    <div class="anniu_xianshichaxinjiguo">
        <span class="span chaxunjieguo">获取清单号结果：</span> <span
            class="presentNo">0</span>/<span class="totalNo">0</span>

    </div>
    <div class="xianshijieguokuang">
        <div class="biaotihang">
				<span class="span lie_1"><input class="anniu_fuxian quanxuan"
                                                type="checkbox"></span> <span class="span lie_2">序号</span> <span
                class="span lie_3">内部号</span> <span class="span lie_4">清单号</span> <span
                class="span lie_5">订单号</span> <span class="span lie_6">日 期</span>
        </div>


        <ul class="liebiao">
        </ul>

    </div>
    <div class="tongji">
			<span class="span">共 <span class="shuliang span">000</span>条</span>
    </div>
    <hr class="getiao">
    <div class="yuanquban_dairu">
        <div class="text_biaot">园区版导入</div>

        <div class="">
            <input type="submit" class="anniu_yuanquban_tijiao" value="暂 存">
            <input type="submit" class="anniu_yuanquban_shanbao" value="申 报">
            <input type="submit" class="button_totalTax" value="统计税金">
            <input type="submit" class="button_parkUpdate" value="更新园区版状态">
        </div>
        <p class="p">
            <span class="span">数量：</span><span class="span shuliang">10000</span><span
                class="span shuliang_wenzi">单</span>
        </p>
        <p class="p">
            <span class="span">税金：</span><span class="span shuijin_shu"></span>人民币
            <span class="span">提交成功：</span><span class="span dingdan_shu"></span>单
        </p>


    </div>
</div>

<div class="footer">
    <div class="footer-neirong">
        <p class="p">
            <a href="http://www.huihuitong.hk"> 深圳市汇惠通电子商务有限公司</a> 网站备案/许可证号：<a
                href="http://www.miitbeian.gov.cn">粤ICP备15070622号</a>
        </p>
        <p class="p">
            <a href="http://www.huihuitong.com"> 深圳市卓鼎汇通供应链服务有限公司</a> 网站备案/许可证号：<a
                href="http://www.miitbeian.gov.cn">粤ICP备15025226号</a>
        </p>
        <p class="p">地址：深圳市南山区前海保税区5栋5502室 电话：0755—21614963</p>
        <div class="log-image">
            <a href="http://www.huihuitong.hk"> <img
                    src="image/Official%20Accounts.jpg" alt="汇惠通"></a>
        </div>

    </div>


</div>


</body>
<script src="js/jquery-3.1.1.min.js"></script>

<script src="js/pachong.js"></script>

</html>