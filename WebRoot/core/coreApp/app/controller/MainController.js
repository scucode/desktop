/**
 * 程序主控制器
 */
Ext.define("core.app.controller.MainController",{
	extend: 'Ext.app.Controller',
	mixins: {
		
	},
	
	init : function() {
		var self = this;
		coreApp = self;
		
		//事件注册
		this.control();
	},
	
	views : [
		'core.app.base.BasePanel',
		'core.app.base.BaseForm',
		'core.app.base.BaseGrid'
	]
});