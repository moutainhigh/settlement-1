<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>华兴银行-测试页</title>

    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    
</head>
<body>
华兴银行-测试页<br>
<br>
<center>
<form name="registerForm" method="post" action="${ctx}/ghbank/register/ogw00042" target="_blank" >
姓名：<input type=text name='acName'  size=6>&nbsp;&nbsp;&nbsp;&nbsp;身份证号：<input type=text name='idNo' >&nbsp;&nbsp;&nbsp;&nbsp;
手机号：<input type=text name='mobile' >&nbsp;&nbsp;&nbsp;&nbsp;
<button type=submi>开户测试</button>
</form>
</center>


<br>
<center>
<form name="bindCardForm" method="post" action="${ctx}/ghbank/register/ogw00044" target="_blank" >
e帐号：<input type=text name='acNo' >&nbsp;&nbsp;&nbsp;&nbsp;<input type=hidden name='appIdCode' value='PC'>
<button type=submi>绑卡测试</button>
</form>
</center>
<br>

<center>
<form name="rechargeForm" method="post" action="${ctx}/ghbank/recharge/ogw00045" target="_blank" >
e帐号：<input type=text name='acNo' >&nbsp;&nbsp;&nbsp;&nbsp;姓名：<input type=text name='acName'  size=6>&nbsp;&nbsp;&nbsp;&nbsp;金额：<input type=text name='amount' >&nbsp;&nbsp;&nbsp;&nbsp;
<button type=submi>充值测试</button>
</form>
</center>

<br>

<center>
<form name="withdrawForm" method="post" action="${ctx}/ghbank/withdraw/ogw00047" target="_blank" >
e帐号：<input type=text name='acNo' >&nbsp;&nbsp;&nbsp;&nbsp;姓名：<input type=text name='acName'  size=6>&nbsp;&nbsp;&nbsp;&nbsp;金额：<input type=text name='amount' >&nbsp;&nbsp;&nbsp;&nbsp;
<button type=submi>提现测试</button>
</form>
</center>

<br>
<center>
<form name="investForm" method="post" action="${ctx}/ghbank/invest/ogw00052" target="_blank" >
借款编号：<input type=text name='loanNo' >&nbsp;&nbsp;&nbsp;&nbsp;e帐号：<input type=text name='acNo' >&nbsp;&nbsp;&nbsp;&nbsp;姓名：<input type=text name='acName'  size=6>&nbsp;&nbsp;&nbsp;&nbsp;金额：<input type=text name='amount' >&nbsp;&nbsp;&nbsp;&nbsp;
<button type=submi>投标测试</button>
</form>
</center>

<br>
<center>
<form name="repayForm" method="post" action="${ctx}/ghbank/repay/ogw00067" target="_blank" >
借款编号：<input type=text name='loanNo' >&nbsp;&nbsp;&nbsp;&nbsp;还款人e帐号：<input type=text name='bwAcNo' >&nbsp;&nbsp;&nbsp;&nbsp;还款人姓名：<input type=text name='bwAcName'  size=6>&nbsp;&nbsp;&nbsp;&nbsp;还款金额：<input type=text name='amount' >&nbsp;&nbsp;&nbsp;&nbsp;
<button type=submi>还款测试</button>
</form>
</center>

<br>
<center>
<form name="bondForm" method="post" action="${ctx}/ghbank/bond/ogw00061" target="_blank" >
原投标流水号：<input type=text name='oldReqSeqNo' >&nbsp;&nbsp;&nbsp;&nbsp;原标的编号：<input type=text name='oldReqNumber' >&nbsp;&nbsp;&nbsp;&nbsp;原标的名称：<input type=text name='oldReqName' >&nbsp;&nbsp;&nbsp;&nbsp;<br>
转出人e帐号：<input type=text name='accNo' >&nbsp;&nbsp;&nbsp;&nbsp;转出姓名：<input type=text name='custName'  size=6>&nbsp;&nbsp;&nbsp;&nbsp;剩余金额：<input type=text name='amount' >&nbsp;&nbsp;&nbsp;&nbsp;预计剩余收益：<input type=text name='preIncome' >&nbsp;&nbsp;&nbsp;&nbsp;
<button type=submi>债权转让测试</button>
</form>
</center>

<br>
<center><a href="${ctx}/ghbank/repay/ogw00067" target="about_blank">还款测试</a></center>

</body>
</html>