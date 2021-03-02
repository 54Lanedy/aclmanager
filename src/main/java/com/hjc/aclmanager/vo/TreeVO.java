package com.hjc.aclmanager.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by liyue
 * Time 2021/1/15 16:59
 */
@Data
public class TreeVO {
    private String id;
    //显示节点文本
    private String text;
    private String code;
    //图标
    private String iconCls;
    //节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
    private String state = "open";;
    //表示该节点是否被选中。true false
    private boolean checked = false;
    //被添加到节点的自定义属性。
    private Map<String, Object> attributes= Maps.newHashMap();
    //子节点
    private List<TreeVO> children= Lists.newArrayList();

    private String parentId;
}
