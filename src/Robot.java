import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Robot extends Thread
{
    static AtomicInteger GO;
    final boolean run = true;

    public Robot()
    {
        GO = new AtomicInteger(0);
    }

    public synchronized void update()
    {
        GO.set((GO.get()+1)%4);
    }

    public void run()
    {
        while(run) {
            update();
            if(GO.get()==0)
            {
                System.out.println("go left and right");
            }
            else if(GO.get()==2) {
                System.out.println("go top and bottom");

            }
            else{
                System.out.println("YELLOW");
            }
            synchronized (this) {
                notifyAll();
            }
            try {
                sleep(9000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
