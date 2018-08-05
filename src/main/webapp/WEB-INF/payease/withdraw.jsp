<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path"
       value="${ctx}/payease/trs001008/list?user=${user}&accName=${accName}&accNum=${accNum}&id=${id}&returnCode=${returnCode}&returnStartTime=${returnStartTime}&returnEndTime=${returnEndTime}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>首信易-提现</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //显示错误信息
            if($("#message").val()){
                alert($("#message").val());
            }

            //上传代扣文件
            $("#upload_button").click(function () {
                $("#upload_input").click();
            });
            $("#upload_input").change(function(){
                if($(this).val()){
                    $("#upload_form").submit();
                }
            });

            //格式化银行卡
            $('.bankCardNo').each(function () {
                $(this).html(formateBankcardNo($(this).html()));
            });

            $(".idCardNo").each(function () {
                $(this).html(formateIdCardNo($(this).html()));
            });
        })

        var formateBankcardNo = function (bankCardNo) {
            return bankCardNo.replace(/(\d{4})(?=\d)/g, "<bankcard style='padding-right: 4px;'>" + "$1" + "</bankcard>");
        }

        var formateIdCardNo = function (idNo) {
            return "<idcard style='padding-right: 4px;'>" + idNo.substring(0, 6) + "</idcard>" + "<idcard style='padding-right: 4px;'>" + idNo.substring(6, 14) + "</idcard>" + idNo.substring(14, 19);
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
                        <li class="cont">提现列表</li>
                    </ul>
                </div>
            </div>
      <span id="box_tab">
      <div class="condition_a">
          <form action="${ctx}/payease/trs001006/list" method="post">
              <ul class="clearfix">
                  <li>
                      <label>姓名</label>
                      <input type="text" placeholder="姓名" class="place_txt" name="accName" value="${accName}"/>
                  </li>
                  <li>
                      <label>银行卡号</label>
                      <input type="text" placeholder="银行卡号" class="place_txt" name="accNum" value="${accNum}"/>
                  </li>
                  <li style="margin-left:0px;">
                      <button id="upload_button" type="button" style="margin-left:358px;margin-right:20px;width:80px;">
                          导入
                      </button>
                      <a id="upload_templete" href="${ctx}/payease/templete?templete=首信易-提现模板.xls"
                         style="margin-left:20px;text-decoration: underline;">模板</a>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>用户注册名</label>
                      <input type="text" placeholder="用户注册名" class="place_txt" name="user" value="${user}"/>
                  </li>
                  <li>
                      <label>提现结果</label>
                      <select style="width:150px; margin:0 2px;" name="returnCode">
                          <option></option>
                          <option value="0000" <c:if test="${returnCode eq '0000'}">selected</c:if>>成&nbsp;&nbsp;功
                          </option>
                          <option value="0002" <c:if test="${returnCode eq '0002'}">selected</c:if>>处理中</option>
                          <option value="0222" <c:if test="${returnCode eq '0222'}">selected</c:if>>失&nbsp;&nbsp;败
                          </option>
                      </select>
                  </li>
                  <li>
                      <label>提现时间</label>
                      <input type="text" name="returnStartTime" onClick="WdatePicker()"
                             value="<fmt:formatDate value="${returnStartTime}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="returnEndTime" onClick="WdatePicker()"
                             value="<fmt:formatDate value="${returnEndTime}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:0px;">
                      <button style="margin-left:50px;margin-right:20px;width:80px;">搜索</button>
                  </li>
              </ul>
          </form>
          <form id="upload_form" action="${ctx}/payease/trs001006/upload" method="post" enctype="multipart/form-data">
              <input id="upload_input" type="file" name="importFile" accept="application/vnd.ms-excel" style="display:none;"/>
          </form>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="50">
              <th>流水号</th>
              <th>用户注册名</th>
              <th>客户姓名</th>
              <th>银行卡号</th>
              <th>银行名称</th>
              <th>提现金额</th>
              <th>提现日期</th>
              <th class="td_last">提现结果</th>
          </tr>
          <c:forEach items="${page.result}" var="trs001006">
              <tr height="45">
                  <td>${trs001006.serlNum}</td>
                  <td>${trs001006.user}</td>
                  <td>${trs001006.accName}</td>
                  <td class="bankCardNo">${trs001006.accNum}</td>
                  <td>${trs001006.accBank}</td>
                  <td>${trs001006.amount}</td>
                  <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${trs001006.returnTime}"></fmt:formatDate></td>
                  <td class="td_last">${trs001006.returnMsg}</td>
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
      </span></div>
    </div>
</div>
<div id="shadow" class="shadow" style="z-index: 100"></div>
<input id="message" type="hidden" name="message" value="${message}">
</body>
</html>