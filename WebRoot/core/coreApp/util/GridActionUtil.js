Ext.define("core.util.GridActionUtil", {
			/**
			 * 得到默认值对象
			 * 
			 * @param {}
			 *            defaultObj
			 */
			getDefaultValue : function(defaultObj) {
				var resObj={};
				for (var field in defaultObj) {
					var value = defaultObj[field];
					// @createTime@ @createUser@ @createDept@
					if (value.indexOf("@") >= 0) {
						if (value == "@createUser@") {
							value = "zs";
						} else if (value == "@createUserName@") {
							value = "张三";
						} else if (value == "@createDept@") {
							value = "yfb";
						} else if (value == "@createDeptName@") {
							value = "研发部"
						} else if (value == "@createTime@") {
							value = new Date();
						}
					}
					resObj[field]=value;					
				}
				return resObj;
			}
});