
bindPageFram("groupInfoBt","html/groupInfo.html");
bindPageFram("homework1Bt","html/homeworks/homework1.html");
bindPageFram("homework2Bt","html/homeworks/homework2.html");
bindPageFram("chartsDemo1Bt","html/demos/chartsDemo1.html");
bindPageFram("demo1Bt","html/demos/demo1.html");
bindPageFram("scatterDemo2Bt","html/demos/scatterDemo2.html");
bindPageFram("scatterDemo1Bt","html/demos/scatterDemo1.html");

function bindPageFram(id,url){
    $("#"+id).click(function () {
        mainContentFr.src=url
    });
}
function bindPage(id,url){
    $("#"+id).click(function () {
        $("#mainContent").load(url);
    });
}