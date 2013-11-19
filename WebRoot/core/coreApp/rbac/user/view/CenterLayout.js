Ext.define("core.rbac.user.view.CenterLayout",{
	extend:"Ext.panel.Panel",
	alias : 'widget.rbac.centerlayout',
	layout:"border",
	items : [{
		xtype:"rbac.deptform",
		region:"north",
		height:comm.get("resolutionHeight")*0.2
	},{
		xtype:"rbac.userlayout",
		region:"center"
	}]
})