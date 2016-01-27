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
	<div class="container create-container">
		<textarea class="create-content" placeholder="跟大家 say hi 吧~"></textarea>
	</div>
	<footer class="btn-group">
		<button class="submit-btn btn btn-full-w border border-t">提交</button>
	</footer>
	<div class="loading">
		<div class="circle-container container1 vertical-middle">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
		<div class="circle-container container2 vertical-middle">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
	</div>
	<script>
		$(function () {
			var __pathName = '<%=pathName%>';
			
			function init () {
				var $content = $('.create-content'),
					$submitBtn = $('.submit-btn');

				// 点击提交按钮
				$submitBtn.on('click', function () {
					var val = $content.val().trim();

					// 如果没有输入内容（或全空格），提示他
					if (!val) {
						alert('请先输入内容喔~');
						$content.focus();

						return ;
					}

					// 显示loading动画
					$('.loading').show();	

					// 开始提交
					$.when(submit(val), pushMsg(val))
						// 提交数据成功
						.done(function (id, resp) {
							alert('提交成功啦');
							window.location.href = __pathName + '/sayhi-detail.json?id=' + id;
						})
						// 提交数据失败
						.fail(function () {
							alert('不好意思，提交失败了，请稍后重试...');
						})
						.always(function () {
							// 隐藏loading动画
							$('.loading').hide();
						});
				});
			}

			// 提交数据函数（通过本地缓存模拟保存数据库）
			function submit (content) {
				var deferred = $.Deferred();

				var id = Util.DB.save({content: content});

				if (id !== false) {
					deferred.resolve(id);
				} else {
					deferred.reject();
				}

				return deferred.promise();
			}

			// 推送公共号消息
			function pushMsg (message) {
				var deferred = $.Deferred();

				$.ajax({
					'type': 'POST',
					'url': __pathName + '/rest/pubacc/push/full/msg.json',
					'data': {
						message: message
					},
					'dataType': 'json'
				})
				// 请求发送成功的处理
				.done(function (resp) {
					if (resp.success === true) {
						// 响应提交数据成功
						deferred.resolve(resp);
					} else {
						// 响应提交数据失败
						deferred.reject(resp);
					}
				})
				// 请求发送失败的处理
				.fail(function (resp) {
					// 响应提交数据失败
					deferred.reject(resp);
				});

				return deferred.promise();
			}

			init();
		});
	</script>
</body>
</html>