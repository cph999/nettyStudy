package dfs;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int n;
    static int N = 110;
    static char[][] g = new char[N][N];
    static boolean[][] st = new boolean[N][N];
    static int x1;
    static int y1;
    static int x2;
    static int y2;
    static int[] dx = new int[] {-1,0,1,0};
    static int[] dy = new int[] {0,1,0,-1};

    static boolean dfs(int x,int y)
    {
        if(g[x][y] == '#') return false;
        if(x == x2 && y == y2) return true;
        st[x][y] = true;
        for(int i = 0;i < 4;i ++)
        {
            int a = x + dx[i];
            int b = y + dy[i];
            if(a < 0 || a >= n || b < 0 || b >= n) continue;
            if(st[a][b]) continue;
            if(dfs(a,b)) return true;
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        while(T -- > 0)
        {
            n = scan.nextInt();
            for(int i = 0;i < n;i ++)
            {
                char[] charArray = scan.next().toCharArray();
                for(int j = 0;j < n;j ++)
                    g[i][j] = charArray[j];
            }
            x1 = scan.nextInt();
            y1 = scan.nextInt();
            x2 = scan.nextInt();
            y2 = scan.nextInt();
            for(int i = 0;i < n;i ++) Arrays.fill(st[i], false);
            if(dfs(x1,y1)) System.out.println("YES");
            else System.out.println("NO");
        }
    }
}

