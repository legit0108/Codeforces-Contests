import java.util.*;
 
 
import java.io.*;
 
public class Main {
  static StringBuilder sb;
  static dsu dsu;
  static long fact[];
  static long mod = (long)1e9+7;
  static BufferedReader br;
  static HashMap<Long, Long> map;
 
  static ArrayList<Integer>[] factors;
  static int MAX_M = (int)1e5;
  static int minOps = Integer.MAX_VALUE;
  
  /*
   Ideas: 
   TLDR: Find farthest distance of a node from a node for all nodes-> https://www.geeksforgeeks.org/farthest-distance-of-a-node-from-each-node-of-a-tree/
   Finding the farthest node from a node for all nodes:
   1) Modify this solution
   2) https://www.geeksforgeeks.org/find-farthest-node-from-each-node-in-tree/
   
   Approach: 
   First, we have to find two end vertices of the diameter and to find that, 
   we will choose an arbitrary vertex and find the farthest node from this arbitrary vertex and this node will be one end of the diameter and then make it root to find farthest node from it, 
   which will be the other end of diameter. 
   Now for each node, the farthest node will be one of these two end vertices of the diameter of the tree.
 
   Why it works? 
   Let x and y are the two end vertices of the diameter of the tree and a random vertex is u. 
   Let the farthest vertex from u is v, not x or y. 
   As v is the farthest from u then a new diameter will form having end vertices as x, v or y, v which has greater length but a tree has a unique length of the diameter, 
   so it is not possible and the farthest vertex from u must be x or y.
 
   TC: O(n)
   SC: O(n)
  */
 
  static void solve(int tc) throws Exception{
    int n = i();
    long k = l();
    long c = l();
 
    ArrayList<Integer>[] tree = new ArrayList[n+1];
    for(int node=0; node<=n; node++) tree[node] = new ArrayList();
 
    for(int i=1; i<n; i++){
      int u = i();
      int v = i();
 
      tree[u].add(v);
      tree[v].add(u);
    }
 
    int[] depth = getDepth(tree, n);
    int[] farthestDist = getFarthestDist(tree, n);
 
    long maxProfit = 0;
    for(int node=0; node<=n; node++){
      maxProfit = Math.max(maxProfit, (k*(long)farthestDist[node]) - (c*depth[node]));
    }
 
    sb.append(maxProfit).append("\n");
  }
 
  private static int[] getDepth(ArrayList<Integer>[] tree, int n){
    int[] depth = new int[n+1];
    dfs(tree, 1, -1, 0, depth);
    return depth;
  }
 
  private static void dfs(ArrayList<Integer>[] tree, int node, int par, int currDepth, int[] depth){
    ArrayList<Integer> neighbours = tree[node];
 
    for(int neighbour: neighbours){
      if(neighbour!=par){
        depth[neighbour] = depth[node]+1;
 
        dfs(tree, neighbour, node, currDepth+1, depth);
      }
    }
  }
 
  private static int[] getFarthestDist(ArrayList<Integer>[] tree, int n){
    int[] dist = new int[n+1];
    dfs1(tree, dist, 1, -1);
 
    int[] farthestDist = new int[n+1];
    dfs2(tree, farthestDist, dist, 1, -1);
 
    return farthestDist;
  }
 
  private static void dfs1(ArrayList<Integer>[] tree, int[] dist, int node, int par){
    ArrayList<Integer> neighbours = tree[node];
 
    for(int neighbour: neighbours){
      if(neighbour!=par){
          dfs1(tree, dist, neighbour, node);
          dist[node] = Math.max(dist[node], dist[neighbour]+1);
      }
    }
  }
 
  private static void dfs2(ArrayList<Integer>[] tree, int[] farthestDist, int[] dist, int node, int par){
    ArrayList<Integer> neighbours = tree[node];
    int maxDist = -(int)1e9;
    int secondMaxDist = -(int)1e9;
 
    for(int neighbour: neighbours){
      if(neighbour!=par){
          int currDist = dist[neighbour];
 
          if(currDist>=maxDist){
             secondMaxDist = maxDist;
             maxDist = currDist;
          }else if(currDist>secondMaxDist) secondMaxDist = currDist;
      }
    }
 
    for(int neighbour: neighbours){
      if(neighbour!=par){
          farthestDist[neighbour] = 1+farthestDist[node]; // -> (1)
          int currDist = dist[neighbour];
 
          if(currDist==maxDist){
             farthestDist[neighbour] = Math.max(farthestDist[neighbour], secondMaxDist+2);
          }else farthestDist[neighbour] = Math.max(farthestDist[neighbour], maxDist+2);
 
          dfs2(tree, farthestDist, dist, neighbour, node);
      }
    }
 
    farthestDist[node] = Math.max(farthestDist[node], dist[node]); // note that this needs to be done in postorder because of (1) [we need to MAKE SURE that farthestDist[node] does not include current neighbour]
  }
  public static void main(String[] args) throws Exception{
    sb = new StringBuilder();
    br = new BufferedReader(new InputStreamReader(System.in));
    // factors = new ArrayList[(int)MAX_M];
    // getFactors();
 
    fact=new long[(int)1e6+10]; fact[0]=fact[1]=1; for(int i=2;i<fact.length;i++)
    { fact[i]=((long)(i%mod)*(long)(fact[i-1]%mod))%mod; }
    
    int t = i();
    int i = 1;
    while (t-- > 0) {
      solve(i++);
    }
    
    out.printLine(sb);
    out.close();
  }
}