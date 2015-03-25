package com.gkd.sourceleveldebugger;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.peterdwarf.gui.DwarfTreeNode;
import com.peterswing.CommonLib;
import com.peterswing.FilterTreeNode;

public class ProjectTreeNode extends FilterTreeNode {
	File file;
	Vector<ProjectTreeNode> children = new Vector<ProjectTreeNode>();
	public MutableTreeNode parent;

	public ProjectTreeNode(File file) {
		this.file = file;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		int x = 0;
		for (ProjectTreeNode treeNode : children) {
			if (treeNode == node) {
				return x;
			}
			x++;
		}
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return children.size() == 0;
	}

	@Override
	public Enumeration children() {
		return children.elements();
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
	}

	@Override
	public void remove(int indfileex) {
	}

	@Override
	public void remove(MutableTreeNode node) {
	}

	@Override
	public void setUserObject(Object object) {

	}

	@Override
	public void removeFromParent() {

	}

	@Override
	public void setParent(MutableTreeNode newParent) {

	}

	public String toString() {
		if (file != null) {
			return file.getName();
		} else {
			return "";
		}
	}

	public Vector<ProjectTreeNode> getChildren() {
		return children;
	}

}
