package rs;

import java.util.Arrays;
import java.util.HashMap;

public class RS6 extends RS5 {
    public RS6() {
        super();
    }

    public int[] correctMsg(int[] msg, int[] ePos, int[] err) {
        // HashMap<Integer, Integer> expToIdx = new HashMap<>();
        // expToIdx.put(Integer.parseInt("0", 16), 0);
        // expToIdx.put(Integer.parseInt("1", 16), 1);
        // expToIdx.put(Integer.parseInt("2", 16), 2);
        // expToIdx.put(Integer.parseInt("3", 16), 3);
        // expToIdx.put(Integer.parseInt("4", 16), 4);
        // expToIdx.put(Integer.parseInt("5", 16), 5);
        // expToIdx.put(Integer.parseInt("6", 16), 6);
        // expToIdx.put(Integer.parseInt("7", 16), 7);
        // expToIdx.put(Integer.parseInt("8", 16), 8);
        // expToIdx.put(Integer.parseInt("9", 16), 9);
        // expToIdx.put(Integer.parseInt("a", 16), 10);
        // expToIdx.put(Integer.parseInt("b", 16), 11);
        // expToIdx.put(Integer.parseInt("c", 16), 12);
        // expToIdx.put(Integer.parseInt("d", 16), 13);
        // expToIdx.put(Integer.parseInt("e", 16), 14);
        // expToIdx.put(Integer.parseInt("f", 16), 15);
        // expToIdx.put(Integer.parseInt("10", 16), 16);
        // expToIdx.put(Integer.parseInt("11", 16), 17);
        // expToIdx.put(Integer.parseInt("12", 16), 18);
        // expToIdx.put(Integer.parseInt("12", 16), 19);

        // System.out.println(Arrays.toString(msg));
        // System.out.println(Arrays.toString(ePos));
        // System.out.println(Arrays.toString(err));

        for (int i = 0; i < ePos.length; i++) {
            int idx = 19 - ePos[i];
            // System.out.println("idx = " + idx);
            // System.out.println("msg[idx] = " + msg[idx]);
            // msg[19 - i] ^= err[i];
            msg[idx] = this.sub(msg[idx], err[i]);
            // System.out.println("msg'[idx] = " + msg[idx]);
        }
        return msg;
    }
}
