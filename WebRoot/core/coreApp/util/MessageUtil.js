Ext.define("core.util.MessageUtil",{
	/**
	 *  提示信息
	 * @param {} config
	 */
	msgbox:function(config){
		var title="提示";
		var context="";
		if(typeof(config)=="string"){
			context=config;
		}
		Ext.example.msg(title,context);
	}
	
});