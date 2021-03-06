<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>设置</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/system.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script>
$(function () {
	$(".chenge_btn").hide();
	$(".ri_value").mouseover(function(){
		$(this).css('background','#fff');
		$(this).children(".chenge_btn").show();
	})
	$(".ri_value").mouseout(function(){
		$(".chenge_btn").hide();
		$(this).css('background','none');
	})
	$(".save_btn").hide();
	$(".chenge_btn").click(function(){
		$(this).prev("input").attr("disabled",false);
		$(this).prev("input").focus();
		$(this).parent().nextAll(".save_btn").show();
		$(".ri_value").unbind('mouseout')
	});
	$(".save_btn").click(function(){
		$(this).prev().css('background','none');
		$(this).prev().children(".chenge_btn").hide();
		$(this).hide();
		$(".ri_value").mouseout(function(){
			$(".chenge_btn").hide();
			$(this).css('background','none');
		})
	});
	
});

</script>
</head>

<body>
<div class="tab_cont clearfix">
    <div class="cont_ri" id="ri_cont">
   		<div class="set_box">
        	<h2>高级设置</h2>
        	<p class="tips">以下是高级选项。如果你不是十分了解这些参数，请不要更改。</p>
            <p class="set_title clearfix">
            	<span class="le_65">key</span>
                <span class="ri_35">value</span>
            </p>
            <div class="set_cont clearfix">
            	<div class="le_65">
                	<h3>content.yuanheng.XXXXXX</h3>
                    <p>这里是内容</p>
                </div>
                <div class="ri_35 value_cont">
                	<div class="ri_value clearfix">
                        <input type="text" value="30" disabled="true" />
                        <img class="chenge_btn" src="${ctx}/images/set.png" />
                    </div>
                    <button class="save_btn">保存</button>
                </div>
            </div>
            <div class="set_cont clearfix">
            	<div class="le_65">
                	<h3>content.yuanheng.XXXXXX</h3>
                    <p>这里是内容</p>
                </div>
                <div class="ri_35 value_cont">
                	<div class="ri_value clearfix">
                        <input type="text" value="30" disabled="true" />
                        <img class="chenge_btn" src="${ctx}/images/set.png" />
                    </div>
                    <button class="save_btn">保存</button>
                </div>
            </div>
            
        </div>
    </div>
</div>


</body>
</html>
