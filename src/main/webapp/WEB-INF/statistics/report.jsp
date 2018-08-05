<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>银生宝-报表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/sale.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	window.onload = function(){
		var mainChart = echarts.init(document.getElementById('main'));
		mainChart.showLoading();
		mainOption = {
			title: {
				text: '动态数据',
				subtext: '纯属虚构'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data:['交易笔数', '交易金额']
			},
			toolbox: {
				show: true,
				feature: {
					dataView: {readOnly: true},
					restore: {},
					saveAsImage: {}
				}
			},
			dataZoom: {
				show: false,
				start: 0,
				end: 100
			},
			xAxis: [
				{
					type: 'category',
					boundaryGap: true,
					data: (function (){
						var now = new Date();
						var res = [];
						var len = 10;
						while (len--) {
							res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
							now = new Date(now - 2000);
						}
						return res;
					})()
				},
				{
					type: 'category',
					boundaryGap: true,
					data: (function (){
						var res = [];
						var len = 10;
						while (len--) {
							res.push(len + 1);
						}
						return res;
					})()
				}
			],
			yAxis: [
				{
					type: 'value',
					scale: true,
					name: '万元',
					max: 50,
					min: 0,
					boundaryGap: [0.2, 0.2]
				},
				{
					type: 'value',
					scale: true,
					name: '笔',
					max: 120,
					min: 0,
					boundaryGap: [0.2, 0.2]
				}
			],
			series: [
				{
					name:'交易笔数',
					type:'bar',
					xAxisIndex: 1,
					yAxisIndex: 1,
					data:(function (){
						var res = [];
						var len = 10;
						while (len--) {
							res.push(Math.round(Math.random() * 100));
						}
						return res;
					})()
				},
				{
					name:'交易金额',
					type:'line',
					data:(function (){
						var res = [];
						var len = 0;
						while (len < 10) {
							res.push((Math.random()*25 + 5).toFixed(1) - 0);
							len++;
						}
						return res;
					})()
				}
			]
		};
		clearInterval(timeTicket);
		var count = 11;
		var timeTicket = setInterval(function (){
			axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
		
			var data0 = mainOption.series[0].data;
			var data1 = mainOption.series[1].data;
			data0.shift();
			data0.push(Math.round(Math.random() * 100));
			data1.shift();
			data1.push((Math.random() * 25 + 5).toFixed(1) - 0);
		
			mainOption.xAxis[0].data.shift();
			mainOption.xAxis[0].data.push(axisData);
			mainOption.xAxis[1].data.shift();
			mainOption.xAxis[1].data.push(count++);
			
			mainChart.hideLoading();
			mainChart.setOption(mainOption);
		}, 2100);
		
		
		var secondChart = echarts.init(document.getElementById('second'));
		secondOption = {
			title : {
				text: '划扣状态',
				subtext: '纯属虚构',
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				orient: 'vertical',
				left: 'left',
				data: ['划扣成功','划扣中','划扣失败']
			},
			series : [
				{
					name: '划扣状态',
					type: 'pie',
					radius : '55%',
					center: ['50%', '60%'],
					data:[
						{value:335, name:'划扣成功'},
						{value:310, name:'划扣中'},
						{value:20, name:'划扣失败'}
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			],
			color:['#749f83', '#d48265', '#c23531']
		};
		secondChart.setOption(secondOption);
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
            <li class="cont">银生宝报表</li>
          </ul>
        </div>
      </div>
      <span id="box_tab">
      	<div style="margin-top:100px; margin-left:50px;width: 500px;height:400px;float:left;" id="main"></div>
        <div style="margin-top:100px; margin-right:20px;width: 500px;height:400px;float:right;" id="second"></div>
      </span> 
    </div>
  </div>
</div>
</body>
</html>