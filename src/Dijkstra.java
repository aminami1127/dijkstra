import java.util.HashMap;

public class Dijkstra{

	private final static int COST = 1;
	private final static int startNodeIndex = 0;
	private final static int goalNodeIndex = 4;
	private static Node nodes[] = new Node[5];
	@SuppressWarnings("serial")
	private static HashMap<Integer, String> map = new HashMap<Integer, String>(){
		{
			put(0,"A");
			put(1,"B");
			put(2,"C");
			put(3,"D");
			put(4,"E");
		}
	};
	
	public static void main(String argv[]){
		
		//ノードの生成
		for(int i = 0; i < nodes.length; i++){
			nodes[i] = new Node();
		}
		
		//スタートノードのコストを0に設定し、確定フラグを立てる
		nodes[0].setNodeIndex(0);
		nodes[0].setCost(0);
		nodes[0].setDoneFlg(true);
		
		//問いのノードを設定
		nodes[1].setNodeIndex(1);
		nodes[2].setNodeIndex(2);
		nodes[3].setNodeIndex(3);
		nodes[4].setNodeIndex(4);		
		nodes[0].setEdges_to(new int[] {1,2});
		nodes[1].setEdges_to(new int[] {0,3});
		nodes[2].setEdges_to(new int[] {0,3});
		nodes[3].setEdges_to(new int[] {1,2});
		nodes[4].setEdges_to(new int[] {2,3});
		
		//エッジのコストは全て1
		for(Node node : nodes){
			node.setEdges_cost(new int[]{COST, COST});
		}
		
		//全てのノードが確定済みになるまでループ
		Node scanNode = nodes[0]; //隣接点を探索するノード(scanNodeと呼ぶことにする)
		while(true){
			//scanNodeに隣接するノードをについてコストを更新
			updateCost(scanNode);
			//未確定のノードのうち、コストが最小のノードに確定フラグを立てる
			//確定フラグを立てたノードを次のscanNodeとする。
			scanNode = findMinCostNode();
			//全てのノードの確定フラグが立ったらループを抜ける
			if(scanNode == null) break;
		}
		//ゴールのノードから経路をたどる
		StringBuilder result = new StringBuilder();
		Node foo = nodes[goalNodeIndex];
		System.out.println("Distance : " + foo.getCost());
		while(foo != nodes[startNodeIndex]){
			result.append(map.get(foo.getNodeIndex()) + " → ");
			foo = nodes[foo.getPrevNode()];
		}
		result.append(map.get(nodes[startNodeIndex].getNodeIndex()));
		//逆順にして出力
		System.out.println(result.reverse());
	}
	
	//渡されたノードに隣接するノードのコストを更新する
	private static void updateCost(Node scanNode){
		
		for(Node node : nodes){
			//確定済みノードはスキップ
			if(node.isDone()) continue;	
			//scanNodeが結合しているノードを選択
			for(int i = 0; i < node.getEdges_to().length; i++){
				if(node.getEdges_to()[i] == scanNode.getNodeIndex()){
					//未確定ノードのコストが"scanNodeのコスト + エッジ"のコストよりも大きい場合に更新
					if(node.getCost() > scanNode.getCost() + COST){
						node.setCost(scanNode.getCost() + COST);
						//経路を記憶しておく
						node.setPrevNodeIndex(scanNode.getNodeIndex());
					}
				}
			}
		}
	}
	
	//未確定のノードのうち、コストが最小のものにフラグを立て、そのノードを返す
	private static Node findMinCostNode(){
		
		int minCostNodeIndex = 0;
		int minCost = Integer.MAX_VALUE;
		for(Node node : nodes){
			if(node.isDone()) continue;
			if(node.getCost() < minCost){
				minCost = node.getCost();
				minCostNodeIndex = node.getNodeIndex();
			}
		}
		//最小コストのノードを確定済みにする
		//全てのノードが確定済みの場合はnullを返す
		if(minCostNodeIndex == 0) return null;
		nodes[minCostNodeIndex].setDoneFlg(true);
		return nodes[minCostNodeIndex];
	}
}

class Node {
	
	private int nodeIndex;
	private int[] edges_to; //結合先ノード番号
	private int[] edges_cost; //エッジのコスト
	private boolean doneFlg = false; //確定済みフラグ
	private int cost = Integer.MAX_VALUE; //このノードへのスタートからの最小コス(初期値は擬似∞)
	private int prevNodeIndex = -1; //最短経路におけるこのノードの手前のノード番号
	
	public int getNodeIndex() {
		return nodeIndex;
	}
	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	public boolean isDone() {
		return doneFlg;
	}
	public void setDoneFlg(boolean doneFlg) {
		this.doneFlg = doneFlg;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int[] getEdges_to() {
		return edges_to;
	}
	public void setEdges_to(int[] edges_to) {
		this.edges_to = edges_to;
	}
	public int[] getEdges_cost() {
		return edges_cost;
	}
	public void setEdges_cost(int[] edges_cost) {
		this.edges_cost = edges_cost;
	}
	public int getPrevNode() {
		return prevNodeIndex;
	}
	public void setPrevNodeIndex(int prevNodeIndex) {
		this.prevNodeIndex = prevNodeIndex;
	}
}	