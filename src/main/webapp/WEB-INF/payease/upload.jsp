<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>首信易-导入</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<div class="tab_cont clearfix">
  <div class="cont_ri" id="ri_cont">
    <div class="sale_box">
      <div class="cont_ri_til clearfix">
        <div class="left_btn">
          <ul id="tabs_ul">
            <li class="cont">首信易导入</li>
          </ul>
        </div>
      </div>
      <span id="box_tab">
      <div class="condition_a">
        <ul class="clearfix">
          <li>
            <label>文件名称</label>
            <input type="text" placeholder="导入文件名称" class="place_txt" />
          </li>
          <li>
            <label>操作人</label>
            <input type="text" placeholder="操作人姓名" class="place_txt" />
          </li>
          <li>
            <label>导入时间</label>
            <input type="text" name="" onClick="WdatePicker()" />
            -
            <input type="text" name="" onClick="WdatePicker()" />
          </li>
          <li style="margin-left:50px;">
            <button style="margin-left:20px;margin-right:10px;">搜素</button>
            <button style="margin-left:10px;margin-right:20px;">导入</button>
          </li>
        </ul>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
        <tr height="50">
          <th>文件名称</th>
          <th>导入日期</th>
          <th>操作人</th>
          <th>成功数</th>
          <th>处理中</th>
          <th>失败数</th>
          <th class="td_last">总计</th>
        </tr>
        <tr height="45">
          <td>201505050001.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>22</td>
          <td style="color:#FC0">10</td>
          <td style="color:#F90">1</td>
          <td class="td_last">31</td>
        </tr>
        <tr height="45">
          <td>201505050002.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>22</td>
          <td style="color:#FC0">10</td>
          <td style="color:#F90">1</td>
          <td class="td_last">31</td>
        </tr>
        <tr height="45">
          <td>201505050003.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>22</td>
          <td style="color:#FC0">10</td>
          <td style="color:#F90">1</td>
          <td class="td_last">31</td>
        </tr>
        <tr height="45">
          <td>201505050004.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>22</td>
          <td style="color:#FC0">10</td>
          <td style="color:#F90">1</td>
          <td class="td_last">31</td>
        </tr>
        <tr height="45">
          <td>201505050005.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>22</td>
          <td style="color:#FC0">10</td>
          <td style="color:#F90">1</td>
          <td class="td_last">31</td>
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
      <p class="fund_page clearfix"> <span><a class="h_bg_width" href="#">首页</a> <a href="#"><i class="h_icon_prev"></i></a> <a class="current" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a href="#"><i class="h_icon_next"></i></a> <a class="h_bg_width" href="#">末页</a></span> </p>
      </span> </div>
  </div>
</div>
</body>
</html>