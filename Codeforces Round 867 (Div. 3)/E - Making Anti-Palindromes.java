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
   If n is odd, answer is -1, we cannot do anything with the middle character 
   If maxFreq of any character is >n/2, answer is -1, in such case there will always be atleast one palindromic pair after reordering
   Else we count palindromic pairs, we also store the count of characters of palindromic pairs
   The answer is either ceil(palindromic pairs/2) (we swap palindromic pairs among each other) or maximum frequency count of palindromic pairs (here we cannot swap pairs among one another, we require 1 swap for every character)
   We take maximum of the two   

   TC: O(n)
  */
 
  static void solve(int tc) throws Exception{
    int n = i();
    String str = s();
 
    if(n%2!=0) sb.append(-1).append("\n");
    else{
      int[] map = new int[26];
      int maxFreq = 0;
 
      for(int index=0; index<n; index++){
         char ch = str.charAt(index);
         map[ch-'a']++;
         maxFreq = Math.max(maxFreq, map[ch-'a']);
      }
 
      if(maxFreq>n/2){
        sb.append("-1\n");
        return;
      }
 
      map = new int[26];
      int pairs = 0;
 
      int start = 0;
      int end = n-1;
      maxFreq = 0;
 
      while(start<end){
        if(str.charAt(start)==str.charAt(end)){
           pairs++;
           map[str.charAt(start)-'a']++;
           maxFreq = Math.max(maxFreq, map[str.charAt(start)-'a']);
        }
 
        start++;
        end--;
      }
 
      sb.append(Math.max(maxFreq, (pairs+1)/2)).append("\n");
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