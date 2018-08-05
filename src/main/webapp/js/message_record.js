
$(function () {
    /*标签切换*/
    $('#tabs_ul li').click(function () {
        $(this).siblings().removeClass('cont');
        $(this).addClass('cont');
        var num = $(this).attr("num");
        if (num == "1"){
            /*账户报文*/
            $('#body1').show();
            $('#body2').hide();
            $('#body3').hide();
            $('#body4').hide();
            $('#body5').hide();
            searchByParam();
        }else if (num == "2"){
            /*投标报文*/
            $('#body1').hide();
            $('#body2').show();
            $('#body3').hide();
            $('#body4').hide();
            $('#body5').hide();
            searchByParam2();
        }else if (num == "3"){
            /*标的报文*/
            $('#body1').hide();
            $('#body2').hide();
            $('#body3').show();
            $('#body4').hide();
            $('#body5').hide();
            $('#recordList').html('');
        }else if (num == "4"){
            /*提现报文*/
            $('#body1').hide();
            $('#body2').hide();
            $('#body3').hide();
            $('#body4').show();
            $('#body5').hide();
            searchByParam4();
        }else if (num == "5"){
            /*代扣报文*/
            $('#body1').hide();
            $('#body2').hide();
            $('#body3').hide();
            $('#body4').hide();
            $('#body5').show();
            searchByParam5();
        }
    });
});

/*
账户报文列表方法
 */
function searchByParam(o) {
    var p = getParam();
    if (o){
        p.currentPage = $(o).attr("num");
    }
    $.ajax({
        url:'/settlement/record/records001Page',
        type:'post',
        data:p,
        success:function (data) {
            $('#recordList').html('');
            var list = data.voList;
            for(var i in list){
                var t = Date.parse(list[i].reqTime);
                console.log(t);
                console.log(new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm"));
                console.log(new Date(t).format("yyyy-MM-dd hh:mm"));
                // if (list[i].returnCode == '0000'){
                    if(list[i].returnTime == null){
                        $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].idType+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td>'+list[i].userName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+new Date(t).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td></td><td>'+list[i].operationCode+'</td></tr>');
                    }else{
                        $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].idType+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td>'+list[i].userName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+new Date(t).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td>'+new Date(list[i].returnTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].operationCode+'</td></tr>');
                    }
                // }else {
                //     $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].idType+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td>'+list[i].userName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td>'+new Date(list[i].returnTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].operationCode+'</td></tr>');
                // }
            }

            $('#operNum').html('');
            $('#operNum').append('<a class="h_bg_width" href="#" num="1" onclick="searchByParam(this)">首页</a>');
            $('#operNum').append('<a href="#" num="'+parseInt(parseInt(data.currentPage)-1)+'" onclick="searchByParam(this)"><i class="h_icon_prev"></i></a>');
            for (var i=parseInt(data.pageIndex.startIndex);i<=parseInt(data.pageIndex.endIndex);i++){
                if(parseInt(data.currentPage) == i){
                    $('#operNum').append('<a href="#" onclick="searchByParam(this)" class="current" num="'+i+'">'+i+'</a>');
                }else{
                    $('#operNum').append('<a href="#" onclick="searchByParam(this)" num="'+i+'">'+i+'</a>');
                }
            }
            $('#operNum').append('<a href="#" onclick="searchByParam(this)" num="'+parseInt(parseInt(data.currentPage)+1)+'"><i class="h_icon_next"></i></a>');
            $('#operNum').append('<a class="h_bg_width" href="#" onclick="searchByParam(this)" num="'+parseInt(data.pageCount)+'">末页</a>');
        }
    });
}

/*投标报文列表*/
function searchByParam2(o) {
    var p = getParam();
    if (o){
        p.currentPage = $(o).attr("num");
    }
    $.ajax({
        url:'/settlement/record/records002Page',
        type:'post',
        data:p,
        success:function (data) {
            $('#recordList').html('');
            var list = data.voList;
            for(var i in list){
                if (list[i].returnTime == null){
                    $('#recordList').append('<tr height="45"><td>'+list[i].borrower+'</td><td>'+list[i].borrowerId+'</td><td>'+list[i].borrowerName+'</td><td>'+list[i].userName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td></td><td>'+list[i].operationCode+'</td></tr>');
                }else {
                    $('#recordList').append('<tr height="45"><td>'+list[i].borrower+'</td><td>'+list[i].borrowerId+'</td><td>'+list[i].borrowerName+'</td><td>'+list[i].userName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td>'+new Date(list[i].returnTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].operationCode+'</td></tr>');
                }
            }

            $('#operNum').html('');
            $('#operNum').append('<a class="h_bg_width" href="#" num="1" onclick="searchByParam2(this)">首页</a>');
            $('#operNum').append('<a href="#" num="'+parseInt(parseInt(data.currentPage)-1)+'" onclick="searchByParam2(this)"><i class="h_icon_prev"></i></a>');
            for (var i=parseInt(data.pageIndex.startIndex);i<=parseInt(data.pageIndex.endIndex);i++){
                if(parseInt(data.currentPage) == i){
                    $('#operNum').append('<a href="#" onclick="searchByParam2(this)" class="current" num="'+i+'">'+i+'</a>');
                }else{
                    $('#operNum').append('<a href="#" onclick="searchByParam2(this)" num="'+i+'">'+i+'</a>');
                }
            }
            $('#operNum').append('<a href="#" onclick="searchByParam2(this)" num="'+parseInt(parseInt(data.currentPage)+1)+'"><i class="h_icon_next"></i></a>');
            $('#operNum').append('<a class="h_bg_width" href="#" onclick="searchByParam2(this)" num="'+parseInt(data.pageCount)+'">末页</a>');
        }
    });
}

/*提现报文列表*/
function searchByParam4(o) {
    var p = getParam();
    if (o){
        p.currentPage = $(o).attr("num");
    }
    $.ajax({
        url:'/settlement/record/records006Page',
        type:'post',
        data:p,
        success:function (data) {
            $('#recordList').html('');
            var list = data.voList;
            for(var i in list){
                var t = Date.parse(list[i].reqTime);
                if (list[i].returnTime == null){
                    $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].accName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+list[i].amount+'</td><td>'+list[i].fee+'</td><td>'+new Date(t).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td></td><td>'+list[i].operationCode+'</td></tr>');
                }else {
                    $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].accName+'</td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+list[i].amount+'</td><td>'+list[i].fee+'</td><td>'+new Date(t).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td>'+new Date(list[i].returnTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].operationCode+'</td></tr>');
                }
            }

            $('#operNum').html('');
            $('#operNum').append('<a class="h_bg_width" href="#" num="1" onclick="searchByParam4(this)">首页</a>');
            $('#operNum').append('<a href="#" num="'+parseInt(parseInt(data.currentPage)-1)+'" onclick="searchByParam4(this)"><i class="h_icon_prev"></i></a>');
            for (var i=parseInt(data.pageIndex.startIndex);i<=parseInt(data.pageIndex.endIndex);i++){
                if(parseInt(data.currentPage) == i){
                    $('#operNum').append('<a href="#" onclick="searchByParam4(this)" class="current" num="'+i+'">'+i+'</a>');
                }else{
                    $('#operNum').append('<a href="#" onclick="searchByParam4(this)" num="'+i+'">'+i+'</a>');
                }
            }
            $('#operNum').append('<a href="#" onclick="searchByParam4(this)" num="'+parseInt(parseInt(data.currentPage)+1)+'"><i class="h_icon_next"></i></a>');
            $('#operNum').append('<a class="h_bg_width" href="#" onclick="searchByParam4(this)" num="'+parseInt(data.pageCount)+'">末页</a>');
        }
    });
}

/*代扣报文列表*/
function searchByParam5(o) {
    var p = getParam();
    if (o){
        p.currentPage = $(o).attr("num");
    }
    $.ajax({
        url:'/settlement/record/records008Page',
        type:'post',
        data:p,
        success:function (data) {
            $('#recordList').html('');
            var list = data.voList;
            for(var i in list){
                var t = Date.parse(list[i].reqTime);
                if (list[i].returnTime == null){
                    $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].accName+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+list[i].amount+'</td><td>'+new Date(t).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td></td><td>'+list[i].operationCode+'</td></tr>');
                }else {
                    $('#recordList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].accName+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td>'+list[i].accBank+'</td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accType+'</td><td>'+list[i].amount+'</td><td>'+new Date(t).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].returnCode+'</td><td>'+list[i].returnMsg+'</td><td>'+new Date(list[i].returnTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].operationCode+'</td></tr>');
                }
            }

            $('#operNum').html('');
            $('#operNum').append('<a class="h_bg_width" href="#" num="1" onclick="searchByParam5(this)">首页</a>');
            $('#operNum').append('<a href="#" num="'+parseInt(parseInt(data.currentPage)-1)+'" onclick="searchByParam5(this)"><i class="h_icon_prev"></i></a>');
            for (var i=parseInt(data.pageIndex.startIndex);i<=parseInt(data.pageIndex.endIndex);i++){
                if(parseInt(data.currentPage) == i){
                    $('#operNum').append('<a href="#" onclick="searchByParam5(this)" class="current" num="'+i+'">'+i+'</a>');
                }else{
                    $('#operNum').append('<a href="#" onclick="searchByParam5(this)" num="'+i+'">'+i+'</a>');
                }
            }
            $('#operNum').append('<a href="#" onclick="searchByParam5(this)" num="'+parseInt(parseInt(data.currentPage)+1)+'"><i class="h_icon_next"></i></a>');
            $('#operNum').append('<a class="h_bg_width" href="#" onclick="searchByParam5(this)" num="'+parseInt(data.pageCount)+'">末页</a>');
        }
    });
}


/*获取参数*/
function getParam() {
    var p = {
        currentPage:$('a.current').attr("num")
    };
    return p;
}