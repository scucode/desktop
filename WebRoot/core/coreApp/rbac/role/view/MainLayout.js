Ext.define("core.rbac.role.view.MainLayout",{
	extend:"Ext.panel.Panel",
	alias : 'widget.role.mainlayout',
	layout : 'border',
	items : [{
		xtype:"role.roletree",
		region:"west",
		width:comm.get("clientWidth")*0.18
	},{
		xtype:"role.centerlayout",
		region:"center"
	}]
})