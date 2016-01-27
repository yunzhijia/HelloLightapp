;(function (window, factoryFn) {
	var util = factoryFn(window);

	window.Util = window.Util || util;

})(window, function (window) {
	/* 
	 * 生成唯一码
	 * @param {number} len 生成码的长度
	 * @param {number} radix 生成基数的范围（进制）
	 */
	function uuid (len, radix) {
	    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	    var uuid = [], i;
	    radix = radix || chars.length;
	 
	    if (len) {
	      // Compact form
	      for (i = 0; i < len; i++) {
	      	uuid[i] = chars[0 | Math.random() * radix];
	      }
	    } else {
	      var r;
	 
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';

	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random() * 16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }
	 
	    return uuid.join('');
	}

	// 通过localStorage模拟本地数据库存取
	var localStorage = window.localStorage;

	var db = {
		dbName: 'sayHi',

		// 保存
		save: function (data) {
			if (!data) {
				return false;
			}

			var colleges = this.findAll();
			var id = uuid(8);

			colleges.unshift({
				'id': id,
				'data': data,
				'createTime': +new Date()
			});

			try {
				localStorage.setItem(this.dbName, JSON.stringify(colleges));
				return id;
			} catch (e) {
				return false;
			}
		},

		// 查找
		find: function (id) {
			if (!id) {
				return ;
			}

			var colleges = this.findAll();

			for (var i = 0, len = colleges.length; i < len; i++) {
				if (colleges[i].id == id) {
					return colleges[i];
				}
			}

			return null;
		},

		findAll: function () {
			var colleges = localStorage.getItem(this.dbName);

			if (!colleges) {
				colleges = [];
			} else {
				colleges = JSON.parse(colleges);
			}

			return colleges;
		},

		// 删除
		remove: function (id) {
			if (!id) {
				return ;
			}

			var colleges = this.findAll();

			for (var i = 0, len = colleges.length; i < len; i++) {
				if (colleges[i].id = id) {
					colleges.splice(i, 1);
					return true;
				}
			}

			return false;
		},

		removeAll: function () {
			localStorage.removeItem(this.dbName);
		}
	};

	return {
		// 数据操作
		DB: db,

		//获取url键值对
        getUrlKeyValObj: function () {
        	var url = window.location.href,
				arr, i, len,
				paramsObj = {};	
        	
        	if (this.urlKeyValObj) {
        		return this.urlKeyValObj;
        	}
	
			arr = url.substring(url.indexOf('?')+1).split('&');
			
			for (i = 0, len = arr.length; i < len; i++) {
				var reg = /(.*)\=(.*)/g,
					match = reg.exec(arr[i]);
	
				if (match && match[1]) {
					paramsObj[decodeURIComponent(match[1])] = decodeURIComponent(match[2]);
				}
			}
			
			this.urlKeyValObj = paramsObj;
			
			return paramsObj;
        },
        
        //获取url的对应参数的值
		getUrlValue: function (param) {
			if (!param) {
				return '';
			}
			
			var paramsObj = this.getUrlKeyValObj();

			if (paramsObj.hasOwnProperty(param)) {
				return paramsObj[param];
			} else {
				return '';
			}
		},

		// 时间补0
        timeAddZero: function (time) {
        	if (time < 10) {
                return '0' + time;
            } else {
                return '' + time;
            }
        },

		// 时间格式化
		format: function(date, formatStr) {
	        var year, month, day, hour, minute, second, 
	            reg, rule, afterFormat;

	        date = new Date(date);

	        if (!formatStr) {
	            return date.getTime();
	        }

	        year = date.getFullYear();
	        month = date.getMonth() + 1;
	        day = date.getDate();
	        hour = date.getHours();
	        minute = date.getMinutes();
	        second = date.getSeconds();

	        rule = {
	            'yy': year - 2000,
	            'yyyy': year,
	            'M': month,
	            'MM': this.timeAddZero(month),
	            'd': day,
	            'dd': this.timeAddZero(day),
	            'h': hour,
	            'hh': this.timeAddZero(hour),
	            'm': minute,
	            'mm': this.timeAddZero(minute),
	            's': second,
	            'ss': this.timeAddZero(second)
	        };

	        reg = /y{2,4}|M{1,2}|d{1,2}|h{1,2}|m{1,2}|s{1,2}/g;

	        afterFormat = formatStr.replace(reg, function($) {
	            if ($ in rule) {
	                return rule[$];
	            } else {
	                return $;
	            }
	        });

	       return afterFormat;

	    }
	};
});