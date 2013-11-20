/**
 * 表单控制器
 */
Ext.define("core.app.controller.PanelController",{
	extend:"Ext.app.Controller",
	initPanel:function(){
		var self=this;
		var panelCtr={
			"basepanel":{
				render:function(panel){
					var funCode=panel.funCode;
					panel.itemId=funCode+"_basepanel";
				}
			}
		}
		Ext.apply(self.ctr,panelCtr);
	}
});