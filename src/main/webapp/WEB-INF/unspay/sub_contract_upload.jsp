<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path"
       value="${ctx}/unspay/subContractUpload?filename=${filename}&uploadStartDate=${uploadStartDate}&uploadEndDate=${uploadEndDate}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>银生宝-子协议导入</title>
    <link rel="stylesheet" type="text/css" href="../css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {

            var banks = JSON.parse(localStorage.getItem("banks"));

            var message = $('#message').val();
            if (message != '') {
                var finalMessage = '';
                if (message.indexOf(",") != -1) {
                    var messageArray = message.split(",");
                    for (var i = 0; i < messageArray.length; i++) {
                        finalMessage = finalMessage + messageArray[i] + "\n";
                    }
                } else {
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
                    var $li_lable = $("<li class='cont' data-count='" + $tr.children('td:eq(3)').html() + "' data-success='" + $tr.children('td:eq(4)').children('strong:eq(0)').html() + "' data-failur='" + $tr.children('td:eq(4)').children('strong:eq(1)').html() + "' data-process='" + $tr.children('td:eq(4)').children('strong:eq(2)').html() + "' data-name='" + li_name + "'>" + short_name + "<i class='close'>X</i></li>");
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
                        $upload_detail_title.find('label:eq(1)').html($(this).attr('data-count'));
                        $upload_detail_title.find('label:eq(2)').html($(this).attr('data-success'));
                        $upload_detail_title.find('label:eq(3)').html($(this).attr('data-failur'));
                        $upload_detail_title.find('label:eq(4)').html($(this).attr('data-process'));

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
                $.get("${ctx}/unspay/uploadSubContractDetail", {'filename': filename}, function (list) {
                    var $upload_detail_table = $('#upload_detail_table');
                    $upload_detail_table.children('tr:not(:eq(0))').remove();
                    for (var i = 0; i < list.length; i++) {
                        var item = list[i];
                        var cycle;
                        var status;
                        var operate = '';
                        if (item.cycle == 1)
                            cycle = '年';
                        else if (item.cycle == 2)
                            cycle = '月';
                        else if (item.cycle == 3)
                            cycle = '日';
                        else
                            cycle = '';
                        if (item.signStatus == '0000') {
                            status = '<td class="success">' + '成功' + '</td>';
                        } else if (item.signStatus != '0000' && item.signStatus != '') {
                            status = '<td class="failur">' + item.signMsg + '</td>';
                            operate = '<button style="width: 35px;" data-loanApplyId="' + item.loanApplyId + '">修改</button>';
                        } else
                            status = '<td class="going">' + '处理中' + '</td>';


                        var bankCode = item.bankCode;
                        var bankName = banks[bankCode].shortBankName==null?banks[bankCode].bankName:banks[bankCode].shortBankName;
                        var $item = $('<tr height="45">'
                                + '<td>' + item.subContractId + '</td>'
                                + '<td>' + new Date(item.sendDate).format('yyyy-MM-dd hh:mm') + '</td>'
                                + '<td>' + item.loanApplyId + '</td>'
                                + '<td>' + item.name + '</td>'
                                + '<td>' + item.idCardNo + '</td>'
                                + '<td>' + item.phoneNo + '</td>'
                                + '<td>' + item.cardNo + '</td>'
                                + '<td>' + bankName + '</td>'
                                + '<td>' + new Date(item.startDate).format('yyyy-MM-dd') + '</td>'
                                + '<td>' + new Date(item.endDate).format('yyyy-MM-dd') + '</td>'
                                + '<td>' + cycle + '</td>'
                                + '<td>' + item.triesLimit + '</td>'
                                + '<td>' + item.signStatus + '</td>'
                                + status
                                + '<td class="td_last">' + operate + '</td>'
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
                                + '<td></td>'
                                + '<td></td>'
                                + '<td class="td_last"></td>'
                                + '</tr>');
                        $upload_detail_table.append($item);
                    }
                    //添加修改事件
                    $upload_detail_table.find('button[data-loanApplyId]').unbind('click').click(function () {
                        //清理内容
                        var $edit_box = $(".edit_box");
                        $edit_box.find('data').html('');
                        $edit_box.find(':input').val('');
                        $edit_box.show();
                        $.get("${ctx}/unspay/subContractDetail", {'loanApplyId': $(this).attr('data-loanApplyId')}, function (unspaySubContract) {
                            //填充数据
                            //修正日期数据
                            unspaySubContract.startDate = new Date(unspaySubContract.startDate).format('yyyy-MM-dd');
                            unspaySubContract.endDate = new Date(unspaySubContract.endDate).format('yyyy-MM-dd');
                            for(var property in unspaySubContract){
                                $edit_box.find('data[data-'+property+']').html(unspaySubContract[property]);
                                $edit_box.find(':input[name="'+property+'"]').val(unspaySubContract[property]);
                            }
                        });
                    });

                    $('#edit_sub_contract').click(function(){
                        $('#edit_sub_contract_table').submit();
                    });

                    $('.close_btn').click(function(){
                        $(".edit_box").hide();
                    });

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
                        <li class="cont">子协议导入</li>
                    </ul>
                </div>
            </div>
      <span id="box_upload_list">
      <div class="condition_a">
          <ul class="clearfix">
              <form id="page_form" action="${ctx}/unspay/subContractUpload" method="GET">
                  <li>
                      <label>文件名称</label>
                      <input name="filename" type="text" placeholder="导入文件名称" class="place_txt" value="${filename}"/>
                  </li>
                  <li>
                      <label>导入时间</label>
                      <input type="text" name="uploadStartDate" onClick="WdatePicker()"
                             value="<fmt:formatDate value="${uploadStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="uploadEndDate" onClick="WdatePicker()"
                             value="<fmt:formatDate value="${uploadEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:50px;">
                      <button id="page_btn" style="margin-left:20px;margin-right:10px;">搜索</button>
                  </li>
              </form>
              <li style="width:200px;float: right;">
                  <button id="upload_button">选择上传文件</button>
                  <form id="upload_form" method="post" action="${ctx}/unspay/subContractUpload"
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
              <th class="td_last">成功 | 失败 | 处理中</th>
          </tr>
          <c:forEach begin="0" end="${page.pageSize-1}" items="${page.result}" var="unspaySubContract">
              <tr height="45">
                  <td class="click_td">${unspaySubContract.filename}</td>
                  <td><fmt:formatDate value="${unspaySubContract.uploadDate}"
                                      pattern="yyyy-MM-dd"></fmt:formatDate></td>
                  <td>${unspaySubContract.staffName}</td>
                  <td>${unspaySubContract.count}</td>
                  <td class="td_last"><strong class="success">${unspaySubContract.success}</strong> | <strong
                          class="failur">${unspaySubContract.failur}</strong> | <strong
                          class="going">${unspaySubContract.process}</strong></td>
              </tr>
          </c:forEach>
          <c:forEach begin="${fn:length(page.result)}" end="9">
              <tr height="45">
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td class="td_last"></td>
              </tr>
          </c:forEach>
      </table>
      <p class="fund_page clearfix"> <span>
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
                  <label></label>
              </li>
              <li>总计：
                  <label></label>
              </li>
              <li>成功：
                  <label></label>
              </li>
              <li>失败：
                  <label></label>
              </li>
              <li>处理中：
                  <label></label>
              </li>
          </ul>
      </div>
      <table id="upload_detail_table" cellpadding="0" cellspacing="0" border="0" class="order_box">
          <tr height="50">
              <th>子协议编号</th>
              <th>发送时间</th>
              <th>进件编号</th>
              <th>客户姓名</th>
              <th>身份证号</th>
              <th>手机号码</th>
              <th>银行卡号</th>
              <th>银行名称</th>
              <th>开始时间</th>
              <th>结束时间</th>
              <th>扣款频率</th>
              <th>扣款次数限制</th>
              <th>状态码</th>
              <th>状态</th>
              <th class="td_last">操作</th>
          </tr>
      </table>
      <p class="fund_page clearfix"><span></span></p>
      </span></div>

        <div class="edit_box" style="left: 350px; top: 156px;">
            <h2>修改子协议<a href="#" class="close_btn">关闭</a></h2>

            <form action="${ctx}/unspay/subContractEdit" method="post">
                <table id="edit_sub_contract_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
                    <tr height="45">
                        <td><label>进件编号：</label>
                            <data data-loanApplyId></data>
                            <input name="loanApplyId" type="hidden"/>
                        </td>
                        <td><label>上传人：</label>
                            ${loginStaff.staffName}
                        </td>
                    </tr>
                    <tr height="45">
                        <td><label>姓名：</label>
                            <data data-name></data>
                        </td>
                        <td><label>身份证号：</label>
                            <data data-idCardNo></data>
                        </td>
                    </tr>
                    <tr height="45">
                        <td><label>手机号：</label>
                            <input name="phoneNo" type="text"/>
                        </td>
                        <td><label>银行卡号：</label> <input name="cardNo" type="text"/></td>
                    </tr>
                    <tr height="45">
                        <td><label>开始时间：</label><input type="text" name="startDate" onClick="WdatePicker()"/></td>
                        <td><label>结束时间：</label><input type="text" name="endDate" onClick="WdatePicker()"/></td>
                    </tr>
                    <tr height="45">
                        <td><label>扣款频率：</label><select name="cycle">
                            <option value=""></option>
                            <option value="1">每年</option>
                            <option value="2">每月</option>
                            <option value="3">每日</option>
                        </select></td>
                        <td><label>扣款次数：</label> <input name="triesLimit" type="text"></td>
                    </tr>
                </table>
                <p class="edit_p">
                    <button id="edit_sub_contract">修改</button>
                </p>
            </form>
        </div>
    </div>
</div>

<input id="message" type="hidden" value="${message}">

</body>
</html>
