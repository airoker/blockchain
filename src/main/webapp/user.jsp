<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>用户节点</title>
    <link rel="stylesheet" type="text/css" href="./blockchain/css/common.css">
    <link rel="stylesheet" type="text/css" href="./blockchain/css/button.css">
</head>
<body>

<div id="parent" class="parent">
    <div class="top">
    </div>
    <div id="process" class="left-half">
        <div id="create" style="text-align:center">
            <input id="oneTransaction" class="button button-small-caps button-primary" value="手动触发交易"/>
        </div>
        <div style="height: 50px"></div>
        <div id="batchCreate" style="text-align:center">
            <div>
                <input id="batchTransaction" class="button button-small-caps button-primary" value="自动触发交易"/>
            </div>
            <div>
                <div>
                <a>自动发送间隔(单位：秒):</a><input id="interval" value="1">
                </div>
                <div>
                <a>自动发送次数:</a><input id="count" value="5">
                </div>
                <div>
                <a>已发送:</a><a id="send">0</a>
                </div>
            </div>

        </div>
    </div>

    <div id="status" class="right">
        <div id="statusShow" style="text-align:left">

        </div>
    </div>

</div>

<script src="./blockchain/js/jquery-1.10.2.js"></script>
<script charset="utf-8" type="text/javascript">
    function checkStatus() {
        $.ajax({
            url: '/block/chain/block/transaction/limitStatus',
            type: 'GET',
            timeout: 5000,
            success: function (data) {
                var releaseEle = document.getElementById("limitStatusFalse");
                var limitEle = document.getElementById("limitStatusTrue");
                if(data.ret == true){


                    if(data.data == "true"){
                        // 限制
                        if(releaseEle!=null){
                            document.getElementById("statusShow").removeChild(releaseEle);
                        }

                        if(limitEle==null){
                            var limitHtml = '<a id="limitStatusTrue" class="button button-caution button-small-caps button-primary">限制</a>'
                            $('#statusShow').append(limitHtml);
                        }

                    }else {
                        // 不限制
                        if(limitEle!=null){
                            document.getElementById("statusShow").removeChild(limitEle);
                        }
                        if(releaseEle==null) {
                            var releaseHtml = '<a id="limitStatusFalse" class="button button-action button-small-caps button-primary">不受限制</a>'
                            $('#statusShow').append(releaseHtml);
                        }
                    }
                } else {
                    alert(data.msg)
                }
            }

        });

        return false;
    }

    // 轮询状态
    var timeTask = setInterval(function () {
        checkStatus();
    }, 1000);
    
    (function($) {
        $('#oneTransaction').on('click', function () {

            var valueVar = prompt("请输入交易内容", "1");
            if (valueVar == null ) {
                return false;
            }

            document.getElementById("oneTransaction").setAttribute("disabled", "disabled");

            $.ajax({
                url: '/block/chain/block/transaction/create?value=' + valueVar,
                type: 'GET',
                timeout: 5000,
                success: function (data) {
                    if(data.ret == true){
                        alert(data.data);
                    } else {
                        alert(data.msg)
                    }
                    document.getElementById("oneTransaction").removeAttribute("disabled");
                },
                error: function () {
                    var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                    alert(ERR_MESSAGE);
                    document.getElementById("oneTransaction").removeAttribute("disabled");
                }

            });

            return false;

        });

        $('#batchTransaction').on('click', function () {
            var intervalValue = $('#interval').val();
            var sendCount = $('#count').val();

            var context = "请输入交易内容（每" + intervalValue + "秒，发送" + sendCount + "次)";
            var valueVar = prompt(context, "1");
            if (valueVar == null ) {
                return false;
            }

            document.getElementById("batchTransaction").setAttribute("disabled","disabled");
            var sendEle = document.getElementById("send");

            var currentIndex = 0;
            sendEle.innerText = "" + currentIndex;
            createTransaction();

            function createTransaction() {
                if (currentIndex >= sendCount){
                    return;
                }

                $.ajax({
                    url: '/block/chain/block/transaction/create?value=' + valueVar,
                    type: 'GET',
                    timeout: 5000,
                    async: false,
                    success: function (data) {
                        if(data.ret == true){
                            currentIndex++;
                            sendEle.innerText = "" + currentIndex;
                            sleep(intervalValue * 1000);
                            createTransaction();
                        } else {
                            alert(data.msg);
                        }

                    },
                    error: function () {
                        var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                        alert(ERR_MESSAGE);
                    }

                });

            }

            document.getElementById("batchTransaction").removeAttribute("disabled");

            return false;

        });

        function sleep(numberMillis) {
            var now = new Date();
            var exitTime = now.getTime() + numberMillis;
            while (true) {
                now = new Date();
                if (now.getTime() > exitTime)
                    return;
            }
        }
    })(jQuery)
</script>
</body>
</html>
