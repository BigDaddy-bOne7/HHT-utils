
$(function () {
    // 全选 与 取消全选
    function quanxuan() {
        // 全选 与 取消全选
        var $quanxuan = $(".waibukuang .xianshijieguokuang .biaotihang .lie_1 .quanxuan");

        var $xuanxiang = $(".waibukuang .xianshijieguokuang .liebiao .li .lie_1 .xuanxiang");

        // console.log($quanxuan);

        $quanxuan.on("click", function () {
            if ($(this).prop("checked")) {
                $xuanxiang.prop({
                    "checked": true
                });
            } else {
                $xuanxiang.prop({
                    "checked": false
                });
            }
        });
    }
    quanxuan();

    // 获取清单号
    var $_anniu_huoquqingdanhao = $(".anniu_huoquqingdanhao");
    var $_startDate = $(".startDate");
    var $_endDate = $(".endDate");
    $_anniu_huoquqingdanhao.click(function () {
        $(".liebiao").find("li").remove();
        $.ajax({
            url: '/download',// 跳转到 action
            data: {
                startDate: $_startDate.val().trim(),// 将开始日期转换成字符串 传给后台
                endDate: $_endDate.val().trim()
                // 将结束日期转换成字符串 传给后台
            },
            type: 'post',
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.result) {
                    alert(data.message);
                    getDownloadList();// 调用显示 函数 将更新 完清单号的 结果 显示到页面上
                }
            },
            error: function () {
                alert("获取清单号失败")
            }
        });
        setTimeout(function () {
            huoqu();
        }, 1000);
    });

    // 获取 总数 和 已经获取清单号的数字
    function huoqu() {
        var $_presentNo = $(".presentNo");
        var $_totalNo = $(".totalNo");

        $.ajax({
            url: '/getNo',// 跳转到 action
            data: null,
            type: 'post',
            cache: false,
            dataType: 'json',

            success: function (data) {
                var $_data = data;
                if ($_data.isRunning) {
                    setTimeout(function () {
                        huoqu();
                    }, 1000);
                    $_presentNo.html($_data.presentNo);

                    $_totalNo.html($_data.totalNo);
                }
            },
            error: function () {
                alert("异常！");
            }
        });
    }


    // 将更新 完清单号的 结果 显示到页面上
    function getDownloadList() {

        var $_zongshu = $(".tongji .shuliang"); // 获取 显示 总数
        var $kedaoru_zongshu = $(".yuanquban_dairu .shuliang"); // 获取 可提交总数

        var $_Ul = $(".liebiao");
        $
            .ajax({
                url: '/getDownloadList',// 跳转到 action
                type: 'post', // 传输方式
                data: {
                    startDate: $_startDate.val().trim(),// 将开始日期转换成字符串 传给后台
                    endDate: $_endDate.val().trim()
                    // 将结束日期转换成字符串 传给后台
                },
                async: true, // ajax请求是异步的
                cache: false,// 缓存ajax结果
                dataType: 'json',// 接收 数据格式

                success: function (data) {
                    var list = data;

                    $_zongshu.html(list.length); // 显示 获取的总数
                    $kedaoru_zongshu.html(list.length); // 可提交 总数

                    // 显示获取数据
                    for (var i = 0; i < list.length; i++) {
                        var J = i + 1;
                        var copNo = list[i].copNo;
                        var $_li = '<li class="li">'
                            + '<span class="span lie_1 " >'
                            + '<input class="anniu_fuxian xuanxiang" type="checkbox" name="copNo" value="'
                            + copNo + '">' + '</span>'
                            + '<span class="span lie_2">' + J
                            + '</span>' + '<span class="span lie_3">'
                            + copNo + '</span>'
                            + '<span class="span lie_4">'
                            + list[i].listNo + '</span>'
                            + '<span class="span lie_5">'
                            + list[i].orderNo + '</span>'
                            + '<span class="span lie_6">'
                            + list[i].date + '</span>' + '</li>';
                        $_Ul.append($_li);
                    }
                    quanxuan();// 调用全选函数
                },
                error: function () {
                    alert("异常！");
                }
            });
    }

    var $_anniu_yuanquban_shenbaoliebiao = $(".anniu_yuanquban_shenbaoliebiao");
    $_anniu_yuanquban_shenbaoliebiao.click(function () {
        var $_zongshu = $(".tongji .shuliang"); // 获取 显示 总数
        var $kedaoru_zongshu = $(".yuanquban_dairu .shuliang"); // 获取 可提交总数
        var $_Ul = $(".liebiao");
        $.ajax({
                url: '/getDeclareList',// 跳转到 action
                type: 'post', // 传输方式
                data: null,
                async: true, // ajax请求是异步的
                cache: false,// 缓存ajax结果
                dataType: 'json',// 接收 数据格式

                success: function (data) {
                    $(".liebiao").find("li").remove();
                    var list = data;

                    $_zongshu.html(list.length); // 显示 获取的总数
                    $kedaoru_zongshu.html(list.length); // 可提交 总数

                    // 显示获取数据
                    for (var i = 0; i < list.length; i++) {
                        var J = i + 1;
                        var copNo = list[i].copNo;
                        var $_li = '<li class="li">'
                            + '<span class="span lie_1 " >'
                            + '<input class="anniu_fuxian xuanxiang" type="checkbox" name="copNo" value="'
                            + copNo + '">' + '</span>'
                            + '<span class="span lie_2">' + J
                            + '</span>' + '<span class="span lie_3">'
                            + copNo + '</span>'
                            + '<span class="span lie_4">'
                            + list[i].listNo + '</span>'
                            + '<span class="span lie_5">'
                            + list[i].orderNo + '</span>'
                            + '<span class="span lie_6">'
                            + list[i].date + '</span>' + '</li>';
                        $_Ul.append($_li);
                    }
                    quanxuan();// 调用全选函数
                },
                error: function () {
                    alert("异常！");
                }
            });
    });

    // 按钮

    function huoqu_neibuhao_neibuhao() {
        var $_copNo_zu = []; // 新建数组

        var $_xuanzheshu = $('.waibukuang .xianshijieguokuang .liebiao .li input:checked');

        $_xuanzheshu.each(function () {
            if ($_xuanzheshu) {
                $_copNo_zu.push($(this).val());

            }
        });
        console.log($_copNo_zu.length);
        console.log($_copNo_zu);

        if (parseInt($_copNo_zu.length) < 1) {
            alert("请选择要暂存的订单！");
        }

        $.ajax({
            url: '/uploadList',// 跳转到 action
            type: 'post', // 传输方式
            data: {
                copNo: JSON.stringify($_copNo_zu)
            }, // 传输 选择列表中 的 val
            // 内部号
            async: true, // ajax请求是异步的
            cache: false,// 不缓存ajax结果
            dataType: 'json',// 接收 数据格式

            success: function (data) {
                console.log(data)

            }
        })


    }

    function declare() {
        var $_copNo_zu = []; // 新建数组

        var $_xuanzheshu = $('.waibukuang .xianshijieguokuang .liebiao .li input:checked');

        $_xuanzheshu.each(function () {
            if ($_xuanzheshu) {
                $_copNo_zu.push($(this).val());
            }
        });
        console.log($_copNo_zu.length);
        console.log($_copNo_zu);

        if (parseInt($_copNo_zu.length) < 1) {
            alert("请选择要暂存的订单！");
        }

        $.ajax({
            url: '/declareList',// 跳转到 action
            type: 'post', // 传输方式
            data: {
                copNo: JSON.stringify($_copNo_zu)
            }, // 传输 选择列表中 的 val
            // 内部号
            async: true, // ajax请求是异步的
            cache: false,// 不缓存ajax结果
            dataType: 'json',// 接收 数据格式

            success: function (data) {
                console.log(data)
            }
        })


    }

    var $_shuijin_shu = $(".shuijin_shu");

    function getTax() {
        var $_copNo_zu = []; // 新建数组

        var $_xuanzheshu = $('.waibukuang .xianshijieguokuang .liebiao .li input:checked');

        $_xuanzheshu.each(function () {
            if ($_xuanzheshu) {
                $_copNo_zu.push($(this).val());

            }
        });
        console.log($_copNo_zu.length);
        console.log($_copNo_zu);

        if (parseInt($_copNo_zu.length) < 1) {
            alert("请选择要暂存的订单！");
        }

        $.ajax({
            url: '/getTax',// 跳转到 action
            type: 'post', // 传输方式
            data: {
                copNo: JSON.stringify($_copNo_zu)
            }, // 传输 选择列表中 的 val
            // 内部号
            async: true, // ajax请求是异步的
            cache: false,// 不缓存ajax结果
            dataType: 'json',// 接收 数据格式

            success: function (data) {
                console.log(data);
                $_shuijin_shu.html(data.tax)

            }
        })


    }

    var $_zancunyuanquban_but = $(".yuanquban_dairu .anniu_yuanquban_tijiao"); // 园区版
    var $_declareButton = $(".yuanquban_dairu .anniu_yuanquban_shanbao");
    var $_button_totalTax = $(".yuanquban_dairu .button_totalTax");
    // 暂存
    // 按钮

    $_zancunyuanquban_but.click(function () { // 暂存园区版
        huoqu_neibuhao_neibuhao();

    });
    $_declareButton.click(function () { // 暂存园区版
        declare();
    });

    $_button_totalTax.click(function () { // 暂存园区版
        getTax();
    })
});
