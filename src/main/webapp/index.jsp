<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/base.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){
            if(window.parent!=window){
                window.parent.location.href = window.location.href;
            }
            $('#resend_code').click(function(){
                if($(':input[name=username]').val()==''||$(':input[name=passwd]').val()==''){
                    alert('用户名密码不能为空！');
                    return;
                }
                $.get('${ctx}/code',{'username':$(':input[name=username]').val(),'passwd':$(':input[name=passwd]').val()},function(response){
                    if(response=='1'){
                        $('#resend_code').attr('disabled','disabled');
                        $('#resend_code').html('剩余<data id="rest_second">60</data>秒');
                        var second = 59;
                        var $rest_second = $('#rest_second');
                        var interval = setInterval(function(){
                            if(second>=0){
                                $rest_second.html(second);
                                second--;
                            }else{
                                clearInterval(interval);
                                $('#resend_code').removeAttr('disabled').html('获取验证码');
                            }
                        },1000);
                    }else if(response=='-1'){
                        alert('用户名密码不正确！');
                    }else{
                        alert('用户名密码不能为空！');
                    }
                },'json');
                return false;
            });
        });
    </script>
</head>

<body style="height:100%; background:url(${ctx}/images/bg.png) repeat; width:100%;">
<div class="login_bg">
    <div class="login_bg_bg"></div>
    <div class="login_box clearfix">
        <div class="clearfix">
            <div class="login_box_le">
                <h2></h2>
                <section><img src="${ctx}/images/logo.png"></section>
            </div>
            <div class="login_box_ri">
                <h2>结算系统</h2>

                <div>
                    <form action="${ctx}/login" method="post">
                        <label>用户名</label><input type="text" name="username"/><br/>
                        <label>密&nbsp;&nbsp;&nbsp;码</label><input type="password" name="passwd"/><br/>
                        <label>验证码</label><input name="code" style="width:50px;" type="text"/><button id="resend_code" style="padding:5px;margin-left:20px;">获取验证码</button>

                        <p><input type="submit" value="登录"/></p>
                    </form>
                </div>
            </div>
        </div>
        <div class="login_box_f"></div>
    </div>
    <div class="login_bg_bgs"></div>
</div>
</body>
</html>
