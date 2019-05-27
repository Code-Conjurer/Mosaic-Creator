package back;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


public class Tile implements Comparable<Tile>{

	//private Color avgColor;
	//private double width, height;
	private Image image;
	private double[][] red;
	private double[][] green;
	private double[][] blue;
    private double averageRed;
    private double averageGreen;
    private double averageBlue;
    private double averageBrightness;
	private double[][] brightness;
	
	private final double opacity = 0;

	public Tile(Image image, int numberOfQuadrantsSquared){
		this.image = image;
		//width = image.getWidth();
		//height = image.getHeight();

		if(numberOfQuadrantsSquared <= 1)
		    simpleInit();
		quadrantInit(numberOfQuadrantsSquared);
	}

	public Tile(Image image){
		this.image = image;
		//width = image.getWidth();
		//height = image.getHeight();
		simpleInit();
	}

	private void quadrantInit(int n){
        red = new double[n][n];
        green = new double[n][n];
        blue = new double[n][n];


    }

    private double[] quadrantAverage(int xOffset, int yOffset, int width, int height, PixelReader pr){
        double tempRed = 0;
        double tempGreen = 0;
        double tempBlue = 0;
        double tempBrightness;
        double[] returnArr = new double[4];
        Color[][] array = new Color[width][height];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                array[x][y] = pr.getColor(x + xOffset,y + yOffset);
            }
        }

        for(Color[] s : array){
            for(Color p : s){
                tempRed += p.getRed();
                tempGreen += p.getGreen();
                tempBlue += p.getBlue();
            }
        }

        tempRed /= width*height;
        tempGreen /= width*height;
        tempBlue /= width*height;
        tempBrightness = Color.color(tempRed,tempGreen,tempBlue).getBrightness();

        returnArr[0] = tempRed;
        returnArr[1] = tempGreen;
        returnArr[2] = tempBlue;
        returnArr[3] = tempBrightness;

        return returnArr;
    }

	private void simpleInit(){
        red = new double[1][1];
        green = new double[1][1];
        blue = new double[1][1];
        brightness = new double[1][1];

        PixelReader pr = image.getPixelReader();
        double[] tempArr = quadrantAverage(0, 0,(int) image.getWidth(),(int) image.getHeight(), pr);

        red[0][0] = tempArr[0];
        green[0][0] = tempArr[1];
        blue[0][0] = tempArr[2];
        brightness[0][0] = tempArr[3];

        averageRed = red[0][0];
        averageGreen = green[0][0];
        averageBlue = blue[0][0];
        averageBrightness = brightness[0][0];
        /*
        Color[][] array = new Color[(int) width][(int) height];
        PixelReader pr = image.getPixelReader();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                array[i][j] = pr.getColor(i,j);
            }
        }

        for(Color[] s : array){
            for(Color p : s){
                averageRed += p.getRed();
                averageGreen += p.getGreen();
                averageBlue += p.getBlue();
            }
        }

        averageRed = averageRed/(width*height);
        averageGreen = averageGreen/(width*height);
        averageBlue = averageBlue/(width*height);
        //avgColor = new Color(averageRed, averageGreen, averageBlue, opacity);
        red[0][0] = averageRed;
        green[0][0] = averageGreen;
        blue[0][0] = averageBlue;
        brightness = avgColor.getBrightness();
        */
    }

	public double getBrightness(){
		return (averageBrightness);
	}
    public double getRed(){
        return  averageRed;
    }
    public double getGreen(){return  averageGreen; }
    public double getBlue(){ return averageBlue; }
    public double[][] getBrightnessArr(){
        return (brightness);
    }
	public double[][] getRedArr(){
		return  (red);
	}
	public double[][] getGreenArr(){
		return  (green);
	}
	public double[][] getBlueArr(){
		return (blue);
	}
	public Image getImage(){
		return image;
	}

	@Override
	public int compareTo(Tile o){
		double tempBr = o.getBrightness();
		if(averageBrightness > tempBr){
			return 1;
		}
		if(averageBrightness < tempBr){
			return -1;
		}
		return 0;
	}
}
