
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author Ankush 
 */
public class RmiServer {

    public static void main(String[] args) {
        // TODO code application logic here
        
            try{
                Implementation add=new Implementation();
                Naming.bind("add", add);
                System.out.println("Add object Registered.");
                Implementation sub=new Implementation();
                Naming.bind("sub", sub);
                System.out.println("sub object Registered.");
                Implementation transpose=new Implementation();
                Naming.bind("transpose", transpose);
                System.out.println("transpose object Registered.");
                Implementation multiplication=new Implementation();
                Naming.bind("multiplication", multiplication);
                System.out.println("multiplication object Registered.");
            }catch(Exception e)
            {

            }
    }
    
}
    