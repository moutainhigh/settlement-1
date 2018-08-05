<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path"
       value="${ctx}/payease/trs001002/list?v_merdata4=${v_merdata4}&v_merdata3=${v_merdata3}&v_merdata5=${v_merdata5}&v_ymd_start=${v_ymd_start}&v_ymd_end=${v_ymd_end}&v_pstatus=${v_pstatus}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>首信易-充值</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {

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
                        <li class="cont">充值列表</li>
                    </ul>
                </div>
            </div>
      <span id="box_tab">
      <div class="condition_a">
          <form action="${ctx}/payease/trs001002/list" method="post">
              <ul class="clearfix">
                  <li>
                      <label>姓名</label>
                      <input type="text" placeholder="姓名" class="place_txt" name="v_merdata4" value="${v_merdata4}"/>
                  </li>
                  <li>
                      <label>身份证号</label>
                      <input type="text" placeholder="身份证号" class="place_txt" style="width:200px;" name="v_merdata3"
                             value="${v_merdata3}"/>
                  </li>
                  <li>
                      <label>充值结果</label>
                      <select style="width:150px; margin:0 2px;" name="v_pstatus">
                          <option></option>
                          <option value="20" <c:if test="${v_pstatus eq '0000'}">selected</c:if>>成&nbsp;&nbsp;功
                          </option>
                          <option value="1" <c:if test="${v_pstatus eq '0002'}">selected</c:if>>处理中</option>
                          <option value="30" <c:if test="${v_pstatus eq '0222'}">selected</c:if>>失&nbsp;&nbsp;败
                          </option>
                      </select>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>手机号</label>
                      <input type="text" placeholder="手机号" class="place_txt" name="v_merdata5" value="${v_merdata5}"/>
                  </li>
                  <li>
                      <label>充值时间</label>
                      <input type="text" name="v_ymd_start" onClick="WdatePicker()"
                             value="<fmt:formatDate value="${v_ymd_start}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                      -
                      <input type="text" name="v_ymd_end" onClick="WdatePicker()"
                             value="<fmt:formatDate value="${v_ymd_end}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                  </li>
                  <li style="margin-left:0px;">
                      <button style="margin-left:50px;margin-right:20px;width:80px;">搜索</button>
                  </li>
              </ul>
          </form>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
          <tr height="50">
              <th>订单编号</th>
              <th>客户姓名</th>
              <th>身份证号</th>
              <th>手机号</th>
              <th>充值金额</th>
              <th>充值日期</th>
              <th class="td_last">充值结果</th>
          </tr>
          <c:forEach items="${page.result}" var="recharge">
              <tr height="45">
                  <td>${recharge.v_oid}</td>
                  <td>${recharge.v_merdata4}</td>
                  <td class="idCardNo">${recharge.v_merdata3}</td>
                  <td>${recharge.v_merdata5}</td>
                  <td>${recharge.v_amount}</td>
                  <td>${recharge.v_ymd}</td>
                  <td class="td_last">${recharge.v_pstring}</td>
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
</body>
</html>