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
      Observations:
      -> Can always reach a distance d even if m is not a divisor of d
      -> If current length of legs is m, can reach destination d in m-1 + ceil(d/m) steps
      -> It is never optimal to increase m beyond sqrt(d)
    */
   
    static void solve() throws Exception{
      long a = l();
      long b = l();
      long minMoves = Long.MAX_VALUE;
   
      for(long m=1; m<=(long)1e5; m++){
          long moves = ceil(a, m) + ceil(b, m) + m-1;
          minMoves = Math.min(minMoves, moves);
      }
   
      sb.append(minMoves).append("\n");
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