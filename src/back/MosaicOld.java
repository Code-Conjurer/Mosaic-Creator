package back;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MosaicOld extends Application {

	static File[] dirList;
	static Piece[] pieces;
	static int sizeX = 10; //80
	static int sizeY = 10; //60
	private static final double opacity = 0;

	int sceneHeight = 800;
	int sceneWidth = 600;
	int tileSizeX =sizeX;
	int tileSizeY =sizeY;

	public static void main(String[] args){


		try{

			File dir = new File(MosaicOld.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "/res/tiles/jpg");
			dirList = dir.listFiles();

		pieces = new Piece[dirList.length];
		Image temp;
		for(int i = 0; i < dirList.length; i++){
			temp = new Image(dirList[i].toURI().toString());
			pieces[i] = new Piece(temp);
		}

		mergeSort(pieces);

	} catch (Exception e){
		System.out.println(e);
	}
		launch(args);
	}

	public void start(Stage theStage){
		Image img = null;
		try {
			img = new Image(getClass().getResource("/res/image/Trees.jpg").toExternalForm());


		} catch (Exception e) {
			System.out.println("file not found");
		}

		Image[][] imageList = new Image[(int)(img.getWidth()/sizeX)][(int)(img.getHeight()/sizeY)];
		double red = 0; double green = 0; double blue = 0;
		double brightness;
		int index = 0;

		int pixelsCounted = 0;
		Color temp = null;

		long start = System.currentTimeMillis();

        PixelReader pr = img.getPixelReader();
        int numberOfTilesX = ((int)img.getWidth()) / tileSizeX;
        int numberOfTilesY = ((int)img.getHeight()) / tileSizeY;


        Thread[] tileBuilders = new TileBuilder[numberOfTilesX * numberOfTilesY];

        //int index;
        for (int x = 0; x < numberOfTilesX; x++) {
            for (int y = 0; y < numberOfTilesY; y++) {
                index = (numberOfTilesX * x) + y;

                tileBuilders[index] = new TileBuilder(
                        x * tileSizeX, y * tileSizeY, tileSizeX, tileSizeY,
                        pieces, imageList, pr);

                tileBuilders[index].start();
            }
        }
        for(Thread t : tileBuilders) {
            try {
                t.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
		/*for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){

				//every 80x60
				if(y % sizeY == 0 && x % sizeX == 0  && (x != 0 && y != 0)){
					red = red / (pixelsCounted);
					green = green / (pixelsCounted);
                    blue = blue / (pixelsCounted);


					temp = new Color(red, green, blue, opacity);
					brightness = (temp.getBrightness());

					//imageList[index] = pieces[search(pieces, temp, brightness)].getImage();
					imageList[(x/sizeX) - 1][(y/sizeY) - 1] = pieces[search(pieces, temp, brightness)].getImage();

					index++;

					//reset
					red = 0;
					green = 0;
					blue = 0;
					pixelsCounted = 0;

				}
				PixelReader pr = img.getPixelReader();
				temp = pr.getColor(x,y);
				pixelsCounted++;
				red += temp.getRed();
				green += temp.getGreen();
				blue += temp.getBlue();
			}
		}*/
        long end = System.currentTimeMillis();
        System.out.println((end-start));


		GridPane gp = new GridPane();
		Scene scene = new Scene(gp, sceneWidth, sceneHeight);

		int imagePixelsX = ((int)img.getWidth());
		int imagePixelsY = ((int)img.getHeight());
		WritableImage mosaic = new WritableImage(imagePixelsX, imagePixelsY);
		PixelWriter pixelWriter = mosaic.getPixelWriter();
		PixelReader pixelReader;
		//ImageView tile = new ImageView();
		Image tile;
		for(int x = 0; x < imagePixelsX/sizeX - 1; x++ ){
			for(int y = 0; y < imagePixelsY/sizeY - 1; y++){
				tile = ImageTransformer.reduce(imageList[x][y], tileSizeX, tileSizeY);
				pixelReader = tile.getPixelReader();
				pixelWriter.setPixels(x*sizeX, y*sizeY, tileSizeX, tileSizeY, pixelReader, 0, 0);

				/*ImageView tile = new ImageView();
				tile.setImage(imageList[x][y]);
				tile.setFitHeight(tileSizeY);
				tile.setFitWidth(tileSizeX);
				gp.add(tile, x, y);*/
			}
		}

		ImageView imageView = new ImageView(mosaic);
		imageView.setFitWidth(imagePixelsX);
		imageView.setFitHeight(imagePixelsY);

		gp.add(imageView, 0,0);
		theStage.setTitle("MosaicOld");
		theStage.setScene(scene);
		theStage.show();

	}

	/*
	 * compares the brightness of two colors within the bounds, then
	 * uses ColorCheck if similar brightness
	 */
	public static int search(Piece[] pieces, Color color, double brightness){
		int low = 0;
		int high = pieces.length - 1;
		double MARGIN_OF_ERROR = 10.0/256.0; //Currently chosen arbitrarily

		while( high >= low){
			int mid = (low + high) / 2;

			if(Math.abs(pieces[mid].getBrightness() - brightness) <= MARGIN_OF_ERROR){
				return colorCheck(pieces, mid, color, MARGIN_OF_ERROR);
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
	 * compares the color values and returns if there in the marginOfError
	 * recurse if the color is not found in the, by modifying the marginOfError
	 */
	public static int colorCheck(Piece[] pieces,int index, Color c, double marginOfError){

		double red = (c.getRed());
		double green = (c.getGreen());
		double blue = (c.getBlue());

		for(int i = index; i < pieces.length; i++){
			double tempRed = pieces[i].getRed();
			double tempGreen = pieces[i].getGreen();
			double tempBlue = pieces[i].getBlue();

			if(Math.abs(tempRed - red) <= marginOfError
					&& Math.abs(tempGreen - green) <= marginOfError
					&& Math.abs(tempBlue - blue) <= marginOfError){
				return i;
			}

		}
		for(int j = index; j > 0; j--){
			double tempRed = pieces[j].getRed();
			double tempGreen = pieces[j].getGreen();
			double tempBlue = pieces[j].getBlue();

			if(Math.abs(tempRed - red) <= marginOfError
					&& Math.abs(tempGreen - green) <= marginOfError
					&& Math.abs(tempBlue - blue) <= marginOfError){
				return j;
			}

		}
		return colorCheck(pieces,index, c, marginOfError + (marginOfError / 10)); //TODO: define the increment for the marginOfError
	}

	/*
	 * merge sort using overwritten compareTo method int Piece
	 */
	public static void mergeSort(Piece[] list){
		int length = list.length;
		if(length < 2)
			return;
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

    /*private void initializeMosaicArr(Image img, Image[][] mosaicArr, int imageWidth, int imageHeight) {
        PixelReader pr = img.getPixelReader();
        int numberOfTilesX = imageWidth / tileSizeX;
        int numberOfTilesY = imageHeight / tileSizeY;


        Thread[] tileBuilders = new TileBuilder[numberOfTilesX * numberOfTilesY];

        int index;
        for (int x = 0; x < numberOfTilesX; x++) {
            for (int y = 0; y < numberOfTilesY; y++) {
                index = (numberOfTilesX * x) + y;

                tileBuilders[index] = new TileBuilder(
                        x * tileSizeX, y * tileSizeY, tileSizeX, tileSizeY,
                        pieces, mosaicArr, pr);

                tileBuilders[index].start();
            }
        }
    }*/

}
