import java.util.*;
import java.io.*;

public class Main {
  static StringBuilder sb;
  static dsu dsu;
  static long fact[];
  static long mod = 998244353l;
  static BufferedReader br;
  static HashMap<Long, Long> map;

  static ArrayList<Integer>[] factors;
  static int MAX_M = (int)1e5;
  static int minOps = Integer.MAX_VALUE;
  
  /*
    Ideas:

    -> We cannot have 0 in the array since all subarray sums have to be either positive or negative
    -> We will put positive numbers in the array till some index j and then negative numbers
    -> Place a negative number such that we get total k positive subarrays
    -> We will not place 1s because that may lead to a subarray sum of 0 which we do not want (each subarray has a positive or negative sum)
    -> We place 2s, and odd negative numbers, since even-odd = odd we will never get subarray sum as 0

    TC: O(n)
    SC: O(1)
   */
  
  static void solve() {
    int positive = 0; // number of positive subarrays till now
    int n = i();
    int k = i();
    boolean flag = false; // if flag is true, we have filled all positive subarrays and need to fill negative subarrays

    for(int index=1; index<=n; index++){
        if(flag){
          sb.append("-1000 ");
        }else{
          if(positive+index<=k){
              positive+=index;
              sb.append("2 ");
          }else{
              int requiredPositive = k-positive; // positive subarrays required
              sb.append(-2*(index-requiredPositive)+1).append(" "); // element to be added to form requiredPositive subarrays 
              flag = true;

              /*
              How did we arrive at -2*(index-requiredPositive)+1?
              
              -> 2 2 2 2 2 2 N (where N is a negative element)
              -> Sum of entire subarray = 2*j-N, where j is the number of times 2 occurs
              -> Sum of subarray starting from 2nd index: 2*(j-1)-N
              -> To get one positive subarray: 2*j-N>0 and 2*(j-1)-N<0
              -> Simplifying: N<2*j and N>2*(j-1) or N<2*j and N>2*j-2
              -> Only possible value for N = 2*j-1 since it lies between 2*j-2 and 2*j
              -> N<0 so actually we need to put -(2*j-1) = -2*j+1
              -> Now j = number of times 2 occurs = index-1
              -> j = index-1 when we want 1 positive subarray, j = index-2 when we want 2 positive subarrays
              -> j = index-requiredPositive when we want requiredPositive subarrays 
              */
          }
        }
    }

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

  /*
   * fact=new long[(int)1e6+10]; fact[0]=fact[1]=1; for(int i=2;i<fact.length;i++)
   * { fact[i]=((long)(i%mod)1L(long)(fact[i-1]%mod))%mod; }
   */
//**************NCR%P******************
  
  // get all factors for a number <= MAX_M
  
  private static void getFactors(){ // MAX_M * log (MAX_M)
    for(int idx=0; idx<=MAX_M; idx++) factors[idx] = new ArrayList();

    for(int num1=1; num1<=MAX_M; num1++){
       for(int num2=num1; num2<=MAX_M; num2+=num1){
           factors[num2].add(num1);
       }
    } 
  }
  

  // sqrt in logn

  private static long sqrt(long n) {
      long low = 0;
      long high = Math.min(n, (long)1e9);
      long ans = -1;
        
      while(low<=high){
          long mid = low + (high-low)/2;
            
          if(mid*mid>n) high = mid-1;
          else{
              ans = mid;
              low = mid+1;
          }
      }
        
      return ans;
  }

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
  
  //   ****** next permutation=================
  public static int[] swap(int arr[], int start, int end)
  {
    int temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;
 
    return arr;
  }
 
  public static int[] reverse(int arr[], int start, int end)
  {
    while (start < end) {
      int temp = arr[start];
      arr[start++] = arr[end];
      arr[end--] = temp;
    }
 
    return arr;
  }
 
  public static boolean next_permutation(int arr[]) {
    if (arr.length <= 1)
      return false;
 
    int last = arr.length - 2;
 
    while (last >= 0) {
      if (arr[last] < arr[last + 1]) {
        break;
      }
      last--;
    }
 
    if (last < 0)
      return false;
 
    int nextGreater = arr.length - 1;
 
    for (int i = arr.length - 1; i > last; i--) {
      if (arr[i] > arr[last]) {
        nextGreater = i;
        break;
      }
    }
 
    arr = swap(arr, nextGreater, last);
 
    arr = reverse(arr, last + 1, arr.length - 1);
 
    return true;
  }

  // *************Disjoint set
  // union*********//
  static class dsu {
    int parent[];
    int rank[];
    int cycles;
    
    dsu(int n) {
      cycles = n;
      parent = new int[n+1];
      rank = new int[n+1];
      for (int i = 0; i <=n; i++){
        parent[i] = -1;
        rank[i] = 1;
      }
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
      int la = find(a);
      int lb = find(b);
      if (la == lb)
        return;
      
      if(rank[la]<rank[lb]) parent[la] = lb;
      else if(rank[la]>rank[lb]) parent[lb] = la;
      else{
          parent[la] = lb;
          rank[lb]++;
      }
      
      cycles--;
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
  
  public static int[] sort(int[] a2) {
    int n = a2.length;
    ArrayList<Integer> l = new ArrayList<>();
    for (int i : a2)
      l.add(i);
    Collections.sort(l);
    for (int i = 0; i < l.size(); i++)
      a2[i] = l.get(i);
    return a2;
  }

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