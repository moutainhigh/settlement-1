$(function(){
    
    /*新增窗口*/
    $('#add_newTransfer').click(function(){
        $('#transfer_shadow').show();
        $('#transfer_info_box').show();
    });

    /*自动查询姓名*/
    $('#transfer_outRegName').blur(function () {
        var regName = $('#transfer_outRegName').val();
        var outType = $('input[name="transfer_out"]:checked').val();
        if (outType==null){
            alert('请选择转账类型');
            return false;
        }
        var p = {regName:regName,type:outType};
        if(regName != ''){
            nameAmount($('#transfer_outRelName'),$('#transfer_outBalance'),p);
        }
    });
    $('#transfer_inRegName').blur(function () {
        var regName = $('#transfer_inRegName').val();
        var inType = $('input[name="transfer_in"]:checked').val();
        if (inType==null){
            alert('请选择转账类型');
            return false;
        }
        var p = {regName:regName,type:inType};
        if(regName != ''){
            nameAmount($('#transfer_inRelName'),$('#transfer_inBalance'),p);
        }
    });

    /*关闭新增窗口*/
    $(".close_btn").click(function () {
        $('#transfer_shadow').hide();
        $('#transfer_info_box').hide();
        $('#transfer_info_box input[type="text"]').val('');
        $('#transfer_info_box font').html('');
    });

    /*账户类型选择*/
    $('input[name="transfer_out"]').change(function () {
       if($(this).val() == '3') {
           $('input[name="transfer_in"][value="2"]').attr('disabled',true);
           $('#f_in').css('color','#D0D0D0');
       }else{
           $('input[name="transfer_in"][value="2"]').attr('disabled',false);
           $('#f_in').css('color','');
       }
        var p = {regName:$('#transfer_outRegName').val(),type:$('input[name="transfer_out"]:checked').val()};
        nameAmount($('#transfer_outRelName'),$('#transfer_outBalance'),p);
    });
    $('input[name="transfer_in"]').change(function () {
       if($(this).val() == '2') {
           $('input[name="transfer_out"][value="3"]').attr('disabled',true);
           $('#f_out').css('color','#D0D0D0');
       }else{
           $('input[name="transfer_out"][value="3"]').attr('disabled',false);
           $('#f_out').css('color','');
       }
        var p = {regName:$('#transfer_inRegName').val(),type:$('input[name="transfer_in"]:checked').val()};
        nameAmount($('#transfer_inRelName'),$('#transfer_inBalance'),p);
    });

    /*新增按钮*/
    $('#new_transfer_btn').click(function () {
        var param = {};
        if ($('#transfer_outRegName').val() == ''){
            alert("请填写转出方信息");
            return false;
        }
        param.transfer_outRegName = $('#transfer_outRegName').val();
        if ($('#transfer_inRegName').val() == ''){
            alert("请填写转入方信息");
            return false;
        }
        param.transfer_inRegName = $('#transfer_inRegName').val();
        var outType = $('input[name="transfer_out"]:checked').val();
        var inType = $('input[name="transfer_in"]:checked').val();
        if (outType==null || inType==null){
            alert('请选择转账类型');
            return false;
        }
        if (parseInt(outType) > parseInt(inType)){
            param.type = outType;
        }else if (parseInt(outType) < parseInt(inType)){
            param.type = inType;
        }
        if ($('#transfer_amount').val() == ''){
            alert("请填写转账金额");
            return false;
        }
        if (!isDouble($('#transfer_amount').val())){
            alert("请正确填写金额");
            return false;
        }
        if ($('#transfer_amount_re').val() == ''){
            alert("请确认转账金额");
            return false;
        }
        if (!isDouble($('#transfer_amount_re').val())){
            alert("请正确填写金额");
            return false;
        }

        if (parseFloat($('#transfer_amount_re').val()) != parseFloat($('#transfer_amount').val())){
            alert("转账金额不一致");
            return false;
        }
        if (Number($('#transfer_amount').val()) > Number($('#transfer_outBalance').html()) || Number($('#transfer_amount').val()) <= 0){
            alert("转账金额不在可用余额范围之内");
            return false;
        }
        param.transfer_amount = $('#transfer_amount').val();
        $('#new_transfer_btn').attr('disabled','disabled');
        showShadow();
        $.ajax({
            url:'/settlement/payease/transferAmount',
            type:'post',
            data:param,
            success:function (data) {
                if(data){
                    alert("转账成功");
                }else{
                    alert("转账失败");
                }
                $('#new_transfer_btn').removeAttr('disabled');;
                hideShadow();
                $('#transfer_shadow').hide();
                $('#transfer_info_box').hide();
                $('#transfer_info_box input[type="text"]').val('');
                $('#transfer_info_box font').html('');
                searchByParam();
            },
            error:function () {
                alert("系统出错");
                $('#new_transfer_btn').removeAttr('disabled');
                hideShadow();
            }
        });

    });





    /*导入excel*/
    $('#importTransfer').click(function () {
        $("#upload_input").click();
    });

    $('#upload_input').change(function () {
        if($(this).val()){
            $("#upload_form").submit();
        }
    });
});


//查询姓名和账号余额
function nameAmount(a,b,p){
    $.ajax({
        url:'/settlement/payease/getTransferName',
        data:p,
        type:'post',
        success:function (data) {
            if (typeof data == 'undefined' || data == ''){
                alert('无效的账户');
            }else{
                a.html(data.name);
                b.html(data.balance);
            }
        }
    });
}



function searchByParam(o){
    var p = getParam();
    p.currentPage = $(o).attr("num");
    $.ajax({
        url:'/settlement/payease/transferAccountPage',
        type:'post',
        data:p,
        success:function (data) {
            $('#transferList').html('');
            var list = data.voList;
            for(var i in list){
                if (list[i].returnCode == '0000' || list[i].result == '成功'){
                    $('#transferList').append('<tr height="45"><td>'+list[i].serlNum+'</td><td>'+list[i].transferOutUser+'</td><td>'+list[i].outName+'</td><td>'+list[i].transferAmount+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].transferInUser+'</td><td>'+list[i].inName+'</td><td style="color: green">'+list[i].result+'</td></tr>');
                }else {
                    $('#transferList').append('<tr height="45"><td>'+list[i].serlNum+'</td><td>'+list[i].transferOutUser+'</td><td>'+list[i].outName+'</td><td>'+list[i].transferAmount+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].transferInUser+'</td><td>'+list[i].inName+'</td><td style="color: red">'+list[i].result+'</td></tr>');
                }
                // if (list[i].returnCode == '0222' || list[i].returnCode == '' || list[i].result == '失败')
                // else if (list[i].returnCode == '0006' ){
                //     $('#transferList').append('<tr height="45"><td>'+list[i].serlNum+'</td><td>'+list[i].transferOutUser+'</td><td>'+list[i].outName+'</td><td>'+list[i].transferAmount+'</td><td>'+new Date(list[i].reqTime).format("yyyy-MM-dd hh:mm")+'</td><td>'+list[i].transferInUser+'</td><td>'+list[i].inName+'</td><td>'+list[i].result+'</td></tr>');
                // }
            }

            $('#transOperNum').html('');
            $('#transOperNum').append('<a class="h_bg_width" href="#" num="1" onclick="searchByParam(this)">首页</a>');
            $('#transOperNum').append('<a href="#" num="'+parseInt(parseInt(data.currentPage)-1)+'" onclick="searchByParam(this)"><i class="h_icon_prev"></i></a>');
            for (var i=parseInt(data.pageIndex.startIndex);i<=parseInt(data.pageIndex.endIndex);i++){
                if(parseInt(data.currentPage) == i){
                    $('#transOperNum').append('<a href="#" onclick="searchByParam(this)" class="current" num="'+i+'">'+i+'</a>');
                }else{
                    $('#transOperNum').append('<a href="#" onclick="searchByParam(this)" num="'+i+'">'+i+'</a>');
                }
            }
            $('#transOperNum').append('<a href="#" onclick="searchByParam(this)" num="'+parseInt(parseInt(data.currentPage)+1)+'"><i class="h_icon_next"></i></a>');
            $('#transOperNum').append('<a class="h_bg_width" href="#" onclick="searchByParam(this)" num="'+parseInt(data.pageCount)+'">末页</a>');
        }
    });

}

function getParam() {
    var p = {
        currentPage:$('a.current').attr("num")
    };
    if ($('#outRegName').val() != ''){
        p.outRegName = $('#outRegName').val();
    }
    if ($('#outRelName').val() != ''){
        p.outrelname = $('#outrelname').val();
    }
    if ($('#inRegName').val() != ''){
        p.inRegName = $('#inRegName').val();
    }
    if ($('#inRelName').val() != ''){
        p.inRelName = $('#inRelName').val();
    }
    
    if ($('#date1').val() != ''){
        p.date1 = $('#date1').val();
    }
    if ($('#date2').val() != ''){
        p.date2 = $('#date2').val();
    }
    if ($('#returnCode').val() != ''){
        p.returnCode = $('#returnCode').val();
    }
    return p;
}