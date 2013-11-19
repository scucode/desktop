Ext.define("core.rbac.user.store.DeptStore",{
	extend: 'Ext.data.TreeStore',
	defaultRootId:"ROOT",
	model:factory.ModelFactory.getModelByName("com.desktop.model.extjs.JSONTreeNode","checked").modelName,
	proxy:{
		type:"ajax",
		url:"/rbac/deptAction!getTree.do",
		extraParams :{excludes: 'checked'},
		reader:{
			type:"json"
		},
		writer:{
			type:"json"
		}
	},
	autoLoad:true
});