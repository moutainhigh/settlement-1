<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path"
       value="${ctx}/payease/syn001001/list?plateform=${plateform}&userName=${userName}&accNum=${accNum}&returnCode=${returnCode}&reqTimeStartDate=${reqTimeStartDate}&reqTimeEndDate=${reqTimeEndDate}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>首信易-开户</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {

            if ($("#message").val()) {
                alert($("#message").val());
            }

            $("#search_account_list_btn").click(function () {
                $("#account_list_form").submit();
            });

            //新增客户
            $("#new_button").click(function () {
                $('#shadow').show();
                //清空数据
                $("#new_user_table").find("input").val("");
                $('#user_info_box').show();
            });

            $("a.query").click(function () {
                var $td = $(this).parent("td");
                $td.html("查询中...");

                $.ajax({
                    url: "${ctx}/payease/trs001010/queryAccount",
                    data: {"user": $td.parent().children("td:eq(0)").html()},
                    dataType: "JSON",
                    success: function (trs001010Resp) {
                        $td.replaceWith("<td>" + trs001010Resp[0].availableBalance + "</td><td>" + trs001010Resp[1].availableBalance + "</td>");
                    },
                    error: function () {
                        alert("服务端异常，请稍后重试！");
                    }
                });
            });

            //上传开户文件
            $("#upload_input").change(function () {
                if ($(this).val()) {
                    $("#upload_form").submit();
                }
            });

            $(".close_btn").click(function () {
                $('#shadow').hide();
                $('#user_info_box').hide();
                $("#user_bindCard_box").hide();
                $("#new_deduct_box").hide();
            });


            $("#bindBankCard").click(function () {
                if ($("#tabs_ul li[class='cont']").attr("data-returnCode") == '0010') {
                    alert("更换银行卡请发邮件操作！");
                    return;
                }
                $('#shadow').show();
                $("#user_bindCard_box").show();
                var $detail_list_til = $("#box_upload_detail").children(".detail_list_til");
                var $new_bindCard_table = $('#new_bindCard_table');
                $new_bindCard_table.find(":input[name='user']").val($detail_list_til.find("p:eq(0)").html());
                $new_bindCard_table.find(":input[name='userName']").val($detail_list_til.find("p:eq(3)").html());
                $new_bindCard_table.find(":input[name='id']").val($detail_list_til.find("p:eq(1)").html());
                $new_bindCard_table.find(":input[name='accNum']").val("");
                $new_bindCard_table.find(":input[name='reAccNum']").val("");
                $new_bindCard_table.find(":input[name='accBankCode']").val("");
                //$new_bindCard_table.find(":input[name='accNum']").val($detail_list_til.find("p:eq(5)").html());
                //$new_bindCard_table.find(":input[name='reAccNum']").val($detail_list_til.find("p:eq(5)").html());
                //$new_bindCard_table.find(":input[name='accBankCode']").val($detail_list_til.find("p:eq(7)").html());
                $("#new_bindCard_btn").unbind("click").click(function () {
                    if ($new_bindCard_table.find(":input[name='reAccNum']").val() != $new_bindCard_table.find(":input[name='accNum']").val()) {
                        alert("银行卡两次输入不一致！");
                        return;
                    }
                    var data = {};
                    data.user = $new_bindCard_table.find(":input[name='user']").val();
                    data.userName = $new_bindCard_table.find(":input[name='userName']").val();
                    data.id = $new_bindCard_table.find(":input[name='id']").val();
                    data.accNum = $new_bindCard_table.find(":input[name='accNum']").val();
                    data.accBankCode = $new_bindCard_table.find(":input[name='accBankCode']").val();
                    $.post("${ctx}/payease/syn001001/binding", data, function (syn001001Resp) {
                        if (syn001001Resp) {
                            alert("请求已提交！");
                        }
                        $('#shadow').hide();
                        $("#user_bindCard_box").hide();
                    }, "json");
                });
            });

            $("#queryBankCard").click(function () {
                var user = $("#tabs_ul li[class='cont']").attr("data-user");
                $.get("${ctx}/payease/syn001001/queryBankCard", {"user": user}, function (syn001001Resp) {
                    window.location.reload();
                }, 'json');
            });

            //新增代扣
            $("#new_deduct").click(function () {
                $('#shadow').show();
                //清空数据
                var $new_deduct_table = $("#new_deduct_table");
                $new_deduct_table.find("input").val("");

                var bankCode = $('#tabs_ul li.cont').attr("bankCode");
                console.log(bankCode);
                $.ajax({
                    url:'/settlement/payease/getBankDesc',
                    data:{bankCode:bankCode},
                    type:'post',
                    success:function (data) {
                        $('#deduct_bankName').html(data.fullName);
                        $('#deduct_sl').html(parseInt(data.payeaseDeductSingleLimit)/10000);
                        $('#deduct_dl').html(parseInt(data.payeaseDeductDailyLimit)/10000);

                    }
                });
                $('#new_deduct_box').show();

                $("#new_deduct_btn").unbind("click").click(function () {
                    $("#new_deduct_btn").attr("disabled", "disabled");
                    var $inputs = $new_deduct_table.find("input");
                    var amount = $inputs[0].value;
                    var reAmount = $inputs[1].value;
                    if (isNaN(amount)) {
                        alert("金额输入有误！");
                        $("#new_deduct_btn").removeAttr("disabled");
                        return;
                    } else {
                        if (amount != reAmount) {
                            alert("两次输入不一致！");
                            $("#new_deduct_btn").removeAttr("disabled");
                            return;
                        }
                        //发送代扣请求
                        $.post("${ctx}/payease/trs001008/add", {
                            "user": $("#tabs_ul li[class='cont']").attr("data-user"),
                            "amount": amount
                        }, function (trs001008Resp) {
                            $('#shadow').hide();
                            $('#new_deduct_box').hide();
                            alert(trs001008Resp.returnMsg);
                            $("#new_deduct_btn").removeAttr("disabled");
                            loadTradeRecordData({"user": $("#tabs_ul li[class='cont']").attr("data-user")});
                        }, "json");
                    }
                });
            });

            //新增提现
            $("#new_withdraw").click(function () {
                $('#shadow').show();
                //清空数据
                var $new_deduct_table = $("#new_withdraw_table");
                $new_deduct_table.find("input").val("");
                $('#new_withdraw_box').show();

                $("#new_withdraw_btn").unbind("click").click(function () {
                    $("#new_withdraw_btn").attr("disabled", "disabled");
                    var $inputs = $new_deduct_table.find("input");
                    var amount = $inputs[0].value;
                    var reAmount = $inputs[1].value;
                    if (isNaN(amount)) {
                        alert("金额输入有误！");
                        $("#new_withdraw_btn").removeAttr("disabled");
                        return;
                    } else {
                        if (amount != reAmount) {
                            alert("两次输入不一致！");
                            $("#new_withdraw_btn").removeAttr("disabled");
                            return;
                        }
                        //发送代扣请求
                        $.post("${ctx}/payease/trs001006/add", {
                            "user": $("#tabs_ul li[class='cont']").attr("data-user"),
                            "amount": amount
                        }, function (trs001006Resp) {
                            $('#shadow').hide();
                            $('#new_withdraw_box').hide();
                            alert(trs001006Resp.returnMsg);
                            $("#new_withdraw_btn").removeAttr("disabled");
                            loadTradeRecordData({"user": $("#tabs_ul li[class='cont']").attr("data-user")});
                        }, "json");
                    }
                });
            });

            $("#searchTrade").click(function () {
                var data = {"user": $("#tabs_ul li[class='cont']").attr("data-user")};
                var $conditions = $("#trade_record_table").prev().find(":input[name]");
                $conditions.each(function () {
                    var name = this.name;
                    var value = this.value;
                    if (value) {
                        data[name] = value;
                    }
                });
                loadTradeRecordData(data);
            });

            var $box_upload_list = $('#box_upload_list');
            var $box_upload_detail = $('#box_upload_detail');

            //点击标签-->显示内容页
            $('#tabs_ul li:eq(0)').click(function () {
                var $cont = $('.cont');
                $cont.each(function (index, element) {
                    $(this).attr("class", "");
                });

                $(this).attr("class", "cont");
                $box_upload_list.show();
                $box_upload_detail.hide();
            });
            //点击导入文件名，显示详情页
            var $click_td = $('.click_td');
            var $tabs_ul = $('#tabs_ul');
            $click_td.each(function (index, element) {
                $(this).click(function () {
                    var $tr = $(this).parent();
                    var li_user = $tr.children(":eq(0)").html();
                    var li_userName = $tr.children(":eq(1)").html();
                    var li_id = $tr.children(":eq(2)").attr("data-id");
                    var li_accNum = $tr.children(":eq(3)").attr("data-accNum");
                    var li_accBank = $tr.children(":eq(4)").html();
                    var bankCode = $tr.children(":eq(4)").attr("code");
                    var li_returnCode = $tr.children(":eq(7)").attr("data-returnCode");
                    var $tabs_ul_li = $('#tabs_ul li');
                    var exist = false;
                    for (var i = 0; i < $tabs_ul_li.length; i++) {
                        var $current_li = $($tabs_ul_li[i]);
                        if ($current_li.attr('data-user') == li_user) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        $('#tabs_ul li[data-user="' + li_user + '"]').click();
                        return;
                    }
                    var short_name = li_user.length > 8 ? li_user.substr(0, 5) + "..." : li_user;
                    var $li_lable = $("<li class='cont' bankCode='" + bankCode + "' data-user='" + li_user + "' data-userName='" + li_userName + "' data-id='" + li_id + "' data-accNum='" + li_accNum + "' data-accBank='" + li_accBank + "' data-returnCode='" + li_returnCode + "' >" + short_name + "<i class='close'>X</i></li>");
                    //加入新创建的lable
                    $tabs_ul.append($li_lable);
                    $('#tabs_ul li:not(:eq(0))').unbind('click');
                    $('#tabs_ul li:not(:eq(0))').click(function () {
                        var $cont = $('.cont');
                        $cont.each(function (index, element) {
                            $(this).attr("class", "");
                        });
                        $(this).attr("class", "cont");
                        //填充数据
                        var $ps = $("#box_upload_detail").children(".detail_list_til").find("p");
                        $ps[0].innerHTML = $(this).attr("data-user");
                        $ps[1].innerHTML = $(this).attr("data-id");
                        $ps[3].innerHTML = $(this).attr("data-userName");
                        $ps[5].innerHTML = $(this).attr("data-accNum");
                        $ps[4].innerHTML = $(this).attr("data-accBank");
                        $ps[7].innerHTML = $(this).attr("bankCode");
                        //余额
                        $.ajax({
                            url: "${ctx}/payease/trs001010/queryAccount",
                            data: {"user": $(this).attr("data-user")},
                            dataType: "JSON",
                            success: function (trs001010Resp) {
                                $ps[2].innerHTML = trs001010Resp[0].availableBalance;
                                $ps[6].innerHTML = trs001010Resp[1].availableBalance;
                            },
                            error: function () {
                                alert("服务端异常，请稍后重试！");
                            }
                        });

                        //查询交易记录
                        loadTradeRecordData({"user": $(this).attr("data-user")});

                        $box_upload_list.hide();
                        $box_upload_detail.show();
                    });
                    $('#tabs_ul li[data-user="' + li_user + '"]').click();
                    //关闭标签
                    var close_list = $('.close');
                    for (var j = 0; j < close_list.length; j++) {
                        close_list[j].onclick = function (event) {
                            if (this.parentNode.getAttribute('class') == 'cont') {
                                $('#tabs_ul li:eq(0)').click();
                            }
                            this.parentNode.remove();
                            if (event) //停止事件冒泡
                                event.stopPropagation();
                            else
                                window.event.cancelBubble = true;
                        }
                    }
                });
            });

            $("#upload_button").click(function () {
                $("#upload_input").click();
            });

            //格式化银行卡
            $('.bankCardNo').each(function () {
                $(this).html(formateBankcardNo($(this).html()));
            });

            $(".idCardNo").each(function () {
                $(this).html(formateIdCardNo($(this).html()));
            });
        });

        var formateBankcardNo = function (bankCardNo) {
            return bankCardNo.replace(/(\d{4})(?=\d)/g, "<bankcard style='padding-right: 4px;'>" + "$1" + "</bankcard>");
        }

        var formateIdCardNo = function (idNo) {
            return "<idcard style='padding-right: 4px;'>" + idNo.substring(0, 6) + "</idcard>" + "<idcard style='padding-right: 4px;'>" + idNo.substring(6, 14) + "</idcard>" + idNo.substring(14, 19);
        }


        var loadTradeRecordData = function (data) {
            $.get("${ctx}/payease/syn001001/record", data, function (page) {
                var $trade_record_table = $('#trade_record_table');
                $trade_record_table.find('tr:not(:eq(0))').remove();
                var list = page.result;
                for (var i = 0; i < list.length; i++) {
                    var item = list[i];

                    var $item = $('<tr height="45">'
                    + '<td>' + item.serlNum + '</td>'
                    + '<td>' + new Date(item.returnTime).format('yyyy-MM-dd hh:mm') + '</td>'
                    + '<td>' + item.operationName + '</td>'
                    + '<td>' + item.amount + '</td>'
                    + '<td>' + item.accNum + '</td>'
                    + '<td >' + (item.accBank ? item.accBank : '') + '</td>'
                    + '<td class="td_last">' + item.returnMsg + '</td>'
                    + '</tr>');
                    $trade_record_table.append($item);
                }
                for (var j = list.length; j < 10; j++) {
                    var $item = $('<tr height="45">'
                            + '<td></td>'
                            + '<td></td>'
                            + '<td></td>'
                            + '<td></td>'
                            + '<td></td>'
                            + '<td></td>'
                            + '<td class="td_last"></td>'
                            + '</tr>');
                    $trade_record_table.append($item);
                }

                $trade_record_table.next().remove();

                var paging = '<p class="fund_page clearfix"><span>';

                if (page.hasPre) {
                    paging = paging + '<a class="h_bg_width paging" href="javascript:;" data-pageNo="1" data-user="' + data.user + '">首页</a><a href="javascript:;" class="paging" data-pageNo="1" data-user="' + data.user + '"><i class="h_icon_prev"></i></a>';
                }

                if (page.pageNo <= 3) {
                    for (var i = 1; i <= page.totalPages && i <= 5; i++) {
                        if (page.pageNo == i) {
                            paging = paging + '<a data-pageNo="' + i + '" data-user="' + data.user + '" class="current" href="#">' + i + '</a>';
                        } else {
                            paging = paging + '<a data-pageNo="' + i + '" data-user="' + data.user + '" class="paging" href="#">' + i + '</a>';
                        }
                    }
                } else if (page.totalPages - page.pageNo <= 3) {
                    for (var i = page.totalPages; i >= page.pageNo - 2 && page.pageNo - 2 >= 1; i--) {
                        if (page.pageNo == i) {
                            paging = paging + '<a data-pageNo="' + i + '" data-user="' + data.user + '" class="current" href="#">' + i + '</a>';
                        } else {
                            paging = paging + '<a data-pageNo="' + i + '" data-user="' + data.user + '" class="paging" href="#">' + i + '</a>';
                        }
                    }
                } else {
                    for (var i = page.pageNo - 2 >= 1 ? page.pageNo - 2 : 1; i <= page.pageNo + 2 && page.pageNo + 2 <= page.totalPages; i++) {
                        if (page.pageNo == i) {
                            paging = paging + '<a data-pageNo="' + i + '" data-user="' + data.user + '" class="current" href="#">' + i + '</a>';
                        } else {
                            paging = paging + '<a data-pageNo="' + i + '" data-user="' + data.user + '" class="paging" href="#">' + i + '</a>';
                        }
                    }
                }
                if (page.hasNext) {
                    paging = paging + '<a href="javascript:;" class="paging" data-pageNo="1" data-user="' + data.user + '"><i class="h_icon_next"></i></a><a class="h_bg_width paging" href="javascript:;" data-pageNo="1" data-user="' + data.user + '">末页</a>';
                }

                paging = paging + '</span></p>';

                $trade_record_table.after(paging);

                //绑定翻页
                $trade_record_table.next().find("a.paging").unbind("click").click(function () {
                    var data = {"user": $(this).attr("data-user"), "pageNo": $(this).attr("data-pageNo")};
                    var $conditions = $trade_record_table.prev().find(":input[name]");
                    $conditions.each(function () {
                        var name = this.name;
                        var value = this.value;
                        if (value) {
                            data[name] = value;
                        }
                    });
                    loadTradeRecordData(data);
                });
            }, 'json');
        }



        function addline(o) {
            $(o).attr("style","text-decoration:underline");
        }

        function rmline(o){
            $(o).attr("style","text-decoration:none");
        }
    </script>
</head>

<body>
<div class="tab_cont clearfix">
    <div class="cont_ri" id="ri_cont">
        <div class="sale_box">
            <div class="cont_ri_til clearfix">
                <div class="left_btn">
                    <ul id="tabs_ul">
                        <li class="cont">开户列表</li>
                    </ul>
                </div>
            </div>
      <span id="box_upload_list">
      <div class="condition_a">
          <form id="account_list_form" action="${ctx}/payease/syn001001/list" method="GET">
              <input type="hidden" name="plateform" value="${plateform}">
              <ul class="clearfix">
                  <li>
                      <label>姓名</label>
                      <input type="text" placeholder="姓名" class="place_txt" style="width:160px;" name="userName"
                             value="${userName}"/>
                  </li>
                  <li>
                      <label>开户时间</label>
                      <input type="text" onClick="WdatePicker()" name="reqTimeStartDate"
                             value="<fmt:formatDate value="${reqTimeStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" onClick="WdatePicker()" name="reqTimeEndDate"
                             value="<fmt:formatDate value="${reqTimeEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>银行卡号</label>
                      <input type="text" placeholder="银行卡号" class="place_txt" style="width:160px;" name="accNum"
                             value="${accNum}"/>
                  </li>
                  <li>
                      <label>账户状态</label>
                      <select style="width:150px; margin:0 2px;" name="returnCode" value="${returnCode}">
                          <option <c:if test="${empty retCode}">selected</c:if>></option>
                          <option value="0222" <c:if test="${'0222' eq returnCode}">selected</c:if>>未开户</option>
                          <option value="0011" <c:if test="${'0011' eq returnCode}">selected</c:if>>开户</option>
                          <option value="0010" <c:if test="${'0010' eq returnCode}">selected</c:if>>开户+绑卡</option>
                      </select>
                  </li>
                  <li style="margin-left:0px;">
                      <button id="search_account_list_btn" style="margin-left:100px; width:80px;">搜索</button>
                  </li>
                  <li style="margin-left:0px;">
                      <button id="new_button" type="button" style="margin-left:150px; width:80px;">开户</button>
                      <button id="upload_button" type="button" style="margin-left:25px;width:80px;">导入</button>
                      <a id="upload_templete" href="${ctx}/payease/templete?templete=首信易-开户模板.xls"
                         style="margin-left:20px;text-decoration: underline;">模板</a>
                  </li>
              </ul>
          </form>
          <form id="upload_form" method="post" action="${ctx}/payease/syn001001/upload"
                enctype="multipart/form-data">
              <input id="upload_input" name="importFile" type="file" accept="application/vnd.ms-excel"
                     style="display:none;"/>
          </form>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="25">
              <th rowspan="2">用户注册名</th>
              <th rowspan="2">用户姓名</th>
              <th rowspan="2">身份证号</th>
              <th rowspan="2">银行卡号</th>
              <th rowspan="2">银行名称</th>
              <th colspan="2">账户余额</th>
              <th rowspan="2">账户状态</th>
              <th class="td_last" rowspan="2">开户时间</th>
          </tr>
          <tr height="25">
              <th>投资账户</th>
              <th>负债账户</th>
          </tr>
          <c:forEach items="${page.result}" var="syn001001">
              <tr height="45">
                  <td class="click_td" onmouseover="addline(this)" onmouseout="rmline(this)">${syn001001.user}</td>
                  <td class="click_td" onmouseover="addline(this)" onmouseout="rmline(this)">${syn001001.userName}</td>
                  <td class="idCardNo click_td" onmouseover="addline(this)" onmouseout="rmline(this)" data-id="${syn001001.id}">${syn001001.id}</td>
                  <td class="bankCardNo" data-accNum="${syn001001.accNum}">${syn001001.accNum}</td>
                  <td code="${syn001001.accBankCode}">${syn001001.accBank}</td>
                  <td colspan="2"><a href="javascript:;" class="query">查询</a></td>
                  <td data-returnCode="${syn001001.returnCode}">${syn001001.status}</td>
                  <td class="td_last"><fmt:formatDate value="${syn001001.reqTime}"
                                                      pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
              </tr>
          </c:forEach>
          <c:forEach begin="${fn:length(page.result)}" end="9">
              <tr height="45">
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td class="td_last"></td>
              </tr>
          </c:forEach>
      </table>
      <p class="fund_page clearfix"><span>
          <c:if test="${page.hasPre}">
              <a class="h_bg_width" href="${search_path}&pageNo=1">首页</a>
              <a href="${search_path}&pageNo=${page.pageNo-1}"><i class="h_icon_prev"></i></a>
          </c:if>
          <c:if test="${page.pageNo-2>0}">
              <a href="${search_path}&pageNo=${page.pageNo-2}">${page.pageNo-2}</a>
          </c:if>
          <c:if test="${page.pageNo-1>0}">
              <a href="${search_path}&pageNo=${page.pageNo-1}">${page.pageNo-1}</a>
          </c:if>
          <a class="current" href="javascript:;">${page.pageNo}</a>
          <c:if test="${page.pageNo+1<=page.totalPages}">
              <a href="${search_path}&pageNo=${page.pageNo+1}">${page.pageNo+1}</a>
          </c:if>
          <c:if test="${page.pageNo+2<=page.totalPages}">
              <a href="${search_path}&pageNo=${page.pageNo+2}">${page.pageNo+2}</a>
          </c:if>
          <c:if test="${page.hasNext}">
              <a href="${search_path}&pageNo=${page.pageNo+1}"><i class="h_icon_next"></i></a>
              <a class="h_bg_width" href="${search_path}&pageNo=${page.totalPages}">末页</a>
          </c:if>
      </span></p>
      </span>
        <span id="box_upload_detail" class="clearfix" style="display:none;">
            <div class="detail_list_til clearfix" style="text-align: center;">
                <ul style="width: 70%;">
                    <li style="width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <label style="width: 40%;text-align: right;float: left;">用户注册名：</label>
                        <p style="width: 60%;float: left;text-align: left;"></p>
                    </li>
                    <li style="width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <name style="width: 40%;text-align: right;float: left;">身份证号：</name>
                        <p style="width: 60%;float: left;text-align: left;"></p>
                    </li>
                    <li style="width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <label style="width: 60%;text-align: right;float: left;">投资账户余额：</label>
                        <p style="width: 40%;float: left;text-align: left;">0.00</p>
                    </li>
                </ul>
                <ul style="width: 70%;">
                    <li style="width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <label style="width: 40%;text-align: right;float: left;">用户姓名：</label>
                        <p style="width: 60%;float: left;text-align: left;"></p>
                    </li>
                    <li style="width: 270px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 51px;">
                        <label style="width: 27%;text-align: right;float: left;">银行名称：</label>
                        <p style="width: 73%;float: left;text-align: left;"></p>
                    </li>
                    <li style="width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <label style="width: 40%;text-align: right;float: left;">银行卡号：</label>
                        <p style="width: 60%;float: left;text-align: left;"></p>
                    </li>
                    <li style="width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <label style="width: 60%;text-align: right;float: left;">负债账户余额：</label>
                        <p style="width: 40%;float: left;text-align: left;">0.00</p>
                    </li>
                    <li style="display:none; width: 240px; float: left;font-size: 14px;height: 30px;line-height: 30px; margin-left: 30px;">
                        <label style="width: 60%;text-align: right;float: left;">银行名称：</label>
                        <p style="width: 40%;float: left;text-align: left;"></p>
                    </li>
                </ul>
            </div>
            <div class="condition_a">
                <ul class="clearfix">
                    <li>
                        <label>交易类型</label>
                        <select style="width:150px; margin:0 2px;" name="tradeType">
                            <option></option>
                            <option value="TRS001007">转&nbsp;&nbsp;账</option>
                            <option value="TRS001008">代&nbsp;&nbsp;扣</option>
                            <option value="TRS001006">提&nbsp;&nbsp;现</option>
                        </select>
                    </li>
                    <li>
                        <label>交易结果</label>
                        <select style="width:150px; margin:0 2px;" name="returnCode">
                            <option></option>
                            <option value="0000">成&nbsp;&nbsp;功</option>
                            <option value="0002">处理中</option>
                            <option value="0222">失&nbsp;&nbsp;败</option>
                        </select>
                    </li>
                    <li>
                        <label>交易时间</label>
                        <input type="text" name="returnStartTime" onClick="WdatePicker()"/>
                        -
                        <input type="text" name="returnEndTime" onClick="WdatePicker()"/>
                    </li>
                    <li style="margin-left:0px;">
                        <button id="searchTrade" style="margin-left:50px;margin-right:20px;width:80px;">搜索</button>
                        <button id="bindBankCard" style="margin-left:20px;margin-right:20px;width:80px;">验卡</button>
                        <button id="queryBankCard" style="margin-left:20px;margin-right:20px;width:80px;">同步</button>
                        <button id="new_deduct" style="margin-left:20px;margin-right:20px;width:80px;">代扣</button>
                        <button id="new_withdraw" style="margin-left:20px;margin-right:20px;width:80px;">提现</button>
                    </li>
                </ul>
            </div>
            <table id="trade_record_table" cellpadding="0" cellspacing="0" border="0" class="order_box">
                <tr height="50">
                    <th>流水号</th>
                    <th>交易日期</th>
                    <th>交易类型</th>
                    <th>交易金额</th>
                    <th>银行卡号</th>
                    <th>银行名称</th>
                    <th class="td_last">交易结果</th>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
                <tr height="45">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td_last"></td>
                </tr>
            </table>
            <p class="fund_page clearfix"><span></span></p>
        </span>
        </div>
    </div>
</div>
<div id="shadow" class="shadow" style="z-index: 100"></div>
<div id="user_info_box" class="edit_box" style="left: 30%; top: 20%;">
    <h2 style="text-align: center; font-size: 16px;">新增开户信息<a href="javascript:;" class="close_btn"><img
            style="margin-top: 5px;" src="${ctx}/images/xx.png"/></a></h2>
    <form action="${ctx}/payease/syn001001/add" method="POST">
        <table id="new_user_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
            <tr height="45">
                <td><label>真实姓名：</label>
                    <input name="userName" type="text"/>
                </td>
                <td colspan="2"><label>银行名称：</label>
                    <select name="accBankCode" style="width: 153px;">
                        <option></option>
                        <c:forEach items="${banks}" var="bank">
                            <option value="${bank.code}">${bank.code} <c:choose>
                                <c:when test="${not empty bank.shortName}">${bank.shortName}</c:when>
                                <c:otherwise>${bank.fullName}</c:otherwise>
                            </c:choose></option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr height="45">
                <td><label>身份证号：</label>
                    <input name="id" type="text"/>
                </td>
                <td><label>再次输入身份证：</label>
                    <input name="reId" type="text"/>
                </td>
            </tr>
            <tr height="45">
                <td><label>银行卡号：</label>
                    <input name="accNum" type="text"/>
                </td>
                <td><label>再次输入银行卡：</label>
                    <input name="reAccNum" type="text"/>
                </td>
            </tr>
        </table>
        <p class="edit_p">
            <button id="new_user_btn" type="submit">添加</button>
        </p>
    </form>
</div>
<div id="user_bindCard_box" class="edit_box" style="left: 30%; top: 20%;">
    <h2 style="text-align: center; font-size: 16px;">客户绑卡<a href="javascript:;" class="close_btn"><img
            style="margin-top: 5px;" src="${ctx}/images/xx.png"/></a></h2>
    <table id="new_bindCard_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
        <tr height="45" style="display: none;">
            <td><label>用户注册名：</label>
                <input name="user" type="text"/>
            </td>
        </tr>
        <tr height="45" style="display: none;">
            <td><label>真实姓名：</label>
                <input name="userName" type="text"/>
            </td>
            <td><label>身份证号：</label>
                <input name="id" type="text"/>
            </td>
        </tr>
        <tr height="45">
            <td><label>银行卡号：</label>
                <input name="accNum" type="text"/>
            </td>
            <td><label>再次输入银行卡：</label>
                <input name="reAccNum" type="text"/>
            </td>
        </tr>
        <tr height="45">
            <td colspan="2"><label>银行名称：</label>
                <select name="accBankCode" style="width: 153px;">
                    <option></option>
                    <c:forEach items="${banks}" var="bank">
                        <option value="${bank.code}">${bank.code} <c:choose>
                            <c:when test="${not empty bank.shortName}">${bank.shortName}</c:when>
                            <c:otherwise>${bank.fullName}</c:otherwise>
                        </c:choose></option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <p class="edit_p">
        <button id="new_bindCard_btn" type="button">绑定</button>
    </p>
</div>
<div id="new_deduct_box" class="edit_box" style="left: 30%; top: 20%;">
    <h2 style="text-align: center; font-size: 16px;">新增代扣信息<a href="javascript:;" class="close_btn"><img
            style="margin-top: 5px;" src="${ctx}/images/xx.png"/></a></h2>
    <table id="new_deduct_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
        <tr height="45">
            <td colspan="2" style="text-align:center;" id="deduct_bankName"></td>
        </tr>
        <tr height="45">
            <td><label>单笔限额：</label>
                <lavel id="deduct_sl"></lavel>万
            </td>
            <td><label>每日限额：</label>
                <lavel id="deduct_dl"></lavel>万
            </td>
        </tr>
        <tr height="45">
            <td><label>代扣金额：</label>
                <input name="amount" type="text"/>
            </td>
            <td><label>再次输入：</label>
                <input name="reAmount" type="text"/>
            </td>
        </tr>
    </table>
    <p class="edit_p">
        <button id="new_deduct_btn" type="button">新增代扣</button>
    </p>
</div>


<div id="new_withdraw_box" class="edit_box" style="left: 30%; top: 20%;">
    <h2 style="text-align: center; font-size: 16px;">新增提现信息<a href="javascript:;" onclick="closeDraw()"
                                                              class="close_btn"><img style="margin-top: 5px;"
                                                                                     src="${ctx}/images/xx.png"/></a>
    </h2>
    <table id="new_withdraw_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
        <tr height="45">
            <td><label>提现金额：</label>
                <input name="amount" type="text"/>
            </td>
            <td><label>确认金额：</label>
                <input name="reAmount" type="text"/>
            </td>
        </tr>
    </table>
    <p class="edit_p">
        <button id="new_withdraw_btn" type="button">新增提现</button>
    </p>
</div>

<input id="message" type="hidden" name="message" value="${message}">

</body>
</html>