import java.util.Scanner;

/**
 * @author rishav12
 */
public class OptimalRead {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ts = sc.nextInt();
        while (ts-- > 0) {
            int n = sc.nextInt();
            int[] arr = new int[n + 1];
            int[] cum = new int[n + 1];
            int avg = 0;
            int days;
            days = sc.nextInt();
            int sum = 0;
            arr[0] = 0;
            cum[0] = 0;
            for (int i = 1; i <= n; i++) {
                arr[i] = sc.nextInt();
                cum[i] = cum[i - 1] + arr[i];
                System.out.println(cum[i]);
//avg+=arr[i];
            }
            sum = cum[n];
            avg = sum / days;
            int[][] dp = new int[days + 1][n + 1];
            for (int i = 1; i <= days; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[i][j] = Integer.MAX_VALUE;
                }
            }
            for (int i = 1; i <= n; i++) {
                dp[1][i] = Math.abs(cum[i] - avg);
            }
            System.out.println("avg " + avg);
            int mini = Integer.MAX_VALUE;
            for (int i = 2; i <= days; i++) {
//System.out.println(i+"th iteration");
                int temp = Integer.MAX_VALUE;
                for (int j = 1; j <= n; j++) {

                    for (int k = j + 1; k <= n; k++) {
                        int s = cum[k] - cum[j];
                        int a = Math.abs(s - avg);
                        temp = Math.min(temp, dp[i - 1][j] + a);
                        if (dp[i - 1][j] != Integer.MAX_VALUE) {
                            dp[i][k] = Math.min(dp[i][k], dp[i - 1][j] + a);
                        }
//System.out.println(dp[i][k]+" "+dp[i-1][j]+" "+k+" "+j);
// System.out.println(dp[i-1][j]+" "+dp[i][k]);
                    }
                }
            }
            System.out.println(dp[days][n]);
        }
    }
}
