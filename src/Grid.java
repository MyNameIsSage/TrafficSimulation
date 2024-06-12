import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grid
{
    static BufferedImage map;
    GridBlock[][] blocks;
    private final GridBlock[] entrances;
    private final GridBlock[] exits;

    public Grid() {
        blocks = new GridBlock[10][10];
        for(int i = 0; i<10; i++)
        {
            for (int j = 0; j<10;j++)
            {
                blocks[i][j]= new GridBlock(i,j);
            }
        }
        entrances = createEntrances();
        exits = createExits();
        try {
            map = ImageIO.read(new File("images/intersection.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public GridBlock[] createEntrances()
    {
        GridBlock[] temp = new GridBlock[4];
        temp[0]= blocks[0][4];
        temp[1]= blocks[5][0];
        temp[2]= blocks[4][9];
        temp[3]= blocks[9][5];
        return temp;
    }

    public GridBlock[] createExits()
    {
        GridBlock[] temp = new GridBlock[4];
        temp[0]= blocks[0][5];
        temp[1]= blocks[4][0];
        temp[2]= blocks[5][9];
        temp[3]= blocks[9][4];
        return temp;
    }

    public GridBlock[] getEntrances()
    {
        return entrances;
    }

    public GridBlock[] getExits()
    {
        return exits;
    }


    public GridBlock getRight(GridBlock gridBlock)
    {
        return blocks[gridBlock.getX()+1][gridBlock.getY()];
    }
    public GridBlock getLeft(GridBlock gridBlock)
    {
        return blocks[gridBlock.getX()-1][gridBlock.getY()];
    }
    public GridBlock getAbove(GridBlock gridBlock)
    {
        return blocks[gridBlock.getX()][gridBlock.getY()-1];
    }
    public GridBlock getBelow(GridBlock gridBlock)
    {
        return blocks[gridBlock.getX()][gridBlock.getY()+1];
    }
}