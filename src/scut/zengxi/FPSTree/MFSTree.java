package scut.zengxi.FPSTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MFSTree {
	// 最小支持度(%)
	private float minSuport=0;

	public float getMinSuport() {
		return minSuport;
	}

	public void setMinSuport(float minSuport) {
		if(minSuport>=100.0f)
			this.minSuport=100.0f;
		else if(minSuport<=0.0f)
			this.minSuport=0.0f;
		else
			this.minSuport = minSuport;
	}
	/**
	 * 构建项头表，同时也是频繁1项集
	 * 
	 * @param transRecords
	 *            会话集，每个会话是一个页面访问序列
	 * @return   升序方式返回1阶频繁项集
	 */
	public ArrayList<TreeNode> buildHeaderTable(List<List<String>> transRecords) {
		ArrayList<TreeNode> F1 = null;    //一阶频繁项集
		if (transRecords.size() > 0) {
			F1 = new ArrayList<TreeNode>();
			// 用Map统计每个页面出现的频数
			Map<String, TreeNode> map = new HashMap<String, TreeNode>();
			// 计算事务数据库中各项的支持度
			for (List<String> record : transRecords) {
				for (String item : record) {
					if (!map.keySet().contains(item)) {
						TreeNode node = new TreeNode(item);
						node.setCount(1);
						map.put(item, node);
					} else {
						map.get(item).countIncrement(1);
					}
				}
			}
			// 把支持度大于（或等于）minSup的项加入到F1中
			Set<String> names = map.keySet();
			for (String name : names) {
				TreeNode tnode = map.get(name);
				if (tnode.getCount() >= getMinSuport()*transRecords.size()/100.0f) {
					F1.add(tnode);
				}
			}
			// 按升序排序
			Collections.sort(F1);
			// 按升序方式输出1阶频繁页面
			System.out.println("===========1阶频繁页面===========");
			for (int i = 0; i < F1.size(); i++) {
				System.out.println(F1.get(i).getName() + ":"
						+ F1.get(i).getCount());
			}
			return F1;
		} else {
			return null;
		}
	}

	/**
	 * 根据会话集构建1阶频繁序列并构建树
	 * 
	 * @param transRecords
	 *            会话集，每个会话是一个页面访问序列
	 */
	/*public void MFSGrowth(List<List<String>> transRecords) {
		// 构建项头表，同时也是频繁1项集
		ArrayList<TreeNode> HeaderTable = buildHeaderTable(transRecords);
		// 构建FP-Tree
		TreeNode treeRoot = buildFSTree(transRecords, HeaderTable);
		// 如果FP-Tree为空则返回
		if (treeRoot.getChildren() == null
				|| treeRoot.getChildren().size() == 0)
			return;
		// 遍历FSTree,验证构造的树是否正确：只做验证用
		// travelFSTree(treeRoot);
		// 遍历某个1阶频繁项的同项链是否构建正确：只做验证用
		//travelByHom(HeaderTable.get(6));
	}*/

	/**
	 * 以递归的方式遍历FSTree,用于验证FSTree是否构建正确
	 * @param treeRoot   FSTree的根节点
	 */
	public void travelFSTree(TreeNode treeRoot) {
		if (treeRoot == null)
			return;
		else {
			System.out.print(treeRoot.getName() + ":" + treeRoot.getCount()+ " ");
			List<TreeNode> children = treeRoot.getChildren();
			if (children != null) {
				for (TreeNode child : children) {
					travelFSTree(child);
				}
			}
		}
	}

	/**
	 * 构建FSTree
	 * @param transRecords 会话集，每个会话是一个页面访问序列
	 * @param F1  1阶频繁项集，用于构造逆序树的链表
	 * @return TreeNode的根节点
	 */
	public TreeNode buildFSTree(List<List<String>> transRecords,
			ArrayList<TreeNode> F1) {
		TreeNode root = new TreeNode("root"); // 创建树的根节点
		root.setCount(1);
		for (List<String> transRecord : transRecords) {
			//将List转化为LinkedList
			LinkedList<String> record = changeType(transRecord);
			//添加节点
			addNodes(root, record, F1);
		}
		return root;
	}

	/**
	 * 将List转化为LinkedList
	 * 
	 * @param transRecords
	 * @return LinkedList类型的序列
	 */
	public LinkedList<String> changeType(List<String> transRecords) {
		LinkedList<String> rest = new LinkedList<String>();
		for (String transRecord : transRecords) {
			rest.add(transRecord);
		}
		return rest;
	}

	/**
	 * 以递归方式添加节点
	 * @param ancestor   父节点
	 * @param record     一个会话：页面的访问序列
	 * @param F1         1阶频繁序列
	 */
	public void addNodes(TreeNode ancestor, LinkedList<String> record,
			ArrayList<TreeNode> F1) {
		if (record.size() > 0) {
			while (record.size() > 0) {
				String item = record.poll();
				if (ancestor.findChild(item) != null) {
					// System.out.println("++++");
					ancestor.findChild(item).countIncrement(1);
					//递归添加节点
					addNodes(ancestor.findChild(item), record, F1);
				}
				//两种添加节点的方式，如果以tired树(字典树)的思想添加节点，则把下面的注释，如果以另一种方式构建树则可以取消注释，另一种思想有可以参考
				//《基于Web点击流的频繁访问序列挖掘研究》张啸剑——第二章，不过有缺陷，例子中的P1，P2，P5不是极大频繁序列
				/*
				 * else if(ancestor.findAncestor(item)!=null){
				 * //System.out.println("===****===");
				 * ancestor.findAncestor(item).countIncrement(1);
				 * addNodes(ancestor.findAncestor(item), record, F1); }
				 */
				else {
					TreeNode leafnode = new TreeNode(item);
					leafnode.setCount(1);
					leafnode.setParent(ancestor);
					ancestor.addChild(leafnode);
					//以链表方式将相同页面的节点连起来
					for (TreeNode f1 : F1) {
						if (f1.getName().equals(item)) {
							while (f1.getNextHomonym() != null) {
								f1 = f1.getNextHomonym();
							}
							f1.setNextHomonym(leafnode);
							break;
						}
					}
					//递归添加节点
					addNodes(leafnode, record, F1);
				}
			}

		}
	}
	/**
	 * 打印某个节点的条件模式基，即祖先节点，只做验证用
	 * @param treeNode
	 */
	public void printParent(TreeNode treeNode) {
		if (treeNode == null)
			return;
		while (treeNode.getParent() != null) {
			System.out.println(treeNode.getParent().getName());
			treeNode = treeNode.getParent();
		}

	}

	/**
	 * 遍历某个1阶频繁项的同项链是否构建正确：只做验证用
	 * @param treeNode
	 */
	public static void travelByHom(TreeNode treeNode) {
		while (treeNode != null) {
			System.out.print(treeNode.getName() + ":" + treeNode.getCount()
					+ " ");
			treeNode = treeNode.getNextHomonym();
		}
		System.out.println();
	}


	/**
	 * 从若干个文件中读入Transaction Record
	 * @param filenames
	 * @return 会话序列集
	 */
	public List<List<String>> readTransRocords(String... filenames) {
		List<List<String>> transaction = null;
		if (filenames.length > 0) {
			transaction = new LinkedList<List<String>>();
			for (String filename : filenames) {
				try {
					FileReader fr = new FileReader(filename);
					BufferedReader br = new BufferedReader(fr);
					try {
						String line;
						List<String> record;
						while ((line = br.readLine()) != null) {
							if (line.trim().length() > 0) {
								String str[] = line.split(",");
								record = new LinkedList<String>();
								for (String w : str)
									record.add(w);
								transaction.add(record);
							}
						}
					} finally {
						br.close();
					}
				} catch (IOException ex) {
					System.out.println("Read transaction records failed."
							+ ex.getMessage());
					System.exit(1);
				}
			}
		}
		return transaction;
	}

/*	public static void main(String[] args) {
		FSTree fstree = new MFSTree();
		fstree.setMinSuport(30.0f);
		List<List<String>> transRecords = fstree
				.readTransRocords("E:\\新建文件夹\\sequences.txt");
		fstree.MFSGrowth(transRecords);
	}*/

}
