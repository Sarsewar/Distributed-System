/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author rcoem
 */
public class Implementation extends UnicastRemoteObject implements NewInterface{

    Implementation() throws RemoteException
    {
       // super();
    }
    
    
    @Override
    public int[][] add(int[][] a, int[][] b) throws RemoteException {
        int columns=a[0].length;
        int rows= a.length;
       int[][] resultMatix = new int[rows][columns];
           for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < columns; j++) {
                        resultMatix[i][j] = a[i][j] + b[i][j];
                  }
           }
           return resultMatix;
    }

    @Override
    public int[][] sub(int[][] a, int[][] b) throws RemoteException {
      int columns=a[0].length;
        int rows= a.length;
       int[][] resultMatix = new int[rows][columns];
           for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < columns; j++) {
                        resultMatix[i][j] = a[i][j] - b[i][j];
                  }
           }
           return resultMatix;
    }

    @Override
    public int[][] transpose(int[][] a) throws RemoteException {
       
    int columns=a[0].length;
        int rows= a.length;
    int transpose[][] = new int[columns][rows];
           for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < columns; j++)
                        transpose[j][i] = a[i][j];
           }
           return transpose;
 
    }

    @Override
    public int[][] multiplication(int[][] a, int[][] b) throws RemoteException {
                int row1=a.length;
        int row2=b.length;
        int column1=a[0].length;
        int column2=b[0].length; 
        int[][] resultMatrix  = new int[row1][column2];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < column2; j++) {
                for (int k = 0; k < column2; k++) { 
                    resultMatrix[i][j] = resultMatrix[i][j] + a[i][k] * b[k][j];
                }
                }
            }
            return resultMatrix;
 
    
    }
   
   }

    
