/**
 * Created by asus on 2017-06-19.
 */

// 获取清单号
$(function () {
    var $_anniu_huoquqingdanhao = $(".anniu_huoquqingdanhao");

    var $_startDate = $(".startDate");

    var $_endDate = $(".endDate");

    $_anniu_huoquqingdanhao.click(function () {
        $.ajax({
            url: '/HHT-utils/download',// 跳转到 action
            data: {
                startDate: $_startDate.val().trim(),
                endDate: $_endDate.val().trim()
            },
            type: 'post',
            cache: false,
            dataType: 'json',

            success: function (data) {
                if (data.result) {
                    alert(data.message);
                    getDownloadList();
                }
            },
            error: function (data) {
                alert(data.message);
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
            url: '/HHT-utils/getNo',// 跳转到 action
            data: null,
            type: 'post',
            cache: false,
            dataType: 'json',

            success: function (data) {
                var $_data = data;
                if ($_data.isRunning) {
                    setTimeout(function () {
                        huoqu();
                    }, 5000);
                } else {
                    alert("获取完毕！");
                }
                $_presentNo.html($_data.presentNo);

                $_totalNo.html($_data.totalNo);
            },
            error: function () {
                alert("异常！");
            }
        });
    }

    // huoqu();

    // 显示 更新结果

    var $_Ul = $(".liebiao");

    // 全选
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
        $xuanxiang
            .on(
                "click",
                function () {
                    for (var i = 0; i < $xuanxiang.length; i++) { // 当 所有
                        // 子选项
                        // input
                        // 为checked 时 自动 给
                        // 全选 添加 为checked

                        if ($('.waibukuang .xianshijieguokuang .liebiao .li input:checked').length == $xuanxiang.length) {

                            $quanxuan.prop("checked", true);

                        } else {
                            $quanxuan.prop("checked", false);
                        }
                    }
                })
    }

    quanxuan();

    var $_zongshu = $(".tongji .shuliang"); // 获取 显示 总数

    var $kedaoru_zongshu = $(".yuanquban_dairu .shuliang"); // 获取 可提交总数

    function getDownloadList() {

        $
            .ajax({
                url: '/HHT-utils/getDownloadList',// 跳转到 action
                type: 'post', // 传输方式
                data: null,// 传输 选择列表中 的 val
                async: true, // ajax请求是异步的
                cache: false,// 不缓存ajax结果
                dataType: 'json',// 接收 数据格式

                success: function (data) {
                    var list = data;

                    $_zongshu.html(list.length); // 显示 获取的总数

                    $kedaoru_zongshu.html(list.length); // 可提交 总数

                    // 显示获取数据
                    for (var i = 0; i < list.length; i++) {
                        var J = i + 1;

                        var $_li = '<li class="li">'
                            + '<span class="span lie_1 " >'
                            + '<input class="anniu_fuxian xuanxiang" type="checkbox" name="copNo" value="'
                            + list[i].copNo + '">' + '</span>'
                            + '<span class="span lie_2">' + J
                            + '</span>' + '<span class="span lie_3">'
                            + list[i].copNo + '</span>'
                            + '<span class="span lie_4">'
                            + list[i].listNo + '</span>'
                            + '<span class="span lie_5">'
                            + list[i].orderNo + '</span>'
                            + '<span class="span lie_6">'
                            + list[i].date + '</span>' + '</li>';
                        $_Ul.append($_li);
                        quanxuan();
                    }
                },
                error: function () {
                    alert("异常！");
                }
            });
    }

    var $_copNo_zu = []; // 新建数组

    function huoqu_neibuhao_neibuhao() {
        var $_copNo = $(".waibukuang .xianshijieguokuang .liebiao .li .copNo");
        var $_xuanzheshu = $('.waibukuang .xianshijieguokuang .liebiao .li input:checked');
        $_xuanzheshu.each(function () {
            if ($_xuanzheshu) {
                $_copNo_zu.push($(this).val());
            }
        });

        $.ajax({
            url: '/HHT-utils/uploadList',// 跳转到 action
            type: 'post', // 传输方式
            data: {
                copNo: JSON.stringify($_copNo_zu)
            }, // 传输 选择列表中 的 val
            // 内部号
            // data:this.$_copNo_zu,
            async: true, // ajax请求是异步的
            cache: false,// 不缓存ajax结果
            dataType: 'json',// 接收 数据格式

            success: function (data) {

            }
        })

    }

    var $zancunyuanquban_but = $(".yuanquban_dairu .anniu_yuanquban_tijiao"); // 暂存
    // 园区版

    $zancunyuanquban_but.click(function () { // 暂存园区版
        huoqu_neibuhao_neibuhao();

    })

})
