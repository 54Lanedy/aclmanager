var _role = $.extend({}, _role);
layui.use('transfer',function () {
});
aclm.init(function () {
    var transfer = layui.transfer;
    _role.root = aclm.root + '/sys/role';
    //左侧权限模块列表初始化
    $('#role_layout_west_table').datalist({
        url: aclm.root + '/sys/aclModule/list.json',
        lines: true,
        fit:true,
        border: false,
        textFormatter: function (value, row, index) {
            return row.name;
        },
        onSelect:function (index, row) {
            //点击左侧列表加载中间角色列表
            $("#role_layout_center_table").datalist({
                url: _role.root + '/list.json',
                queryParams:{aclModuleId: row.id},
                textFormatter: function (value, row, index) {
                    return row.name;
                },
                onSelect: function (index, role) {
                    //加载最右侧角色权限、角色用户关系
                    _role.loadRoleAndAclTree(row.id, role.id);
                    _role.loadRoleAndUserTree(role.id);
                }
            });
            //置空角色与权限tree
            $("#role_and_acl_tree").tree('loadData',[]);

            if (transfer.index > 0) {
                transfer.reload('role_transfer',{
                    data:[]
                });
            }
        }
    });
    //初始化角色列表
    $("#role_layout_center_table").datalist({
        lines: true,
        fit:true,
        border: false
    });
    //初始化角色与权限tree
    $("#role_and_acl_tree").tree({

    });
    //点击角色加载最右侧的角色和权限tab
    _role.loadRoleAndAclTree = function(aclModuleId,roleId){
        $("#role_and_acl_tree").tree({
            url: _role.root + '/roleAndAclTree.json',
            lines: true,
            // checkbox:true,
            cascadeCheck:false, //级联选中
            queryParams:{
                aclModuleId: aclModuleId,
                roleId: roleId
            },
            checkbox:function (data) {
                //判断是否有子节点
                if (data && data.parentId === 'ROOT') {
                    return false;
                }
                return true;
            }
        });
    };
    //点击角色加载最右侧的角色与用户
    _role.loadRoleAndUserTree = function(roleId){
        //角色与权限穿梭框
        var transfer = layui.transfer;
        aclm.ajax(_role.root + "/getUserFormatTransfer.json",{roleId:roleId},function (map) {
            transfer.render({
                elem: '#role_transfer'
                ,title: ['候选用户', '已选用户']
                ,showSearch: true
                ,id: 'role_transfer'
                ,data: map.data['data']
                ,value: map.data['value']
                ,height:370
            });
        });
    };

    //保存角色与权限关系
    _role.saveRoleAndAcl = function(){
        var aclModule = $('#role_layout_west_table').datalist('getSelected');
        var role = $("#role_layout_center_table").datalist('getSelected');
        if (!role || !aclModule) {
            return aclm.TTipWaring('请选择完整的权限系统和角色');
        }
        var aclIds = [];
        var aclNodes = $("#role_and_acl_tree").tree('getChecked');
        if (aclNodes.length > 0) {
            $.each(aclNodes, function (index, acl) {
                aclIds.push(acl.id);
                var parent = $("#role_and_acl_tree").tree('getParent', acl.target);
                if (parent && aclIds.indexOf(parent.id) === -1) {
                    aclIds.push(parent.id);
                }
            });
        }

        aclm.ajax(_role.root+'/saveRoleAndAcl.json', {aclIds:aclIds,roleId:role.id},function (res) {
            aclm.callMsg(res);
            if (res.success) {
                $("#role_and_acl_tree").tree('reload');
            }
        });
    };

    //保存角色与用户关系
    _role.saveRoleAndUser =function(){
        var role = $("#role_layout_center_table").datalist('getSelected');
        if (!role) {
            return aclm.TTipWaring("请选择角色");
        }
        var transfer = layui.transfer;
        var rightData = transfer.getData('role_transfer');
        var checkedUserIds = [];
        if (rightData.length > 0) {
            $.each(rightData, function (index, row) {
                checkedUserIds.push(row.value);
            })
        }
        aclm.ajax(_role.root + "/saveRoleAndUser.json",{roleId:role.id, userIds: checkedUserIds},function (res) {
            aclm.callMsg(res);
        });
    };

    _role.openAddDia = function (type) {
        var aclModule = $('#role_layout_west_table').datalist('getSelected');
        if (type === 0) {
            //新增
            if (!aclModule) {
                return aclm.TTipWaring('请先选择归属的权限系统');
            }
            $("#role_dia_edit_form").form("reset");
            $("#role_aclModuleId").val(aclModule.id);
            $("#role_aclModuleName").textbox("setValue", aclModule.name);
        }else {
            //修改
            var row = $('#role_layout_center_table').datalist('getSelected');
            if (!row) {
                return aclm.TTipWaring("至少选择一个角色");
            }
            row.aclModuleName = aclModule.name;
            $("#role_dia_edit_form").form("load", row);
        }
        $("#role_dia").dialog('open');
    };
    //执行添加保存
    _role.save = function () {
        $('#role_dia_edit_form').form('submit', {
            url: _role.root + '/save.json',
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
                    $("#role_dia").dialog('close');
                    var aclModule = $('#role_layout_west_table').datalist('getSelected');
                    $('#role_layout_center_table').datalist("load", {aclModuleId: aclModule.id});
                }
            }
        });
    };



});