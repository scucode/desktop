Ext.define("core.app.view.query.MtssWindow",{
	extend: 'Ext.window.Window',
	modal:true,
	maximizable:true,
	title:"树形组件",
	alias: 'widget.mtsswinview',
	layout:"fit",
	initComponent: function(){
	   	var config=this.config;
	   	var items={
	   		xtype:this.queryType,
	   		multiSelect:this.multiSelect
	   	};
	   	items=Ext.apply(items,config);
	   	this.items=items;
	   	this.buttonAlign="center";
	   	this.buttons=[{
					text : '确定',
					ref : 'ssOkBtn',
					iconCls : 'tree_ok'
					
				},{
					text: '取消',
					ref : 'ssCancelBtn',
					iconCls : 'tree_delete'
				}];
		this.callParent(arguments);
	}
});