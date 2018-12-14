package javaapplication1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Ankush Sarsewar
 */
public class Rayment {

    public static int holder = -1;
    public static Node n0, n1, n2, n3, n4, n5, n6;

    public static class Node {

        int n;
        ArrayList<Node> prev;
        ArrayList<Node> queue;
        boolean token;
        Node next;

        Node(int i) {
            this.n = i;
            next = null;
            prev = new ArrayList<>();
            queue = new ArrayList<>();
            token = false;
        }

        void setNode(Node i) {
            this.next = i;
            i.prev.add(this);
        }

    }

    public static Node getNode(int i) {
        switch (i) {
            case 0:
                return n0;
            case 1:
                return n1;
            case 2:
                return n2;
            case 3:
                return n3;
            case 4:
                return n4;
            case 5:
                return n5;
            case 6:
                return n6;
        }
        return null;

    }

    public static void requestUpward(Node i) {
        Node r = i;
        Node p = i;
        while (r != null) {
            r.queue.add(getNode(p.n));
            System.out.println("Node " + p.n + " is added to " + r.n);
            p = r;
            r = r.next;
            duplicate();
        }
        display();
    }

    public static void duplicate() {
        for (int i = 0; i < 6; i++) {
            Node n = getNode(i);
            if (!n.queue.isEmpty()) {
                Node j = n.queue.get(0);
                if (n.queue.size() < 2) {
                    continue;
                }
                Node b = n.queue.get(1);
                if (j.n == b.n) {
                    n.queue.remove(0);
                }
            }
        }

    }

    public static boolean check(Node n, int i) {
        if (n == null) {
            return false;
        }
        for (Node k : n.prev) {
            if (n.n == i) {
                return true;
            }
        }
        return false;
    }

    public static void requestDownward(Node i) {
        Node r = i;
        Node p = i;
        while (r != null) {
            System.out.println("Enter");
            if (r.prev.size() == 1) {
                p = r.prev.get(0);
                if (i.n != p.n) {
                    p.queue.add(getNode(r.n));
                }
                System.out.println("1.Node " + r.n + " added to " + p.n);
                r = p;
            } else if (r.prev.size() > 1) {
                for (Node b : r.prev) {
                    System.out.println("b.n" + b.n);
                    b.queue.add(getNode(r.n));
                    System.out.println("2.Node " + r.n + " added to " + b.n);
                    r = b;
                    if (r.token) {
                        break;
                    }

                }
            }
            duplicate();
            if (r.token) {
                break;
            }
        }
        System.out.println("Exit");
        display();
    }

    public static void display() {
        System.out.println("Queues:");
        for (int i = 0; i < 6; i++) {
            Node n = getNode(i);
            System.out.print("Node " + n.n + ": ");
            for (Node j : n.queue) {
                System.out.print(j.n + "\t");
            }
            System.out.println("");
        }
    }

    public static boolean AllEmpty() {
        for (int i = 0; i < 6; i++) {
            Node n = getNode(i);
            if (n.queue.size() > 0) {
                return false;
            }
        }
        return true;
    }

    public static void process(Node i) {
        Scanner sc = new Scanner(System.in);
        String str = "";
        Node r = i;
        Node p = i;
        while (r != null && !AllEmpty()) {
            if (!r.queue.isEmpty()) {
                p = r.queue.remove(0);

                holder = p.n;
                System.out.println("Token " + r.n + " is pass to " + p.n);
                r.token = false;
                p.token = true;
                r = p;

            } else {
                if (r.next != null) {
                    System.out.println("Token " + r.n + " is pass to " + r.next.n);
                    r = r.next;
                }
            }

            System.out.println("Any Request(yes/no)?");
            str = sc.next();
            if (str.equalsIgnoreCase("yes")) {
                System.out.println("Enter node number:");

                int h = sc.nextInt();
                if (h < r.n) {
                    System.out.println("downward");
                    requestDownward(getNode(h));
                } else {
                    requestUpward(getNode(h));
                }
            }
            System.out.println("Done!");

        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n0 = new Node(0);
        n0.token = true;
        n1 = new Node(1);
        n2 = new Node(2);
        n3 = new Node(3);
        n4 = new Node(4);
        n5 = new Node(5);
        n1.setNode(n0);
        n2.setNode(n1);
        n3.setNode(n2);
        n4.setNode(n3);
        n5.setNode(n3);
        holder = 0;
        System.out.println("no.prec" + n0.prev.get(0).n);
        requestUpward(n3);
        requestUpward(n4);
        process(n0);
    }
}
/*
output:
Node 3 is added to 3
Node 3 is added to 2
Node 2 is added to 1
Node 1 is added to 0
Queues:
Node 0: 1	
Node 1: 2	
Node 2: 3	
Node 3: 3	
Node 4: 
Node 5: 
Node 4 is added to 4
Node 4 is added to 3
Node 3 is added to 2
Node 2 is added to 1
Node 1 is added to 0
Queues:
Node 0: 1	
Node 1: 2	
Node 2: 3	
Node 3: 3	4	
Node 4: 4	
Node 5: 
Token 0 is pass to 1
Any Request(yes/no)?
n
Done!
Token 1 is pass to 2
Any Request(yes/no)?
n
Done!
Token 2 is pass to 3
Any Request(yes/no)?
yes
Enter node number:
0
downward
Enter
1.Node 0 added to 1
Enter
1.Node 1 added to 2
Enter
1.Node 2 added to 3
Exit
Queues:
Node 0: 
Node 1: 0	
Node 2: 1	
Node 3: 3	4	2	
Node 4: 4	
Node 5: 
Done!
Token 3 is pass to 3
Any Request(yes/no)?
m
Done!
Token 3 is pass to 4
Any Request(yes/no)?
m
Done!
Token 4 is pass to 4
Any Request(yes/no)?
m
Done!
Token 4 is pass to 3
Any Request(yes/no)?
n
Done!
Token 3 is pass to 2
Any Request(yes/no)?
b
Done!
Token 2 is pass to 1
Any Request(yes/no)?
yes
Enter node number:
5
Node 5 is added to 5
Node 5 is added to 3
Node 3 is added to 2
Node 2 is added to 1
Node 1 is added to 0
Queues:
Node 0: 1	
Node 1: 0	2	
Node 2: 3	
Node 3: 5	
Node 4: 
Node 5: 5	
Done!
Token 1 is pass to 0
Any Request(yes/no)?
n
Done!
Token 0 is pass to 1
Any Request(yes/no)?
n
Done!
Token 1 is pass to 2
Any Request(yes/no)?
nb
Done!
Token 2 is pass to 3
Any Request(yes/no)?
n
Done!
Token 3 is pass to 5
Any Request(yes/no)?
b
Done!
Token 5 is pass to 5
Any Request(yes/no)?
b
Done!

*/
