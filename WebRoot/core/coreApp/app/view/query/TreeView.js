Ext.define('core.app.view.query.TreeView',{
 	extend: 'Ext.tree.Panel',
	frame : true,
	animCollapse : false,
	alias: 'widget.mttreeview',
	rootVisible : false,
	multiSelect : false,
	width:comm.get("clientWidth")*0.3,
	height:comm.get("resolutionHeight")*0.7,
	autoScroll : true,
	animate : true,
	initComponent: function(){
	  	this.store=Ext.create("core.app.store.query.TreeStore",{
	   		url:this.url
	   });
	   var params=this.store.getProxy().extraParams;
	   if(this.params){
	   		params=Ext.apply(params,this.params)
	   }
	   if(!this.multiSelect){
	   		params.excludes="checked";
	   }
	   var qc = Ext.create('Ext.form.field.ComboBox',{
            queryMode: 'local',
            store : new Ext.data.Store({fields :['id','text','parentText']}),
            hideTrigger : true,
            valueField  : 'id',
            displayField : 'text',
            ref : 'queryTreeCBB',
            emptyText  : '输入查询信息...',
            flex : 1,
            listConfig:function(df) { 
                    return "{text}<tpl if='parentText'><div style='color:#C0C0C0;'>({parentText})</div></tpl>";
                }
        });
		this.dockedItems= [{
       	 xtype: 'toolbar',dock: 'top',layout : 'hbox',items: [qc]
     	}]
     	this.callParent(arguments);
	}
});