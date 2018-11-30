
import java.util.Scanner;
/**
@Author : Ankush Sarsewar
Rollno: 36(B)
**/

public class Lamport {

    public static int ne=0;
    public static int np=0;
    public static void main(String[] args)
    {

        System.out.println("Enter the number of processess:");

        Scanner sc=new Scanner(System.in);
        np=sc.nextInt();

        System.out.println("Enter the max number of events :");
        ne=sc.nextInt();
        int[][] res=new int[np][ne];
        int k=1;
        for(int i=0;i<np;i++,k=1)
        {
            for(int j=0;j<ne;j++)
            {
                res[i][j]=k;
                k++;
            }
        }
        System.out.println("Note: Enter $ if there is no event in particular process\n" +
                "Enter * if there is no external events msg between process's events\n" +
                "Enter 'p-e' at dep(i,j) if there is external event occur between jth event of ith process to" +
                "eth event of pth process.");

        System.out.println("Enter the dependency matrix\n:");
        String[][] dep=new String[np][ne];
        for(int i=0;i<np;i++)
        {
            for(int j=0;j<ne;j++)
            {
                dep[i][j]=sc.next();
            }
        }
        System.out.println("Processing.......\n");
        lamportAlgo(res,dep,np,ne);


    }
    public static void update(int[][] res,int i,int j,int p,int e)
    {
        int m=Math.max(res[i][j],res[p][e-1]);
        int k=res[p][e]=m+1;
        for(int l=e;l<ne;l++)
            res[p][l]=k++;
    }
    public static void lamportAlgo(int[][] res,String[][] dep,int np,int ne)
    {

        for(int i=0;i<ne;i++)
        {
            for(int j=0;j<np;j++)
            {
                if(!dep[j][i].equals("*") && !dep[j][i].equals("$"))
                {
                    String s=dep[j][i];
                    String[] sp=s.split("-");
                    int p=Integer.parseInt(sp[0]);
                    int e=Integer.parseInt(sp[1]);
                    update(res,j,i,p,e);
                }
            }
        }

        for(int i=0;i<np;i++)
        {
            for(int j=0;j<ne;j++)
            {
                if(dep[i][j].equals("$"))
                    System.out.print("-\t");
                else
                    System.out.print(res[i][j]+"\t");
            }
            System.out.println("\n");
        }



    }
/**

 Output :
 Enter the number of processess:
 3
 Enter the max number of events :
 8
 Note: Enter $ if there is no event in particular process
 Enter * if there is no external events msg between process's events
 Enter 'p-e' at dep(i,j) if there is external event occur between jth event of ith process toeth event of pth process.
 Enter the dependency matrix
 :
 *
 2-3
 1-2
 *
 *
 *
 1-5
 *
 *
 0-3
 *
 *
 2-5
 *
 *
 $
 1-3
 0-4
 0-5
 *
 *
 *
 *
 $
 Processing.......

 1	2	3	4	5	6	7	8

 1	2	4	5	6	8	9	-

 1	2	3	4	5	7	8	-


 Process finished with exit code 0
**/

}