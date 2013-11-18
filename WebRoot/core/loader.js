/**
 * 程序外部组件引用
 */
Ext.QuickTips.init(); //初始化Ext.QuickTips，以使得tip提示可用
Ext.tip.QuickTipManager.init(); //初始化提示工具框

//启动动态加载机制
Ext.Loader.setConfig({
	enabled:true,
	paths:{
		baseUx:"core/ux/base",
		factory:"core/coreApp/util"
	}
});

//同步加载
Ext.syncRequire([
	"baseUx.form.datetime.DateTimePicker",
    "baseUx.form.datetime.DateTime"
    
]);