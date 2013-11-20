Ext.define("core.rbac.role.store.RoleStore",{
	extend:"Ext.data.TreeStore",
	defaultRootId:"ROOT",
	model:factory.ModelFactory.getModelByName("com.desktop.model.extjs.JSONTreeNode","checked").modelName,
	proxy:{
		type:"ajax",
		url:"/rbac/roleAction!getTree.do",
		extraParams :{excludes: 'checked',whereSql:" and 1=1",orderSql:" order by orderIndex"},
		reader:{
			type:"json"
		},
		writer:{
			type:"json"
		}
	},
	autoLoad:true
 });