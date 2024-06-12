import java.util.concurrent.atomic.AtomicInteger;

public class GridBlock
{
    private final int[] coords;
    private AtomicInteger isOccupied;


    public GridBlock( int x, int y)
    {
        coords = new int[] {x,y};
        isOccupied = new AtomicInteger(-1);
    }

    public int getX()
    {
        return coords[0];
    }
    public int getY()
    {
        return coords[1];
    }
    public boolean isOccupied()
    {
        return isOccupied.get()!=-1;
    }


    public void release()
    {
        isOccupied.set(-1);
    }

    public synchronized void occupy(Car car)
    {
        isOccupied.set(car.getID());
        car.currentBlock = this;
    }

    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}
