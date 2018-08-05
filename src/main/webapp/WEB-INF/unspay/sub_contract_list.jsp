<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path" value="${ctx}/unspay/subContractList?name=${name}&phoneNo=${phoneNo}&idCardNo=${idCardNo}&filename=${filename}&signStatus=${signStatus}&cardNo=${cardNo}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>银生宝-列表</title>
    <link rel="stylesheet" type="text/css" href="../css/sale.css">
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
                        <li class="cont">银生宝子协议列表</li>
                    </ul>
                </div>
            </div>
      <span id="box_tab">
      <div class="condition_a">
          <form id="sub_contract_list_form" action="${ctx}/unspay/subContractList" method="GET">
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
                      <input name="idCardNo" type="text" placeholder="身份证号" class="place_txt" style="width:200px;" value="${idCardNo}"/>
                  </li>
              </ul>
              <ul class="clearfix">
                  <li>
                      <label>文件名称</label>
                      <input name="filename" type="text" placeholder="导入文件名称" class="place_txt" value="${filename}"/>
                  </li>
                  <li>
                      <label>协议状态</label>
                      <select name="signStatus" style="width:150px; margin:0 2px;"  value="${signStatus}">
                          <option></option>
                          <option value="0000" <c:if test="${'0000' eq signStatus}">selected</c:if>>成&nbsp;&nbsp;功</option>
                          <option value="10" <c:if test="${'10' eq signStatus}">selected</c:if>>处理中</option>
                          <option value="20" <c:if test="${'20' eq signStatus}">selected</c:if>>失&nbsp;&nbsp;败</option>
                      </select>
                  </li>
                  <li>
                      <label>银行卡号</label>
                      <input name="cardNo" type="text" placeholder="银行卡号" class="place_txt" value="${cardNo}"/>
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
              <th>子协议编号</th>
              <th>发送时间</th>
              <th>进件编号</th>
              <th>文件名称</th>
              <th>客户姓名</th>
              <th>身份证号</th>
              <th>手机号码</th>
              <th>银行卡号</th>
              <th>银行名称</th>
              <th>开始时间</th>
              <th>结束时间</th>
              <th>扣款频率</th>
              <th>扣款次数</th>
              <th>状态码</th>
              <th class="td_last">协议状态</th>
          </tr>
          <c:forEach items="${page.result}" var="unspaySubContract">
              <tr height="45">
                  <td>${unspaySubContract.subContractId}</td>
                  <td><fmt:formatDate value="${unspaySubContract.sendDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                  <td>${unspaySubContract.loanApplyId}</td>
                  <td>${unspaySubContract.filename}</td>
                  <td>${unspaySubContract.name}</td>
                  <td>${unspaySubContract.idCardNo}</td>
                  <td>${unspaySubContract.phoneNo}</td>
                  <td>${unspaySubContract.cardNo}</td>
                  <td><yh:bank bankCode="${unspaySubContract.bankCode}"/></td>
                  <td><fmt:formatDate value="${unspaySubContract.startDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                  <td><fmt:formatDate value="${unspaySubContract.endDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                  <td><c:choose>
                      <c:when test="${unspaySubContract.cycle==1}">
                          年
                      </c:when>
                      <c:when test="${unspaySubContract.cycle==2}">
                          月
                      </c:when>
                      <c:when test="${unspaySubContract.cycle==3}">
                          日
                      </c:when>
                  </c:choose></td>
                  <td>${unspaySubContract.triesLimit}</td>
                  <td>${unspaySubContract.signStatus}</td>
                  <td class="td_last <c:choose>
                          <c:when test="${unspaySubContract.signStatus eq '0000'}">
                              success
                          </c:when>
                          <c:when test="${empty unspaySubContract.signStatus}">
                              going
                          </c:when>
                          <c:otherwise>
                              failur
                          </c:otherwise>
                      </c:choose>">
                      <c:choose>
                          <c:when test="${unspaySubContract.signStatus eq '0000'}">
                              成功
                          </c:when>
                          <c:when test="${empty unspaySubContract.signStatus}">
                              处理中
                          </c:when>
                          <c:otherwise>
                              ${unspaySubContract.signMsg}
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
