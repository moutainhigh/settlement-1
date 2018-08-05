<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_params"
       value="name=${name}&phoneNo=${phoneNo}&idCardNo=${idCardNo}&filename=${filename}&payResult=${payResult}&responseStartDate=${responseStartDate}&responseEndDate=${responseEndDate}"/>
<c:set var="search_path"
       value="${ctx}/unspay/payList?${search_params}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>银生宝-列表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //搜索
            $('#search_pay_list_btn').click(function () {
                $('#pay_list_form').submit();
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
                        <li class="cont">银生宝列表</li>
                    </ul>
                </div>
            </div>
      <span id="box_tab">
      <div class="condition_a">
          <form id="pay_list_form" action="${ctx}/unspay/payList" method="GET">
              <ul class="clearfix">
                  <li>
                      <label>姓名</label>
                      <input name="name" type="text" placeholder="姓名" class="place_txt" value="${name}"/>
                  </li>
                  <li>
                      <label>手机号</label>
                      <input name="phoneNo" type="text" placeholder="手机号" class="place_txt" value="${phoneNo}"/>
                  </li>
                  <li>
                      <label>身份证号</label>
                      <input name="idCardNo" type="text" placeholder="身份证号" class="place_txt" style="width:200px;"
                             value="${idCardNo}"/>
                  </li>
                  <li>
                      <label>银行卡号</label>
                      <input name="cardNo" type="text" placeholder="银行卡号" class="place_txt" value="${cardNo}"/>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>文件名称</label>
                      <input name="filename" type="text" placeholder="导入文件名称" class="place_txt" value="${filename}"/>
                  </li>
                  <li>
                      <label>扣款结果</label>
                      <select name="payResult" style="width:150px; margin:0 2px;" value="${payResult}">
                          <option></option>
                          <option value="0000" <c:if test="${'0000' eq payResult}">selected</c:if>>成&nbsp;&nbsp;功</option>
                          <option value="10" <c:if test="${'10' eq payResult}">selected</c:if>>处理中</option>
                          <option value="20" <c:if test="${'20' eq payResult}">selected</c:if>>失&nbsp;&nbsp;败</option>
                      </select>
                  </li>
                  <li>
                      <label>扣款时间</label>
                      <input type="text" name="responseStartDate" onClick="WdatePicker()" value="<fmt:formatDate value="${responseStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="responseEndDate" onClick="WdatePicker()" value="<fmt:formatDate value="${responseEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:0px;">
                      <button id="search_pay_list_btn" style="margin-left:50px;margin-right:20px;width:80px;">搜索</button>
                      <button id="download_pay_list_btn" style="margin-left:20px;margin-right:50px;width:80px;"><a style="color: black;" href="${ctx}/unspay/payDownload?${search_params}">导出</a></button>
                  </li>
              </ul>
          </form>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="50">
              <th>订单编号</th>
              <th>文件名称</th>
              <th>进件编号</th>
              <th>分期编号</th>
              <th>客户姓名</th>
              <th>手机号码</th>
              <th>银行卡号</th>
              <th>银行名称</th>
              <th>付款金额</th>
              <th>计划时间</th>
              <th>发送时间</th>
              <th>返回时间</th>
              <th>状态码</th>
              <th>付款结果</th>
              <th class="td_last">付款原因</th>
          </tr>
          <c:forEach items="${page.result}" var="unspayPay">
              <tr height="45">
                  <td>${unspayPay.orderId}</td>
                  <td>${unspayPay.filename}</td>
                  <td>${unspayPay.loanApplyId}</td>
                  <td>${unspayPay.repayPhaseId}</td>
                  <td>${unspayPay.name}</td>
                  <td>${unspayPay.phoneNo}</td>
                  <td>${unspayPay.cardNo}</td>
                  <td><yh:bank bankCode="${unspayPay.bankCode}"/></td>
                  <td style="font-weight: bold;">
                      <fmt:formatNumber pattern="###,###,###" value="${unspayPay.amount}"></fmt:formatNumber><small><fmt:formatNumber pattern=".00" value="${unspayPay.amount % 1.0}"></fmt:formatNumber></small>
                  </td>
                  <td><fmt:formatDate value="${unspayPay.planDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                  <td><fmt:formatDate value="${unspayPay.sendDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                  <td><fmt:formatDate value="${unspayPay.responseDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                  <td>${unspayPay.payResult}</td>
                  <td class="<c:choose><c:when test="${unspayPay.payResult eq '0000'}">success</c:when>
                      <c:when test="${unspayPay.payResult eq '10'}">going</c:when>
                      <c:otherwise>failur</c:otherwise>
                  </c:choose>">
                      <c:choose>
                          <c:when test="${unspayPay.payResult eq '0000'}">
                              成功
                          </c:when>
                          <c:when test="${unspayPay.payResult eq '10'}">
                              处理中
                          </c:when>
                          <c:otherwise>
                              ${unspayPay.desc}
                          </c:otherwise>
                      </c:choose>
                  </td>
                  <td class="td_last">${unspayPay.purpose}</td>
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