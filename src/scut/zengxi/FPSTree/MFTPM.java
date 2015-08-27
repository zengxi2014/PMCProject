package scut.zengxi.FPSTree;

import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;
import net.sf.json.JSONArray;
import org.junit.Test;

import java.util.*;

/**
 * 生成极大频繁序列的类
 * @author zengxi
 *
 */
public class MFTPM {
	private static ArrayList<TreeNode> HeaderTable;      //频繁项表头
	private static MFSTree fstree;      //频繁序列树
	private static List<List<String>> mftss;    //极大频繁序列集
	private static List<List<String>> transRecords= new LinkedList<List<String>>();

	/**
	 * 初始化函数
	 */
	public static void init() {
		fstree = new MFSTree();
		fstree.setMinSuport(10.0f);   //设置最小支持度
/*		List<List<String>> transRecords = fstree
				.readTransRocords("E:\\新建文件夹\\sequence.txt");*/
		ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();
		transRecords = activityRouteInfoBasicService.getRoutes("85d4a553-ee8d-4136-80ab-2469adcae44d");
		for(int i=0;i<transRecords.size();i++){
			System.out.println(transRecords.get(i));
		}
		System.out.println("data set size:"+transRecords.size());
		mftss = new LinkedList<List<String>>();
		HeaderTable = fstree.buildHeaderTable(transRecords);     //生成1阶频繁项
		TreeNode treeRoot = fstree.buildFSTree(transRecords, HeaderTable);    //构建频繁序列树
	}
	/**
	 * 将list<TreeNode>反转
	 * @param prePage   条件模式基
	 * @return  反转后的序列树
	 */
	public static List<TreeNode> invList(List<TreeNode> prePage) {
		List<TreeNode> res = null;
		if (prePage.size() > 0)
			res = new LinkedList<TreeNode>();
		for (int i = prePage.size() - 1; i >= 0; i--) {
			res.add(prePage.get(i));
		}
		return res;
	}
	/**
	 * 将list<String>反转
	 * @param prePage
	 * @return 反转后的序列
	 */
	public static List<String> invStrList(List<String> prePage) {
		List<String> res = null;
		if (prePage.size() > 0)
			res = new LinkedList<String>();
		for (int i = prePage.size() - 1; i >= 00; i--) {
			res.add(prePage.get(i));
		}
		return res;
	}


	/**
	 * 将path中所有项的“：数量”删去
	 * @param path  路径集
	 * @return  处理后的路径集
	 */
	public static List<String> trimSep(List<String> path){
		List<String> res= new LinkedList<String>();
		for (int i = 0; i < path.size(); i++) {
			if(path.get(i).indexOf("=")!=-1)
				res.add(path.get(i));
			else if(path.get(i).indexOf(":")!=-1)
				res.add(path.get(i).substring(0,path.get(i).indexOf(":")));
		}
		return res;
	}
	/**
	 * 找到1阶频繁序列集的所有频繁项的所有条件模式基，并将极大频繁序列添加到mftss中
	 * @param headerTable 1阶频繁序列集
	 */
	public static void findPrePages(ArrayList<TreeNode> headerTable) {
		 List<List<TreeNode>> prePageSet = null;
		for (int i = 0; i < headerTable.size(); i++) {
			TreeNode cur = headerTable.get(i).getNextHomonym();
		    if(cur!=null)prePageSet = new LinkedList<List<TreeNode>>();
			while (cur != null) {
				int counter = cur.getCount();
					List<TreeNode> prePage = new LinkedList<TreeNode>();
					TreeNode parent = cur;
					prePage.add(parent);
					while ((parent = parent.getParent()) != null
							&& !(parent.getName().equals("root"))) {
						prePage.add(parent);
					}
					//按counter的数量作为添加次数
					while(counter-->0){
						prePageSet.add(prePage);
					}
				cur = cur.getNextHomonym();
			}
			List<List<String>> invPath= InvTree.buildInvTree(prePageSet,fstree.getMinSuport()*transRecords.size()/100.0f);
			for (int j = 0; j < invPath.size(); j++) {
						//invStrList(trimSep(invPath.get(j))), mftss)) {
					//把支持度作为路径的一项添加进来，支持度的逆序路径的最后一个节点的count值
					invPath.get(j).add("sup="+invStrList(invPath.get(j)).get(0).substring(invStrList(invPath.get(j)).get(0).indexOf(":")+1)+" ");
					mftss.add(invStrList(trimSep(invPath.get(j))));
			}
		}
		//删除长度小于2的极大频繁序列
		removeLessThan(mftss);
		//把重复的子频繁序列删除
		removeDump(mftss);
	}

	/**
	 *这里可以设置极大频繁序列的最小长度，默认为2
	 * @param mftss2
	 */
	private static void removeLessThan(List<List<String>> mftss2){
		//删除长度小于最小长度（默认为2）的极大频繁序列
		for (int i = 0; i < mftss2.size(); i++) {
			if(mftss2.get(i).size()<=2){
				mftss2.remove(mftss2.get(i));
				i--;
			}
		}
	}
	
	/**
	 * 删除极大频繁序列集中的非极大频繁序列以及只包含一个页面的频繁序列

	 * @param mftss2   极大频繁序列集
	 */
    private static void removeDump(List<List<String>> mftss2) {

		//删除非极大的频繁序列
		for (int i = 0; i < mftss2.size(); i++) {
			String tmp = StringUtil.list2Str(mftss2.get(i)).substring(StringUtil.list2Str(mftss2.get(i)).indexOf(" ")+1);
			//System.out.println(tmp);
			for (int j = 0; j < mftss2.size(); j++) {
				if(j==i)continue;
				if(StringUtil.KMP_Index(StringUtil.list2Str(mftss2.get(j)),tmp)){
					mftss2.remove(mftss2.get(i));
					i--;
					break;
				}
			}
		}
	}
    /**
     * 以极大频繁序列的长度降序排序
     * @param lists  需要排序的list
     * @return   排序后的list
     */
    public static List<List<String>> sortByLengthDesc(List<List<String>> lists){
    	List<String> temp=new LinkedList<String>();
    	for (int i = 0; i < lists.size(); i++) {
			lists.get(i).add(""+lists.get(i).size());
		}
    	for (int i = 0; i < lists.size(); i++) {
    		for (int j = i; j < lists.size(); j++) {
    			if(Integer.parseInt(lists.get(i).get(lists.get(i).size()-1))<Integer.parseInt(lists.get(j).get(lists.get(j).size()-1))){
    				temp=lists.get(i);
    				lists.set(i, lists.get(j));
    				lists.set(j,temp);
    			}
    		}
		}
    	for(int i = 0; i < lists.size(); i++) {
    		lists.get(i).remove(lists.get(i).size()-1);
    	}
    	return lists;
    }
    
    /**
     * 以极大频繁序列的长度升序排序
     * @param lists  需要排序的list
     * @return   排序后的list
     */
    public static List<List<String>> sortByLengthAsc(List<List<String>> lists){
    	List<String> temp=new LinkedList<String>();
    	for (int i = 0; i < lists.size(); i++) {
			lists.get(i).add(""+lists.get(i).size());
		}
    	for (int i = 0; i < lists.size(); i++) {
    		for (int j = i; j < lists.size(); j++) {
    			if(Integer.parseInt(lists.get(i).get(lists.get(i).size()-1))>Integer.parseInt(lists.get(j).get(lists.get(j).size()-1))){
    				temp=lists.get(i);
    				lists.set(i, lists.get(j));
    				lists.set(j,temp);
    			}
    		}
		}
    	for(int i = 0; i < lists.size(); i++) {
    		lists.get(i).remove(lists.get(i).size()-1);
    	}
    	return lists;
    }
    
    /**
     * 以极大频繁序列的长度升序排序
     * @param lists  需要排序的list
     * @return   排序后的list
     */
    public static List<List<String>> sortBySupAsc(List<List<String>> lists){
    	List<String> temp=new LinkedList<String>();
    	for (int i = 0; i < lists.size(); i++) {
    		for (int j = i; j < lists.size(); j++) {
    			int beginIndex=lists.get(i).get(0).indexOf("=")+1;
    			int endIndex = lists.get(i).get(0).indexOf(" ");
    			//System.out.println(beginIndex+"   "+lists.get(i).get(0).substring(beginIndex));
    			if(Integer.parseInt(lists.get(i).get(0).substring(beginIndex,endIndex))>Integer.parseInt(lists.get(j).get(0).substring(beginIndex,endIndex))){
    				temp=lists.get(i);
    				lists.set(i, lists.get(j));
    				lists.set(j,temp);
    			}
    		}
		}
    	return lists;
    }
    
    /**
     * 以极大频繁序列的长度升序排序
     * @param lists  需要排序的list
     * @return   排序后的list
     */
    public static List<List<String>> sortBySupDesc(List<List<String>> lists){
    	List<String> temp=new LinkedList<String>();
    	for (int i = 0; i < lists.size(); i++) {
    		for (int j = i; j < lists.size(); j++) {
    			int beginIndex=lists.get(i).get(0).indexOf("=")+1;
    			int endIndex = lists.get(i).get(0).indexOf(" ");
    			//System.out.println(beginIndex+"   "+lists.get(i).get(0).substring(beginIndex));
    			if(Integer.parseInt(lists.get(i).get(0).substring(beginIndex,endIndex).trim())<Integer.parseInt(lists.get(j).get(0).substring(beginIndex,endIndex).trim())){
    				temp=lists.get(i);
    				lists.set(i, lists.get(j));
    				lists.set(j,temp);
    			}
    		}
		}
    	return lists;
    }

	/**
	 * 获取极大频繁序列挖掘结果的接口函数
	 * @return
	 */
    public static JSONArray getMFSResults(){
		List<FreqSeqBean> freqSequences= new LinkedList<FreqSeqBean>();
		init();
		try{
			findPrePages(HeaderTable);
		}catch (NullPointerException e){
			System.out.println("目前的数据集大小比您所设置的支持度阈值小，请添加数据集或设置更小的支持度阈值!");
		}
		System.out.println("===================极大频繁访问序列====================");
		mftss=sortBySupDesc(mftss);
		for (int i = 0; i < mftss.size(); i++) {
			FreqSeqBean freqSeq= new FreqSeqBean();
			if(mftss.get(i).size()>0){
				freqSeq.setId(i+1);
				freqSeq.setFrequency(Integer.parseInt(mftss.get(i).get(0).substring(4, mftss.get(i).get(0).length() - 1)));
				String sequence = "";
				for (int j = 1; j < mftss.get(i).size()-1; j++) {
					sequence+=mftss.get(i).get(j) + "→";
				}
				sequence+=mftss.get(i).get(mftss.get(i).size()-1);
				freqSeq.setFreq_seq(sequence);
				//System.out.println(sups.get(i));
			}
			freqSequences.add(freqSeq);
		}
		JSONArray json = JSONArray.fromObject(freqSequences);
		System.out.println(json.toString());
		return json;
	}

	@Test
	public void testGetMFSResults(){
		getMFSResults();
	}
}

