var _log = $.extend({}, _log);
aclm.init(function () {
    _log.root = aclm.root + '/sys/log';

    _log.search = function () {
        var type = $("#log_type").combobox('getValue');
        var operator = $("#log_operator").textbox('getValue');
        var oldValue = $("#log_old_value").textbox('getValue');
        var newValue = $("#log_new_value").textbox('getValue');
        var d1 = $("#log_d1").datebox('getValue');
        var d2 = $("#log_d2").datebox('getValue');
        var params = {type:type, operator:operator, beforeSeg:oldValue, afterSeg:newValue, fromTime:d1, toTime: d2};
        _log.dg.datagrid("load", params);
    };

    _log.dg = $('#log_dg').datagrid({
        url : _log.root + '/search.json',
        rownumbers : true,// 设置为true将显示行数。
        singleSelect : true,// 如果为true，则只允许选择一行。
        striped : true,// 是否显示斑马线效果。
        nowarp : false,// 是否折行
        border : false,// 是否显示边框
        fit : true,// 是否最大化
        pagination:true,
        fitColumns : true,// 真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。
        toolbar : '#log_toolbar',
        nowrap:false,
        columns : [[
            {field : 'operator',title : '操作者',width:30},
            {field : 'typeStr',title : '操作类型',width:30},
            {field : 'operatorTime',title : '操作时间',width:50},
            {field:'oldValue',title:'操作前的值',width:200,align:'center', formatter: function (value, row, index) {
                    var formatJson = _log.formatJson(value);
                    var rowsCount = 0;
                    //处理json对象
                    if (value) {
                        var json = JSON.parse("[" + value + "]");
                        //获取key的数量
                        rowsCount += Object.keys(json[0]).length + 4;
                        return '<textarea id="log_textarea_'+row.id+'"  rows="'+rowsCount+'"' +
                            ' style="width: 90%" readonly="readonly">'+formatJson+'</textarea>';
                    }
                    return '<textarea id="log_textarea_'+row.id+'"  rows="'+rowsCount+'"' +
                        ' style="width: 90%" readonly="readonly">无</textarea>';
                }},
            {field:'newValue',title:'操作后的值',width:200,align:'center' ,formatter: function (value, row, index) {
                    var formatJson = _log.formatJson(value);
                    var rowsCount = 0;
                    //处理json对象
                    if (value) {
                        var json = JSON.parse("[" + value + "]");
                        //获取key的数量
                        rowsCount += Object.keys(json[0]).length + 4;
                        return '<textarea id="log_textarea_'+row.id+'"  rows="'+rowsCount+'"' +
                            ' style="width: 90%" readonly="readonly">'+formatJson+'</textarea>';
                    }
                    return '<textarea id="log_textarea_'+row.id+'"  rows="'+rowsCount+'"' +
                        ' style="width: 90%" readonly="readonly">无</textarea>';
                }},
            {field:'czl',title:'操作列',width:30,align:'center',formatter:function (value, row, index) {
                    return '<a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick=_log.recover("'+row.id+'") plain="true">' +
                        '<i class="icon-fast-backward"></i>撤销</a>';
                }}
        ]]
    });

    //撤销
    _log.recover = function (id) {
        aclm.confirm("确定撤回上一次操作吗？", function () {
            aclm.ajax(_log.root + '/recover.json', {id: id}, function (res) {
                aclm.callMsg(res);
                if (res.success) {
                    _log.dg.datagrid("load", {});
                }
            });
        })
    };

    _log.formatJson = function (json, options) {
        if(json == '') return '';
        var reg = null,
            formatted = '',
            pad = 0,
            PADDING = '    '; // one can also use '\t' or a different number of spaces

        // optional settings
        options = options || {};
        // remove newline where '{' or '[' follows ':'
        options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
        // use a space after a colon
        options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

        // begin formatting...
        if (typeof json !== 'string') {
            // make sure we start with the JSON as a string
            json = JSON.stringify(json);
        } else {
            // is already a string, so parse and re-stringify in order to remove extra whitespace
            json = JSON.parse(json);
            json = JSON.stringify(json);
        }

        // add newline before and after curly braces
        reg = /([\{\}])/g;
        json = json.replace(reg, '\r\n$1\r\n');

        // add newline before and after square brackets
        reg = /([\[\]])/g;
        json = json.replace(reg, '\r\n$1\r\n');

        // add newline after comma
        reg = /(\,)/g;
        json = json.replace(reg, '$1\r\n');

        // remove multiple newlines
        reg = /(\r\n\r\n)/g;
        json = json.replace(reg, '\r\n');

        // remove newlines before commas
        reg = /\r\n\,/g;
        json = json.replace(reg, ',');

        // optional formatting...
        if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
            reg = /\:\r\n\{/g;
            json = json.replace(reg, ':{');
            reg = /\:\r\n\[/g;
            json = json.replace(reg, ':[');
        }
        if (options.spaceAfterColon) {
            reg = /\:/g;
            json = json.replace(reg, ': ');
        }

        $.each(json.split('\r\n'), function(index, node) {
            var i = 0,
                indent = 0,
                padding = '';

            if (node.match(/\{$/) || node.match(/\[$/)) {
                indent = 1;
            } else if (node.match(/\}/) || node.match(/\]/)) {
                if (pad !== 0) {
                    pad -= 1;
                }
            } else {
                indent = 0;
            }

            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }

            formatted += padding + node + '\r\n';
            pad += indent;
        });
        return formatted.replaceAll('"', "'");
    };

});