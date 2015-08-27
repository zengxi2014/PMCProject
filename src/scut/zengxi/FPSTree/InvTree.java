package scut.zengxi.FPSTree;

import java.util.LinkedList;
import java.util.List;

/**
 * 构建逆序树
 * @author zengxi
 *
 */
public class InvTree {
	private static TreeNode root;    //逆序树的根节点
	private static List<String> stack;    //用于深度优先遍历逆序树的栈
	private static List<String> pathList;     //用于保存逆序树的根节点到所有满足条件的节点的路径
	/**
	 * 
	 * @param prePageSet   条件模式基集合（前序页面集）
	 * @param minSup       最小支持度
	 * @return  返回所有路径
	 */
	public static List<List<String>> buildInvTree(List<List<TreeNode>> prePageSet,float minSup) {
		root = new TreeNode("root");
		root.setCount(1);
		stack= new LinkedList<String>();
		pathList= new LinkedList<String>();
		for (List<TreeNode> transRecord : prePageSet) {
			//添加节点
            addNodes(root, treeList2strList(transRecord));
        }
		//构建逆序路径
		buildPath(stack,root,pathList,minSup);
		
		List<List<String>> paths= new LinkedList<List<String>>();
		//将数组转化为list并保存，并将root节点去掉
		for (int i = 0; i < pathList.size(); i++) {
			String[] path=pathList.get(i).split(" ");
			List<String> pathStr= new LinkedList<String>();
			for (int j = 0; j < path.length; j++) {
				if(!path[j].startsWith("root"))
				pathStr.add(path[j]);
			}
			paths.add(pathStr);
		}
		return paths;
	}
	/**
	 * 将TreeNode 类型的List转化为 String 类型的LinkedList
	 * @param list  TreeNode 类型的List
	 * @return String 类型的LinkedList
	 */
	public static LinkedList<String> treeList2strList(List<TreeNode> list) {
		LinkedList<String> res = null;
		if (list.size() > 0)
			res = new LinkedList<String>();
		for (int i = 0; i < list.size(); i++) {
			res.add(list.get(i).getName());
		}
		return res;
	}
	/**
	 * 以递归方式添加节点
	 * @param ancestor  父节点
	 * @param record    访问序列
	 */
    public static void addNodes(TreeNode ancestor, LinkedList<String> record) {
        if (record.size() > 0) {
            while (record.size() > 0) {
                String item = record.poll();
                if (ancestor.findChild(item)!= null) {
                	ancestor.findChild(item).countIncrement(1);
                	//递归方式添加节点
                	addNodes(ancestor.findChild(item), record);
                }else{
                	TreeNode leafnode = new TreeNode(item);
                    leafnode.setCount(1);
                    leafnode.setParent(ancestor);
                    ancestor.addChild(leafnode);     
                    //递归方式添加节点
                    addNodes(leafnode, record);
                }
            }
        }
    }
    /**
	 * 该算法使用递归方式实现，采用深度优先遍历树的节点，同时记录下已经遍历的节点保存在栈中。
	 * 当遇到叶子节点时，输出此时栈中的所有元素，即为当前的一条路径；然后pop出当前叶子节点
	 * @paramstack 为深度优先遍历过程中存储节点的栈
	 * @paramroot 为树的要节点或子树的根节点
	 * @parampathList 为树中所有从根到叶子节点的路径的列表
	 */
	public static void buildPath(List<String> stack, TreeNode root, List<String> pathList,float sup) {

		if (root != null&&(root.getCount()>=sup||root.getName().equals("root"))) {
			stack.add(root.getName()+":"+root.getCount());
			//stack.add(root.getName());
			if (root.getChildren()==null) {
				changeToPath(stack, pathList); // 把值栈中的值转化为路径
			} else {
				List<TreeNode> items = root.getChildren();
				for (int i = 0; i < items.size(); i++) {
						buildPath(stack, items.get(i), pathList,sup);
				}
			}
			stack.remove(stack.size() - 1);
		}else if(!(root.getCount()>=sup||root.getName().equals("root"))){
			//if(root.getCount()<sup)pathList.add(root.getParent().getCount()+"==");
			changeToPath(stack, pathList);
		}
	}

	/**
	 * 将stack中的页面集转换为路径
	 * @param path
	 * @param pathList
	 */
	public static void changeToPath(List<String> path, List<String> pathList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i) != null) {
				sb.append(path.get(i) + " ");
			}

		}
		pathList.add(sb.toString().trim());
	}
}
