<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path" value="${ctx}/chanpay/g60001/list?reqSn=${reqSn}&bankGeneralName=${bankGeneralName}&bankCode=${bankCode}&accountNo=${accountNo}&accountName=${accountName}&retCode=${retCode}&timestampStartDate=${timestampStartDate}&timestampEndDate=${timestampEndDate}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>畅捷通-认证</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //查询
            $('#search_sub_contract_list_btn').click(function () {
                $('#sub_contract_list_form').submit();
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
                        <li class="cont">畅捷通认证列表</li>
                    </ul>
                </div>
            </div>
      <span id="box_tab">
      <div class="condition_a">
          <form id="sub_contract_list_form" action="${ctx}/chanpay/g60001/list" method="GET">
              <ul class="clearfix">
                  <li>
                      <label>客户姓名</label>
                      <input name="accountName" type="text" placeholder="姓名" class="place_txt" value="${accountName}"/>
                  </li>
                  <li>
                      <label>银行名称</label>
                      <input name="bankGeneralName" type="text" placeholder="银行名称" class="place_txt" value="${bankGeneralName}"/>
                  </li>
                  <li>
                      <label>开户行号</label>
                      <input name="bankCode" type="text" placeholder="开户行号" class="place_txt" style="width:200px;" value="${bankCode}"/>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>认证状态</label>
                      <select name="retCode" style="width:150px;"  value="${retCode}">
                          <option></option>
                          <option value="0000" <c:if test="${'0000' eq retCode}">selected</c:if>>成&nbsp;&nbsp;功</option>
                          <option value="1000" <c:if test="${!empty retCode and '0000' ne retCode}">selected</c:if>>失&nbsp;&nbsp;败</option>
                      </select>
                  </li>
                  <li>
                      <label>银行卡号</label>
                      <input name="accountNo" type="text" placeholder="银行卡号" class="place_txt" value="${accountNo}"/>
                  </li>
                  <li>
                      <label>发送时间</label>
                      <input type="text" name="timestampStartDate" onClick="WdatePicker()" value="<fmt:formatDate value="${timestampStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="timestampEndDate" onClick="WdatePicker()" value="<fmt:formatDate value="${timestampEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:0px;">
                      <button id="search_sub_contract_list_btn" style="margin-left:50px;margin-right:20px;width:80px;">
                          搜索
                      </button>
                  </li>
              </ul>
          </form>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="50">
              <th>交易请求号</th>
              <th>发送时间</th>
              <th>银行名称</th>
              <th>开户行号</th>
              <th>银行卡号</th>
              <th>客户姓名</th>
              <th>返回代码</th>
              <th class="td_last">认证状态</th>
          </tr>
          <c:forEach items="${page.result}" var="g60001">
              <tr height="45">
                  <td>${g60001.reqSn}</td>
                  <td><fmt:formatDate value="${g60001.timestamp}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                  <td>${g60001.bankGeneralName}</td>
                  <td>${g60001.bankCode}</td>
                  <td>${g60001.accountNo}</td>
                  <td>${g60001.accountName}</td>
                  <td>${g60001.retCode}</td>
                  <td class="td_last <c:choose>
                          <c:when test="${g60001.retCode eq '0000'}">
                              success
                          </c:when>
                          <c:when test="${empty g60001.retCode}">
                              going
                          </c:when>
                          <c:otherwise>
                              failur
                          </c:otherwise>
                      </c:choose>">
                      <c:choose>
                          <c:when test="${g60001.retCode eq '0000'}">
                              成功
                          </c:when>
                          <c:when test="${empty g60001.retCode}">
                              处理中
                          </c:when>
                          <c:otherwise>
                              ${g60001.errMsg}
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
