/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.*;

/**
 *
 * @author rcoem
 */
public interface NewInterface extends Remote{
    public int[][] add(int[][] a ,int[][] b) throws RemoteException;
    public int[][] sub(int[][] a, int[][] b) throws RemoteException;
    public int[][] transpose(int[][] a) throws RemoteException;
    public int[][] multiplication(int[][] a, int[][] b) throws RemoteException;
}
