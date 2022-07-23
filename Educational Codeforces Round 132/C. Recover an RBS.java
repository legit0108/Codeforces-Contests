import java.util.*;
import java.io.*;

public class Main {
  static StringBuilder sb;
  static dsu dsu;
  static long fact[];
  static long mod = (long) (1e9 + 7);
  static BufferedReader br;
  static HashMap<Long, Long> map;
  
  // think of opening bracket as +1
  // and closing bracket as -1
  // at any point prefix sum of RBS is non negative
  // we can find opening and closing brackets required to make the string RBS
  // using open-close and open+close equations
  // we then place opening brackets first followed by closing brackets
  // this is the optimal approach , we want prefix sum of RBS at any point to be non zero
  // so we can always generate one RBS using optimal approach
  // we try generating an RBS using another approach
  // let's say you have count opening brackets , in the second approach
  // place (count-1) opening brackets , then place a closing bracket , 
  // then place one opening bracket , then place remaining closing brackets
  // if we can generate another RBS using second approach then 
  // ans is NO else ans is YES
  // we only need to check the second optimal approach
  // since first optimal approach always gives us an RBS
  // second optimal approach is same as swapping one opening with closing in first optimal approach
  
  // TC : O(len)
  // SC : O(len)
  
  static void solve() throws Exception{
     char str[] = s().toCharArray();
     StringBuilder optimalStr = new StringBuilder();
     int buffer = 0; // ?
     int count = 0; // count of opening and closing
     int len = str.length;
     
     for(int idx=0;idx<len;idx++){
         char ch = str[idx];
         if(ch=='?') buffer++;
         else if(ch==')') count++;
         else count--;
     }
     
     //open-close = count
     //open+close = buffer
     
     int open = (buffer+count)/2;
     int close = (buffer-count)/2;
     
     // only one RBS
     if(open==0||close==0){
         sb.append("YES\n");
         return;
     }
     
     //build second optimal string
     for(int bracket=1;bracket<=open-1;bracket++) optimalStr.append("(");
     
     optimalStr.append(")");
     optimalStr.append("(");
     
     for(int bracket=1;bracket<=close-1;bracket++) optimalStr.append(")");
     
     count = 0;
     
     int idx=0;
     for(int strIdx=0;strIdx<len;strIdx++){
         if(str[strIdx]=='?'){
             str[strIdx] = optimalStr.charAt(idx);
             idx++;
         }
     }
     
     idx=0;
     while(idx<len){
         char ch = str[idx];
         if(ch=='(') count++;
         else count--;
         
         if(count<0){
             sb.append("YES\n");
             return;
         }
         
         idx++;
     }
     
     sb.append("NO\n");
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
  static class Pair implements Comparable<Pair> {
    int x;
    long y;

    Pair(int x, long y) {
      this.x = x;
      this.y = y;
    }

    public int compareTo(Pair o) {
      return (int) (this.y - o.y);

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

  private static long bins(long arr[], long tar) {
    int low = 0;
    int high = arr.length - 1;

    while (low <= high) {
      int mid = low + (high - low) / 2;
      if (arr[mid] > tar) high = mid - 1;
      else if (arr[mid] < tar) low = mid + 1;
      else return mid;;
    }

    return -1;
  }

  // ********COUNTER******//

  private static void count(long arr[]) {
    map = new HashMap();
    for (long val : arr) map.put(val, map.getOrDefault(val, 0l) + 1l);
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