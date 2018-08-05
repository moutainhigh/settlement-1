<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>首信易-银行信息</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
</head>

<body>
<div class="tab_cont clearfix">
  <div class="cont_ri" id="ri_cont">
    <div class="sale_box">
      <div class="cont_ri_til clearfix">
        <div class="left_btn">
          <ul id="tabs_ul">
            <li class="cont" num="1">银行限额列表</li>
          </ul>
        </div>
      </div>
      <span id="box_upload_list">

            <%--<div class="condition_a">--%>
          <%--<ul class="clearfix">--%>
              <%--<li>--%>
                  <%--<label style="width: 60px;">姓名</label>--%>
                  <%--<input type="text" placeholder="姓名" id="p_name" class="place_txt" style="width:100px;"/>--%>
              <%--</li>--%>
              <%--<li>--%>
                  <%--<label style="width: 60px;">身份证号</label>--%>
                  <%--<input type="text" placeholder="身份证号" id="p_idCard" class="place_txt" style="width:160px;"/>--%>
              <%--</li>--%>
              <%--<li>--%>
                  <%--<label style="width: 60px;">认证时间</label>--%>
                  <%--<input type="text" id="p_date1" onClick="WdatePicker()"/>--%>
                  <%-----%>
                  <%--<input type="text"  id="p_date2" onClick="WdatePicker()"/>--%>
              <%--</li>--%>
              <%--<li>--%>
                  <%--<label style="width: 60px;">认证状态</label>--%>
                  <%--<select style="width:80px; margin:0 2px;" id="p_status">--%>
                      <%--<option value="">&nbsp;</option>--%>
                      <%--<option value="1">成功</option>--%>
                      <%--<option value="2">失败</option>--%>
                  <%--</select>--%>
              <%--</li>--%>
              <%--<li style="margin-left:0px;">--%>
                  <%--<button style="margin-left:100px; width:80px;" onclick="searchByParam()">搜索</button>--%>
                  <%--<button id="new_button" style="margin-left:100px;width:80px;">新增</button>--%>
                  <%--<button id="upload_button" style="margin-left:20px;width:80px;">导入</button>--%>
                  <%--<a href="#" style="text-decoration: underline;" onclick="downloadExc('/templete/','首信易-实名认证模板.xls')">模板</a>--%>
                  <%--<form id="upload_form" method="post" action="${ctx}/payease/uploadIdCardVerify" enctype="multipart/form-data">--%>
                      <%--<input id="upload_input" name="importFile" type="file" accept="application/vnd.ms-excel"--%>
                             <%--style="display:none;"/>--%>
                  <%--</form>--%>
                  <%--<form id="download_form">--%>
                      <%--<input type="hidden" name="path" id="download_path"/>--%>
                  <%--</form>--%>
              <%--</li>--%>
          <%--</ul>--%>
      <%--</div>--%>


      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="35">
              <th rowspan="2">银行代码</th>
              <th rowspan="2">银行简称</th>
              <th rowspan="2">银行全称</th>
              <th colspan="2">首信易代扣</th>
              <th class="td_last" colspan="2">银生宝代扣</th>
          </tr>
          <tr height="35">
              <th>单笔限额</th>
              <th>每日限额</th>
              <th>单笔限额</th>
              <th>每日限额</th>
          </tr>
          <tbody id="bankList">
                <c:forEach items="${pageVo.voList}" var="m">
                      <tr height="45">
                          <td>${m.code}</td>
                          <td>${m.shortName}</td>
                          <td>${m.fullName}</td>
                          <td>${m.ps}万</td>
                          <td>${m.pd}万</td>
                          <td>${m.us}万</td>
                          <td>${m.ud}万</td>
                      </tr>
                </c:forEach>
          </tbody>

      </table>

      <%--<p class="fund_page clearfix">--%>
          <%--<span id="operNum">--%>
              <%--<a class="h_bg_width" href="#" num="1" onclick="searchByParam(this)">首页</a>--%>
              <%--<a href="#" num="${pageVo.currentPage-1}" onclick="searchByParam(this)"><i class="h_icon_prev"></i></a>--%>
              <%--<c:forEach var="i" begin="${pageVo.pageIndex.startIndex}" end="${pageVo.pageIndex.endIndex}">--%>
                  <%--<c:choose>--%>
                      <%--<c:when test="${i == pageVo.currentPage}">--%>
                          <%--<a href="#" class="current" num="${i}" onclick="searchByParam(this)">${i}</a>--%>
                      <%--</c:when>--%>
                      <%--<c:otherwise>--%>
                          <%--<a href="#"  num="${i}" onclick="searchByParam(this)">${i}</a>--%>
                      <%--</c:otherwise>--%>
                  <%--</c:choose>--%>
              <%--</c:forEach>--%>
              <%--<a href="#" num="${pageVo.currentPage+1}" onclick="searchByParam(this)"><i class="h_icon_next"></i></a>--%>
              <%--<a class="h_bg_width" href="#" num="${pageVo.pageCount}" onclick="searchByParam(this)">末页</a>--%>
          <%--</span></p>--%>
      </span>
    </div>
  </div>
</div>
<%--<div id="shadow" class="shadow" style="z-index: 100"></div>--%>
<%--<div id="user_info_box" class="edit_box" style="left: 30%; top: 20%;">--%>
  <%--<h2>新增实名认证<a href="javascript:;" class="close_btn">关闭</a></h2>--%>
  <%--<form>--%>
    <%--<table id="new_user_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">--%>
      <%--<tr height="45">--%>
        <%--<td><label>真实姓名：</label>--%>
          <%--<input id="add_username" type="text"/>--%>
        <%--</td>--%>
        <%--<td><label>身份证号：</label>--%>
          <%--<input id="add_idcard" type="text"/>--%>
        <%--</td>--%>
      <%--</tr>--%>
    <%--</table>--%>
    <%--<p class="edit_p">--%>
      <%--<button id="new_user_btn" type="button">认证</button>--%>
    <%--</p>--%>
  <%--</form>--%>
<%--</div>--%>
</body>
</html>