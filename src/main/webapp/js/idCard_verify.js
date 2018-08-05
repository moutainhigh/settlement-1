$(function () {

    //新增实名认证
    $("#new_button").click(function () {
        //清空数据
        $('#shadow').show();
        $('#user_info_box').show();
    });

    $(".close_btn").click(function () {
        $('#shadow').hide();
        $('#user_info_box').hide();
        $('#user_info_box input').val('');
    });
    
    $('#new_user_btn').click(function () {
        var name = $('#add_username').val();
        if (name == ''){
            alert('请填写姓名');
            return false;
        }
        var idcard = $('#add_idcard').val();
        if (idcard == ''){
            alert('请填写身份证号');
            return false;
        }
        if (!IdentityCodeValid(idcard)){
            alert('请正确填写身份证号码');
            return false;
        }
        $('#new_user_btn').attr('disabled',true);
        $.ajax({
            url:'/settlement/payease/verifyIdcard',
            data:{name:name,idcardNo:idcard},
            type:'post',
            success:function (data) {
                searchByParam();
                $('#shadow').hide();
                $('#user_info_box').hide();
                $('#user_info_box input').val('');
                $('#new_user_btn').attr('disabled',false);
            },
            error:function () {
                $('#shadow').hide();
                $('#user_info_box').hide();
                $('#user_info_box input').val('');
                $('#new_user_btn').attr('disabled',false);
            }
        });
    });

    $("#upload_button").click(function () {
        $("#upload_input").click();
    });
    $('#upload_input').change(function () {
        if($(this).val()){
            $("#upload_form").submit();
        }
    });

});





function IdentityCodeValid(code) {
    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
    var tip = "";
    var pass= true;

    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
        tip = "身份证号格式错误";
        pass = false;
    }

    else if(!city[code.substr(0,2)]){
        tip = "地址编码错误";
        pass = false;
    }
    else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17]){
                tip = "校验位错误";
                pass =false;
            }
        }
    }
    // if(!pass) alert(tip);
    return pass;
}



function searchByParam(o){
    var p = getParam();
    p.currentPage = $(o).attr("num");
    console.log(p);
    $.ajax({
        url:'/settlement/payease/idCardVerifyPage',
        type:'post',
        data:p,
        success:function (data) {
            console.log(data);
            $('#idCardList').html('');
            var list = data.voList;
            for(var i in list){
                if (list[i].status == '1'){
                    $('#idCardList').append('<tr height="45"><td>'+list[i].name+'</td><td class="gap"><em>'+list[i].idcardNo.substring(0,6)+'</em><em>'+list[i].idcardNo.substring(6,14)+'</em><em>'+list[i].idcardNo.substring(14)+'</em></td><td>'+new Date(list[i].verifyTime).format("yyyy-MM-dd hh:mm")+'</td><td style="color: green">成功</td></tr>');
                }else if (list[i].status == '2'){
                    $('#idCardList').append('<tr height="45"><td>'+list[i].name+'</td><td class="gap"><em>'+list[i].idcardNo.substring(0,6)+'</em><em>'+list[i].idcardNo.substring(6,14)+'</em><em>'+list[i].idcardNo.substring(14)+'</em></td><td>'+new Date(list[i].verifyTime).format("yyyy-MM-dd hh:mm")+'</td><td style="color: red">失败</td></tr>');
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
        currentPage:$('a.current').attr("num")
    };
    if ($('#p_name').val() != ''){
        p.name = $('#p_name').val();
    }
    if ($('#p_idCard').val() != ''){
        p.idcardNo = $('#p_idCard').val();
    }
    if ($('#p_date1').val() != ''){
        p.date1 = $('#p_date1').val();
    }
    if ($('#p_date2').val() != ''){
        p.date2 = $('#p_date2').val();
    }
    if ($('#p_status').val() != ''){
        p.status = $('#p_status').val();
    }
    return p;
}