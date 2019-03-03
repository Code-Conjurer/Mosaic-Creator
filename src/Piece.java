

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
	private Color greyScale;
	//private int avgVal;
	private String mainColor;
	//private int width, height;
	private double width, height;
	//private BufferedImage image;
	private Image image;
	//private int red, green, blue, grey;
	private double red, green, blue;
	private double brightness;
	
	private final double opacity = 0;
	
	//public Piece(BufferedImage image){
	public Piece(Image image){
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		
		Color[][] array = new Color[(int) width][(int) height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//array[i][j] = new Color(image.getRGB(i,j));
				PixelReader pr = image.getPixelReader();
				array[i][j] = pr.getColor(i,j);
			}
		}
		
		for(Color[] s : array){
			for(Color p : s){
				red += p.getRed();
				green += p.getGreen();
				blue += p.getBlue();
			}
		}
		red = red/(width*height);
		green = green/(width*height);
		blue = blue/(width*height);
		//grey = ((int)(red * 0.21 + green * 0.72 + blue * 0.07));
		//grey = (red * 0.21 + green * 0.72 + blue * 0.07);
		avgColor = new Color(red, green, blue, opacity);
		brightness = avgColor.getBrightness();
		//avgVal = avgColor.getRGB();
		//greyScale = new Color(grey, grey, grey, opacity);
	}
	
	public Color getAvgColor(){
		return avgColor;
	}
	/*public int avgBright(){
		return (avgColor.getRed() + avgColor.getGreen() + avgColor.getBlue())/3; 
	}*/
	
	/*public Color getGrey(){
		return avgColor.grayscale();
	}*/
	public int getBrightness(){
		return (int) (brightness * 255);
	}
	public int getRed(){
		return  (int) (red * 255);
	}
	public int getGreen(){
		return  (int) (green * 255);
	}
	public int getBlue(){
		return (int) (blue * 255);
	}
	public Image getImage(){
		return image;
	}
	/*public Color getGreyScale(){
		return greyScale;
	}
	public int getGrey(){
		return grey;
	}
	public int getRed(){
		return red;
	}
	public int getGreen(){
		return green;
	}
	public int getBlue(){
		return blue;
	}
	public BufferedImage getImage(){
		return image;
	}*/
	
	@Override
	public int compareTo(Piece o){
		int tempBr = o.getBrightness();
		if((int)(brightness * 255) > tempBr){
			return 1;
		}
		if((int)(brightness * 255) < tempBr){
			return -1;
		}
		return 0;
		/*
		if(this.avgBright() > o.avgBright()){
			return 1;
		}
		if(this.avgBright() < o.avgBright()){
			return -1;
		}
		return 0;
		*/
		
	}
}
