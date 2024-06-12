import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Simulation
{
    static int numberCars = 400;
    static Window window;
    static Car[] cars;
    static Grid grid;



    public Simulation()
    {}
    public static void setupGUI()
    {
        JFrame frame = new JFrame("Traffic Simulation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(516,544);
        JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g,3));
        g.setSize(Grid.map.getWidth(),Grid.map.getHeight());
        window = new Window(cars,grid);
        g.add(window);
        frame.setLocationRelativeTo((Component)null);
        frame.add(g);
        frame.setContentPane(g);
        frame.setVisible(true);
    }
    public static void main(String[] args)
    {
        grid = new Grid();
        Car.intersection = grid;
        Car.robot = new Robot();
        cars = new Car[numberCars];
        try{    //do this in main to avoid multiple times
            Car.colours[0][0]= ImageIO.read(new File("images/blueCar0.png"));
            Car.colours[0][1]= ImageIO.read(new File("images/blueCar1.png"));
            Car.colours[0][2]= ImageIO.read(new File("images/blueCar2.png"));
            Car.colours[0][3]= ImageIO.read(new File("images/blueCar3.png"));
            Car.colours[1][0]= ImageIO.read(new File("images/greenCar0.png"));
            Car.colours[1][1]= ImageIO.read(new File("images/greenCar1.png"));
            Car.colours[1][2]= ImageIO.read(new File("images/greenCar2.png"));
            Car.colours[1][3]= ImageIO.read(new File("images/greenCar3.png"));
            Car.colours[2][0]= ImageIO.read(new File("images/redCar0.png"));
            Car.colours[2][1]= ImageIO.read(new File("images/redCar1.png"));
            Car.colours[2][2]= ImageIO.read(new File("images/redCar2.png"));
            Car.colours[2][3]= ImageIO.read(new File("images/redCar3.png"));
            Car.colours[3][0]= ImageIO.read(new File("images/yellowCar0.png"));
            Car.colours[3][1]= ImageIO.read(new File("images/yellowCar1.png"));
            Car.colours[3][2]= ImageIO.read(new File("images/yellowCar2.png"));
            Car.colours[3][3]= ImageIO.read(new File("images/yellowCar3.png"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        setupGUI();
        for (int i = 0; i<numberCars;i++)
        {
            cars[i]=new Car(i);
        }
        Thread t = new Thread(window);
        t.start();
        for (int i = 0; i<numberCars;i++)
        {
            cars[i].start();

        }
        Car.robot.start();
    }
}
