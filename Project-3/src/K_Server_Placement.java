import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class K_Server_Placement {
    // Method to find optimal server positions to minimize traffic in a K-server placement problem
    public static int[] server_placement(int[] a, int n, int k) {
        // Create a dynamic programming table to store the minimum traffic values
        int[][] b = new int[n + 1][k + 1];

        // Initialize base cases (when there's only one server)
        for (int i = 1; i <= n; i++) {
            b[i][1] = b[i - 1][1] + a[i - 1];
        }

        // Dynamic programming to fill the table for more servers
        for (int i = 2; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                int min_Traffic = Integer.MAX_VALUE;
                for (int l = 1; l <= j; l++) {
                    int traffic = Math.max(b[l][i - 1], b[j][1] - b[l][1]);
                    min_Traffic = Math.min(min_Traffic, traffic);
                }
                b[j][i] = min_Traffic;
            }
        }

        // Traceback to find the optimal server positions with minimum traffic
        int[] servers = new int[k];
        int j = n;
        for (int i = k; i > 0; i--) {
            int min_Traffic = Integer.MAX_VALUE;
            for (int l = j; l > i - 1; l--) {
                int traffic = Math.max(b[l][i - 1], b[j][1] - b[l][1]);
                if (traffic < min_Traffic) {
                    min_Traffic = traffic;
                    servers[i - 1] = l;
                }
            }
            j = servers[i - 1] - 1;
        }

        return servers; // Return the positions of the K servers
    }

    public static void main(String[] args) {
        //long begin = System.nanoTime();
        Scanner sc = new Scanner(System.in);
        Random r = new Random();

        // Prompt the user to enter the number of client machine (number of traffic weights)
        System.out.println("Enter number of client machine: ");
        int n = sc.nextInt();

        // Generate random traffic weights for the servers
        int[] a = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = r.nextInt(10);
        }

        // Prompt the user to enter the value of k (number of servers)
        System.out.println("Enter the value for k: ");
        int k = sc.nextInt();

        // Find the optimal server positions to minimize traffic
        int[] Optimal_Server_Positions = server_placement(a, n, k);

        // Display the optimal server positions
        System.out.println("Optimal server positions: " + Arrays.toString(Optimal_Server_Positions));

        //long end = System.nanoTime();
        //long time = end-begin;
        //long timeMS = TimeUnit.NANOSECONDS.toMillis(time);
        //System.out.println("Elapsed Time: "+time);
        //System.out.println("Elapsed Time: "+timeMS);
    }
}