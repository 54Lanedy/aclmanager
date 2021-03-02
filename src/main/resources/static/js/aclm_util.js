/** 这是一个全局js */
// 1. 如果只为$.extend()指定了一个参数（既{}的size=1），则意味着参数target被省略。此时，target就是jQuery对象本身。通过这种方式，我们可以为全局对象jQuery添加新的函数。
// 2. 如果多个对象具有相同的属性，则后者会覆盖前者的属性值。
var aclm = $.extend({},aclm);
$.fn.datagrid.defaults.pageSize = 50;
$.fn.datagrid.defaults.pageList = [20, 30, 50, 80, 100, 150, 200, 300];
$.fn.datalist.defaults.pageSize = 50;
$.fn.datalist.defaults.pageList = [20, 30, 50, 80, 100, 150, 200, 300];

aclm.init = function (fn) {
    $(function () {
        try {
            fn();
        } catch (e) {
            console.log("加载页面异常！");
        }
    });
};

//封装一些常用方法
$(function () {
    aclm.root = '/aclmanager';
    // 封装ajax 异步
    aclm.ajax = function (url, obj, fn) {
        $.ajax({
            url: url,
            type: 'POST',
            cache: false,
            dataType: 'json',
            data: obj,
            beforeSend: function (XMLHttpRequest) {
                // TODO
            },
            complete: function (XMLHttpRequest) {
                // TODO
            },
            success: fn,
            error: function (error) {
                var status = error.status;
                if (status == 403) {
                    aclm.TTipError("操作失败");
                    return;
                }
            }
        });
    };

    // 封装ajax 同步
    aclm.ajaxAsync = function (url, obj, fn) {
        $.ajax({
            url: url,
            type: 'POST',
            cache: false,
            dataType: 'json',
            data: obj,
            async: false,
            beforeSend: function (XMLHttpRequest) {
                // TODO
            },
            complete: function (XMLHttpRequest) {
                // TODO
            },
            success: fn,
            error: function (error) {
                var status = error.status;
                if (status == 403) {
                    aclm.TTipError("操作失败");
                    return;
                }
            }
        });
    };
    /*让页面加载的时候显示等待信息*/
    aclm.queryLoad = function (isShow) {
        aclm.progress(isShow, "数据加载中，请等待.....");
    };
    /*定义数据提交加载事件  isShow:true表示显示*/
    aclm.submitLoad = function (isShow) {
        aclm.progress(isShow, "数据提交中，请等待.....");
    };
    /*加载进度条*/
    aclm.progress = function (bol, text) {
        if (bol) {
            $.messager.progress({text: text, interval: 100});
        } else {
            $.messager.progress('close');
        }
    };

    //顶部消息提示 ty:0表示消息，1成功，2失败，3警告，4错误
    aclm.topMsgUtil = function (ty, msg) {
        var shortCutFunction,message,title;
        switch (ty) {
            case 1:
                shortCutFunction = "success";
                message = msg;
                title = '成功提示框';
                break;
            case 2:
                shortCutFunction = "info";
                message = msg;
                title = '信息提示框';
                break;
            case 3:
                shortCutFunction = "warning";
                message = msg;
                title = '警告提示框';
                break;
            case 4:
                shortCutFunction = "error";
                message = msg;
                title = '错误提示框';
                break;
        }
        toastr.options = {
            // "preventOpenDuplicates": true, //重复内容的提示框在开启时只出现一个 如果当前的提示框已经打开，不会多开。直到提示框关闭后，才可再开)
            "preventDuplicates": true, //重复内容的提示框只出现一次，无论提示框是打开还是关闭
            "closeButton": false, //设置显示"X" 关闭按钮
            "debug": false,
            "progressBar": false, //设置显示timeout时间进度条
            "positionClass": "toast-top-center", //设置toastr显示位置的class
            "onclick": null,   //点击事件
            "showDuration": "400",
            "hideDuration": "1000",
            "timeOut": "2000",  //设置toastr过多久关闭
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "slideDown", //显示方法slideDown：下滑；fadeIn：淡入；
            "hideMethod": "fadeOut"    //隐藏方法slideUp：上滑；fadeOut：淡出；
        };
        toastr[shortCutFunction](message, title);
    };
    //普通提示框--
    //成功提示框
    aclm.TTipSuccess = function (msg) {
        aclm.topMsgUtil(1,msg);
    };
    //信息提示框
    aclm.TTipInfo = function (msg) {
        aclm.topMsgUtil(2,msg);
    };
    //警告提示框
    aclm.TTipWaring = function (msg) {
        aclm.topMsgUtil(3,msg);
    };
    //失败提示框
    aclm.TTipError = function (msg) {
        aclm.topMsgUtil(4,msg);
    };
    //jsonVO数据回调信息--
    aclm.callMsg = function (data) {
        aclm.topMsgUtil(data.success ? 1 : 3, data.msg);
    };

    //提交确认
    aclm.confirm = function (text,fn) {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.msg(text, {
                icon: 3,
                anim: 6,
                time: 0 //不自动关闭
                ,btn: ['确定', '取消']
                ,yes: function(index){
                    layer.close(index);
                    fn();
                }
            });
        });
    };

    aclm.messageTip = function(obj){
        if (obj.success) {
            aclm.TTipSuccess(obj.msg);
        } else {
            aclm.TTipError(obj.msg);
        }
    };
});