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
    We find maximum range such that the end characters of the range are different in a and a1
    The ends being unequal denote that the numbers have been permuted -> they have been sorted
    Then we try to extend the ends of such range
 
    TC: O(n)
   */
  
   static void solve(int tc) throws Exception{
    int n = i();
    int[] a = readArrayi(n);
    int[] a1 = readArrayi(n);
    
    int start = n;
    int end = -1;
 
    for(int i =0; i<n; i++){
      if(a[i]!=a1[i]){
         start = Math.min(start, i);
         end = Math.max(end, i);
      }
    }
 
    // since ans is always possible we do not really need to find max and min in [start, end], we can instead just compare ends
    while(start-1>=0 && a1[start-1]<=a1[start]) start--; // a1[start-1] is guaranteed to be a minimum element 
    while(end+1<n && a1[end+1]>=a1[end]) end++; // a1[end+1] is guaranteed to be a maximum element
 
    sb.append(start+1).append(" ").append(end+1).append("\n");
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