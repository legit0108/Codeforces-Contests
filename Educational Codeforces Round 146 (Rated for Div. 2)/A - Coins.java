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
    -> If n is even we can always generate n 
    -> If n is odd, we can generate n if k is also odd, else we can never generate n
    
    Alternate solution:
    ax+by=d has a solution if d%gcd(a,b)==0 (Bezout's identity)
    this question has a solution if n%gcd(2,k)==0
  */
 
  static void solve() throws Exception{
    long n = l();
    long k = l();
 
    if(n%2==0) sb.append("YES\n");
    else{
      if(k%2==0) sb.append("NO\n");
      else sb.append("YES\n");
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
    while (t-- > 0) {
      solve();
    }
    
    out.printLine(sb);
    out.close();
  }
}