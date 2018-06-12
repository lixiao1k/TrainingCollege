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


    //用户订单类型统计饼图
    var sOrderTypeACharts = echarts.init(document.getElementById("type"));
    var typeOption = {
        title : {
            text: '用户订单类型统计图',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['文','理','工','商','医']
        },
        series : [
            {
                name: '课程类型',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'文'},
                    {value:310, name:'理'},
                    {value:234, name:'工'},
                    {value:135, name:'商'},
                    {value:1548, name:'医'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    sOrderTypeACharts.setOption(typeOption);

    $.ajax({
        url:"/sOrderTypeA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var typeData = [];
            var type1 = {};
            type1["value"] = data.wenAmount;
            type1["name"] = "文";
            typeData.push(type1);
            var type2 = {};
            type2["value"] = data.liAmount;
            type2["name"] = "理";
            typeData.push(type2);
            var type3 = {};
            type3["value"] = data.gongAmount;
            type3["name"] = "工";
            typeData.push(type3);
            var type4 = {};
            type4["value"] = data.shangAmount;
            type4["name"] = "商";
            typeData.push(type4);
            var type5 = {};
            type5["value"] = data.yiAmount;
            type5["name"] = "医";
            typeData.push(type5);
            typeOption.series[0].data = typeData;
            sOrderTypeACharts.setOption(typeOption);
        }
    });

    var sGradeChart = echarts.init(document.getElementById("grade"));
    var gradeOption = {
        title : {
            text: '用户成绩统计图',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['优秀','良好','及格','不及格']
        },
        series : [
            {
                name: '成绩类型',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'优秀'},
                    {value:310, name:'良好'},
                    {value:234, name:'及格'},
                    {value:135, name:'不及格'},
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    $.ajax({
        url:"/sGradeA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var gradeData = [];
            var grade1 = {};
            grade1["value"] = data.excellent;
            grade1["name"] = "优秀";
            gradeData.push(grade1);
            var grade2 = {};
            grade2["value"] = data.good;
            grade2["name"] = "良好";
            gradeData.push(grade2);
            var grade3 = {};
            grade3["value"] = data.pass;
            grade3["name"] = "及格";
            gradeData.push(grade3);
            var grade4 = {};
            grade4["value"] = data.fail;
            grade4["name"] = "不及格";
            gradeData.push(grade4);
            gradeOption.series[0].data = gradeData;
            sGradeChart.setOption(gradeOption);
        }
    });




});