

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


/*
 * Converted Buffered Image to Image:
 * -to revert back change Color import
 *  to awt (from javafx)
 *  
 * -uncomment all commented lines, and remove* redundant lines 
 */
public class Piece implements Comparable<Piece>{

	private Color avgColor;
	private double width, height;
	private Image image;
	private double red, green, blue;
	private double brightness;
	
	private final double opacity = 0;

	public Piece(Image image){
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		double averageRed = 0;
		double averageGreen = 0;
		double averageBlue = 0;

		Color[][] array = new Color[(int) width][(int) height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				PixelReader pr = image.getPixelReader();
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
		avgColor = new Color(averageRed, averageGreen, averageBlue, opacity);
		red = averageRed;
		green = averageGreen;
		blue = averageBlue;
		brightness = avgColor.getBrightness();

	}
	
	public Color getAvgColor(){
		return avgColor;
	}

	public double getBrightness(){
		return (brightness);
	}
	public double getRed(){
		return  (red);
	}
	public double getGreen(){
		return  (green);
	}
	public double getBlue(){
		return (blue);
	}
	public Image getImage(){
		return image;
	}

	@Override
	public int compareTo(Piece o){
		double tempBr = o.getBrightness();
		if(brightness > tempBr){
			return 1;
		}
		if(brightness < tempBr){
			return -1;
		}
		return 0;

		
	}
}
