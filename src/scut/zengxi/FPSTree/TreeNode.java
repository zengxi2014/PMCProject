package scut.zengxi.FPSTree;

import java.util.ArrayList;
import java.util.List;
  
public class TreeNode implements Comparable<TreeNode> {
  
    private String name; // 节点名称
    private int count; // 计数
    private TreeNode parent; // 父节点
    private List<TreeNode> children; // 子节点
    private TreeNode nextHomonym; // 下一个同名节点
  
    public TreeNode() {
  
    }
  
    public TreeNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
  
    public void setName(String name) {
        this.name = name;
    }
  
    public int getCount() {
        return count;
    }
  
    public void setCount(int count) {
        this.count = count;
    }
  
    public TreeNode getParent() {
        return parent;
    }
  
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
  
    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
    /**
     * 获取下一个同名节点
     * @return
     */
    public TreeNode getNextHomonym() {
        return nextHomonym;
    }

    public void setNextHomonym(TreeNode nextHomonym) {
        this.nextHomonym = nextHomonym;
    }
    /**
     * 添加子节点
     * @param child  子节点
     */
    public void addChild(TreeNode child) {
        if (this.getChildren() == null) {
            List<TreeNode> list = new ArrayList<TreeNode>();
            list.add(child);
            this.setChildren(list);
        } else {
            this.getChildren().add(child);
        }
    }
    /**
     * 查找以name为value的子节点
     * @param name  查找条件
     * @return  如果找到则返回treenode找不到则返回null
     */
    public TreeNode findChild(String name) {
        List<TreeNode> children = this.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                if (child.getName().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }
    /**
     * 查找以name为value的祖先节点
     * @param name  查找条件
     * @return  如果找到则返回treenode找不到则返回null
     */
    public TreeNode findAncestor(String name){
    	TreeNode cur = this;
    	if(cur.getName()==null||cur.getName().equals(""))
    		return null;
    	if(cur.getName().equals(name))
    		return cur;
    	while(cur.getParent()!=null){
    		cur=cur.getParent();
    		if(cur.getName().equals(name))
        		return cur;
    	}
    	return null;
    }

    /**
     * 输出当前节点的所有子节点
     */
    public void printChildrenName() {
        List<TreeNode> children = this.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                System.out.print(child.getName() + " ");
            }
        } else {
            System.out.print("null");
        }
    }

    /**
     * 计数增加函数
     * @param n
     */
    public void countIncrement(int n) {
        this.count += n;
    }

    @Override
    public int compareTo(TreeNode arg0) {
        // TODO Auto-generated method stub
        int count0 = arg0.getCount();
        // 默认的排序比较
        return  this.count-count0;
    }
}