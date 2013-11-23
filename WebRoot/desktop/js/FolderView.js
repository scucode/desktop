Ext.define('Ext.ux.desktop.FolderView', {
    extend: 'Ext.view.View',
    frame:false,
    style: {
        background: 'white'
    },
    alias:"widget.folderdataview",
    tpl: [
           '<tpl for=".">',
            '<div class="ux-desktop-shortcut" id="{text}-shortcut">',
                '<div class="ux-desktop-shortcut-icon style:\"background-image:{bigIcon}\"">',
                    '<img src="{bigIcon}" title="{text}">',
                '</div>',
                '<span class="ux-desktop-shortcut-text"><font color=black>{text}</font></span>',
            '</div>',
        '</tpl>',
        '<div class="x-clear"></div>'
           ],
      trackOver: true,
      overItemCls: 'x-item-over',
      itemSelector: 'div.ux-desktop-shortcut',
      store:Ext.create("Ext.data.Store",{
      	model:factory.ModelFactory.getModelByName("com.desktop.model.extjs.JSONTreeNode","checked").modelName      	
      })
});