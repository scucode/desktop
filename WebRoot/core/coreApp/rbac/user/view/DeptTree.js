Ext.define("core.rbac.user.view.DeptTree",{
	extend:"Ext.tree.Panel",
	alias : "widget.rbac.depttree",
	displayField : "text",
	rootVisible : false,
	store : "core.rbac.user.store.DeptStore",
	title:"部门管理",
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
	    	var mainLayout=tree.up("panel[xtype=rbac.mainlayout]");
			var deptForm=mainLayout.down("panel[xtype=rbac.deptform]");
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
			tooltip : '添加部门',
			iconCls : 'tree_model_add',
			ref : 'treeIns'
		},{
			xtype : 'button',
			tooltip : '添加子部门',
			iconCls : 'tree_func_add',
			disabled : true,
			ref : 'treechildIns'
		}, {
			xtype : 'button',
			tooltip : '删除部门',
			iconCls : 'tree_delete',
			disabled : true,
			ref : 'treeDel'
		}]
})