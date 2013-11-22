Ext.define("core.sys.menu.store.MenuStore",{
	extend: 'Ext.data.TreeStore',
	model:factory.ModelFactory.getModelByName("com.desktop.model.extjs.JSONTreeNode","checked").modelName,
	proxy: {
		type: 'ajax',
		url: '/pc/menuAction!getTree.do',
		extraParams :{excludes: 'checked'},
		reader:{
			type:"json"
		},
		writer:{
			type:"json"
		}
	},
	autoLoad: true
});