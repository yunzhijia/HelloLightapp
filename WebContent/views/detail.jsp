<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<title>demo</title>
	<%@ include  file="common.jsp"%>
</head>
<body>
	<div class="container detail-container">
		<div class="detail-time"></div>
		<div class="detail-content"></div>
	</div>
	<footer class="btn-group">
		<button class="share-btn btn btn-yes btn-full-w border border-t">分享</button>
	</footer>
	
	<script>
		$(function () {
			var __pathName = '<%=pathName%>';
			var __lappId = '';
			
			function init () {
				// 获取当前用户身份
				getUserInfo();
				
				// 点击分享按钮
				$('.share-btn').on('click', function () {
					share();
				});

				var detail = getDetailData();

				if (!detail) {
					return ;
				}

				$('.detail-time').text('发送时间：' + Util.format(detail.createTime, 'MM-dd hh:mm'));
				$('.detail-content').text(detail.data.content);
			}
			

			// 调用分享js桥
			function share () {
				var appId = '',
					lightAppId = __lappId,
					thumbData = 'iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAYAAAEi6oPRAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA4JpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowOTgwMTE3NDA3MjA2ODExOEMxNDhGRDhBQTQyRDkzQiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozMjFGMUM5NzFDNzUxMUU1QTc1NUYzMzYwQzA0MzIxQiIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozMjFGMUM5NjFDNzUxMUU1QTc1NUYzMzYwQzA0MzIxQiIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxNCAoTWFjaW50b3NoKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjNkOGJkMWIzLTQ1NTAtNDljOC05Y2MwLTZmNzAyNmE3YTkxMCIgc3RSZWY6ZG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOjAzYmNkMDZjLTYxZGQtMTE3OC1hYjQxLWZi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';
				
				XuntongJSBridge.call("share",  {
					"shareType": "4",
					"appId": appId,	// 公共号ID（重要：实际开发请换用自己申请的id)
					"appName": "demo",
					"lightAppId": lightAppId,		// 轻应用id（重要：实际开发请换用自己申请的id)
					"theme": "分享demo组",	// 主题（可选），如传入，创建组时以此命名组名称
					"title": "分享demo",			// 分享标题
					"content": "分享demo弹窗内容",	// 分享时弹窗界面内容
					"cellContent": "分享demo内容",	// 分享后聊天界面显示的内容
					"thumbData": thumbData,			// 分享的缩略图，使用Base64编码
					"webpageUrl": location.href,	//分享内容的跳转路径url
					"sharedObject": 'all'			//分享的对象：all（包含单人或组），group（组），person（单人）
				}, function(result) {
					// 分享回调
					// alert("结果："+JSON.stringify(result));
				});
			}

			// 拉取详情数据
			function getDetailData () {
				var id = Util.getUrlValue('id');

				if (!id) {
					return ;
				}

				var data = Util.DB.find(id);

				return data;
			}

			// 获取当前用户身份
			function getUserInfo () {
				$.ajax({
					'type': 'POST',
					'url': __pathName + '/rest/get/context.json',
					'dataType': 'json'
				})
				.done(function (resp) {
					if (resp.success === true) {
						__lappId = resp.data.lappId;
						console.log('lappId: ' + __lappId);
					} else {
						alert('获取当前用户信息失败了');
					}
				})
				.fail(function () {
					alert('获取当前用户信息失败了');
				});
			}
			

			init();
		});
	</script>
</body>
</html>