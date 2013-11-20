Ext.define("core.rbac.user.view.UserLayout",{
	extend:"core.app.base.BasePanel",
	alias : 'widget.rbac.userlayout',
	funCode:"usermodule_main",
	funData:{
	        action:"/rbac/userAction", //请求Action
	        whereSql:"",//表格查询条件
	        orderSql:" order by orderIndex",//表格排序条件
	        pkName:"userId",
	        modelName:"com.desktop.rbac.model.EndUser",//实体全路径
	        tableName:"EndUser",//表名
	        defaultObj:{password:"123456"}
	},
	items :{
		xtype:"rbac.usergrid"
	}
})