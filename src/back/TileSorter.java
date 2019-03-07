package back;

public class TileSorter {

    private TileSorter(){}

    public static void sort(Piece[] list){
        mergeSort(list);
    }

    private static void mergeSort(Piece[] list){
        /*
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
        */
    }

    private static void merge(Piece[] left, Piece[] right, Piece[] list){
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
}
