<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<meta name="format-detection" content="telephone=no" />
	<title>出錯啦</title>
</head>
<body>
	<p style="margin-top: 50px; text-align: center;" class="tips">你的用户信息已失效，请<a class="close" href="javascript:;">关闭页面</a>重新进入！</p>
	<script src="http://do.yunzhijia.com/pub/js/qingjs.js"></script>
	<script>
	   document.querySelector('.close').onclick = function() {
	   		XuntongJSBridge && XuntongJSBridge.call('close');
	   }

	   // 判断是否在云之家app
	   if (!(/Qing/i.test(navigator.userAgent))) {
		   document.querySelector('.tips').innerHTML = '你的用户信息已失效，请获取新的ticket';
	   }
	</script>
	
	
	
</body>
</html>
