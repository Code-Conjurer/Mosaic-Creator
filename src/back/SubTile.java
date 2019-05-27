package back;

public class SubTile implements Comparable<SubTile> {

    private Tile tile;
    private int quadrantX, quadrantY;
    private double averageRed;
    private double averageGreen;
    private double averageBlue;
    private double averageBrightness;


    public SubTile(Tile tile, int quadrantX, int quadrantY) {
        this.tile = tile;
        this.quadrantX = quadrantX;
        this.quadrantY = quadrantY;

        averageRed = tile.getRedArr()[quadrantX][quadrantY];
        averageGreen = tile.getGreenArr()[quadrantX][quadrantY];
        averageBlue = tile.getBlueArr()[quadrantX][quadrantY];
        averageBrightness = tile.getBrightnessArr()[quadrantX][quadrantY];
    }

    public double getBrightness(){
        return (averageBrightness);
    }
    public double getRed(){
        return  averageRed;
    }
    public double getGreen(){return  averageGreen; }
    public double getBlue(){ return averageBlue; }

    @Override
    public int compareTo(SubTile o) {
        double tempBr = o.getBrightness();
        if (averageBrightness > tempBr) {
            return 1;
        }
        if (averageBrightness < tempBr) {
            return -1;
        }
        return 0;
    }

}
