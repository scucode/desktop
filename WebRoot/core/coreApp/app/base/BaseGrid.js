Ext.define("core.app.base.BaseGrid",{
	extend: 'Ext.grid.Panel',
	alias:"widget.basegrid",
	border:0,
	multiSelect:true,
	frame:true,
	selModel:{
		selType:"checkboxmodel"
	},
	tbar:[
		{xtype:'button',text:'添加到表单',ref:'gridInsertF',iconCls:'table_add'},
		{xtype:'button',text:'添加',ref:'gridInsert',iconCls:'table_add'},
		{xtype:'button',text:'编辑',ref:'gridEdit',iconCls:'table_remove'},
		{xtype:'button',text:'删除',ref:'gridDelete',iconCls:'table_remove'},
		{xtype:'button',text:'保存',ref:'gridSave',iconCls:'table_save'}
	],
	columns:[],
	enableKeyNav:true,  //可以使用键盘控制上下
	columnLines:true, //展示竖线
	initComponent:function(){
		this.editing=Ext.create("Ext.grid.plugin.CellEditing");
		this.plugins=[this.editing];
		this.callParent(arguments);
	}
})