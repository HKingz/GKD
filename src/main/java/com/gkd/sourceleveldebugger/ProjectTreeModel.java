package com.gkd.sourceleveldebugger;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ProjectTreeModel implements TreeModel {
	ProjectTreeNode root;

	public ProjectTreeModel(ProjectTreeNode root) {
		this.root = root;
	}

	public void setRoot(ProjectTreeNode root) {
		this.root = root;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((ProjectTreeNode) parent).getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((ProjectTreeNode) parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((ProjectTreeNode) node).isLeaf();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return ((ProjectTreeNode) parent).getIndex((TreeNode) child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {

	}

}
