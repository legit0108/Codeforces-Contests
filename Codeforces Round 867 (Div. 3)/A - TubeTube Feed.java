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
  
  // TC: O(n)
  
  static void solve(int tc) throws Exception{
    int n = i();
    int t = i();
    int[] a = readArrayi(n);
    int[] b = readArrayi(n);

    int vid = -1;

    for(int i =0; i<n; i++){
      if(i+a[i]<=t){ // can view video if it fits time constraint (skipping to video + watching it)
        if(vid==-1) vid = i;
        else if(b[i]>b[vid]) vid = i; // chose one with maximum entertainment value
      }
    }

    if(vid==-1) sb.append(-1).append("\n");
    else sb.append(vid+1).append("\n");
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