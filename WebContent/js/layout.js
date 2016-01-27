(function (window, document) {
	var eventType = 'orientationchange' in window ? 'orientationchange' : 'resize',
		calc = function () {
			// 自适应布局
			var html = document.documentElement;
			var windowWidth = html.clientWidth;
			html.style.fontSize = windowWidth / 6.4 + 'px';

			var dpr = window.devicePixelRatio;

			if (dpr > 1.5 && dpr <= 2) {
				dpr = 2;
			} else if (dpr > 2) {
				dpr = 3;
			}

			html.setAttribute('data-dpr', dpr);
		};

	calc();

	window.addEventListener(eventType, calc, false);
})(window, document);