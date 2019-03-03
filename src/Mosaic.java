
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mosaic extends Application {

	public static File[] dirList;
	public static Piece[] pieces;
	public static final int sizeX = 80;
	public static final int sizeY = 60;
	private static final double opacity = 0;


	public static void main(String[] args){


		try{

			File dir = new File(Mosaic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "/res/tiles/jpg");
			dirList = dir.listFiles();

			pieces = new Piece[dirList.length];

			int count = 0;
			for(int i = 0; i < dirList.length; i++){
				Image temp = new Image(dirList[i].toURI().toString());
				pieces[i] = new Piece(temp);
				count++;

			}
			mergeSort(pieces);

		} catch (Exception e){
			System.out.println(e);
		}

		/*for(Piece p : pieces){
			System.out.println(p.getBrightness());
		}*/

		launch(args);
	}

	public void start(Stage theStage){
		//StackPane pane = new StackPane();

		Image img = null;

		try {
			img = new Image(getClass().getResource("/res/image/Color-Wheel-Basics-Full-CMY-color-wheel.jpg").toExternalForm());


		} catch (Exception e) {
			System.out.println("file not found");
		}
		Image[] imageList = new Image[(int) ((img.getWidth()/sizeX)*(img.getHeight()/sizeY))];

		double red = 0; double green = 0; double blue = 0;
		int brightness;
		int index = 0;

		int counter = 0;
		Color temp = null;

		for(int i = 0; i < img.getWidth(); i++){
			for(int j = 0; j < img.getHeight(); j++){
				//every 80x60 
				if(i % sizeX == 0 && j % sizeY == 0 && (i != 0 && j != 0)){
					red = red / (counter);
					green = green / (counter);
					blue = blue / (counter);

					temp = new Color(red, green, blue, opacity);
					brightness = toWebColor(temp.getBrightness());					

					imageList[index] = pieces[search(pieces, temp, brightness)].getImage();
					
					index++;

					//reset
					red = 0;
					green = 0;
					blue = 0;
					counter = 0;

				}
				PixelReader pr = img.getPixelReader();
				temp = pr.getColor(i,j);
				counter++;
				red += temp.getRed();
				green += temp.getGreen();
				blue += temp.getBlue();				
			}
		}
		
		GridPane gp = new GridPane();
		Scene scene = new Scene(gp, 1200, 1200);

		Image tempImage = null;
		
		for(int i = 0; i < img.getWidth()/sizeX ; i++ ){
			for(int j = 0; j < img.getHeight()/sizeY ; j++){

				tempImage = imageList[(int) (i * (img.getHeight()/sizeY) + j)];
				ImageView tile = new ImageView();
				tile.setImage(tempImage);
				gp.add(tile, i, j);
			}
		}

		theStage.setTitle("Mosaic");
		theStage.setScene(scene);
		theStage.show();
	}

	/*
	 * compares the brightness of two colors within the bounds, then
	 * uses ColorCheck if similar brightness
	 */
	public static int search(Piece[] pieces, Color color, int brightness){
		int low = 0;
		int high = pieces.length - 1;
		final int BOUNDS = 10;

		while( high >= low){
			int mid = (low + high) / 2;

			if(Math.abs(pieces[mid].getBrightness() - brightness) <= BOUNDS){
				return colorCheck(pieces, mid, color, BOUNDS);
			}
			if(pieces[mid].getBrightness() < brightness){
				low = mid + 1;
			}
			if(pieces[mid].getBrightness() > brightness){
				high = mid - 1;
			}
		}
		System.out.println("broke");
		return -1;

	}
	/*
	 * compares the int color values and returns if there in the bounds
	 * recurses if the color is not found in the, by modifing the bounds
	 */
	public static int colorCheck(Piece[] pieces,int index, Color c, int bounds){

		int red = toWebColor(c.getRed());
		int green = toWebColor(c.getGreen());
		int blue = toWebColor(c.getBlue());

		final int INCREMENT = 1;
		final int BOUNDS = bounds;

		for(int i = index; i < pieces.length; i++){
			int tempRed = pieces[i].getRed();
			int tempGreen = pieces[i].getGreen();
			int tempBlue = pieces[i].getBlue();

			if(Math.abs(tempRed - red) <= BOUNDS
					&& Math.abs(tempGreen - green) <= BOUNDS
					&& Math.abs(tempBlue - blue) <= BOUNDS){
				return i;
			}

		}
		for(int j = index; j > 0; j--){
			int tempRed = pieces[j].getRed();
			int tempGreen = pieces[j].getGreen();
			int tempBlue = pieces[j].getBlue();

			if(Math.abs(tempRed - red) <= BOUNDS
					&& Math.abs(tempGreen - green) <= BOUNDS
					&& Math.abs(tempBlue - blue) <= BOUNDS){
				return j;
			}

		}
		return colorCheck(pieces,index, c, BOUNDS + INCREMENT);
	}

	/*
	 * merge sort using overwitten compareTo method int Piece 
	 */

	public static void mergeSort(Piece[] list){
		int length = list.length;
		if(length < 2) return;
		int mid = length/2;
		Piece[] leftArr = new Piece[mid];
		Piece[] rightArr = new Piece[length - mid];
		for(int i = 0; i < length; i++){
			if(i < mid) leftArr[i] = list[i];
			else if(i >= mid) rightArr[i - mid] = list[i];
		}

		mergeSort(rightArr);
		mergeSort(leftArr);
		merge(leftArr, rightArr, list);
	}
	public static void merge(Piece[] left, Piece[] right, Piece[] list){
		int leftSize = left.length;
		int rightSize = right.length;

		int indexL = 0;
		int indexR = 0;
		int index = 0;
		while (indexL < leftSize && indexR < rightSize) {
			if (left[indexL].compareTo(right[indexR]) <= 0) {
				list[index++] = left[indexL++];
			} else {
				list[index++] = right[indexR++];
			}
		}
		while (indexL < leftSize) {
			list[index++] = left[indexL++];
		}
		while (indexR < rightSize) {
			list[index++] = right[indexR++];
		}
	}	
	/* changes color values from a range of 0.0 - 1.0 into the range 0 - 255
	 */
	public static int toWebColor(double x){
		return (int)(x * 255);
	}

}
