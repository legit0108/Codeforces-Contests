import java.util.*;
import java.io.*;

public class Main {
  static StringBuilder sb;
  static dsu dsu;
  static long fact[];
  static long mod = (long) (1e9 + 7);
  static BufferedReader br;
  static HashMap<Long,Long> map;
  static int minTree[];
  static int maxTree[];
  static int arr[];
  
  // if all pairs considered , then our graph 
  // will have n^2 nodes , which will TLE
  // we can reduce number of nodes in graph
  // to n , perform bfs on this graph to find min path
  // the idea is to consider every element as maximum 
  // and minimum element respectively , then perform 
  // range minimum , range maximum on range starting from 
  // current element to next greater , next smaller element 
  // present , so we can remove unnecessary nodes in graph
  // then our graph will have n nodes , perform bfs on this graph
  
  // TC : O(nlogn)
  // SC : O(n)
  
  static void solve() throws Exception{
    int n = i();
    minTree = new int[4*n+1];
    maxTree = new int[4*n+1];
    int map[] = new int[n+1];
    arr= new int[n+1];
    ArrayList<Integer> graph[] = new ArrayList[n+1];
    
    for(int idx=1;idx<=n;idx++){
        int val = i();
        arr[idx] = val;
        map[val] = idx;
        graph[idx] = new ArrayList();
    }
    
    buildMinTree(1,1,n);
    buildMaxTree(1,1,n);
    int nextGreater[] = new int[n+1];
    int nextSmaller[] = new int[n+1];
    
    Stack<Integer> stack = new Stack();
    for(int idx=n;idx>=1;idx--){
        while(stack.size()>0&&arr[stack.peek()]>arr[idx]) stack.pop();
        if(stack.size()==0) nextSmaller[idx] = n+1;
        else nextSmaller[idx] = stack.peek();
        stack.push(idx);
    }
    
    stack = new Stack();
    for(int idx=n;idx>=1;idx--){
        while(stack.size()>0&&arr[stack.peek()]<arr[idx]) stack.pop();
        if(stack.size()==0) nextGreater[idx] = n+1;
        else nextGreater[idx] = stack.peek();
        stack.push(idx);
    }
    
    for(int idx = 1;idx<=n;idx++){
        // let arr[idx] be minimum
        int nextMin = nextSmaller[idx];
        if(idx+1<=nextMin-1){
            int val = queryMax(1,1,n,idx+1,nextMin-1);
            int node = map[val];
            graph[idx].add(node);
            graph[node].add(idx);
        }
        
        // let arr[idx] be maximum
        int nextLarger = nextGreater[idx];
        if(idx+1<=nextLarger-1){
            int val = queryMin(1,1,n,idx+1,nextLarger-1);
            int node = map[val];
            graph[idx].add(node);
            graph[node].add(idx);
        }
    }
    
    // perform bfs
    boolean visited[] = new boolean[n+1];
    Queue<Integer> queue = new ArrayDeque();
    queue.add(1);
    int len = 0;
    
 mainLoop : while(queue.size()>0){
        int size = queue.size();
        
        while(size>0){
            int node = queue.remove();
            size--;
            if(visited[node]) continue;
            
            if(node==n) break mainLoop;
            
            visited[node] = true;
            
            for(int nextNode : graph[node]){
                if(!visited[nextNode]) queue.add(nextNode);
            }
        }
        
        len++;
    }
    
    sb.append(len).append("\n");
  }
  
  static int queryMin(int node,int start,int end,int left,int right){
      if(end<left||start>right) return Integer.MAX_VALUE;
      if(start>=left&&end<=right) return minTree[node];
      
      int mid = start + (end-start)/2;
      int leftMin = queryMin(node*2,start,mid,left,right);
      int rightMin = queryMin(node*2+1,mid+1,end,left,right);
      int min = Math.min(leftMin,rightMin);
      return min;
  }
  
  static int queryMax(int node,int start,int end,int left,int right){
      if(end<left||start>right) return Integer.MIN_VALUE;
      if(start>=left&&end<=right) return maxTree[node];
      
      int mid = start + (end-start)/2;
      int leftMax = queryMax(node*2,start,mid,left,right);
      int rightMax = queryMax(node*2+1,mid+1,end,left,right);
      int max = Math.max(leftMax,rightMax);
      return max;
  }
  
  static void buildMinTree(int node,int start,int end){
      if(start==end){
          minTree[node] = arr[start];
          return;
      }
      
      int mid = start + (end-start)/2;
      buildMinTree(node*2,start,mid);
      buildMinTree(node*2+1,mid+1,end);
      int min = Math.min(minTree[node*2],minTree[node*2+1]);
      minTree[node] = min;
  }
  
  static void buildMaxTree(int node,int start,int end){
      if(start==end){
          maxTree[node] = arr[start];
          return;
      }
      
      int mid = start + (end-start)/2;
      buildMaxTree(node*2,start,mid);
      buildMaxTree(node*2+1,mid+1,end);
      int max = Math.max(maxTree[node*2],maxTree[node*2+1]);
      maxTree[node] = max;
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