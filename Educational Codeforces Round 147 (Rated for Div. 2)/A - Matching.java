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
  
  static void solve(int tc) throws Exception{
    String str = s();

    if(str.charAt(0)=='0') sb.append(0).append("\n"); // Leading zeros are not allowed
    else{
       long count = 1;

       for(int i =0; i<str.length(); i++){
         if(str.charAt(i)!='?') continue;
         else{
            if(i==0) count = count*9; // place any number from 1-9, we cannot place 0 at the first index -> leading zeros are not allowed
            else count = count*10; // place any number from 0-9
         }
       }
 
       sb.append(count).append("\n");
    }
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