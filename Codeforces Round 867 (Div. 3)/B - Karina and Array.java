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
  
  // Maximal product is obtained from product of two largest numbers or two smallest numbers (for negative numbers)
  
  // TC: O(n) 
  
  static void solve(int tc) throws Exception{
    int n = i();
    long[] arr = readArray((long)n);
    long max = Long.MIN_VALUE;
    long secondMax = Long.MIN_VALUE;
    long min = Long.MAX_VALUE;
    long secondMin = Long.MAX_VALUE;

    for(long val: arr){
      if(val>=max){
        secondMax = max;
        max = val;
      }else if(val>=secondMax) secondMax = val;

      if(val<=min){
        secondMin = min;
        min = val;
      }else if(val<=secondMin) secondMin = val;
    }

    long ans = Math.max(max*secondMax, min*secondMin);
    sb.append(ans).append("\n");
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