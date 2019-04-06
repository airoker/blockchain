<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>矿工节点</title>
    <link rel="stylesheet" type="text/css" href="./blockchain/css/common.css">
    <link rel="stylesheet" type="text/css" href="./blockchain/css/button.css">
</head>
<body>

<div id="parent" class="parent">
    <div class="top">
    </div>
    <div id="process" class="left">
        <div id="unprocessedTransaction" style="text-align:center">
            未处理交易: <a id="unprocessedTransactionCount"></a>

        </div>

        <div style="height: 50px"></div>
        <div id="threshold" style="text-align:center">
            <input id="limitThreshold"><input id="updateThreshold" type="button" value="修改阈值">
        </div>

    </div>

    <div class="right">
        <div class="left">
            <div id="processedTransaction" style="text-align:center">
                已处理交易: <a id="processedTransactionCount"></a>
            </div>

            <div style="height: 50px"></div>
            <div id="currentBlock" style="text-align:center">
                当前区块数: <a id="blockCount"></a>
            </div>
        </div>

        <div class="right">
            <div id="work" style="text-align:center">
                <input id="workStatus" class="button button-small-caps button-primary" value="启动"/>
            </div>
        </div>
    </div>

</div>

<script src="./blockchain/js/jquery-1.10.2.js"></script>
<script charset="utf-8" type="text/javascript">
    checkStatus();
    processThreshold();
    checkWorkerStatus();

    function checkWorkerStatus() {
        $.ajax({
            url: '/block/chain/config/workerStatus',
            type: 'GET',
            timeout: 5000,
            success: function (data) {
                var workStatusEle = document.getElementById("workStatus");
                if (data.ret == true) {
                    // 工作状态
                    if (data.data == "work") {
                        workStatusEle.value = "停止"
                    } else {
                        workStatusEle.value = "启动"
                    }
                } else {
                    alert(data.msg)
                }
            },
            error: function () {
                var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                alert(ERR_MESSAGE);
            }

        });

        return false;
    }
    function processThreshold() {
        $.ajax({
            url: '/block/chain/config/transactionLimit',
            type: 'GET',
            timeout: 5000,
            success: function (data) {
                var limitThresholdEle = document.getElementById("limitThreshold");
                if(data.ret == true){
                    // 限制
                    limitThresholdEle.value= data.data;
                } else {
                    alert(data.msg)
                }
            },
            error: function () {
                var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                alert(ERR_MESSAGE);
            }

        });

        return false;
    }

    function modifyThreshold() {
        $.ajax({
            url: '/block/chain/config/update/transactionLimit?limit='+$('#limitThreshold').val(),
            type: 'GET',
            timeout: 5000,
            success: function (data) {
                if(data.ret == true){
                    alert(data.data)
                } else {
                    alert(data.msg)
                }
            },
            error: function () {
                var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                alert(ERR_MESSAGE);
            }

        });

        return false;
    }

    function checkStatus() {
        $.ajax({
            url: '/block/chain/block/worker/countStatus',
            type: 'GET',
            timeout: 5000,
            success: function (data) {
                var processedEle = document.getElementById("processedTransactionCount");
                var unprocessEle = document.getElementById("unprocessedTransactionCount");
                var blockCountEle = document.getElementById("blockCount");
                if(data.ret == true){
                    // 限制
                    processedEle.innerText= ""+data.data.processedCount;
                    unprocessEle.innerText= ""+data.data.unprocessCount;
                    blockCountEle.innerText= ""+data.data.blockCount;

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
    }, 2000);

    (function($) {
        $('#updateThreshold').on('click', function () {
            modifyThreshold();

            return false;

        });

        $('#workStatus').on('click', function () {
            var workStatusEle = document.getElementById("workStatus");
            if (workStatusEle.value == "停止"){
                $.ajax({
                    url: '/block/chain/config/update/workerStatus?value=stop',
                    type: 'GET',
                    timeout: 5000,
                    success: function (data) {
                        if(data.ret == true){
                            alert(data.data);
                            checkWorkerStatus()
                        } else {
                            alert(data.msg);
                            checkWorkerStatus()
                        }
                    },
                    error: function () {
                        var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                        alert(ERR_MESSAGE);
                    }

                });
            }

            if (workStatusEle.value == "启动"){
                $.ajax({
                    url: '/block/chain/config/update/workerStatus?value=work',
                    type: 'GET',
                    timeout: 5000,
                    success: function (data) {
                        if(data.ret == true){
                            alert(data.data);
                            checkWorkerStatus()
                        } else {
                            alert(data.msg);
                            checkWorkerStatus()
                        }
                    },
                    error: function () {
                        var ERR_MESSAGE = "提示：操作失败，请稍后重试!";
                        alert(ERR_MESSAGE);
                    }

                });
            }
            return false;

        });

    })(jQuery)

</script>
</body>
</html>
