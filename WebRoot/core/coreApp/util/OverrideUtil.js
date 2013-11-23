/**
 * 增加按钮的点击前点击后事件
 */
Ext.override(Ext.button.Button,{
    initComponent: function() {
        var me = this;
        if(!Ext.isEmpty(me.handler) && Ext.isString(me.handler)){
            me.handler = eval(me.handler);
        }
        me.addEvents('beforeclick','clicked');
        me.callParent(arguments);
    },
    onClick : function(e){
        var me = this;
        if (me.preventDefault || (me.disabled && me.getHref()) && e) {
            e.preventDefault();
        }
        if (e.button !== 0) {
            return;
        }
        if (!me.disabled) {
            if (me.enableToggle && (me.allowDepress !== false || !me.pressed)) {
                me.toggle();
            }
            if (me.menu && !me.hasVisibleMenu() && !me.ignoreNextClick) {
                me.showMenu();
            }
            var flag = me.fireEvent('beforeclick', me, e);//单击前
            if(flag != false){
                me.fireEvent('click', me, e);
                if (me.handler) {
                    me.handler.call(me.scope || me, me, e);
                }
                me.onBlur();
            }else{
            	me.onBlur();
            }
            me.fireEvent('clicked', me, e);//单击后
        }
    }
});

/**附件字段的改造*/
Ext.override(Ext.form.field.File,{  
	setReadOnly : function(readOnly){
		var me = this;
		if(me.buttonEl)me.buttonEl.setVisible(!readOnly);//隐藏浏览按钮
        me.callParent(arguments);
	},
	buttonText: '浏览',
	setValue : function(v){
        var me = this,inputEl = me.inputEl;
		var data = {docName : ''};
		if(!Ext.isEmpty(v)){
			//截取文件名
			var index = v.lastIndexOf('/');
			if(index == -1){
				index = v.lastIndexOf('\\');
			}
			var fn = v.substring(index+1,v.length);
			data.docName=fn;
			
		}
		v = Ext.value(v,'');
		data.address=v;
		//如果没有附件，则以自己本身的值作为路径
		data.address = Ext.value(data.address,v);
		me.fileData = data;
        if (inputEl && me.emptyText && !Ext.isEmpty(value)) {
            inputEl.removeCls(me.emptyCls);
        }
		if(inputEl){
			inputEl.dom.value = Ext.value(data.docName,v);
		}
        me.callParent(arguments);
        me.applyEmptyText();
	},
	afterRender: function(){
		var me = this;
        me.callParent();
        //下载链接
		var html = "<a style='color : red;' href = '#' target='_black'></a>";
		
		me.hiddenEl = me.bodyEl.insertHtml('afterBegin',html,true);
		me.inputEl.dom.onclick = function(){
			if(me.fileData && !Ext.isEmpty(me.fileData.address)){
				window.open(me.fileData.address);
			}
		};
		me.inputEl.dom.style.textDecoration = 'underline';
		me.inputEl.dom.style.color = 'blue';
    },
    getText : function(){
    	return this.fileData.docName;
    }
});
