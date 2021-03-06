<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>员工部门</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/system.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>

<body>
<div class="tab_cont clearfix">
    <div class="cont_ri" id="ri_cont">
    	<div class="system_box">
        	<h3>总部 > 销售拓展中心 > 长沙分部</h3>
            <div class="system_box_cont" id="system_box_cont">
                <div class="condition clearfix">
                    <button id="add_btn">新增</button>
                    <input type="text" placeholder="员工ID、姓名" class="place_txt" />
                    <input type="text" placeholder="部门ID、名称" class="place_txt" />
                    <label>状态 <select>
                        <option></option>
                        <option>状态1</option>
                        <option>状态2</option>
                    </select></label>
                    <button>搜索</button>
                </div>
                <table cellpadding="0" cellspacing="0" border="0" class="order_box" id="order_tab">
                    <tr height="50">
                        <th>ID</th>
                        <th>名称</th>
                        <th>角色/级别</th>
                        <th>状态</th>
                        <th>手机</th>
                        <th class="td_last">邮件</th>
                    </tr>
                    <tr height="45">
                        <td colspan="6" class="td_last upward">向上 <span>注销</span></td>
                    </tr>
                    <tr height="45">
                        <td>301</td>
                        <td class="img_td"><img src="../images/team.png" /> 长沙一店</td>
                        <td>门店</td>
                        <td></td>
                        <td></td>
                        <td class="td_last"></td>
                    </tr>
                    <tr height="45">
                        <td>401</td>
                        <td class="img_td"><img src="../images/team.png" /> 团队1</td>
                        <td>团队</td>
                        <td></td>
                        <td></td>
                        <td class="td_last"></td>
                    </tr>
                    <tr height="45">
                        <td>301120</td>
                        <td class="department_btn">张XX</td>
                        <td>客服</td>
                        <td>在职</td>
                        <td>135********</td>
                        <td class="td_last">123456789@qq.com</td>
                    </tr>
                </table>
                <p class="fund_page clearfix">
                    <span><a class="h_bg_width" href="#">首页</a>
                    <a href="#"><i class="h_icon_prev"></i></a>
                    <a class="current" href="#">1</a>
                    <a href="#">2</a>
                    <a href="#">3</a>
                    <a href="#">4</a>
                    <a href="#">5</a>
                    <a href="#"><i class="h_icon_next"></i></a>
                    <a class="h_bg_width" href="#">末页</a></span>
                </p>
            </div>
            <div class="system_tab" id="system_tab">
            	<ul id="system_tab_til" class="clearfix">
                	<li class="on">员工</li>
                    <li>部门</li>
                </ul>
                <div class="system_tab_cont" style="display:block;">
                	<table cellpadding="0" cellspacing="0" border="0" width="698" class="system_table">
                    	<tr height="45">
                        	<td><label>姓名：</label> <input type="text" /></td>
                            <td><label>ID：</label> <input type="text" /></td>
                        </tr>
                        <tr height="45">
                        	<td><label>身份证号：</label> <input type="text" /></td>
                            <td><label>电话：</label> <input type="text" /></td>
                        </tr>
                        <tr height="45">
                        	<td><label>角色：</label> <input type="text" /><button class="role_btn">...</button></td>
                            <td><label>部门：</label> <input type="text" /><button class="depart_btn">...</button></td>
                        </tr>
                        <tr height="45">
                        	<td><label>邮件：</label> <input type="text" /></td>
                            <td></td>
                        </tr>
                        <tr height="45">
                        	<td><label>口令：</label> <input type="text" /></td>
                            <td><label>再次输入：</label> <input type="text" /></td>
                        </tr>
                    </table>
                    <h5><button>新增</button></h5>
                    <div id="shadow"></div>
                    <div class="role_box">
                    	<h4>门店角色</h4>
                        <table cellpadding="0" cellspacing="0" border="0" width="350">
                        	<tr height="35">
                            	<td align="center" width="80"><input type="checkbox" /></td>
                                <td align="center" width="80">001</td>
                                <td style="padding-left:15px; border-right:none;">业务员</td>
                            </tr>
                            <tr height="35">
                            	<td align="center" width="80"><input type="checkbox" /></td>
                                <td align="center" width="80">002</td>
                                <td style="padding-left:15px; border-right:none;">团队经理</td>
                            </tr>
                            <tr height="35">
                            	<td align="center" width="80"><input type="checkbox" /></td>
                                <td align="center" width="80">001</td>
                                <td style="padding-left:15px; border-right:none;">业务员</td>
                            </tr>
                            <tr height="35">
                            	<td align="center" width="80"><input type="checkbox" /></td>
                                <td align="center" width="80">003</td>
                                <td style="padding-left:15px; border-right:none;">团队经理</td>
                            </tr>
                        </table>
                        <h4>信审角色</h4>
                        <table cellpadding="0" cellspacing="0" border="0" width="350">
                        	<tr height="35">
                            	<td align="center" width="80"><input type="checkbox" /></td>
                                <td align="center" width="80">001</td>
                                <td style="padding-left:15px; border-right:none;">初审员</td>
                            </tr>
                            <tr height="35">
                            	<td align="center" width="80"><input type="checkbox" /></td>
                                <td align="center" width="80">002</td>
                                <td style="padding-left:15px; border-right:none;">终审员</td>
                            </tr>
                        </table>
                        <p><button class="role_giveup">取消</button><button>确定</button></p>
                    </div>
                    <div class="depart_box">
                    	<h4>部门</h4>
                    	<ul class="depart_box_ul">
                        	<li><input type="checkbox" />总裁办</li>
                            <li><input type="checkbox" />秘书处</li>
                            <li><input type="checkbox" />计划财务部</li>
                            <li><input type="checkbox" />运营结算中心</li>
                            <li><input type="checkbox" />信用管理部
                            	<ul>
                                	<li><input type="checkbox" />信审室</li>
                                    <li><input type="checkbox" />政策研究室</li>
                                    <li><input type="checkbox" />资产管理室</li>
                                </ul>
                            </li>
                            <li><input type="checkbox" />人力行政部</li>
                            <li><input type="checkbox" />互联网中心</li>
                            <li><input type="checkbox" />市场部</li>
                            <li><input type="checkbox" />销售拓展中心
                            	<ul>
                                	<li><input type="checkbox" />拓展中心管理部</li>
                                    <li><input type="checkbox" />拓展中心一区</li>
                                    <li><input type="checkbox" />拓展中心二区</li>
                                </ul>
                            </li>
                            <li><input type="checkbox" />财富中心
                            	<ul>
                                	<li><input type="checkbox" />财富管理中心一部</li>
                                    <li><input type="checkbox" />财富管理中心二部</li>
                                </ul>
                            
                            </li>
                        </ul>
                        <p>
                        	<button class="depart_cancel">取消</button>
                            <button>确定</button>
                        </p>
                    
                    
                    </div>
                </div>
                <div class="system_tab_cont">
                	<table cellpadding="0" cellspacing="0" border="0" width="698" class="system_table">
                    	<tr height="45">
                        	<td><label>名称：</label> <input type="text" /></td>
                            <td><label>ID：</label> <input type="text" /></td>
                        </tr>
                        <tr height="45">
                        	<td colspan="2"><label>地址：</label> 
                            	<select>
                                	<option></option>
                                    <option>河北</option>
                                    <option>江苏</option>
                                </select> 省
                                <select>
                                	<option></option>
                                    <option>唐山</option>
                                    <option>石家庄</option>
                                </select> 市
                                <select>
                                	<option></option>
                                    <option>路南区</option>
                                    <option>丰润区</option>
                                </select> 区
                            
                            </td>
                        </tr>
                        <tr height="45">
                        	<td colspan="2"><input class="big_width" type="text" /></td>
                        </tr>
                        <tr height="45">
                        	<td><label>电话1：</label> <input type="text" /></td>
                            <td><label>电话2：</label> <input type="text" /></td>
                        </tr>
                        <tr height="45">
                        	<td><label>负责人：</label> <input type="text" /></td>
                            <td></td>
                        </tr>
                    </table>
                    <h5><button>新增</button></h5>
                </div>
            
            
            </div>
        </div>
    </div>
</div>

<div class="shadow"></div>
<div class="depart_ment_box">
	<h3><span class="department_x"><img src="../images/x.png" /></span></h3>
    <table cellpadding="0" cellspacing="0" border="0" width="698" class="system_table">
        <tr height="45">
            <td><label>姓名：</label> 张三</td>
            <td><label>ID：</label> 2015001001</td>
        </tr>
        <tr height="45">
            <td><label>身份证号：</label> 123456789456321478</td>
            <td><label>电话：</label> <input type="text" /></td>
        </tr>
        <tr height="45">
            <td><label>角色：</label> <input type="text" /><button class="role_btn">...</button></td>
            <td><label>部门：</label> <input type="text" /><button class="depart_btn">...</button></td>
        </tr>
        <tr height="45">
            <td><label>邮件：</label> <input type="text" /></td>
            <td><label>状态：</label> 
            	<select>
                	<option></option>
                    <option>已离职</option>
                    <option>在职</option>
                    <option>休假</option>
                </select>
            </td>
        </tr>
    </table>
    <p class="depart_p">
    	<button>保存</button>
        <span class="depart_dw"><button>重置密码</button> <i>密码已重置为000000</i></span>
    </p>
    <div id="shadow"></div>
    <div class="role_box">
        <h4>门店角色</h4>
        <table cellpadding="0" cellspacing="0" border="0" width="350">
            <tr height="35">
                <td align="center" width="80"><input type="checkbox" /></td>
                <td align="center" width="80">001</td>
                <td style="padding-left:15px; border-right:none;">业务员</td>
            </tr>
            <tr height="35">
                <td align="center" width="80"><input type="checkbox" /></td>
                <td align="center" width="80">002</td>
                <td style="padding-left:15px; border-right:none;">团队经理</td>
            </tr>
            <tr height="35">
                <td align="center" width="80"><input type="checkbox" /></td>
                <td align="center" width="80">001</td>
                <td style="padding-left:15px; border-right:none;">业务员</td>
            </tr>
            <tr height="35">
                <td align="center" width="80"><input type="checkbox" /></td>
                <td align="center" width="80">003</td>
                <td style="padding-left:15px; border-right:none;">团队经理</td>
            </tr>
        </table>
        <h4>信审角色</h4>
        <table cellpadding="0" cellspacing="0" border="0" width="350">
            <tr height="35">
                <td align="center" width="80"><input type="checkbox" /></td>
                <td align="center" width="80">001</td>
                <td style="padding-left:15px; border-right:none;">初审员</td>
            </tr>
            <tr height="35">
                <td align="center" width="80"><input type="checkbox" /></td>
                <td align="center" width="80">002</td>
                <td style="padding-left:15px; border-right:none;">终审员</td>
            </tr>
        </table>
        <p><button class="role_giveup">取消</button><button>确定</button></p>
        </div>
        <div class="depart_box">
        <h4>部门</h4>
        <ul class="depart_box_ul">
            <li><input type="checkbox" />总裁办</li>
            <li><input type="checkbox" />秘书处</li>
            <li><input type="checkbox" />计划财务部</li>
            <li><input type="checkbox" />运营结算中心</li>
            <li><input type="checkbox" />信用管理部
                <ul>
                    <li><input type="checkbox" />信审室</li>
                    <li><input type="checkbox" />政策研究室</li>
                    <li><input type="checkbox" />资产管理室</li>
                </ul>
            </li>
            <li><input type="checkbox" />人力行政部</li>
            <li><input type="checkbox" />互联网中心</li>
            <li><input type="checkbox" />市场部</li>
            <li><input type="checkbox" />销售拓展中心
                <ul>
                    <li><input type="checkbox" />拓展中心管理部</li>
                    <li><input type="checkbox" />拓展中心一区</li>
                    <li><input type="checkbox" />拓展中心二区</li>
                </ul>
            </li>
            <li><input type="checkbox" />财富中心
                <ul>
                    <li><input type="checkbox" />财富管理中心一部</li>
                    <li><input type="checkbox" />财富管理中心二部</li>
                </ul>
            
            </li>
        </ul>
        <p>
            <button class="depart_cancel">取消</button>
            <button>确定</button>
        </p>
    
    
    </div>

</div>






</body>
</html>
