$(function () {
    $("#li1").attr("onclick", "click1()");
    $("#li1_1").attr("onclick", "click1_1()");
    $("#li1_2").attr("onclick", "click1_2()");
    $("#li1_3").attr("onclick", "click1_3()");
    $("#li2").attr("onclick", "click2()");
    $("#li2_1").attr("onclick", "click2_1()");
    $("#li2_2").attr("onclick", "click2_2()");
    $("#li2_3").attr("onclick", "click2_3()");
    $("#li3").attr("onclick", "click3()");
    $("#li3_1").attr("onclick", "click3_1()");
    $("#li3_2").attr("onclick", "click3_2()");
    $("#li3_3").attr("onclick", "click3_3()");
    $("#li4").attr("onclick", "click4()");
    $("#li4_1").attr("onclick", "click4_1()");
    $("#li4_2").attr("onclick", "click4_2()");
    $("#li4_3").attr("onclick", "click4_3()");
    $("#li5").attr("onclick", "click5()");
    $("#li6").attr("onclick", "click6()");
    $("#li7").attr("onclick", "click7()");
    $("#li8").attr("onclick", "click8()");
    $("#li9").attr("onclick", "click9()");
    $("#li9_1").attr("onclick", "click9_1()");
    $("#li9_2").attr("onclick", "click9_2()");
    $("#li9_3").attr("onclick", "click9_3()");
    init();
});
//初始化界面内容有第一个统计图和总体统计数据
function init() {
    $.ajax({
        url:"/iGetIOrderA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            if(data != null){
                $("#totalPrice").text(data.totalPrice);
                $("#totalStudent").text(data.studentAmount);
                $("#loyalty").text((data.allOrder / data.studentAmount).toFixed(2));
            }
        }
    });
    click1_1();
}

//点击订单总数
function click1() {
    if($("#li1").attr("class") == "treeview"){
        remove();
        $("#li1").attr("class", "active treeview");
    }else{
        $("#li1").attr("class", "treeview");
    }
}
//点击订单总数年
function click1_1() {
    $.ajax({
        url:"/iGetOrderYearA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var yAxis = [];
            var broken = [];
            var payed = [];
            $.each(data, function (index) {
                yAxis.push(data[index].year);
                broken.push(data[index].brokenOrder);
                payed.push(data[index].payedOrder);
            });
            chart1_show(yAxis, broken, payed);
        }
    });
}

//点击订单总数季度
function click1_2() {
    $.ajax({
        url:"/iGetOrderSeasonA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var yAxis = [];
            var broken = [];
            var payed = [];
            $.each(data, function (index) {
                var season = "";
                if((data[index].season % 4) == 0){
                    season = (parseInt(data[index].season / 4) + 1999) + "-" + "4";
                }else {
                    season = (parseInt(data[index].season / 4) + 2000) + "-" + data[index].season % 4
                }
                yAxis.push(season);
                broken.push(data[index].brokenOrder);
                payed.push(data[index].payedOrder);
            });
            chart1_show(yAxis, broken, payed);
        }
    });
}

//点击订单总数月份
function click1_3() {
    $.ajax({
        url:"/iGetOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var yAxis = [];
            var broken = [];
            var payed = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                yAxis.push(month);
                broken.push(data[index].brokenOrder);
                payed.push(data[index].payedOrder);
            });
            chart1_show(yAxis, broken, payed);
        }
    });
}

//订单总数统计表
function chart1_show(yAxis, broken, payed) {
    var charts = echarts.init(document.getElementById("charts"));
    charts.showLoading();
    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['退单量', '成交量']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: yAxis
        },
        series: [
            {
                name: '退单量',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: broken
            },
            {
                name: '成交量',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: payed
            }
        ]
    };
    charts.setOption(option);
    charts.hideLoading();
}

//点击订单总额年度
function click2_1() {
    $.ajax({
        url:"/iGetOrderYearA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var totalPrice = [];
            $.each(data, function (index) {
                xAxis.push(data[index].year);
                totalPrice.push(data[index].totalPrice);
            });
            chart2_show("成交总额","年度",xAxis, totalPrice);
        }
    });
}

//点击订单总额季度
function click2_2() {
    $.ajax({
        url:"/iGetOrderSeasonA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var totalPrice = [];
            $.each(data, function (index) {
                var season = "";
                if((data[index].season % 4) == 0){
                    season = (parseInt(data[index].season / 4) + 1999) + "-" + "4";
                }else {
                    season = (parseInt(data[index].season / 4) + 2000) + "-" + data[index].season % 4
                }
                xAxis.push(season);
                totalPrice.push(data[index].totalPrice);
            });
            chart2_show("成交总额","季度", xAxis, totalPrice);
        }
    });
}

//点击订单总额月份
function click2_3() {
    $.ajax({
        url:"/iGetOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var totalPrice = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                totalPrice.push(data[index].totalPrice);
            });
            chart2_show("成交总额","月份", xAxis, totalPrice);
        }
    });
}

//总成交率年度
function click3_1() {
    $.ajax({
        url:"/iGetOrderYearA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var closeRatio = [];
            $.each(data, function (index) {
                xAxis.push(data[index].year);
                closeRatio.push((data[index].payedOrder / data[index].allOrder).toFixed(2));
            });
            chart2_show("总成交率","年度",xAxis, closeRatio);
        }
    });
}
//总成交率季度
function click3_2() {
    $.ajax({
        url:"/iGetOrderSeasonA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var closeRatio = [];
            $.each(data, function (index) {
                var season = "";
                if((data[index].season % 4) == 0){
                    season = (parseInt(data[index].season / 4) + 1999) + "-" + "4";
                }else {
                    season = (parseInt(data[index].season / 4) + 2000) + "-" + data[index].season % 4
                }
                xAxis.push(season);
                closeRatio.push((data[index].payedOrder / data[index].allOrder).toFixed(2));
            });
            chart2_show("总成交率","季度", xAxis, closeRatio);
        }
    });
}
//总成交率月份
function click3_3() {
    $.ajax({
        url:"/iGetOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var closeRatio = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                closeRatio.push((data[index].payedOrder / data[index].allOrder).toFixed(2));
            });
            chart2_show("总成交率","月份", xAxis, closeRatio);
        }
    });
}

//平均订单单价年度
function click9_1() {
    $.ajax({
        url:"/iGetOrderYearA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var averagePrice = [];
            $.each(data, function (index) {
                xAxis.push(data[index].year);
                averagePrice.push((data[index].totalPrice / data[index].payedOrder).toFixed(2));
            });
            chart2_show("平均订单单价","年度",xAxis, averagePrice);
        }
    });
}
//平均订单单价季度
function click9_2() {
    $.ajax({
        url:"/iGetOrderSeasonA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var averagePrice = [];
            $.each(data, function (index) {
                var season = "";
                if((data[index].season % 4) == 0){
                    season = (parseInt(data[index].season / 4) + 1999) + "-" + "4";
                }else {
                    season = (parseInt(data[index].season / 4) + 2000) + "-" + data[index].season % 4
                }
                xAxis.push(season);
                averagePrice.push((data[index].totalPrice / data[index].payedOrder).toFixed(2));
            });
            chart2_show("平均订单单价","季度", xAxis, averagePrice);
        }
    });
}
//平均订单单价月份
function click9_3() {
    $.ajax({
        url:"/iGetOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var averagePrice = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                averagePrice.push((data[index].totalPrice / data[index].payedOrder).toFixed(2));
            });
            chart2_show("平均订单单价","月份", xAxis, averagePrice);
        }
    });
}

//展示成交总额的图标
function chart2_show(name, text, xAxis, totalPrice) {
    var charts = echarts.init(document.getElementById("charts"));
    charts.showLoading();

    var option = {
        title: {
            text: name,
            subtext: text
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:[name]
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: xAxis
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name:name,
                type:'line',
                data:totalPrice,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:"",
                type:'line',
                data:[],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }

        ]
    };
    charts.setOption(option);
    charts.hideLoading();

}

//购买方式比例年度
function click4_1() {
    $.ajax({
        url:"/iGetOrderYearA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var onlineRatio = [];
            var offlineRatio = [];
            $.each(data, function (index) {
                xAxis.push(data[index].year);
                onlineRatio.push((data[index].payedOnline / data[index].allOrder).toFixed(2));
                offlineRatio.push((data[index].payedOffline / data[index].allOrder).toFixed(2));
            });
            chart3_show("购买方式比例","年度",xAxis, onlineRatio, offlineRatio);
        }
    });
}
//购买方式比例季度
function click4_2() {
    $.ajax({
        url:"/iGetOrderSeasonA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var onlineRatio = [];
            var offlineRatio = [];
            $.each(data, function (index) {
                var season = "";
                if((data[index].season % 4) == 0){
                    season = (parseInt(data[index].season / 4) + 1999) + "-" + "4";
                }else {
                    season = (parseInt(data[index].season / 4) + 2000) + "-" + data[index].season % 4
                }
                xAxis.push(season);
                onlineRatio.push((data[index].payedOnline / data[index].allOrder).toFixed(2));
                offlineRatio.push((data[index].payedOffline / data[index].allOrder).toFixed(2));
            });
            chart3_show("购买方式比例", "季度", xAxis, onlineRatio, offlineRatio);
        }
    });
}

//购买方式比例月份
function click4_3() {
    $.ajax({
        url:"/iGetOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var onlineRatio = [];
            var offlineRatio = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                onlineRatio.push((data[index].payedOnline / data[index].allOrder).toFixed(2));
                offlineRatio.push((data[index].payedOffline / data[index].allOrder).toFixed(2));
            });
            chart3_show("购买方式比例", "月份", xAxis, onlineRatio, offlineRatio);
        }
    });
}

//展示线上下交易比例图表
function chart3_show(name, text, xAxis, onlineRatio, offlineRatio) {
    var charts = echarts.init(document.getElementById("charts"));
    charts.showLoading();

    var option = {
        title: {
            text: name,
            subtext: text
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['线上交易比例','线下交易比例']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: xAxis
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name:'线上交易比例',
                type:'line',
                data:onlineRatio,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'线下交易比例',
                type:'line',
                data:offlineRatio,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };

    charts.setOption(option);
    charts.hideLoading();
}


function click2() {
    if($("#li2").attr("class") == "treeview"){
        remove();
        $("#li2").attr("class", "active treeview");
    }else{
        $("#li2").attr("class", "treeview");
    }
}

function click3() {
    if($("#li3").attr("class") == "treeview"){
        remove();
        $("#li3").attr("class", "active treeview");
    }else{
        $("#li3").attr("class", "treeview");
    }
}

function click4() {
    if($("#li4").attr("class") == "treeview"){
        remove();
        $("#li4").attr("class", "active treeview");
    }else{
        $("#li4").attr("class", "treeview");
    }
}

function click5() {
    remove();
    $("#li5").attr("class", "active");
}

function click6() {
    remove();
    $("#li6").attr("class", "active");
}

function click7() {
    remove();
    $("#li7").attr("class", "active");
}

function click8() {
    remove();
    $("#li8").attr("class", "active");
}

function click9() {
    if($("#li9").attr("class") == "treeview"){
        remove();
        $("#li9").attr("class", "active treeview");
    }else{
        $("#li9").attr("class", "treeview");
    }
}


function remove() {
    $("#li1, #li2, #li3, #li4, #li9").attr("class", "treeview");
    $("#li5, #li6, #li7, #li8").attr("class", "");
}