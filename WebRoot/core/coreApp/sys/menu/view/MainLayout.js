Ext.define("core.sys.menu.view.MainLayout",{
	extend: 'Ext.panel.Panel',
	alias: "widget.menu.mainlayout",
	layout: 'border',
	items : [{
		xtype:"menu.menutree",
		region:"west",
		width:comm.get("clientWidth")*0.18
	},{
		xtype:"menu.menuform",
		region:"center"
	}]
});