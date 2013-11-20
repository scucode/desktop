/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.App', {
    extend: 'Ext.ux.desktop.App',

    requires: [
        'Ext.window.MessageBox',

        'Ext.ux.desktop.ShortcutModel',
        'MyDesktop.Settings'
    ],

    init: function() {
        // custom logic before getXYZ methods get called...

        this.callParent();

        // now ready...
    },

    getModules : function(){
        return [
            
        ];
    },

    getDesktopConfig: function () {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            //cls: 'ux-desktop-black',

            contextMenuItems: [
                { text: '背景个性化', handler: me.onSettings, scope: me }
            ],

            shortcuts: Ext.create('Ext.data.Store', {
                model: 'Ext.ux.desktop.ShortcutModel',
                data: [
                	{ name: '基础组件', iconCls: 'accordion-shortcut', module: 'acc-win',controller:'core.test.controller.TestController',viewIconCls:'user-girl',xtype:'testpanel' },
                    { name: 'Notepad', iconCls: 'notepad-shortcut', module: 'notepad',controller:'',viewIconCls:'connect' },
                    { name: '部门人员管理', iconCls: 'accordion-shortcut',viewIconCls:"icon_customers",xtype:"rbac.mainlayout",controller:"core.rbac.user.controller.DeptUserController",module:"deptusermainview"},
                    { name: '角色权限管理', iconCls: 'customers',viewIconCls:"icon_admin",xtype:"role.mainlayout",controller:"core.rbac.role.controller.RolePermController",module:"rolepermmainview"}
                    
                ]
            }),

            wallpaper: 'desktop/wallpapers/Blue-Sencha.jpg',
            wallpaperStretch: false
        });
    },

    // config for the start menu
    getStartConfig : function() {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            title: 'Don Griffin',
            iconCls: 'user',
            height: 300,
            toolConfig: {
                width: 100,
                items: [
                    {
                        text:'桌面背景',
                        iconCls:'settings',
                        handler: me.onSettings,
                        scope: me
                    },
                    '-',
                    {
                        text:'系统注销',
                        iconCls:'logout',
                        handler: me.onLogout,
                        scope: me
                    }
                ]
            }
        });
    },

    getTaskbarConfig: function () {
        var ret = this.callParent();

        return Ext.apply(ret, {
            quickStart: [
//                { name: 'Accordion Window', iconCls: 'accordion', module: 'acc-win' },
//                { name: 'Grid Window', iconCls: 'icon-grid', module: 'grid-win' }
            ],
            trayItems: [
                { xtype: 'trayclock', flex: 1 }
            ]
        });
    },

    onLogout: function () {
        Ext.Msg.confirm('注销', '您确定需要注销系统吗?');
    },

    onSettings: function () {
        var dlg = new MyDesktop.Settings({
            desktop: this.desktop
        });
        dlg.show();
    }
});
