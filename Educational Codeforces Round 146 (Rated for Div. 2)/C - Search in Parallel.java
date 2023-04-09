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
    -> If a ball is being accessed more times, that ball should be accessed first
    -> Sort on basis of frequency in descending order
    -> Assign ball to robot which takes less time to process ball
  */
 
  static void solve() throws Exception{
    int n = i();
    long s1 = l();
    long s2 = l();
 
    List<long[]> list = new ArrayList();
 
    for(int index=1; index<=n; index++){
      list.add(new long[]{index, l()});
    }
 
    Collections.sort(list, (a,b)->Long.compare(b[1], a[1]));
    List<Long> list1 = new ArrayList();
    List<Long> list2 = new ArrayList();
 
    int index = 0;
 
    while(index<list.size()){
      long time1 = list1.size()*s1 + s1;
      long time2 = list2.size()*s2 + s2;
 
      if(time1<time2) list1.add(list.get(index)[0]);
      else list2.add(list.get(index)[0]);
 
      index++;
    }
 
    sb.append(list1.size()).append(" ");
    for(long val: list1) sb.append(val).append(" ");
 
    sb.append("\n");
 
    sb.append(list2.size()).append(" ");
    for(long val: list2) sb.append(val).append(" "); 
 
    sb.append("\n");
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