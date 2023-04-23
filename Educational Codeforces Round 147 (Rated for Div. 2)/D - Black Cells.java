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
    We only consider skipping a segment if it contains a single cell
    Why? A single cell segment requires 3 operations (press+travel+release), if skipped and covered later it requires 2 operations (skip and travel ahead + travel while coloring some other segment)
    Keep count of such cells 
    Utilize these cells if and only if they are needed (if they are skipped previously but skipping them results in covering <k cells)
    All other segments if skipped will result in either equal number of operations required later on (for segments with 2 cells) or greater number of operations (for segments with >2 cells), so we will never skip them instead always pick them
    
    TC: O(n)
   */
  
  static void solve(int tc) throws Exception{
    int n = i();
    long k = l();
 
    long[] l = readArray((long)n);
    long[] r = readArray((long)n);
 
    long minOps = Long.MAX_VALUE;
    long singleCells = 0; // single cells encountered previously
    long ops = 0; // number of times we pressed and released shift for previous segments
 
    for(int i=0; i<n; i++){
      long left = l[i];
      long right = r[i];
 
      if(right-left+1>=k){
        minOps = Math.min(minOps, left+k-1+ops+2); // we travel till left so we add left, we add k-1 to cover cells left, we add ops to account for previous pressing/releasing shift, we add 2 to press/release shift for current segment
        break;
      }else if(right-left+1+singleCells>=k){ // if we can cover all cells using previously encountered cells and current segment
        minOps = Math.min(minOps, right+ops+2+(k-(right-left+1))*2); // we travel till right so we add right, we add ops to account for previous pressing/releasing shift, we add 2 to press/release shift for current segment, we cover k - (cells in current segment) single cells, we add 2 operations for these (press/release shift) 
      }
 
      if(right-left==0) singleCells++; // single cell encountered
      else{ // always beneficial to cover a segment having >1 cell 
        ops+=2;
        k-=(right-left+1);
      }
    }
 
    if(minOps==Long.MAX_VALUE) sb.append("-1\n");
    else sb.append(minOps).append("\n");
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