<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <title>结算-首页</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
    <script>
        $(function () {
            menuFix();//鼠标移动效果
            //点击子菜单效果
            $('#nav li ul li a').click(function () {
                $('#nav li ul li a').removeClass("on");
                $(this).addClass('on');
            });

            //管理员密码修改
            $('#edit_staff').click(function(){
                $('#shadow').show();
                $.get('${ctx}/staff',function(sysStaff){
                    $('#staff_info_box').show();
                    var $datas = $('#edit_staff_table').find('data[data-name]');
                    var $inputs = $('#edit_staff_table').find('input[name]');
                    for(var property in sysStaff){
                        $datas.each(function(){
                            console.log(property);
                            console.log($(this).attr('data-name'));
                            if(property == $(this).attr('data-name')){
                                $(this).html(sysStaff[property]);
                            }
                        });
                        $inputs.each(function(){
                            if(property == $(this).attr('name')){
                                $(this).val(sysStaff[property]);
                            }
                        });
                    }
                    //绑定修改
                    $('#edit_staff_btn').unbind('click').click(function(){
                        var newSysStaff = {};
                        $datas.each(function(){
                            if( $(this).html()!=''){
                                var property = $(this).attr('data-name');
                                newSysStaff[property] = $(this).html();
                            }
                        });
                        $inputs.each(function(){
                            if($(this).val()!=''){
                                var property = $(this).attr('name');
                                newSysStaff[property] = $(this).val();
                            }
                        });
                        //密码校验
                        if(newSysStaff.newPassword!=''){
                            if(newSysStaff.newPassword!=newSysStaff.rePassword){
                                alert('两次输入的密码不一致');
                                return;
                            }
                        }
                        newSysStaff.password = newSysStaff.newPassword;
                        delete newSysStaff.newPassword;
                        $.post('${ctx}/edit',newSysStaff,function(status){
                            if(status=='1'){
                                alert('修改成功！');
                                $('.close_btn').click();
                            }
                        },'json');
                    });

                },'json');
            });

            $('.close_btn').click(function(){
                $('#staff_info_box').hide();
                $('#shadow').hide();
                $('edit_staff').find('data[data-name]').html('');
                $('edit_staff').find('input[name]').val('');
            });

            //获取银行代码信息存储到本地
            $.post('${ctx}/resource/bank',function(banks){
                localStorage.setItem("banks",JSON.stringify(banks));
            },'json');

        });

        var LastLeftID;

        function menuFix() {
            var obj = document.getElementById("nav").getElementsByTagName("li");

            for (var i = 0; i < obj.length; i++) {
                obj[i].onmouseover = function () {
                    this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                }
                obj[i].onMouseDown = function () {
                    this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                }
                obj[i].onMouseUp = function () {
                    this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                }
                obj[i].onmouseout = function () {
                    this.className = this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
                }
            }
        }
        function DoMenu(emid) {
            var obj = document.getElementById(emid);
            obj.className = (obj.className.toLowerCase() == "expanded" ? "collapsed" : "expanded");
            if ((LastLeftID != null) && (LastLeftID != "") && (emid != LastLeftID)) //关闭上一个Menu
            {
                document.getElementById(LastLeftID).className = "collapsed";
            }
            LastLeftID = emid;
        }

    </script>
</head>

<body style="height:100%">
<div class="tab_cont clearfix">
    <div class="cont_les clearfix" style="z-index:1"><img src="${ctx}/images/logo2.png"/>
        <ul id="nav">
            <li><a href="${ctx}/payease/syn001001/list" target="iframe">首页</a></li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu1')">首信易(线下)</a>
                <ul id="ChildMenu1" class="collapsed">
                    <li><a href="${ctx}/payease/syn001001/list" target="iframe">账户</a></li>
                    <li><a href="${ctx}/payease/transfer" target="iframe">转账</a></li>
                    <li><a href="${ctx}/payease/trs001008/list" target="iframe">代扣</a></li>
                    <li><a href="${ctx}/payease/trs001006/list" target="iframe">提现</a></li>
                    <li><a href="${ctx}/record/batchQuery" target="iframe">批量查询</a></li>
                    <li><a href="${ctx}/record/messageRecord" target="iframe">报文管理</a></li>
                    <li><a href="${ctx}/payease/idCardVerify" target="iframe">实名认证</a></li>
                    <li><a href="${ctx}/payease/bankList" target="iframe">银行信息</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu2')">首信易(线上)</a>
                <ul id="ChildMenu2" class="collapsed">
                    <li><a href="${ctx}/payease/syn001001/list?plateform=1" target="iframe">账户</a></li>
                    <li><a href="${ctx}/payease/transfer?plateform=1" target="iframe">转账</a></li>
                    <li><a href="${ctx}/payease/trs001002/list" target="iframe">投资</a></li>
                    <li><a href="${ctx}/payease/trs001006/list?plateform=1" target="iframe">提现</a></li>
                    <li><a href="${ctx}/payease/recharge/list" target="iframe">充值</a></li>
                    <li><a href="${ctx}/record/batchQuery" target="iframe">批量查询</a></li>
                    <li><a href="${ctx}/record/messageRecord" target="iframe">报文管理</a></li>
                    <li><a href="${ctx}/payease/idCardVerify" target="iframe">实名认证</a></li>
                    <li><a href="${ctx}/payease/bankList" target="iframe">银行信息</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu3')">银生宝</a>
                <ul id="ChildMenu3" class="collapsed">
                    <li><a href="${ctx}/unspay/subContractUpload" target="iframe">子协议导入</a></li>
                    <li><a href="${ctx}/unspay/subContractList" target="iframe">子协议查询</a></li>
                    <li><a href="${ctx}/unspay/payUpload" target="iframe">代付导入</a></li>
                    <li><a href="${ctx}/unspay/payList" target="iframe">代付查询</a></li>
                    <li><a href="${ctx}/unspay/deductUpload" target="iframe">代扣导入</a></li>
                    <li><a href="${ctx}/unspay/deductList" target="iframe">代扣查询</a></li>
                    <li><a href="${ctx}/unspay/report" target="iframe">报表</a></li>
                    <li><a href="${ctx}/zcgdUnspay/zcguodianPayUpload" target="iframe">国典代付导入</a></li>
                    <li><a href="${ctx}/zcgdUnspay/zcguodianPayList" target="iframe">国典代付查询</a></li>
                    <%-- <li><a href="${ctx}/zcgdUnspay/zcguodianReport" target="iframe">国典代付报表</a></li> --%>
                </ul>
            </li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu4')">畅捷通</a>
                <ul id="ChildMenu4" class="collapsed">
                    <li><a href="${ctx}/chanpay/q20003/list" target="iframe">充值</a></li>
                    <li><a href="${ctx}/chanpay/g10002/list" target="iframe">代付</a></li>
                    <li><a href="${ctx}/chanpay/g60001/list" target="iframe">认证</a></li>
                    <li><a href="${ctx}/chanpay/gw/report" target="iframe">报表</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu5')">华兴银行</a>
                <ul id="ChildMenu5" class="collapsed">
                    <li><a href="${ctx}/ghbank/test1/testIndex" target="iframe">测试</a></li>
                    <li><a href="${ctx}/ghbank/account/list" target="iframe">账户</a></li>

                </ul>
            </li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu6')">统计</a>
                <ul id="ChildMenu6" class="collapsed">
                    <li><a href="${ctx}/statistics/list" target="iframe">搜索列表</a></li>
                    <li><a href="${ctx}/statistics/report" target="iframe">报表</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" onclick="DoMenu('ChildMenu7')">系统管理</a>
                <ul id="ChildMenu7" class="collapsed">
                    <li><a href="${ctx}/system/manage" target="iframe">员工</a></li>
                    <li><a href="${ctx}/system/setting" target="iframe">设置</a></li>
                </ul>
            </li>
        </ul>
        <p class="set_btn"><a id="edit_staff" href="javascript:;">${staffName}</a> | <a href="${ctx}/logout">退出</a></p>
    </div>

    <div id="shadow" class="shadow" style="z-index: 100"></div>
    <div id="shadow_img"></div>
    <div id="staff_info_box" class="edit_box" style="left: 350px; top: 156px;">
        <h2>管理员信息修改<a href="javascript:;" class="close_btn">关闭</a></h2>

        <form>
            <table id="edit_staff_table" cellpadding="0" cellspacing="0" border="0" width="698" class="edit_table">
                <tr height="45">
                    <td><label>姓名：</label>
                        <data data-name="staffName"></data>
                    </td>
                    <td><label>身份证号：</label>
                        <data data-name="idCardNo"></data>
                    </td>
                </tr>
                <tr height="45">
                    <td><label>邮箱：</label>
                        <input name="email" type="text"/>
                    </td>
                    <td><label>手机号：</label>
                        <input name="mobile" type="text"/>
                    </td>
                </tr>
                <tr height="45">
                    <td><label>密码：</label>
                        <input name="newPassword" type="password"/>
                    </td>
                    <td><label>再次输入：</label> <input name="rePassword" type="password"/></td>
                </tr>
            </table>
            <p class="edit_p">
                <button id="edit_staff_btn" type="button">修改</button>
            </p>
        </form>
    </div>
    <iframe src="${ctx}/payease/syn001001/list" id="iframe" height="99%" width="100%" style="z-index:-1;" frameborder="0" marginheight="0"
            marginwidth="0" name="iframe"/>
</div>
</body>
</html>
