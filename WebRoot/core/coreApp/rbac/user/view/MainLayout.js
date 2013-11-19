Ext.define("core.rbac.user.view.MainLayout",{
	extend:"Ext.panel.Panel",
	alias : 'widget.rbac.mainlayout',
	layout : 'border',
	items : [{
		xtype:"rbac.depttree",
		region:"west",
		width:comm.get("clientWidth")*0.18
	},{
		xtype:"rbac.centerlayout",
		region:"center"
	}]
})