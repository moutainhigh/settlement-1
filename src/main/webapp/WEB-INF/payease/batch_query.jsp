<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>首信易-批量查询</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/batch_query.js"></script>
</head>

<body>
<div class="tab_cont clearfix">
  <div class="cont_ri" id="ri_cont">
    <div class="sale_box">
      <div class="cont_ri_til clearfix">
        <div class="left_btn">
          <ul id="tabs_ul">
            <li class="cont">查询列表</li>
          </ul>
        </div>
      </div>
      <span id="box_upload_list">
          <div class="condition_a">
              <ul class="clearfix">
                  <li>
                    <label>起始用户</label>
                    <input type="text" placeholder="用户注册名" id="bRegName" class="place_txt" />
                  </li>
                  <li>
                    <label>结束用户</label>
                    <input type="text" placeholder="用户注册名" id="eRegName" class="place_txt" style="width:100px;"/>
                  </li>
                  <li style="margin-top: 10px; ">
                      <button style="margin-left:100px; width:80px;" onclick="searchByParam()">搜索</button>
                  </li>
              </ul>
          </div>


      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
        <tr height="30">
              <th rowspan="2">用户注册名</th>
              <th rowspan="2">用户姓名</th>
              <th rowspan="2">身份证号</th>
              <th rowspan="2">银行卡号</th>
              <th rowspan="2">银行名称</th>
              <th colspan="2">余额</th>
              <th rowspan="2">账户状态</th>
              <th rowspan="2" class="td_last">开户时间</th>
          </tr>
          <tr height="30">
              <th width="7%">投资户</th>
              <th width="7%">负债户</th>
          </tr>
        <tbody id="batchList">
                <c:forEach items="${pageVo.voList}" var="m">
                    <fmt:parseDate var="reqTime" value="${m.reqTime}" pattern="yyyy-MM-dd HH:mm"/>
                      <tr height="45">
                          <td>${m.user}</td>
                          <td>${m.userName}</td>
                          <td class="gap"><em>${fn:substring(m.id, 0,6)}</em><em>${fn:substring(m.id, 6,14)}</em><em>${fn:substring(m.id,14,fn:length(m.id))}</em></td>
                          <td class="gap"><em>${fn:substring(m.accNum, 0,4)}</em><em>${fn:substring(m.accNum, 4,8)}</em><em>${fn:substring(m.accNum, 8,12)}</em><em>${fn:substring(m.accNum,12,fn:length(m.accNum))}</em></td>
                          <td>${m.accBank}</td>
                          <td>${m.account1}</td>
                          <td>${m.account2}</td>
                          <td>${m.status}</td>
                          <td><fmt:formatDate value="${reqTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate> </td>
                      </tr>
                </c:forEach>
          </tbody>

      </table>

      <p class="fund_page clearfix">
          <span id="operNum">
              <a class="h_bg_width" href="#" num="1" onclick="searchByParam(this)">首页</a>
              <a href="#" num="${pageVo.currentPage-1}" onclick="searchByParam(this)"><i class="h_icon_prev"></i></a>
              <c:forEach var="i" begin="${pageVo.pageIndex.startIndex}" end="${pageVo.pageIndex.endIndex}">
                  <c:choose>
                      <c:when test="${i == pageVo.currentPage}">
                          <a href="#" class="current" num="${i}" onclick="searchByParam(this)">${i}</a>
                      </c:when>
                      <c:otherwise>
                          <a href="#"  num="${i}" onclick="searchByParam(this)">${i}</a>
                      </c:otherwise>
                  </c:choose>
              </c:forEach>
              <a href="#" num="${pageVo.currentPage+1}" onclick="searchByParam(this)"><i class="h_icon_next"></i></a>
              <a class="h_bg_width" href="#" num="${pageVo.pageCount}" onclick="searchByParam(this)">末页</a>
          </span></p>
      </span>
    </div>
  </div>
</div>
<div class="shadow" style="z-index: 100"></div>
<div class="shadow_img"></div>
</body>
</html>