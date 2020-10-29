import java.util.List;
import java.util.ArrayList;

public class combinatorics{
    public static void main(String[] args) {

        List<int[]> list = new ArrayList<int[]>();
        int[] arr = {1,2,3,4,5,6,7};
        combinations3(arr,list);
        listToString(list);
    }

    static void combinations3(int[] arr, List<int[]> list){
        for(int i = 0; i<arr.length-2; i++)
            for(int j = i+1; j<arr.length-1; j++)
                for(int k = j+1; k<arr.length; k++)
                    list.add(new int[]{arr[i],arr[j],arr[k]});
    }

    private static void listToString(List<int[]> list){
        for(int i = 0; i<list.size(); i++){ //iterate through list
            int[] arr=list.get(i);
            for(int j=0;j<arr.length;j++ ){ //iterate through array
                System.out.printf("%d ",arr[j]);
            }
        System.out.print("\n");
        }
    }
}