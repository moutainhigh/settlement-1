<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_params"
       value="outTradeNo=${outTradeNo}&tradeStatus=${tradeStatus}&orderTimeStartDate=${orderTimeStartDate}&orderTimeEndDate=${orderTimeEndDate}"/>
<c:set var="search_path"
       value="${ctx}/chanpay/g10002/list?${search_params}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>畅捷通-代付</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //搜索
            $('#search_deduct_list_btn').click(function () {
                $('#deduct_list_form').submit();
            });
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
                        <li class="cont">代付记录</li>
                    </ul>
                </div>
            </div>
      <span id="box_tab">
      <div class="condition_a">
          <form id="deduct_list_form" action="${ctx}/chanpay/g10002/list" method="GET">
              <ul class="clearfix">
                  <li>
                      <label>客户姓名</label>
                      <input name="accountName" type="text" placeholder="客户姓名" class="place_txt"
                             value="${accountName}"/>
                  </li>
                  <li>
                      <label>订单号</label>
                      <input name="reqSn" type="text" placeholder="订单号" class="place_txt"style="width:200px;"
                             value="${reqSn}"/>
                  </li>
                  <li>
                      <label>银行卡号</label>
                      <input name="accountNo" type="text" placeholder="银行卡号" class="place_txt"style="width:200px;"
                             value="${accountNo}"/>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>代付结果</label>
                      <select name="tradeStatus" style="width:150px; margin:0 2px;" value="${tradeStatus}">
                          <option></option>
                          <option value="TRADE_PROCESS" <c:if test="${'TRADE_PROCESS' eq tradeStatus}">selected</c:if>>处理中</option>
                          <option value="TRADE_SUCCESS" <c:if test="${'TRADE_SUCCESS' eq tradeStatus}">selected</c:if>>成&nbsp;&nbsp;功</option>
                          <option value="TRADE_FAILURE" <c:if test="${'TRADE_FAILURE' eq tradeStatus}">selected</c:if>>失&nbsp;&nbsp;败</option>
                      </select>
                  </li>
                  <li>
                      <label>代付时间</label>
                      <input type="text" name="timestampStartDate" onClick="WdatePicker()" value="<fmt:formatDate value="${timestampStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="timestampEndDate" onClick="WdatePicker()" value="<fmt:formatDate value="${timestampEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:0px;">
                      <button id="search_deduct_list_btn" style="margin-left:50px;margin-right:20px;width:80px;">搜索</button>
                  </li>
              </ul>
          </form>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="50">
              <th>订单编号</th>
              <th>订单时间</th>
              <th>客户名称</th>
              <th>银行名称</th>
              <th>开户行</th>
              <th>银行卡号</th>
              <th>支付金额</th>
              <th>用途</th>
              <th class="td_last">支付结果</th>
          </tr>
          <c:forEach items="${page.result}" var="g10002Bean">
              <tr height="45">
                  <td>${g10002Bean.reqSn}</td>
                  <td><fmt:formatDate value="${g10002Bean.timestamp}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                  <td>${g10002Bean.accountName}</td>
                  <td>${g10002Bean.bankGeneralName}</td>
                  <td>${g10002Bean.bankName}</td>
                  <td>${g10002Bean.accountNo}</td>
                  <td style="font-weight: bold;">
                      <fmt:formatNumber pattern="###,###,###" value="${g10002Bean.amount}"></fmt:formatNumber><small><fmt:formatNumber pattern=".00" value="${g10002Bean.amount % 1.0}"></fmt:formatNumber></small>
                  </td>
                  <td>${g10002Bean.postscript}</td>
                  <td class="td_last <c:choose>
                                    <c:when test="${empty g10002Bean.retCode or g10002Bean.tradeCode eq '0001' or g10002Bean.tradeCode eq '0002'}">going</c:when>
                                    <c:when test="${g10002Bean.tradeCode eq '0000'}">success</c:when>
                                    <c:otherwise>failur</c:otherwise>
                                  </c:choose>">
                      <c:choose>
                          <c:when test="${empty g10002Bean.tradeCode or g10002Bean.tradeCode eq '0001' or g10002Bean.tradeCode eq '0002'}">
                              交易中
                          </c:when>
                          <c:when test="${g10002Bean.tradeCode eq '0000'}">
                              支付成功
                          </c:when>
                          <c:otherwise>
                              ${g10002Bean.tradeMsg}
                          </c:otherwise>
                      </c:choose>
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
</body>
</html>