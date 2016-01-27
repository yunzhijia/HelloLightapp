package com.kingdee.lightapp.authority;

/**
 * 
 * 说明：授权类型
 * 
 * @author 
 * 
 */
public enum AuthorityType {

	LAPP("轻应用", 0);// ticket

	private String name;
	private int index;

	private AuthorityType(String name, int index) {
		this.name = name;
		this.index = index;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
