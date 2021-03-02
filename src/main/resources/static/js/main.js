var _main = $.extend({}, _main);

aclm.init(function () {
    _main.root = '/aclmanager';
    _main.tabsId = "#wu-tabs";

    _main.addTab = function (tabsId, obj) {
        if (null == obj.url || "" == obj.url) {
            return;
        }
        if ($(tabsId).tabs('exists', obj.text)) {
            $(tabsId).tabs('select', obj.text);
            _main.refreshTab(obj, tabsId);
        } else {
            var myoptions = {
                closable: true,
                cache: true,
                iconCls: (null != obj.iconCls && '' != obj.iconCls) ? obj.iconCls : 'myicon-bullet_blue',
                title: obj.text,
                cls:'pd3',
                fit:true,
                // content:content,
                tools: [{
                    iconCls: 'icon-mini-refresh',
                    handler: function () {
                        _main.refreshTab(obj, tabsId);
                    }
                }],
                onLoadError: function () {
                    $('#main_loading').hide();
                }
            };
            if (obj.ty == 3) {
                myoptions.cache = false;
                myoptions.content = '<iframe  frameborder="0" scrolling="auto"  src="' + obj.url + '" style="width:100%;height:100%;"></iframe>';
            } else {
                $('#main_loading').show();
                myoptions.href = obj.url;
            }
            $(tabsId).tabs('add', myoptions);
        }
    };

    //刷新
    _main.refreshTab = function (obj, tabsId) {
        var tab = $(tabsId).tabs('getSelected');  // 获取选择的面板
        var myoptions = {
            href: obj.url,
            onLoadError: function () {
                $('#main_loading').hide();
            }
        };
        if (obj.ty == 3) {
            myoptions.cache = false;
            myoptions.content = '<iframe  frameborder="0" scrolling="auto"  src="' + obj.url + '" style="width:100%;height:100%;"></iframe>';
        } else {
            $('#main_loading').show();
        }
        $(tabsId).tabs('update', {
            tab: tab,
            options: myoptions
        });
        if (obj.ty != 3) {
            tab.panel('refresh');
        }
    };
    _main.openTab = function (name, url, iconCls) {
        var obj = {text:name, url: _main.root + url, iconCls:iconCls};

        _main.addTab(_main.tabsId, obj);
    };


});
