import java.awt.image.BufferedImage;
import java.util.Random;

public class Car extends Thread
{
    Random rand;
    private final int ID;
    static Grid intersection;
    GridBlock currentBlock;
    final GridBlock entrance;
    final GridBlock exit;
    final int colourIndex;
    static Robot robot;
    static BufferedImage[][] colours = new BufferedImage[4][4];
    BufferedImage state;


    public Car(int ID)
    {
        rand = new Random();
        this.ID=ID;

        int en = rand.nextInt(4);
        int ex = rand.nextInt(4);
        while (ex==en)
        {
            ex = rand.nextInt(4);
        }
        entrance = intersection.getEntrances()[en];
        exit = intersection.getExits()[ex];

        colourIndex = rand.nextInt(4);
        state = colours[colourIndex][en];
    }

    public int getID()
    {
        return ID;
    }

    //check
    public void enterGrid() throws InterruptedException {

        synchronized (entrance)
        {
            while(entrance.isOccupied())
            {
                entrance.wait();
            }
            entrance.occupy(this);
        }
    }

    //check
    public void exitGrid()
    {
        synchronized (exit)
        {
            currentBlock.release();
            currentBlock=null;
            exit.notifyAll();
        }
    }


    public boolean onRoad()
    {
        if (currentBlock==null)
        {
            return false;
        }
        return currentBlock.isOccupied();
    }

    //check
    public void move(GridBlock destination) throws InterruptedException {
        GridBlock temp = currentBlock;
        if (atRobot()) {
            synchronized (robot)
            {
                while (!canGo()) {
                    robot.wait();
                }
                sleep(1000);
            }
        }

        synchronized (temp) {
            synchronized (destination) {
                while (destination.isOccupied()) {
                    destination.wait();
                }
                changeState(currentBlock, destination);
                currentBlock.release();
                destination.occupy(this);
                temp.notifyAll();
            }
        }

    }

    public void changeState(GridBlock start, GridBlock end)
    {
        if (end.getX()-start.getX()==1)
        {
            state = colours[colourIndex][0];
        }
        else if (end.getX()-start.getX()==-1)
        {
            state = colours[colourIndex][3];
        }
        else if (end.getY()- start.getY()==1)
        {
            state = colours[colourIndex][1];
        }
        else
        {
            state = colours[colourIndex][2];
        }
    }

    public GridBlock findNextBlock()
    {
        if (entrance.getX()==4)
        {
            if (currentBlock.getY()!= exit.getY())
            {
                return intersection.getAbove(currentBlock);
            }
            else if (exit.getY()==4)
            {
                return intersection.getRight(currentBlock);
            }
            else
            {
                return intersection.getLeft(currentBlock);
            }
        }
        else if (entrance.getX()==5)
        {
            if (currentBlock.getY()!= exit.getY())
            {
                return intersection.getBelow(currentBlock);
            }
            else if (exit.getY()==4)
            {
                return intersection.getRight(currentBlock);
            }
            else
            {
                return intersection.getLeft(currentBlock);
            }
        }
        else if (entrance.getY()==4)
        {
            if (currentBlock.getX()!= exit.getX())
            {
                return intersection.getRight(currentBlock);
            }
            else if (exit.getX()==4)
            {
                return intersection.getAbove(currentBlock);
            }
            else
            {
                return intersection.getBelow(currentBlock);
            }
        }
        else
        {
            if (currentBlock.getX()!= exit.getX())
            {
                return intersection.getLeft(currentBlock);
            }
            else if (exit.getX()==4)
            {
                return intersection.getAbove(currentBlock);
            }
            else
            {
                return intersection.getBelow(currentBlock);
            }
        }
    }
    public boolean isMyExit(GridBlock other)
    {
        return other == exit;
    }

    public boolean atRobot()
    {
        if(currentBlock.getX()==3&&currentBlock.getY()==4)
        {
            return true;
        }
        if (currentBlock.getX()==6 && currentBlock.getY()==5)
        {
            return true;
        }
        if (currentBlock.getX()==5 && currentBlock.getY()==3)
        {
            return true;
        }
        return currentBlock.getX() == 4 && currentBlock.getY() == 6;
    }

    public boolean canGo()
    {
        if ((entrance.getX()==0||entrance.getX()==9)&& Robot.GO.get()==0 )
        {
            return true;
        }
        return ((entrance.getX() == 4 || entrance.getX() == 5) && Robot.GO.get()==2);
    }

    public void run()
    {
        try {
            this.enterGrid();
            System.out.println(this.getID()+" entered at " + this.entrance);
            System.out.println(this.getID()+" exit is " + this.exit);
            while (!this.isMyExit(currentBlock)) {
                sleep(1000);
                this.move(this.findNextBlock());
            }
            sleep(1000);
            this.exitGrid();
            System.out.println(this.getID()+" exited at " + this.exit);
        }catch(InterruptedException i)
        {

        }
    }

}
