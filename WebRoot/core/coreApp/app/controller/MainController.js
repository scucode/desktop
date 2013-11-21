/**
 * 程序主控制器
 */
Ext.define("core.app.controller.MainController",{
	extend: 'Ext.app.Controller',
	mixins: {
		btnCtr:"core.app.controller.ButtonController",
		formCtr:"core.app.controller.FormController",
		gridCtr:"core.app.controller.GridController",
		panelCtr:"core.app.controller.PanelController",
		queryCtr:"core.app.controller.QueryController",
		gridActionUtil:"core.util.GridActionUtil",
		suppleUtil:"core.util.SuppleUtil",
		messageUtil:"core.util.MessageUtil",
		formUtil:"core.util.FormUtil",
		sqlUtil:"core.util.SqlUtil"
	},
	ctr:{},
	init : function() {
		var self = this;
		coreApp = self;
		
		self.initBtn();
		self.initForm();
		self.initGrid();
		self.initPanel();
		self.initQuery();
		//事件注册
		this.control(self.ctr);
	},
	
	views : [
		'core.app.base.BasePanel',
		'core.app.base.BaseForm',
		'core.app.base.BaseGrid'
	]
});