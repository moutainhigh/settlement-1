<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>首信易-列表</title>
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
            <li class="cont">首信易列表</li>
          </ul>
        </div>
      </div>
      <span id="box_tab">
      <div class="condition_a">
        <ul class="clearfix">
          <li>
            <label>姓名</label>
            <input type="text" placeholder="姓名" class="place_txt" />
          </li>
          <li>
            <label>手机号</label>
            <input type="text" placeholder="手机号" class="place_txt" />
          </li>
          <li>
            <label>身份证号</label>
            <input type="text" placeholder="身份证号" class="place_txt" style="width:200px;"/>
          </li>
          <li>
            <label>银行卡号</label>
            <input type="text" placeholder="银行卡号" class="place_txt" />
          </li>
        </ul>
        <ul class="clearfix">
          <li>
            <label>文件名称</label>
            <input type="text" placeholder="导入文件名称" class="place_txt" />
          </li>
          <li>
            <label>扣款结果</label>
            <select style="width:150px; margin:0 2px;">
              <option></option>
              <option>成&nbsp;&nbsp;功</option>
              <option>处理中</option>
              <option>失&nbsp;&nbsp;败</option>
            </select>
          </li>
          <li>
            <label>扣款时间</label>
            <input type="text" name="" onClick="WdatePicker()" />
            -
            <input type="text" name="" onClick="WdatePicker()" />
          </li>
          <li style="margin-left:0px;">
            <button style="margin-left:50px;margin-right:20px;width:80px;">搜素</button>
            <button style="margin-left:20px;margin-right:50px;width:80px;">导出</button>
          </li>
        </ul>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
        <tr height="50">
          <th>订单编号</th>
          <th>文件名称</th>
          <th>扣款日期</th>
          <th>客户姓名</th>
          <th>身份证号</th>
          <th>手机号码</th>
          <th>银行卡号</th>
          <th>扣款金额</th>
          <th>扣款目的</th>
          <th class="td_last">扣款结果</th>
        </tr>
        <tr height="45">
          <td>201505050001</td>
          <td>201505050001.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>11010219560203****</td>
          <td>135****6090</td>
          <td>622202*****5623</td>
          <td>10000</td>
          <td>正常还款</td>
          <td class="td_last">成功</td>
        </tr>
        <tr height="45">
          <td>201505050002</td>
          <td>201505050001.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>11010219560203****</td>
          <td>135****6090</td>
          <td>622202*****5623</td>
          <td>10000</td>
          <td>正常还款</td>
          <td class="td_last" style="color:#F90">失败</td>
        </tr>
        <tr height="45">
          <td>201505050003</td>
          <td>201505050001.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>11010219560203****</td>
          <td>135****6090</td>
          <td>622202*****5623</td>
          <td>10000</td>
          <td style="color:#F90">逾期还款</td>
          <td class="td_last" style="color:#FC0">处理中</td>
        </tr>
        <tr height="45">
          <td>201505050004</td>
          <td>201505050001.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>11010219560203****</td>
          <td>135****6090</td>
          <td>622202*****5623</td>
          <td>10000</td>
          <td>正常还款</td>
          <td class="td_last">成功</td>
        </tr>
        <tr height="45">
          <td>201505050005</td>
          <td>201505050001.xlsx</td>
          <td>2015-5-5</td>
          <td>成志超</td>
          <td>11010219560203****</td>
          <td>135****6090</td>
          <td>622202*****5623</td>
          <td>10000</td>
          <td>正常还款</td>
          <td class="td_last">成功</td>
        </tr>
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
      </table>
      <p class="fund_page clearfix"> <span><a class="h_bg_width" href="#">首页</a> <a href="#"><i class="h_icon_prev"></i></a> <a class="current" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a href="#"><i class="h_icon_next"></i></a> <a class="h_bg_width" href="#">末页</a></span> </p>
      </span> </div>
  </div>
</div>
</body>
</html>