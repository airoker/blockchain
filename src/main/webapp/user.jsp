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
    <div id="process" class="left">
        <div id="create" style="text-align:center">
            <input id="oneTransaction" class="button button-small-caps button-primary" value="手动触发交易"/>
        </div>
        <div style="height: 50px"></div>
        <div id="batchCreate" style="text-align:center">
            <input id="batchTransaction" class="button button-small-caps button-primary" value="自动触发交易"/>
        </div>
    </div>

    <div id="status" class="right">
        <div id="statusShow" style="text-align:center">

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
            document.getElementById("oneTransaction").setAttribute("disabled","disabled");

            $.ajax({
                url: '/block/chain/block/transaction/create?value=1',
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
            document.getElementById("batchTransaction").setAttribute("disabled","disabled");
            $.ajax({
                url: '/block/chain/block/transaction/autoCreate?value=1&count=5&interval=1',
                type: 'GET',
                timeout: 500000,
                success: function (data) {
                    if(data.ret == true){
                        alert(data.data);
                    } else {
                        alert(data.msg)
                    }
                    document.getElementById("batchTransaction").removeAttribute("disabled");
                },
                error: function () {
                    var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                    alert(ERR_MESSAGE);
                    document.getElementById("batchTransaction").removeAttribute("disabled");
                }

            });

            return false;

        });
    })(jQuery)
</script>
</body>
</html>
