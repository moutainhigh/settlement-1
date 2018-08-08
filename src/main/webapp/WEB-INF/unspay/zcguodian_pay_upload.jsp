<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path"
       value="${ctx}/zcgdUnspay/zcguodianPayUpload?filename=${filename}&uploadStartDate=${uploadStartDate}&uploadEndDate=${uploadEndDate}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>中城国典银生宝-导入</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript">
        $(function () {

            var banks = JSON.parse(localStorage.getItem("banks"));

            var message = $('#message').val();
            if(message!=''){
                var finalMessage = '';
                if(message.indexOf(",")!=-1){
                    var messageArray = message.split(",");
                    for(var i = 0 ; i < messageArray.length ; i ++){
                        finalMessage = finalMessage + messageArray[i] + "\n";
                    }
                }else{
                    finalMessage = message;
                }
                alert(finalMessage);
            }

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
                    var li_name = $(this).html();
                    var $tabs_ul_li = $('#tabs_ul li');
                    var exist = false;
                    for (var i = 0; i < $tabs_ul_li.length; i++) {
                        var $current_li = $($tabs_ul_li[i]);
                        if ($current_li.attr('data-name') == li_name) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        $('#tabs_ul li[data-name="' + li_name + '"]').click();
                        return;
                    }
                    var short_name = li_name.length > 8 ? li_name.substr(0, 5) + "..." : li_name;
                    var $tr = $(this).parents('tr');
                    var count = $tr.children('td:eq(3)').html();
                    var notVarify = $tr.children('td:eq(4)').html();
                    var varify = Number(count) - Number(notVarify);
                    var $li_lable = $("<li class='cont' data-name='" + li_name + "' data-count='" + count + "' data-notVarify='" + notVarify + "' data-varify='" + varify + "' >" + short_name + "<i class='close'>X</i></li>");
                    //加入新创建的lable
                    $tabs_ul.append($li_lable);
                    $('#tabs_ul li:not(:eq(0))').unbind('click');
                    $('#tabs_ul li:not(:eq(0))').click(function () {
                        var $cont = $('.cont');
                        $cont.each(function (index, element) {
                            $(this).attr("class", "");
                        });

                        $(this).attr("class", "cont");
                        //加载表头数据
                        var $upload_detail_title = $('#upload_detail_title');
                        $upload_detail_title.find('label:eq(0)').html($(this).attr('data-name'));
                        $upload_detail_title.find('label:eq(1)').html($(this).attr('data-notVarify'));
                        $upload_detail_title.find('label:eq(2)').html($(this).attr('data-varify'));
                        $upload_detail_title.find('label:eq(3)').html($(this).attr('data-count'));

                        //加载数据后台
                        loadUploadDetailData($(this).attr("data-name"));

                        $box_upload_list.hide();
                        $box_upload_detail.show();
                    });
                    $('#tabs_ul li[data-name="' + li_name + '"]').click();
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

            $("#upload_input").change(function () {
                if ($(this).val() != '') {
                    $('#upload_form').submit();
                }
            });

            $('#page_btn').click(function () {
                $('#page_form').submit();
            });

            function loadUploadDetailData(filename) {
                //清理
                var $upload_detail_table = $('#upload_detail_table');
                $upload_detail_table.find('tr:eq(0)').find(':checkbox').prop('checked',false);
                $upload_detail_table.find('tr:gt(0)').remove();

                $.get("${ctx}/zcgdUnspay/uploadZCGDPayDetail", {'filename': filename}, function (list) {

                    for (var i = 0; i < list.length; i++) {

                        var item = list[i];
                        var status;
                        var amount = '';
                        var operate = '';
                        if(item.payResult!=null){
                            if (item.payResult == '0000')
                                status = '<td class="success">' + '成功' + '</td>';
                            else if (item.payResult != '10' && item.payResult != '0000')
                                status = '<td class="failur">' + item.desc + '</td>';
                            else if (item.payResult == '10') {
                                status = '<td class="going">' + '处理中' + '</td>';
                                if(item.sendDate!=null){
                                    operate = '<button style="width: 35px;" data-orderId="' + item.orderId + '">同步</button>';
                                }
                            }
                        }else{
                            if (item.verifyStatus == '0')
                                status = '<td class="notaudited">' + '未审核' + '</td>';
                            else if (item.verifyStatus == '1')
                                status = '<td class="going">' + '待发送' + '</td>';
                            else if (item.verifyStatus == '2')
                                status = '<td class="refused">' + '拒绝' + '</td>';
                            else
                                status = '<td></td>';
                        }

                        var amountStr = String(Number(item.amount).toFixed(2));
                        if (amountStr.indexOf('.') != -1) {
                            var index = amountStr.indexOf('.');
                            var startIndex = 0;
                            var endIndex = index;
                            for (var j = index % 3; j < index + 2; j = j + 3) {
                                amount = amount + amountStr.substring(startIndex, j) + ",";
                                startIndex = startIndex + j;
                            }
                            if(amount.startsWith(',')){
                                amount = amount.substring(1);
                            }
                            amount = amount.substring(0, amount.length - 1) + "<small>" + amountStr.substring(index) + "</small>";
                        } else {
                            var index = amountStr.indexOf('.');
                            var startIndex = 0;
                            var endIndex = index;
                            for (var j = index % 3; j < index + 2; j = j + 3) {
                                amount = amount + amountStr.substring(startIndex, j) + ",";
                                startIndex = startIndex + j;
                            }
                            if(amount.startsWith(',')){
                                amount = amount.substring(1);
                            }
                            amount = amount.substring(0, amount.length - 1) + "<small>00</small>";
                        }

                       /*  var bankCode = item.bankCode;
                        var bankName = banks[bankCode].shortBankName==null?banks[bankCode].bankName:banks[bankCode].shortBankName; */

                        var $item = $('<tr height="45">'
                                + '<td>' + (item.verifyStatus == '0' ? '<input type="checkbox"/>' : '') + '</td>'
                                + '<td>' + item.orderId + '</td>'
                                + '<td>' + (item.sendDate == null ? "" : new Date(item.sendDate).format('yyyy-MM-dd hh:mm')) + '</td>'
                                + '<td>' + item.loanApplyId + '</td>'
                                + '<td>' + item.name + '</td>'
                                + '<td>' + item.idCardNo + '</td>'
                                + '<td>' + (item.phoneNo==null?"":item.phoneNo) + '</td>'
                                + '<td>' + item.cardNo + '</td>'
                                + '<td style="font-weight: bold;">' + amount + '</td>'
                                + '<td>' + (item.payResult==null?"":item.payResult) + '</td>'
                                + status
                                + '<td>' + operate + '</td>'
                                + '<td class="td_last">' + item.purpose + '</td>'
                                + '</tr>');
                        $upload_detail_table.append($item);
                    }
                    for (var j = list.length; j < 10; j++) {
                        var $item = $('<tr height="45">'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td></td>'
                                + '<td class="td_last"></td>'
                                + '</tr>');
                        $upload_detail_table.append($item);
                    }
                    //checkBox事件加载
                    var $check_box_items = $upload_detail_table.find(':checkbox:not(:eq(0))');
                    $check_box_items.click(function () {
                        var checked_all = true;
                        for (var i = 0; i < $check_box_items.length; i++) {
                            if (!$($check_box_items[i]).prop('checked')) {
                                checked_all = false;
                                break;
                            }
                        }
                        $('#check_all_check_box').prop('checked', checked_all);
                    });

                    //添加同步事件
                    $upload_detail_table.find('button[data-orderId]').unbind('click').click(function () {
                        var orderId = $(this).attr('data-orderId');
                        $.get("${ctx}/unspay/queryPayOrder", {'orderId': orderId}, function (unspayPayResponse) {
                            var $tr = $upload_detail_table.find('button[data-orderId="'+orderId+'"]').parents('tr');
                            if(unspayPayResponse.resultCode!='0000'){
                                alert('同步失败，'+unspayPayResponse.resultMessage);
                                $tr.children(':eq(10)').html(unspayPayResponse.resultCode);
                                $tr.children(':eq(11)').html(unspayPayResponse.resultMessage);
                            }else{
                                $tr.children(':eq(10)').html(unspayPayResponse.deductResult);
                                $tr.children(':eq(11)').html(unspayPayResponse.desc);
                            }
                            $tr.children(':eq(12)').children('button').hide();
                        },'json');
                    });

                }, 'json');
            }

            //全选checkBox
            $('#check_all_check_box').click(function () {
                if ($(this).prop('checked')) {
                    $('#upload_detail_table').find(':checkbox').prop('checked', true);
                } else {
                    $('#upload_detail_table').find(':checkbox').prop('checked', false);
                }
            });

            //审核同意
            $('#verify_agress_btn').click(function () {
                verify("1");
            });
            //审核拒绝
            $('#verify_refuse_btn').click(function () {
                verify("2");
            });

            //审核请求
            function verify(verifyStatus) {
                //获取所有的审核的orderId
                var orders = "";
                var $trs = $('#upload_detail_table').find('tr:gt(0)');
                $trs.each(function (index, item) {
                    var $checkbox = $(this).find(':checkbox');
                    if ($checkbox.prop('checked')) {
                        orders = orders + $(this).children('td:eq(1)').html() + ",";
                    }
                });
                orders = orders.substring(0, orders.length - 1);

                $.post("${ctx}/zcgdUnspay/varifyZCGDPay/" + verifyStatus, {
                    'orderIds': orders,
                    'date': new Date()
                }, function (status) {
                    if (status != '1') {
                        alert('操作失败！');
                    }
                    $('#tabs_ul li[class="cont"]').click();
                }, 'json');

            }

        });
    </script>
</head>

<body>
<div class="tab_cont clearfix">
    <div class="cont_ri" id="ri_cont">
        <div class="sale_box">
            <div class="cont_ri_til clearfix">
                <div class="left_btn">
                    <ul id="tabs_ul">
                        <li class="cont">中城国典银生宝导入</li>
                    </ul>
                </div>
            </div>
      <span id="box_upload_list">
      <div class="condition_a">
          <ul class="clearfix">
              <form id="page_form" action="${ctx}/zcgdUnspay/zcguodianPayUpload" method="GET">
                  <li>
                      <label>文件名称</label>
                      <input name="filename" type="text" placeholder="导入文件名称" class="place_txt" value="${filename}"/>
                  </li>
                  <li>
                      <label>导入时间</label>
                      <input type="text" name="uploadStartDate" onClick="WdatePicker()" value="<fmt:formatDate value="${uploadStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="uploadEndDate" onClick="WdatePicker()" value="<fmt:formatDate value="${uploadEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:50px;">
                      <button id="page_btn" style="margin-left:20px;margin-right:10px;">搜索</button>
                  </li>
              </form>
              <li style="width:200px;float: right;">
                  <button id="upload_button">选择上传文件</button>
                  <form id="upload_form" method="post" action="${ctx}/zcgdUnspay/zcguodianPayUpload"
                        enctype="multipart/form-data">
                      <input id="upload_input" name="importFile" type="file" accept="application/vnd.ms-excel"
                             style="display:none;"/>
                  </form>
              </li>
          </ul>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="50">
              <th>文件名称</th>
              <th>导入时间</th>
              <th>操作人</th>
              <th>总计</th>
              <th>未审核</th>
              <th>未通过</th>
              <th class="td_last">成功 | 失败 | 处理中</th>
          </tr>
          <c:forEach begin="0" end="${page.pageSize-1}" items="${page.result}" var="unspayPay">
              <tr height="45">
                  <td class="click_td">${unspayPay.filename}</td>
                  <td><fmt:formatDate value="${unspayPay.uploadDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                  <td>${unspayPay.staffName}</td>
                  <td>${unspayPay.count}</td>
                  <td class="notaudited">${unspayPay.notVerify}</td>
                  <td class="refused">${unspayPay.refused}</td>
                  <td class="td_last"><strong class="success">${unspayPay.success}</strong> | <strong
                          class="failur">${unspayPay.failur}</strong> | <strong class="going">${unspayPay.process}</strong>
                  </td>
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
      </span> <span id="box_upload_detail" class="clearfix" style="display:none;">
      <div id="upload_detail_title" class="detail_list_til clearfix">
          <ul>
              <li>文件名：
                  <label>201505050005.xlsx</label>
              </li>
              <li>待审核数：
                  <label>5</label>
              </li>
              <li>已审核数：
                  <label>31</label>
              </li>
              <li>总计：
                  <label>36</label>
              </li>
          </ul>
          <button id="verify_agress_btn" style="margin-right:20px;">审核通过</button>
          <button id="verify_refuse_btn" style="margin-left:20px;">审核拒绝</button>
      </div>
      <table id="upload_detail_table" cellpadding="0" cellspacing="0" border="0" class="order_box">
          <tr height="50">
              <th><input id="check_all_check_box" type="checkbox"/></th>
              <th>订单编号</th>
              <th>发送时间</th>
              <th>合同编号</th>
              <th>客户姓名</th>
              <th>身份证号</th>
              <th>手机号码</th>
              <th>银行卡号</th>
              <th>金额</th>
              <th>状态码</th>
              <th>状态</th>
              <th>操作</th>
              <th class="td_last">付款原因</th>
          </tr>
      </table>
      <p class="fund_page clearfix"><span></span></p>
      </span></div>
    </div>
</div>

<input id="message" type="hidden" value="${message}">

</body>
</html>