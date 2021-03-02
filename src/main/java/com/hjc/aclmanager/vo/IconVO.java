package com.hjc.aclmanager.vo;

public class IconVO {
	
	private String id;
	
	private String code;// 图标名称
	
	private String iconUrl;
	
	private String iconUrl2;
	
	private String ext;
	
	private int sort;
	
	private String describe;//描述
	
	public IconVO(String code, String ext, String describe) {
		super();
		this.code = code;
		this.ext = ext;
		this.describe = describe;
	}
	
	public IconVO(String code, String iconUrl) {
		super();
		this.code = code;
		this.iconUrl = iconUrl;
	}
	
	public String getExt() {
		return ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public String getIconUrl2() {
		return iconUrl2;
	}
	
	public void setIconUrl2(String iconUrl2) {
		this.iconUrl2 = iconUrl2;
	}
	
	public IconVO(String id, String code, String iconUrl, int sort,
                  String describe) {
		super();
		this.id = id;
		this.code = code;
		this.iconUrl = iconUrl;
		this.sort = sort;
		this.describe = describe;
	}
	
	public IconVO(String id, String code, int sort, String describe, String ext) {
		super();
		this.id = id;
		this.code = code;
		this.sort = sort;
		this.describe = describe;
		this.ext = ext;
	}
	
	public String getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public int getSort() {
		return sort;
	}
	
	public String getDescribe() {
		return describe;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public IconVO() {
	}
	
}
