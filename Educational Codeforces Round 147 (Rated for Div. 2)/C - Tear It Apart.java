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
    For each character, we find maximum difference between consecutive appearances of that character
    The idea is that we can remove all other characters in log(max(diff)) iterations
    We minimize this value for all characters
 
    TC: O(n)
   */
  
  static void solve(int tc) throws Exception{
    String str = s();
    int n = str.length();
    HashMap<Character, ArrayList<Integer>> map = new HashMap();
    
    for(int i =0; i<n; i++){
      char ch = str.charAt(i);
      if(!map.containsKey(ch)) map.put(ch, new ArrayList());
      map.get(ch).add(i);
    }
 
    int minOps = Integer.MAX_VALUE;
 
    for(char ch: map.keySet()){
      ArrayList<Integer> list = map.get(ch);
      int size = list.size();
      int maxDiff = 0;
 
      for(int i =0; i<size; i++){
        int diff = -1;
 
        if(i==0) diff = list.get(i);
        else diff = list.get(i)-list.get(i-1)-1;
        
        maxDiff = Math.max(maxDiff, diff);
      }
 
      int diff = n-list.get(size-1)-1;
      maxDiff = Math.max(maxDiff, diff);
      
      int currOps = 0;
 
      while(maxDiff>0){
        maxDiff = maxDiff/2;
        currOps++;
      }
      
      minOps = Math.min(minOps, currOps); 
    }
 
    sb.append(minOps).append("\n");
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