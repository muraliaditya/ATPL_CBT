import java.util.Scanner;

public class Solution {
    public boolean isPalindrome(String s) {
        if(s == null) return false;
        int i = 0, j = s.length() - 1;
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        Solution sol = new Solution();
        System.out.println(sol.isPalindrome(input));
    }
}