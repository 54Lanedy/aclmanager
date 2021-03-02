<script type="text/javascript" src="./js/sys/log.js"></script>

<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="log_toolbar">
        <div class="wu-toolbar-search">
            类型:
            <select id="log_type" class="easyui-combobox" panelHeight="auto" style="width:100px" data-options="editable:false">
                <option value="">全部</option>
                <option value="3">权限模块</option>
                <option value="4">权限菜单</option>
                <option value="5">角色</option>
                <option value="6">角色权限关系</option>
                <option value="7">角色用户关系</option>
            </select>&nbsp;&nbsp;
            <label>操作人：</label><input id="log_operator" class="easyui-textbox" style="width:100px">&nbsp;&nbsp;
            <label>操作前的值：</label><input id="log_old_value" class="easyui-textbox" style="width:100px">&nbsp;&nbsp;
            <label>操作后的值：</label><input id="log_new_value" class="easyui-textbox" style="width:100px">&nbsp;&nbsp;
            <label>开始时间：</label><input id="log_d1" class="easyui-datebox" style="width:100px" data-options="editable:false">-
            <label>结束时间：</label><input id="log_d2" class="easyui-datebox" style="width:100px" data-options="editable:false">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="_log.search();">开始检索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="log_dg" class="easyui-datagrid" ></table>
</div>