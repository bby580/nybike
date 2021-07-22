let this_url=window.location.origin+"/nybike/realtime";
console.log(this_url)
//获取信息失败时调用
function connectError(chart){
    chart.hideLoading();
    alert("获取数据失败！")
}
//*************要求6********************************************
let activetop = echarts.init(document.getElementById('active'));
function getTopData(){
    let hour=topMinInput.value;
    let pai=paihang.value;
    let paiurl=this_url+"/list?what=iotop&hour="+hour+"&top="+pai;
    var stationid=[];
    var top_in=[];
    var top_out=[];
    console.log(paiurl)
    activetop.showLoading();
    $.ajax({url:paiurl,success:function(result){
        let tops=result;
        console.log(result)
        for(let i in tops){
            let topp=tops[i];
            stationid.push(topp.station_id);
            top_in.push(topp.in);
            top_out.push(topp.out);
        }
        option6 = {
            title: {text: '活跃度排行（左至右递减）'},
            tooltip: {trigger: 'axis'},
            legend: {data: ['流入车辆', '流出车辆']},
            toolbox: {
                show: true,
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    data: stationid
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '输入车辆',
                    type: 'bar',
                    data: top_in,
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                },
                {
                    name: '输出车辆',
                    type: 'bar',
                    data: top_out,
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };
        activetop.hideLoading();
        activetop.setOption(option6);
    },error:function (e){
            console.log(e)
        connectError(activetop)}});
}

//**********要求5***************************************************
function getNobikeData() {
    let min=minInput.value;
    let minurl=this_url+"/list?what=nobike&minute="+min;

    $.ajax({url:minurl,success:function(result){
            let str='';
            console.log(result)
            for(let i=0;i<result.length/10;i++){
                str+='<tr>';
                str += `<td>${i+1}</td>`;
                for(let j=0;j<10&&i*10+j<result.length;j++){
                    str+=`<td>${result[i*10+j]}</td>`;
                }
                str+='</tr>';
            }
            nobike_tb.innerHTML = str;
        },error:function (e){connectError()}});
}
//**********要求3、4*************************************************
var noserverUrl="https://gbfs.citibikenyc.com/gbfs/en/station_status.json";
function realtime(selec){
    $.ajax({url:noserverUrl,success:function(result){
        var stations=result.data.stations;
        var nobike=[];
        for(var index in stations){
            var station=stations[index];
            if(selec=='3'){
                var server=station.station_status;
                if(server=='out_of_service'){
                    console.log(station.station_id);
                    nobike.push(station.station_id);
                }
            }
            else
            {
                var station_id=station.station_id;
                if(station_id==realtimeSidInput.value){
                    var newtime=station.last_reported*1000;
                    var timestr=new Date(newtime);
                    console.log(station);
                    var str='<tr>';
                    str+=`<td>${station.station_id}</td>`;
                    str+=`<td>${station.station_status}</td>`;
                    str+=`<td>${station.num_bikes_disabled}</td>`;
                    str+=`<td>${station.num_ebikes_available}</td>`;
                    str+=`<td>${station.num_bikes_available}</td>`;
                    str+=`<td>${timestr}</td>`;
                    str+='</tr>';
                    tb3.innerHTML = str;
                }
            }
        }
        if(selec=='3'){
            var str='';
            for(var i=0;i<nobike.length/10;i++){
                str+='<tr>';
                str += `<td>${i+1}</td>`;
                for(var j=0;j<10&&i*10+j<nobike.length;j++){
                    str+=`<td>${nobike[i*10+j]}</td>`;
                }
                str+='</tr>';
            }
            tb2.innerHTML = str;
        }
    },error:function (e){connectError()}});
}
// *******要求1、2***************************************************************
let chat_nba = echarts.init(document.getElementById('chartNba'));
let chat_nda = echarts.init(document.getElementById('chartNda'));
let option_nba = {
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
let option_nda=JSON.parse(JSON.stringify(option_nba));
function getNumTimeData(sel) {
    let sid=72;
    let hour=1;
    let option={};
    let chat;
    if (sel==='nba') {
        sid=nbaSidInput.value;
        hour=nbaHourInput.value;
        chat=chat_nba;
        option = option_nba;
    } else {
        sid=ndaSidInput.value;
        hour=ndaHourInput.value;
        chat=chat_nda;
        option = option_nda;
    }
    console.log(sid,hour);
    let url=this_url+"/num_available?what="+sel+"&sid="+sid+"&hour="+hour;
    chat.showLoading();
    $.ajax({url:url,success:function(result){
        console.log("结果：",result)
        //收到相应数据
        let xData=result.xData;
        for (let i in xData) xData[i]=xData[i].split(" ")[1].substr(0,5);
        option.xAxis.data=xData;
        option.series[0].data=result.yData;
        // console.log('收到相应数据:',xData,result.yData);
        chat.hideLoading();
        chat.setOption(option);
    },error:function (e){connectError(chat)}});
}