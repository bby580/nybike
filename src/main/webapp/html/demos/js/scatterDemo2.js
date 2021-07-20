let myChart = echarts.init(document.getElementById('map'));
let selId;
let option;
let data = [];
let center=[-73.99392888,40.76727216];
for (let key in geoCoordMap)
    data.push({name: key,value: 100});
let convertData = function (data) {
    let res = [];
    for (let i = 0; i < data.length; i++) {
        let geoCoord = geoCoordMap[data[i].name];
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
    title: {
        text: 'nybike分布 - 百度地图',
        subtext: '站点分布',
        sublink: 'http://www.pm25.in',
        left: 'center'
    },
    tooltip : {
        trigger: 'item'
    },
    bmap: {
        center: center,
        zoom: 15,
        roam: true,
        // mapStyle: {
        //   styleJson: []
        // }
    },
    series : [
        {
            name: '站点名',
            type: 'scatter',
            coordinateSystem: 'bmap',
            data: convertData(data),
            symbolSize: function (val) {
                return val[2] / 10;
            },
            encode: {
                value: 2
            },
            label: {
                formatter: '{b}',
                position: 'right',
                show: false
            },
            emphasis: {
                label: {
                    show: true
                }
            }
        },
        {
            name: 'Top 5',
            type: 'effectScatter',
            coordinateSystem: 'bmap',
            data: convertData(data.sort(function (a, b) {
                return b.value - a.value;
            }).slice(0, 5)),
            symbolSize: function (val) {
                return val[2] / 10;
            },
            encode: {
                value: 2
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                formatter: '{b}',
                position: 'right',
                show: true
            },
            itemStyle: {
                shadowBlur: 10,
                shadowColor: '#333'
            },
            zlevel: 1
        }
    ]
};

option && myChart.setOption(option);

let statueUrl='https://gbfs.citibikenyc.com/gbfs/en/station_status.json'
myChart.on('click',function (e) {
    let name=e.data.name;
    selId=nameIdMap[name];
    station_name.innerHTML=name;
    station_id.innerHTML=selId;

    $.get(statueUrl,function (result) {
        console.log('收到相应数据:',result)
        let stations=result.data.stations
        let station;
        for (i in stations){
            station = stations[i]
            if (station.station_id===selId) break;
        }
        if (station){
            station_id.innerHTML=station.station_id;
            num_bikes_available.innerHTML=station.num_bikes_available;
            num_bikes_disabled.innerHTML=station.num_bikes_disabled;
            num_docks_available.innerHTML=station.num_docks_available;
            num_docks_disabled.innerHTML=station.num_docks_disabled;
            num_ebikes_available.innerHTML=station.num_ebikes_available;
            station_status.innerHTML=station.station_status;
        }
    })
});
let actionChart = echarts.init(document.getElementById('action_chart'));
let option_chart = {
    title: {text: ''},
    xAxis: {
        type: 'category',
        data: ["20:50", "20:55", "21:00", "21:05", "21:10", "21:15", "21:20", "21:25", "21:30", "21:35", "21:40", "21:45"]
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [11, 12, 10, 9, 11, 12, 12, 12, 10, 11, 12, 11],
        type: 'line',
        smooth: true
    }]
};
function getNumTimeData(sel) {
    let sid=72;
    let hour=1;
    if (sel==='nba') {
        sid=selId;
        hour=hourInputBikes.value;
    }else return;
    if (!sid||!selId){
        alert("您未选择站点！");
        return;
    }
    console.log(sid,hour);
    let url="/nybike/realtime/num_available?what="+sel+"&sid="+sid+"&hour="+hour;
    actionChart.showLoading();
    $.get(url,function (result) {
        actionChart.hideLoading();
        console.log(result)
        //收到相应数据
        let xData=result.xData;
        for (let i in xData) xData[i]=xData[i].split(" ")[1].substr(0,5);
        option_chart.xAxis.data=xData;
        option_chart.series[0].data=result.yData;
        option_chart.title.text=hour+" 小时内站点 "+sid+" 可用车数走势";
        // console.log('收到相应数据:',xData,result.yData);
        actionChart.setOption(option_chart);
    })
}