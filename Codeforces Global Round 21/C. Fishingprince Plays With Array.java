import java.util.*;
import java.io.*;

public class Main {
  static StringBuilder sb;
  static dsu dsu;
  static long fact[];
  static long mod = (long) (1e9 + 7);
  static BufferedReader br;
  static HashMap<Long,Long> map;
 
// Keep doing expand(divsion with m) on both arrays until we can't do expand anymore, call the resulting arrays new_a and new_b. It suffices to check if new_a=new_b.
// TC : O((n+k)logm(maxval))
// SC : O(n+k)

  static void solve() throws Exception{
    int n = i();
    long m = l();
    
    List<Pair> a = new ArrayList();
    for(int idx=0;idx<n;idx++){
        long val = l();
        if(idx==0) a.add(new Pair(val,1));
        else{
            long lastVal = a.get(a.size()-1).val;
            if(lastVal==val) a.get(a.size()-1).count++;
            else a.add(new Pair(val,1));
        }
    }
    
    int k = i();
    List<Pair> b = new ArrayList();
    for(int idx=0;idx<k;idx++){
        long val = l();
        if(idx==0) b.add(new Pair(val,1));
        else{
            long lastVal = b.get(b.size()-1).val;
            if(lastVal==val) b.get(b.size()-1).count++;
            else b.add(new Pair(val,1));
        }
    }
    
    int size_a = a.size();
    int size_b = b.size();
    
    List<Pair> new_a = new ArrayList();
    for(int idx=0;idx<size_a;idx++){
        Pair pair = a.get(idx);
        
        while(pair.val%m==0){
            pair.val/=m;
            pair.count*=m;
        }
        
        new_a.add(pair);
    }
    a = new_a;
    
    List<Pair> new_b = new ArrayList();
    for(int idx=0;idx<size_b;idx++){
        Pair pair = b.get(idx);
        
        while(pair.val%m==0){
            pair.val/=m;
            pair.count*=m;
        }
        
        new_b.add(pair);
    }
    b = new_b;
    
    int idx_a = 0;
    int idx_b = 0;
    size_a = new_a.size();
    size_b = new_b.size();
    
    while(idx_a<size_a&&idx_b<size_b){
        Pair pair_a = a.get(idx_a);
        long val_a = pair_a.val;
        long count_a = pair_a.count;
        
        Pair pair_b = b.get(idx_b);
        long val_b = pair_b.val;
        long count_b = pair_b.count;
        
        if(val_a!=val_b){
            sb.append("No\n");
            return;
        }
        
        long minCount = Math.min(count_a,count_b);
        pair_a.count-=minCount;
        pair_b.count-=minCount;
        
        if(pair_a.count==0) idx_a++;
        if(pair_b.count==0) idx_b++;
    }
    
    if(idx_a==size_a&&idx_b==size_b) sb.append("Yes\n");
    else sb.append("No\n");
  }
  
  public static void main(String[] args) throws Exception{
    sb = new StringBuilder();
    br = new BufferedReader(new InputStreamReader(System.in));
    int test = i();
    while (test-- > 0) {
      solve();
    }
    out.printLine(sb);
    out.close();
  }
  
  /*
   * fact=new long[(int)1e6+10]; fact[0]=fact[1]=1; for(int i=2;i<fact.length;i++)
   * { fact[i]=((long)(i%mod)1L(long)(fact[i-1]%mod))%mod; }
   */
//**************NCR%P******************
  static long ncr(int n, int r) {
    if (r > n)
      return (long) 0;

    long res = fact[n] % mod;
    res = ((long) (res % mod) * (long) (p(fact[r], mod - 2) % mod)) % mod;
    res = ((long) (res % mod) * (long) (p(fact[n - r], mod - 2) % mod)) % mod;
    return res;

  }

  static long p(long x, long y)// POWER FXN MODULO //
  {
    if (y == 0)
      return 1;

    long res = 1;
    while (y > 0) {
      if (y % 2 == 1) {
        res = (res * x) % mod;
        y--;
      }

      x = (x * x) % mod;
      y = y / 2;

    }
    return res;
  }

  // *************Disjoint set
  // union*********//
  static class dsu {
    int parent[];

    dsu(int n) {
      parent = new int[n];
      for (int i = 0; i < n; i++)
        parent[i] = -1;
    }

    int find(int a) {
      if (parent[a] < 0)
        return a;
      else {
        int x = find(parent[a]);
        parent[a] = x;
        return x;
      }
    }

    void merge(int a, int b) {
      a = find(a);
      b = find(b);
      if (a == b)
        return;
      parent[b] = a;
    }
  }

//**************PRIME FACTORIZE **********************************//
  static TreeMap<Integer, Integer> prime(long n) {
    TreeMap<Integer, Integer> h = new TreeMap<>();
    long num = n;
    for (int i = 2; i <= Math.sqrt(num); i++) {
      if (n % i == 0) {
        int nt = 0;
        while (n % i == 0) {
          n = n / i;
          nt++;
        }
        h.put(i, nt);
      }
    }
    if (n != 1)
      h.put((int) n, 1);
    return h;

  }

//****CLASS PAIR ************************************************
  static class Pair{
    long val;
    long count;
    
    Pair(long val,long count) {
      this.val = val;
      this.count = count;
    }
  }

  static class InputReader {
    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int read() {
      if (numChars == -1)
        throw new InputMismatchException();
      if (curChar >= numChars) {
        curChar = 0;
        try {
          numChars = stream.read(buf);
        } catch (IOException e) {
          throw new InputMismatchException();
        }
        if (numChars <= 0)
          return -1;
      }
      return buf[curChar++];
    }

    public int Int() {
      int c = read();
      while (isSpaceChar(c))
        c = read();
      int sgn = 1;
      if (c == '-') {
        sgn = -1;
        c = read();
      }
      int res = 0;
      do {
        if (c < '0' || c > '9')
          throw new InputMismatchException();
        res *= 10;
        res += c - '0';
        c = read();
      } while (!isSpaceChar(c));
      return res * sgn;
    }

    public String String() {
      int c = read();
      while (isSpaceChar(c))
        c = read();
      StringBuilder res = new StringBuilder();
      do {
        res.appendCodePoint(c);
        c = read();
      } while (!isSpaceChar(c));
      return res.toString();
    }

    public boolean isSpaceChar(int c) {
      if (filter != null)
        return filter.isSpaceChar(c);
      return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public String next() {
      return String();
    }

    public interface SpaceCharFilter {
      public boolean isSpaceChar(int ch);
    }
  }

  static class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
      writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
      this.writer = new PrintWriter(writer);
    }

    public void print(Object... objects) {
      for (int i = 0; i < objects.length; i++) {
        if (i != 0)
          writer.print(' ');
        writer.print(objects[i]);
      }
    }

    public void printLine(Object... objects) {
      print(objects);
      writer.println();
    }

    public void close() {
      writer.close();
    }

    public void flush() {
      writer.flush();
    }
  }

  static InputReader in = new InputReader(System.in);
  static OutputWriter out = new OutputWriter(System.out);

  public static long[] sort(long[] a2) {
    int n = a2.length;
    ArrayList<Long> l = new ArrayList<>();
    for (long i : a2)
      l.add(i);
    Collections.sort(l);
    for (int i = 0; i < l.size(); i++)
      a2[i] = l.get(i);
    return a2;
  }

  public static char[] sort(char[] a2) {
    int n = a2.length;
    ArrayList<Character> l = new ArrayList<>();
    for (char i : a2)
      l.add(i);
    Collections.sort(l);
    for (int i = 0; i < l.size(); i++)
      a2[i] = l.get(i);
    return a2;
  }
  
//*************NORMAL POWER******************************
  public static long pow(long x, long y) {
    long res = 1;
    while (y > 0) {
      if (y % 2 != 0) {
        res = (res * x);// % modulus;
        y--;

      }
      x = (x * x);// % modulus;
      y = y / 2;
    }
    return res;
  }

//GCD___+++++++++++++++++++++++++++++++
  public static long gcd(long x, long y) {
    if (x == 0)
      return y;
    else
      return gcd(y % x, x);
  }
  
  
  
  
  
  
  
  // ******LOWEST COMMON MULTIPLE
  // *********************************************

  public static long lcm(long x, long y) {
    return (x * (y / gcd(x, y)));
  }
  
  // ****BINARY SEARCH****//
  
  private static long bins(long arr[],long tar){
      int low = 0;
      int high = arr.length-1;
      
      while(low<=high){
          int mid = low + (high-low)/2;
          if(arr[mid]>tar) high = mid-1;
          else if(arr[mid]<tar) low = mid+1;
          else return mid;;
      }
      
      return -1;
  }
  
  // ********COUNTER******//
  
  private static void count(long arr[]){
      map = new HashMap();
      for(long val : arr) map.put(val,map.getOrDefault(val,0l)+1l);
  }

//INPUT PATTERN********************************************************
  public static int i() {
    return in.Int();
  }

  public static long l() {
    String s = in.String();
    return Long.parseLong(s);
  }

  public static String s() {
    return in.String();
  }

  public static int[] readArrayi(int n) {
    int A[] = new int[n];
    for (int i = 0; i < n; i++) {
      A[i] = i();
    }
    return A;
  }

  public static long[] readArray(long n) {
    long A[] = new long[(int) n];
    for (int i = 0; i < n; i++) {
      A[i] = l();
    }
    return A;
  }

}