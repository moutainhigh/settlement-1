<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yh" uri="http://yuanheng100.com/jsp/yhtl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="search_path"
       value="${ctx}/ghbank/account/list?plateform=${plateform}&userName=${userName}&accNum=${accNum}&returnCode=${returnCode}&reqTimeStartDate=${reqTimeStartDate}&reqTimeEndDate=${reqTimeEndDate}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>华兴银行-通用测试表单</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    
</head>
<body>
<center>华兴银行通用测试表单</center><br>

<form name="ogwForm" method="post" action="${url}">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RequestData: <input style="width:1000px" type="text" name="RequestData" value='${xmlparam}'/><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;transCode: <input style="width:200px" type="text" name="transCode" value='${transCode}'/><br>
<center><input type="submit" name="提交"/></center>
</form>

</body>
</html>