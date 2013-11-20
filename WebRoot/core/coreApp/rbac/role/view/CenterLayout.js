Ext.define("core.rbac.role.view.CenterLayout",{
	extend:"Ext.panel.Panel",
	alias : 'widget.role.centerlayout',
	layout:"border",
	items : [{
		xtype:"role.roleform",
		region:"north",
		height:comm.get("resolutionHeight")*0.2
	},{
		xtype:"role.usergrid",
		region:"center"
	}]
})