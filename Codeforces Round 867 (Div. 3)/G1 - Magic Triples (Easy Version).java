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
   ai⋅b=aj and aj⋅b=ak can be written as aj = ai.b & ak=ai.b.b
   Find cross product of all ai, ai.b, ai.b.b pairs
   when b = 1, we need to make sure we do not include an index 'i' more than once in triple
   max number 10^6, can keep b as 10^3, brute force for all b in [1,1000]
 
   TC: O(n*sqrt(maxVal))
   SC: O(n)
  */
 
  static void solve(int tc) throws Exception{
    int n = i();
    int[] a = readArrayi(n);
    HashMap<Integer, Long> map = new HashMap();
    int maxVal = 0;
 
    for(int num: a){ 
      maxVal = Math.max(maxVal, num);
      map.put(num, map.getOrDefault(num, 0l)+1l);
    }
 
    long triples = 0;
    for(int num1: map.keySet()){
      long freq = map.get(num1);
 
      for(int b=2; num1*b*b<=maxVal; b++){
          int num2 = num1*b;
          int num3 = num2*b;
 
          triples+=freq*map.getOrDefault(num2, 0l)*map.getOrDefault(num3, 0l);
      }
 
      triples+=freq*(freq-1)*(freq-2);
    }
 
    sb.append(triples).append("\n");
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