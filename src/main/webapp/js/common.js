/**
 * Created by qianjl on 2016-7-6.
 */
/*
 * 格式化日期
 *
 **/
Date.prototype.format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    };

    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
};


function isDouble(s) {
    if (/^\d*$/.test(s)){
        return true;
    }else if (/^\d+(\.\d{1})?$/.test(s) || /^\d+(\.\d{2})?$/.test(s)) {
        return true;
    }else
        return false;
}

function downloadExc(filePath,fileName){
    var path = filePath+fileName;
    $('#download_path').val(path);
    $('#download_form').attr('action','/settlement/payease/downloadIdCardTem');
    $('#download_form').submit();
}


function showShadow(){
    $("div.shadow_img").css({
        "left": $(document).width() / 2 - $("div.shadow_img").width() / 2,
        "top": ($(document).height() + $(document).scrollTop()) / 2 - $("div.shadow_img").height() / 2
    });
    $("div.shadow_img").show();
}
function hideShadow() {
    $("div.shadow_img").hide();
}
