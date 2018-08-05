<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>首信易-转账</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/transter_account.js"></script>
</head>

<body>
<div class="tab_cont clearfix">
  <div class="cont_ri" id="ri_cont">
    <div class="sale_box">
      <div class="cont_ri_til clearfix">
        <div class="left_btn">
          <ul id="tabs_ul">
            <li class="cont">转账列表</li>
          </ul>
        </div>
      </div>
      <span id="box_upload_list">
          <div class="condition_a">
              <ul class="clearfix">
                  <li>
                    <label>转出方注册名</label>
                    <input type="text" placeholder="转出方注册名" id="outRegName" class="place_txt" />
                  </li>
                  <li>
                    <label>转出方姓名</label>
                    <input type="text" placeholder="转出方姓名" id="outRelName" class="place_txt" style="width:100px;"/>
                  </li>
                  <li>
                    <label>转账时间</label>
                    <input type="text" id="date1" onClick="WdatePicker()" />
                    -
                    <input type="text" id="date2" onClick="WdatePicker()" />
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                    <label>转入方注册名</label>
                    <input type="text" placeholder="转入方注册名" id="inRegName" class="place_txt" />
                  </li>
                  <li>
                    <label>转入方姓名</label>
                    <input type="text" placeholder="转入方姓名" id="inRelName" class="place_txt" style="width:100px;"/>
                  </li>
                  <li>
                    <label>转账结果</label>
                    <select style="width:150px; margin:0 2px;" id="returnCode">
                      <option value="">&nbsp;</option>
                      <option value="0000">成&nbsp;&nbsp;功</option>
                      <option value="0222">失&nbsp;&nbsp;败</option>
                    </select>
                  </li>
                  <li style="margin-left:0px;">
                    <button style="margin-left:50px;margin-right:20px;width:80px;" onclick="searchByParam()">搜索</button>
                    <button style="margin-left:20px;margin-right:20px;width:80px;" id="add_newTransfer">新增</button>
                    <button style="margin-left:20px;margin-right:50px;width:80px;" id="importTransfer">导入</button>
                    <a href="#" style="text-decoration: underline;" onclick="downloadExc('/templete/','首信易-转账模板.xls')">模板</a>
                      <form id="upload_form" method="post" action="${ctx}/payease/uploadTransfer" enctype="multipart/form-data">
                          <input id="upload_input" name="importFile" type="file" accept="application/vnd.ms-excel"
                                 style="display:none;"/>
                      </form>
                      <form id="download_form">
                          <input type="hidden" name="path" id="download_path"/>
                      </form>
                  </li>
            </ul>
          </div>


      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
        <tr height="25">
          <th rowspan="2">流水号</th>
          <th colspan="2">转出方</th>
          <th rowspan="2">转账金额</th>
          <th rowspan="2">转账日期</th>
          <th colspan="2">转入方</th>
          <th rowspan="2" class="td_last">转账结果</th>
        </tr>
        <tr height="25" style="">
          <th width="15%">注册名</th>
          <th width="15%">姓名</th>
          <th width="15%">注册名</th>
          <th width="15%">姓名</th>
        </tr>
        <tbody id="transferList">
                <c:forEach items="${pageVo.voList}" var="m">
                      <tr height="45">
                          <td>${m.serlNum}</td>
                          <td>${m.transferOutUser}</td>
                          <td>${m.outName}</td>
                          <td>${m.transferAmount}</td>
                          <td><fmt:formatDate value="${m.reqTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate> </td>
                          <td>${m.transferInUser}</td>
                          <td>${m.inName}</td>

                            <c:if test="${m.returnCode=='0000' || m.result=='成功'}"><td class="td_last" style="color: green">成功</td></c:if>
                            <c:if test="${m.returnCode=='0222' || m.result=='失败'}"><td class="td_last" style="color: red">失败</td></c:if>
                            <c:if test="${m.returnCode=='0006'}"><td class="td_last"  >${m.result}</td></c:if>
                      </tr>
                </c:forEach>
          </tbody>

      </table>

      <p class="fund_page clearfix">
          <span id="transOperNum">
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
<div id="transfer_shadow" class="shadow" style="z-index: 100"></div>
<div class="shadow_img"></div>
<div id="transfer_info_box" class="edit_box" style="left: 30%; top: 20%;">
    <h2 style="text-align: center; font-size: 16px;">新增转账信息<a href="javascript:;"  class="close_btn"><img style="margin-top: 5px;" src="../images/xx.png"/></a></h2>
    <form>
        <table id="new_user_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
            <tr height="45">
                <td colspan="2"><label style="margin-left: 70px;">转出方</label></td>
                <td class="w"><label class="m">|</label></td>
                <td colspan="2"><label style="margin-left: 70px;">转入方</label></td>
            </tr>
            <tr height="45">
                <td colspan="2"><label>用户注册名：</label>
                    <input name="user" type="text" id="transfer_outRegName" />
                </td>
                <td class="w"><label class="m">|</label></td>
                <td colspan="2"><label>用户注册名：</label>
                    <input name="user" type="text" id="transfer_inRegName"/>
                </td>
            </tr>
            <tr height="45">
                <td colspan="2"><label>真实姓名：</label><font style="text-align: center;" id="transfer_outRelName"></font> </td>
                <td class="w"><label class="m">|</label></td>
                <td colspan="2"><label>真实姓名：</label><font style="text-align: center;" id="transfer_inRelName"></font> </td>
            </tr>
            <tr height="45">
                <td width="120px;"><label>账户类型：</label></td>
                <td>
                    <input name="transfer_out" style="width: 10px;height: 10px;" value="1" checked="checked" type="radio"/>投资账户
                    &nbsp;&nbsp;&nbsp;
                    <input name="transfer_out" style="width: 10px;height: 10px;" value="3" type="radio"/><font id="f_out">负债账户</font>
                </td>
                <td class="w"><label class="m">|</label></td>
                <td width="120px;"><label>账户类型：</label></td>
                <td>
                    <input name="transfer_in" style="width: 10px;height: 10px;" value="0" checked="checked" type="radio"/>投资账户
                    &nbsp;&nbsp;&nbsp;
                    <input name="transfer_in" style="width: 10px;height: 10px;" value="2" type="radio"/><font id=f_in>负债账户</font>
                </td>
            </tr>
            <tr height="45">
                <td colspan="2"><label>账户余额：</label><font style="text-align: center;" id="transfer_outBalance"></font> </td>
                <td class="w"><label class="m">|</label></td>
                <td colspan="2"><label>账户余额：</label><font style="text-align: center;" id="transfer_inBalance"></font> </td>
            </tr>
            <tr>
                <td></td>
                <td colspan="3"><label style="margin-right: 10px;">转账金额：</label><input id="transfer_amount" type="text"/>&nbsp;&nbsp;元</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td colspan="3"><label style="margin-right: 10px;">确认金额：</label><input id="transfer_amount_re" type="text"/>&nbsp;&nbsp;元</td>
                <td></td>
            </tr>
        </table>
        <p class="edit_p">
            <button id="new_transfer_btn" type="button">添加</button>
        </p>
    </form>
</div>
</body>
</html>