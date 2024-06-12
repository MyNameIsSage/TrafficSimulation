import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class Window extends JPanel implements Runnable
{
    private Car[] cars;
    private int wIncr;
    private int hIncr;
    Grid grid;


    public Window(Car[] cars, Grid grid)
    {

        this.cars=cars;
        this.grid=grid;
        int width = 500;
        int height = 500;
        wIncr= width/12;
        hIncr= height/12;
    }

    public void paintComponent(Graphics g)
    {
        int width = getWidth();
        int height = getHeight();
        wIncr= width/12;
        hIncr= height/12;
        g.drawImage(grid.map,0,0,this);
        for (int i =0; i< cars.length;i++)
        {
            if(cars[i].onRoad())
            {
                int x = (cars[i].currentBlock.getX()+1)*wIncr;
                int y = (cars[i].currentBlock.getY()+1)*hIncr;
                g.drawImage(cars[i].state,x+wIncr/4,y+hIncr/4,this);
            }

        }


    }

    @Override
    public void run() {
        while(true)
        {
            this.repaint();
        }
    }
}
