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
   Ideas: Take permutation as : [n, n-1, a1, a2, a3, a4...] where a1+a2 = n-1, a3+a4 = n-1
   We cannot form such a permutation if n is odd (except when n = 1)

   TC: O(n)
   */
 
  static void solve(int tc) throws Exception{
    int n = i();
    
    if(n==1) sb.append(1).append("\n");
    else if(n%2!=0) sb.append(-1).append("\n");
    else{
      sb.append(n).append(" ").append(n-1).append(" ");
      for(int num=2; num<n; num+=2){
        sb.append(num).append(" ").append(n-num-1).append(" ");
      }
 
      sb.append("\n");
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