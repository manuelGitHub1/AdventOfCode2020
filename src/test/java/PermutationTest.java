import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PermutationTest {

    @Test
    public void testThisMethod() {

        Integer[] hallo = new Integer[]{5, 6, 11};
        Set<Integer[]> result = PermutationTest.getPermutationsRecursive(hallo);
        System.out.println(result.size());
        result.stream().forEach(a -> System.out.println(Arrays.asList(a).toString()));
    }

    public static Set<Integer[]> getPermutationsRecursive(Integer[] num) {
        if (num == null)
            return null;

        Set<Integer[]> perms = new HashSet<>();

        //base case
        if (num.length == 0) {
            perms.add(new Integer[0]);
            return perms;
        }

        //shave off first int then get sub perms on remaining ints.
        //...then insert the first into each position of each sub perm..recurse

        int first = num[0];
        Integer[] remainder = Arrays.copyOfRange(num, 1, num.length);
        Set<Integer[]> subPerms = getPermutationsRecursive(remainder);
        for (Integer[] subPerm : subPerms) {
            for (int i = 0; i <= subPerm.length; ++i) { // '<='   IMPORTANT !!!
                Integer[] newPerm = Arrays.copyOf(subPerm, subPerm.length + 1);
                if (newPerm.length - 1 - i >= 0) System.arraycopy(newPerm, i, newPerm, i + 1, newPerm.length - 1 - i);
                newPerm[i] = first;
                perms.add(newPerm);
            }
        }

        return perms;
    }
}
