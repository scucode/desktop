Ext.define("core.sys.menu.view.MenuTree",{
	extend:"Ext.tree.Panel",
	alias : "widget.menu.menutree",
	displayField : "text",
	rootVisible : false,
	store : "core.sys.menu.store.MenuStore",
	title:"菜单管理",
	tools : [{
		type:'refresh',
	   	qtip: '刷新',
	   	 handler: function(event, toolEl, header){
	    	var tree=header.ownerCt
	    	tree.getStore().load();
	    	var treechildIns=tree.down("button[ref=treechildIns]");
	    	treechildIns.setDisabled(true);
	    	var treeDel=tree.down("button[ref=treeDel]");
	    	treeDel.setDisabled(true);
	    	var mainLayout=tree.up("panel[xtype=menu.mainlayout]");
			var menuForm=mainLayout.down("panel[xtype=menu.menuform]");
	    	var formObj=menuForm.getForm();
	    	var fields = formObj.getFields();
	    	fields.each(function(field){
	    		field.setValue(null);
	    	});
	    	tree.getSelectionModel().deselectAll(true);
	   	 }
	}],
	rbar:[{
			xtype : 'button',
			tooltip : '添加菜单',
			iconCls : 'tree_model_add',
			ref : 'treeIns'
		},{
			xtype : 'button',
			tooltip : '添加子菜单',
			iconCls : 'tree_func_add',
			disabled : true,
			ref : 'treechildIns'
		}, {
			xtype : 'button',
			tooltip : '删除菜单',
			iconCls : 'tree_delete',
			disabled : true,
			ref : 'treeDel'
		}]
})