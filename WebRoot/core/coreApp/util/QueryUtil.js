Ext.define("core.util.QueryUtil",{
	/**
	 * 查询树形
	 * @param {} config
	 */
	selTreeWin:function(config){
		config.queryType="mttreeview";
		var win=Ext.create("core.app.view.query.MtssWindow",config);
		win.show();
	}
});