
$(function () {
    click_1();
    $("#li1").attr("onclick","click_1()");
    $("#li2").attr("onclick","click_2()");
    $("#li3").attr("onclick","click_3()");
    $("#li4").attr("onclick","click_4()");
    $("#li5").attr("onclick","click_5()");
    $("#li6").attr("onclick","click_6()");
    $("#li7").attr("onclick","click_7()");
    $("#li8").attr("onclick","click_8()");
    $("#li9").attr("onclick","click_9()");
    $("#li10").attr("onclick","click_10()");
    $("#li11").attr("onclick","click_11()");
});

function click_1() {
    var charts = echarts.init(document.getElementById("charts"));
    charts.clear();
    var html = '<div class="row" style="margin-top: 100px">' +
        '<div class="col-lg-3 col-xs-6">'+
        '<div class="small-box bg-aqua">'+
        '<div class="inner">'+
        '<h3 id="num1">0</h3>'+
        '<p>平台总成交额</p>'+
        '</div>'+
        '<a class="small-box-footer">'+
        '</a>'+
        '</div>'+
        '</div>'+
        '<div class="col-lg-3 col-xs-6">'+
        '<div class="small-box bg-green">'+
        '<div class="inner">'+
        '<h3 id="num2">0</h3>'+
        '<p>平台总成交订单数</p>'+
        '</div>'+
        '<a class="small-box-footer">'+
        '</a>'+
        '</div>'+
        '</div>'+
        '<div class="col-lg-3 col-xs-6">'+
        '<div class="small-box bg-yellow">'+
        '<div class="inner">'+
        '<h3 id="num3">0</h3>'+
        '<p>平台总机构数</p>'+
        '</div>'+
        '<a class="small-box-footer">'+
        '</a>'+
        '</div>'+
        '</div>'+
        '<div class="col-lg-3 col-xs-6">'+
        '<div class="small-box bg-red">'+
        '<div class="inner">'+
        '<h3 id="num4">0</h3>'+
        '<p>平台总学员数</p>'+
        '</div>'+
        '<a class="small-box-footer">'+
        '</a>'+
        '</div>'+
        '</div>'+
        '</div>';
    document.getElementById("content").innerHTML = html;
    $.ajax({
        url:"/mGetOrderA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var totalInstitution  = data.totalInstitution;
            var totalMoney = data.totalMoney;
            var totalOrders = data.totalOrders;
            var totalStudents = data.totalStudents;
            $("#num1").text(totalMoney);
            $("#num2").text(totalOrders);
            $("#num3").text(totalInstitution);
            $("#num4").text(totalStudents);
        }
    });
}

function click_2() {
    $.ajax({
        url:"/mGetMAreaA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var data1 = [];
            $.each(data, function (index) {
                var object = {};
                object["name"] = data[index].province;
                object["value"] = data[index].totalMoney;
                data1.push(object);
            });
            drawArea(data1, "各省份成交额分布图");
        }
    });
}

function click_3() {
    $.ajax({
        url:"/mGetMAreaA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var data1 = [];
            $.each(data, function (index) {
                var object = {};
                object["name"] = data[index].province;
                object["value"] = data[index].totalOrders;
                data1.push(object);
            });
            drawArea(data1, "各省份机构成交订单数分布图");
        }
    });
}

function click_4() {
    $.ajax({
        url:"/mGetMAreaA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var data1 = [];
            $.each(data, function (index) {
                var object = {};
                object["name"] = data[index].province;
                object["value"] = data[index].totalInstitutions;
                data1.push(object);
            });
            drawArea(data1, "各省份机构数量分布图");
        }
    });
}

function click_5() {
    $.ajax({
        url:"/mGetMTypeA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var data1 = [];
            $.each(data, function (index) {
                var object = {};
                object["name"] = data[index].type;
                object["value"] = data[index].totalMoney;
                data1.push(object);
            });
            pie_show("平台各类型课程成交总金额分布饼图", data1);
        }
    });
}

function click_6() {
    $.ajax({
        url:"/mGetMTypeA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var data1 = [];
            $.each(data, function (index) {
                var object = {};
                object["name"] = data[index].type;
                object["value"] = data[index].totalOrders;
                data1.push(object);
            });
            pie_show("平台各类型课程成交总订单数分布饼图", data1);
        }
    });
}

function click_7() {
    $.ajax({
        url:"/mGetMOrderMonthA",
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
                totalPrice.push((data[index].totalMoney));
            });
            line_show("月度平台成交额分析统计图","月份", xAxis, totalPrice);
        }
    });
}

function click_8() {
    $.ajax({
        url:"/mGetMOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var newStudentsList = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                newStudentsList.push((data[index].newStudents));
            });
            line_show("月度平台新增学员数分析统计图","月份", xAxis, newStudentsList);
        }
    });
}

function click_9() {
    $.ajax({
        url:"/mGetMOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var newInstitutionlist = [];
            $.each(data, function (index) {
                var month = "";
                if((data[index].month % 12) == 0){
                    month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                }else {
                    month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                }
                xAxis.push(month);
                newInstitutionlist.push((data[index].newInstitutions));
            });
            line_show("月度平台新增机构数分析统计图","月份", xAxis, newInstitutionlist);
        }
    });
}

function click_10() {
    $.ajax({
        url:"/mGetMOrderMonthA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var xAxis = [];
            var saleGrowthRate = [];
            $.each(data, function (index) {
                if(index >= 1){
                    var month = "";
                    if((data[index].month % 12) == 0){
                        month = (parseInt(data[index].month / 12) + 1999) + "-" + "12";
                    }else {
                        month = (parseInt(data[index].month / 12) + 2000) + "-" + data[index].month % 12
                    }
                    xAxis.push(month);
                    if(data[index - 1].totalMoney == 0){
                        saleGrowthRate.push(0);
                    }else {
                        saleGrowthRate.push(((data[index].totalMoney / data[index - 1].totalMoney - 1 ) * 100).toFixed(2));
                    }
                }
            });
            line_show("月度平台销售额增长率统计图","月份", xAxis, saleGrowthRate);
        }
    });
}

function click_11() {
    var html =
        '<div class="box">' +
        '<div class="box-header">' +
        '<h3 class="box-title">机构数据统计表</h3>' +
        '</div>' +
        '<div class="box-body table-responsive no-padding">'+
        '<table class="table table-hover" id="table_id">' +
        '</table>'+
        '</div>' +
        '</div>';

    document.getElementById("content").innerHTML = html;

    $.ajax({
        url:"/mGetAllInstitutionA",
        contentType: "application/json;charset=UTF-8",
        type:"get",
        dataType:"json",
        success:function (data) {
            var institutionslist  = data.institutions;
            var institutionAslist = data.institutionAs;
            var html = '<tr><th>机构代码</th><th>名称</th><th>省份</th><th>电话</th><th>总成交额</th><th>总学员数</th><th>总订单数</th></tr>';
            for(var i = 0; i < institutionslist.length; i++){
                html += '<tr><td>' + institutionslist[i].code+
                    '</td><td>' + institutionslist[i].name +
                    '</td><td>' + institutionslist[i].address +
                    '</td><td>' + institutionslist[i].phone +
                    '</td><td>' + institutionAslist[i].totalPrice +
                    '</td><td>' + institutionAslist[i].studentAmount+
                    '</td><td>' + institutionAslist[i].payedOrder;
                html += '</td></tr>';
            }
            console.log(html);
            document.getElementById("table_id").innerHTML = html;
        }
    });

    var charts = echarts.init(document.getElementById("charts"));
    charts.clear();
}


function line_show(name, text, xAxis, totalPrice) {
    document.getElementById("content").innerHTML = "";
    var charts = echarts.init(document.getElementById("charts"));
    charts.clear();
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

function pie_show(name, data) {
    document.getElementById("content").innerHTML = "";
    var charts = echarts.init(document.getElementById("charts"));
    charts.clear();
    charts.showLoading();
    var option = {
        title : {
            text: name,
            subtext: '',
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
                data: data,
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
    charts.setOption(option);
    charts.hideLoading();
}

function drawArea(data1, text) {
    document.getElementById("content").innerHTML = "";
    var dom = document.getElementById("charts");
    var myChart = echarts.init(dom);
    myChart.clear();
    var app = {};
    option = null;
    var geoCoordMap = {
        "北京":[116.39,39.93],
        "天津":[117.21,39.14],
        "河北":[114.52,38.05],
        "山西":[112.55,37.89],
        "内蒙古":[111.66,40.83],
        "吉林":[125.32,43.81],
        "黑龙江":[126.66,45.77],
        "上海":[121.48,31.23],
        "江苏":[118.78,32.06],
        "浙江":[120.21,30.26],
        "安徽":[117.28,31.87],
        "福建":[119.33,26.05],
        "江西":[115.89,28.69],
        "山东":[117.02,36.68],
        "河南":[113.65,34.76],
        "湖北":[114.32,30.58],
        "湖南":[112.98,28.21],
        "广东":[113.3,23.12],
        "广西":[108.29,22.81],
        "海南":[110.33,20.02],
        "重庆":[106.55,29.56],
        "四川":[104.06,30.57],
        "贵州":[106.71,26.63],
        "云南":[102.71,24.88],
        "西藏":[91.11,29.66],
        "陕西":[108.94,34.34],
        "甘肃":[103.82,36.06],
        "青海":[101.77,36.64],
        "宁夏":[106.21,38.50],
        "新疆":[87.56,43.84],

    };

    var convertData = function (data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
                res.push({
                    name: data[i].name,
                    value: geoCoord.concat(data[i].value)
                });
            }
        }
        return res;
    };

    option = {
        backgroundColor: '#f3f3f3',
        title: {
            text: text,
            subtext: '',
            sublink: '',
            x:'center',
            textStyle: {
                color: '#808080'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                return params.name + ' : ' + params.value[2];
            }
        },
        legend: {
            orient: 'vertical',
            y: 'bottom',
            x:'right',
            data:['数量'],
            textStyle: {
                color: '#808080'
            }
        },
        visualMap: {
            min: 0,
            max: 200,
            calculable: true,
            inRange: {
                color: ['#50a3ba', '#eac736', '#d94e5d']
            },
            textStyle: {
                color: '#808080'
            }
        },
        geo: {
            map: 'china',
            label: {
                emphasis: {
                    show: false
                }
            },
            itemStyle: {
                normal: {

                    areaColor: '#056197',
                    borderColor: '#fff'
                },
                emphasis: {
                    areaColor: '#004981'
                }
            }
        },
        series: [
            {
                name: '数量',
                type: 'scatter',
                coordinateSystem: 'geo',
                zoom:2,
                data: convertData(data1),
                symbolSize: 12,
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                itemStyle: {
                    emphasis: {
                        borderColor: '#fff',
                        borderWidth: 1
                    }
                }
            }
        ]
    };
    myChart.on('click', function (params) {
        var city = params.name;
        salaryStats(city);
    });
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}