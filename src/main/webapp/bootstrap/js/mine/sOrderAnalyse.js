$(function () {
    //以年份为单位的个人订单统计柱状图
    var sOrderYearAChart = echarts.init(document.getElementById("year"));

    var yearOption = {
        title:{
            text:'各年订单分析'
        },
        tooltip:{},
        legend:{
            data:['订单量']
        },
        xAxis:{
            data:[]
        },
        yAxis:{},
        series:[{
            name: '订单量',
            type: 'bar',
            data: []
        }]
    };
    sOrderYearAChart.setOption(yearOption);

    $.ajax({
        url:"/sOrderYearA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var yAxis = [];
            $.each(data, function (index) {
                    xAxis.push(data[index].year);
                    yAxis.push(data[index].payedOrder);
            });
            sOrderYearAChart.setOption({
                xAxis:{
                    data:xAxis
                },
                series:[{
                    name: '订单量',
                    type: 'bar',
                    data: yAxis
                }]
            })
        }
    });

//    以季度为单位的个人订单统计柱状图
    var sOrderSeasonChart = echarts.init(document.getElementById("season"));
    var seasonOption = {
        title:{
            text:'各季度订单分析'
        },
        tooltip:{},
        legend:{
            data:['订单量']
        },
        xAxis:{
            data:[]
        },
        yAxis:{},
        series:[{
            name: '订单量',
            type: 'bar',
            data: []
        }]
    };
    sOrderSeasonChart.setOption(seasonOption);
    $.ajax({
        url:"/sOrderSeasonA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var yAxis = [];
            $.each(data, function (index) {
                var season = "";
                if((data[index].season % 4) == 0){
                    season = (parseInt(data[index].season / 4) + 1999) + "-" + "4";
                }else {
                    season = (parseInt(data[index].season / 4) + 2000) + "-" + data[index].season % 4
                }
                xAxis.push(season);
                yAxis.push(data[index].payedOrder);
            });
            sOrderSeasonChart.setOption({
                xAxis:{
                    data:xAxis
                },
                series:[{
                    name: '订单量',
                    type: 'bar',
                    data: yAxis
                }]
            })
        }
    });

//    以月为单位的学生个人订单统计柱状图
    var sOrderMonthChart = echarts.init(document.getElementById("month"));
    var monthOption = {
        title:{
            text:'各月份订单分析'
        },
        tooltip:{},
        legend:{
            data:['订单量']
        },
        xAxis:{
            data:[]
        },
        yAxis:{},
        series:[{
            name: '订单量',
            type: 'bar',
            data: []
        }]
    };
    sOrderMonthChart.setOption(monthOption);
    $.ajax({
        url:"/sOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var yAxis = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                yAxis.push(data[index].payedOrder);
            });
            sOrderMonthChart.setOption({
                xAxis:{
                    data:xAxis
                },
                series:[{
                    name: '订单量',
                    type: 'bar',
                    data: yAxis
                }]
            })
        }
    });
});