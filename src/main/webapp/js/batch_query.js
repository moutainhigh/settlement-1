





function searchByParam(o) {
    var p = getParam();
    if (o){
        p.currentPage = $(o).attr("num");
    }
    $.ajax({
        url:'/settlement/record/batchQueryPage',
        type:'post',
        data:p,
        success:function (data) {
            $('#batchList').html('');
            var list = data.voList;
            for(var i in list){
                if (list[i].returnCode == '0000'){
                    $('#batchList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].userName+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accBank+'</td><td>'+list[i].account1+'</td><td>'+list[i].account2+'</td><td>'+list[i].status+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td></tr>');
                }else {
                    $('#batchList').append('<tr height="45"><td>'+list[i].user+'</td><td>'+list[i].userName+'</td><td class="gap"><em>'+list[i].id.substring(0,6)+'</em><em>'+list[i].id.substring(6,14)+'</em><em>'+list[i].id.substring(14)+'</em></td><td class="gap"><em>'+list[i].accNum.substring(0,4)+'</em><em>'+list[i].accNum.substring(4,8)+'</em><em>'+list[i].accNum.substring(8,12)+'</em><em>'+list[i].accNum.substring(12)+'</em></td><td>'+list[i].accBank+'</td><td>'+list[i].account1+'</td><td>'+list[i].account2+'</td><td>'+list[i].status+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td></tr>');
                }
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


function getParam() {
    var p = {
        currentPage:$('a.current').attr("num"),
        bRegName:$('#bRegName').val(),
        eRegName:$('#eRegName').val()
    };
    return p;
}