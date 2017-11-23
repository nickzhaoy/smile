package cn.itcast.erp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构
 * @author syl
 *
 */
public class Tree {

	private String id;  //id
	
	private String text; //树形节点的名称
	
	private boolean checked; //是否选中
	
	private List<Tree> children;  //子节点

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<Tree> getChildren() {
		if(children==null){
			children = new ArrayList<Tree>();
		}
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
}
