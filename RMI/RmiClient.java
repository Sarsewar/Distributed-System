/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.Naming;
import java.util.Scanner;

/**
 *
 * @author rcoem
 */
public class RmiClient {
    public static void display(int[][] a)
    {
        for(int i=0;i<3;i++)
        {   for(int j=0;j<3;j++)
            {   
                System.out.print(a[i][j]+"\t");
            }
            System.out.println("");
            }
    
    }
    public static void main(String[] ars) throws Exception
    {

        Scanner sc=new Scanner(System.in);
        int[][] a=new int[3][3];        
        int[][] b=new int[3][3];
        int[][] res=new int[3][3];
        System.out.println("Enter Matrix 1(3 x 3):");
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                a[i][j]=sc.nextInt();
            }
        }
        System.out.println("Enter Matrix 1(3 x 3):");
         for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                b[i][j]=sc.nextInt();
            }
        }
        String s="rmi://localhost:/add";
        NewInterface i=(NewInterface)Naming.lookup(s);
        res=i.add(a,b);
        System.out.println("Addition of Matrix:");
        display(res);
        s="rmi://localhost:/sub";
        NewInterface sub=(NewInterface)Naming.lookup(s);
        res=i.sub(a,b);
         System.out.println("Subtraction of Matrix:");
        display(res);
        s="rmi://localhost:/transpose";
        NewInterface tran=(NewInterface)Naming.lookup(s);
        res=tran.transpose(a);
        System.out.println("Transpose of Matrix a:");
        display(res);
        res=tran.transpose(b);
        System.out.println("Transpose of Matrix b:");
        display(res);
        s="rmi://localhost:/multiplication";
        NewInterface mul=(NewInterface)Naming.lookup(s);
        res=mul.multiplication(a, b);
        System.out.println("multiplication of Matrix :");
        display(res);
        
        
     
    }
    
}

/*
1 2 3
4 5 6
7 8 9


1 2 3
4 5 6
7 8 9


*/
