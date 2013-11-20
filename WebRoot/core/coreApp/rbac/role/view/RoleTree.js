Ext.define("core.rbac.role.view.RoleTree",{
	extend:"Ext.tree.Panel",
	alias : "widget.role.roletree",
	displayField : "text",
	rootVisible : false,
	store : "core.rbac.role.store.RoleStore",
	title:"角色管理",
	tools : [{
		type:'refresh',
	   	qtip: '刷新',
	   	 handler: function(event, toolEl, header){
	    	var tree=header.ownerCt
	    	tree.getStore().load();
	    	var treeDel=tree.down("button[ref=treeDel]");
	    	treeDel.setDisabled(true);
	    	var mainLayout=tree.up("panel[xtype=role.mainlayout]");
			var deptForm=mainLayout.down("panel[xtype=role.roleform]");
	    	var formObj=deptForm.getForm();
	    	var fields = formObj.getFields();
	    	fields.each(function(field){
	    		field.setValue(null);
	    	});
	    	tree.getSelectionModel().deselectAll(true);
	   	 }
	}],
	rbar:[{
			xtype : 'button',
			tooltip : '添加角色',
			iconCls : 'tree_model_add',
			ref : 'treeIns'
		}, {
			xtype : 'button',
			tooltip : '删除角色',
			iconCls : 'tree_delete',
			disabled : true,
			ref : 'treeDel'
		}]
})