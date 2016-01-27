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
	<div class="container list-container">
		<ul class="list-wrap"></ul>
	</div>
	<footer class="btn-group">
		<a href="<%=pathName %>/sayhi-create.json" class="btn btn-full-w border border-t">新建</a>
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
				var $listWrap = $('.list-wrap');

				var tplHtml = tpl(getListDatas());

				if (tplHtml === false) {
					$listWrap.html('<p class="list-nodata">还没有内容喔~</p>');
				} else {
					$listWrap.html(tplHtml);
				}

				// 点击每一项内容
				$listWrap.on('click', '.list-item', function () {
					var id = $(this).data('id');

					location.href = __pathName + '/sayhi-detail.json?id=' + id;
				});
			}

			// 拼装模板
			function tpl (listDatas) {
				var item, html = '';

				if (!listDatas || !listDatas.length) {
					return false;
				}

				for (var i = 0, len = listDatas.length; i < len; i++) {
					item = listDatas[i];

					html += [
						'<li class="list-item" data-id="' + item.id + '">',
							'<div class="list-item-time">',
								'发送时间：' + Util.format(item.createTime, 'MM-dd hh:mm'),
							'</div>',
							'<div class="list-item-content">',
								item.data.content,
							'</div>',
						'</li>'
					].join('');
				}

				return html;
			}

			// 拉取列表数据
			function getListDatas () {
				return Util.DB.findAll();
			}

			init();
		});
	</script>
</body>
</html>