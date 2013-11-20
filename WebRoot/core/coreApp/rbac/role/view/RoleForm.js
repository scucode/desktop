Ext.define("core.rbac.role.view.RoleForm",{
	extend:"Ext.form.Panel",
	alias:"widget.role.roleform",
	layout:"auto",
	align:"left",
	frame:true,
	title:"角色信息",
//	defaults:{
//			selectOnFocus:true,
//			msgTarget:"side" //提示信息现在的位置
//	},
	items:[{
				xtype:"textfield",
				fieldLabel:"主键",
				name:"roleId",
				hidden:true
			},{
				xtype:"textfield",
				fieldLabel:"角色名称",
				name:"roleName"
			},{
				xtype:"textfield",
				fieldLabel:"角色编码",
				name:"roleCode"
			},{
				xtype:"numberfield",
				fieldLabel:"排序",
				name:"orderIndex"
			},{
				xtype:"button",
				ref:"submit",
				text:"保存"
			}]
});