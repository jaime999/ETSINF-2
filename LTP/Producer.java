public class Producer extends Thread
{
 private Box box;
 private int prodname;
 public Producer (Box c, int name)
 {
 box = c;
 prodname = name;
 }

 public void run()
 { for (int i=1; i<=10; i++){
 box.put(i);
 System.out.println("Producer #" +
 prodname + " puts: " + i);
 try {
 Thread.sleep((int)(Math.random() *
 100));
 } catch (InterruptedException e) { }
 }
 }
}

