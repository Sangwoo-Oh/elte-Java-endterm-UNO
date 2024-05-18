package uno.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class InputSource {
    public final boolean isInteractive;
    private BufferedReader br;
    private int[] inputs;
    private int inputIdx;
    public static final int DONE = -1;

    public InputSource(boolean isInteractive, int... inputs) {
        this.isInteractive = isInteractive;
        if (this.isInteractive) {
            br = new BufferedReader(new InputStreamReader(System.in));
        } else {
            this.inputs = new int[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                this.inputs[i] = inputs[i];
            }
        }
    }

    public int getNextInput() throws IOException {
        if (isInteractive) {
            String s = br.readLine();
            if (s.equals("done")) return DONE;
            return Integer.parseInt(s) - 1;
        } else {
            if (inputIdx == inputs.length) return DONE;
            return inputs[inputIdx++] - 1;
        }
    }
}

