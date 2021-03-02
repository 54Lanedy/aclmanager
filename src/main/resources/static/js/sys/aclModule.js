var _aclModule = $.extend({}, _aclModule);
aclm.init(function () {
    _aclModule.root = aclm.root + "/sys/aclModule";
    _aclModule.acl_root = aclm.root + "/sys/acl";


    //图标选择下拉框初始化
    $('#sys_res_iconCls').combo({
        width:260,
        required: true,
        editable: false
    });
    _aclModule.iconClick = function (code) {
        $('#sys_res_iconCls').combo('setValue', code).combo('setText', code).combo('hidePanel');
    };
    _aclModule.sys_res_search_icon = function () {
        $('#sys_res_sp').appendTo($('#sys_res_iconCls').combo('panel'));
        $('#sys_res_iconCls').combo('clear');
        aclm.ajax(_aclModule.acl_root + '/iconList.json', {}, function (data) {
            $.each(data, function (index, o) {
                $('#sys_res_sp_icons')
                    .append('<img name="' + o.code + '" onclick="_aclModule.iconClick(\'' + o.code + '\');" style="width:18px;height:18px;cursor:pointer;border:1px solid #ccc;padding:2px;"  src="' + o.iconUrl + '">  ');
            });

        });
    };
    //左侧权限模块列表初始化
    $('#aclModule_layout_west_table').datalist({
        url: _aclModule.root + '/list.json',
        lines: true,
        fit:true,
        border: false,
        textFormatter: function (value, row, index) {
            return row.name;
        },
        onSelect:function (index, row) {
            //点击左侧列表加载右侧权限点
            $("#aclModule_layout_center_table").treegrid({
                url: _aclModule.acl_root + '/list.json',
                queryParams:{aclModuleId: row.id}
            });
        },
        onBeforeLoad:function (param) {
            _aclModule.sys_res_search_icon();
        }
    });
    //打开添加form弹窗
    _aclModule.openAddModuleDia = function (op) {
        if (op === 0) {
            //添加
            $("#aclModule_dia_edit_form").form("reset");
        }else {
            //修改
            var row = $('#aclModule_layout_west_table').datalist('getSelected');
            if (!row) {
                return aclm.TTipWaring("至少选择一行");
            }
            $("#aclModule_dia_edit_form").form("load", row);
        }
        $("#aclModule_dia").dialog('open');
    };
    //执行添加保存
    _aclModule.save = function () {
        $('#aclModule_dia_edit_form').form('submit', {
            url: _aclModule.root + '/save.json',
            onSubmit: function(){
                var isValid = $(this).form('validate');
                //return false;
                if (isValid) {
                    aclm.submitLoad(true);
                } else {
                    aclm.TTipWaring("请完善您的表单信息！");
                }
                return isValid;	// 返回false终止表单提交
            },
            success:function(res){
                var json = JSON.parse(res);
                aclm.callMsg(json);
                aclm.submitLoad(false);
                if (json.success) {
                    $("#aclModule_dia").dialog('close');
                    $('#aclModule_layout_west_table').datalist("load", {});
                }
            }
        });
    };
    //列表：权限点管理
    $("#aclModule_layout_center_table").treegrid({
        pagination: false,
        rownumbers : true,// 设置为true将显示行数。
        singleSelect : true,// 如果为true，则只允许选择一行。
        striped : true,// 是否显示斑马线效果。
        nowrap : false,// 是否折行
        border : true,// 是否显示边框
        fit : true,// 是否最大化
        fitColumns : true,// 真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。
        idField:'id',
        treeField:'name',
        columns:[[
            {field:'id',title:'代码',hidden:true},
            {field:'name',title:'权限菜单名称',width:120},
            {field:'aclModuleName',title:'权限系统',width:80},
            {field:'typeStr',title:'类型',width:80},
            {field:'url',title:'URL',width:140},
            {field:'iconCls',title:'图标',width:100},
            {field:'statusStr',title:'状态',width:80},
            {field:'code',title:'权限编码',width:100},
            {field:'seq',title:'排序',width:100}
        ]]
    });

    _aclModule.openAddAclDia = function (op) {
        var row = $('#aclModule_layout_west_table').datalist('getSelected');
        var acl = $("#aclModule_layout_center_table").treegrid('getSelected');
        if (op === 0) {
            //添加
            if (!row) {
                return aclm.TTipWaring('请先选择权限系统');
            }
            $("#acl_dia_edit_form").form('reset');
            $("#acl_moduleName").textbox('setValue', row.name);
            $("#acl_module_id").val(row.id);
            var aclId = 'ROOT';
            if (!acl) {
                aclm.TTipInfo('你将创建模块菜单');
                $("#acl_parentName").textbox('setValue', '-');
            } else {
                aclId = acl.id;
                $("#acl_parentName").textbox('setValue', acl.name);
            }
            $("#acl_parent_id").val(aclId);
        }else {
            //修改
            //回显图标
            _aclModule.iconClick(acl.iconCls);
            //获取当前节点的父节点
            var prow = $("#aclModule_layout_center_table").treegrid('getParent',acl.id);
            acl.parentName = prow ? prow.name : "-";
            $("#acl_dia_edit_form").form('load',acl);
        }
        $("#acl_dia").dialog("open");
    };

    _aclModule.save_acl = function () {
        $('#acl_dia_edit_form').form('submit', {
            url: _aclModule.acl_root + '/save.json',
            onSubmit: function(){
                var isValid = $(this).form('validate');
                //return false;
                if (isValid) {
                    aclm.submitLoad(true);
                } else {
                    aclm.TTipWaring("请完善您的表单信息！");
                }
                return isValid;	// 返回false终止表单提交
            },
            success:function(res){
                var json = JSON.parse(res);
                aclm.callMsg(json);
                aclm.submitLoad(false);
                if (json.success) {
                    $("#acl_dia").dialog('close');
                    var row = $('#aclModule_layout_west_table').datalist('getSelected');
                    $("#aclModule_layout_center_table").treegrid("load", {aclModuleId: row.id});
                }
            }
        });
    };


});