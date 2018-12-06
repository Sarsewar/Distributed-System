/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Scanner;

/**
 *
 * @author Ankush
 */
public class Vector {
    
  
    public static int ne=0;
    public static int td=0;
    public static int np=0;
    public static boolean flag=true;     
    public static class Node
     {
        int n=0;
        public int[] arr;
        Node(int i)
       {
           this.n=i;
           this.arr=new int[this.n];
           for(int k=0;k<n;k++)
               arr[k]=0;
       }
       public  String toString()
        {
            String s="(";
               
            for(int i=0;i<n;i++)
                s+=String.valueOf(arr[i])+",";
            s+=")";
            return (s);
        }
       
     }
    
    public static void main(String[] args)
    {

        System.out.println("Enter the number of processess:");
        Scanner sc=new Scanner(System.in);
        np=sc.nextInt();
        System.out.println("Enter the max number of events :");
        ne=sc.nextInt();
        td=1;
        Node[][] res=new Node[np][ne];
        int k=1;
        for(int i=0;i<np;i++,k=1)
        {
            for(int j=0;j<ne;j++)
            {   res[i][j]=new Node(np);
                res[i][j].arr[i]=k++;
            }
        }  System.out.println("result:");
//        for(int i=0;i<np;i++)
//        {
//            for(int j=0;j<ne;j++){
//                System.out.print(res[i][j].toString()+"\t");
//            }
//            System.out.println("");
//        }

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
        vectorAlgo(res,dep,np,ne);


    }
    public static void update(Node[][] res,int i,int j,int p,int e,String[][] dep)
   {
       for(int v=0;v<np;v++ )
       {  res[p][e].arr[v]=Math.max(res[i][j].arr[v],res[p][e].arr[v]);   
       }
       for(int l=e;l<ne;l++)
       {  
          for(int v=0;v<np;v++)
          { if(l-1>0)
              res[p][l].arr[v]=Math.max(res[p][l-1].arr[v],res[p][l].arr[v]);
                
          }
       }    
        
    }
    public static void vectorAlgo(Node[][] res,String[][] dep,int np,int ne)
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
                    update(res,j,i,p,e,dep);               
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
    
    
/*
 

Enter the number of processess:
3
Enter the max number of events :
3  
Enter the dependency matrix:
1-0
1-1
*
0-1
0-2
*
*
1-1
*
  
Processing.......

(1,0,0,)	(2,1,0,)	(3,2,0,)	

(1,1,0,)	(2,2,2,)	(2,3,2,)	

(0,0,1,)	(0,0,2,)	(0,0,3,)	

BUILD SUCCESSFUL (total time: 4 seconds)
  
*/

}
/*




Enter the number of processess:
5

Enter the max number of events :
6
Enter the dependency matrix:
2-1
*
*
*
1-5
$
*
*
3-3
0-3
*
*
*
1-2
*
1-4
4-4
*
*
*
*
2-4
*
*
3-1
3-2
*
3-4
*
$
:
Processing.......

(1,0,0,0,0,)	(2,0,0,0,0,)	(3,0,0,0,0,)	(4,4,2,0,0,)	(5,4,2,0,0,)	-	

(0,1,0,0,0,)	(0,2,0,0,0,)	(1,3,2,0,0,)	(1,4,2,0,0,)	(1,5,4,0,0,)	(5,6,4,0,0,)	

(0,0,1,0,0,)	(1,0,2,0,0,)	(1,0,3,0,0,)	(1,0,4,0,0,)	(1,3,5,4,2,)	(1,3,6,4,2,)	

(0,0,0,1,0,)	(0,0,0,2,1,)	(0,0,0,3,2,)	(1,3,2,4,2,)	(1,3,2,5,4,)	(1,3,2,6,4,)	

(0,0,0,0,1,)	(0,0,0,0,2,)	(0,0,0,0,3,)	(0,0,0,0,4,)	(1,3,5,4,5,)	-	

BUILD SUCCESSFUL (total time: 8 seconds)

*/