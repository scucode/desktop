/**
 * �����������
 */
Ext.define("core.test.controller.TestController",{
	extend:"Ext.app.Controller",
	init:function(){
		var self=this
		//事件注册
		this.control({
			
		});
	},
	views:[
	"core.test.view.TestGrid",
	"core.test.view.TestForm",
	"core.test.view.TestPanel"
	]
});