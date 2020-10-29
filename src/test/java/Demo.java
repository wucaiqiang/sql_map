import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:TODO
 *
 * @author caiqiang.wu
 * @create 2020/03/21
 **/
public class Demo {
    public void test01(){
        Map<String,String> map=new HashMap<>();
        map.put("1","1");
        map.put("2","2");
    }
    @Test
    public void test() {
        int[] input = {1, 2, 3, 4, 5,6,7,8,9,10};    // input array
        int k = 5;                             // sequence length

        List<int[]> subsets = new ArrayList<>();

        int[] s = new int[k];                  // here we'll keep indices
        // pointing to elements in input array

        if (k <= input.length) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (s[i] = i) < k - 1; i++) ;
            subsets.add(getSubset(input, s));
            for (; ; ) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--);
                if (i < 0) {
                    break;
                }
                s[i]++;                    // increment this item
                for (++i; i < k; i++) {    // fill up remaining items
                    s[i] = s[i - 1] + 1;
                }
                subsets.add(getSubset(input, s));
            }
        }
        subsets.forEach(ints -> {
            for(int i=0;i<ints.length;i++){
                System.out.print(ints[i]+",");
            }
            System.out.print("\n");
        });
    }

    public int[] getSubset(int[] input, int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            result[i] = input[subset[i]];
        return result;
    }
}
