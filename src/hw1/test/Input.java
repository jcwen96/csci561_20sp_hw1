package hw1.test;

import hw1.ChannelType;

import java.io.PrintStream;
import java.util.Random;

public class Input {
    String algoType;
    int w;
    int h;
    YearXYLoc initial;
    YearXYLoc goal;
    int numChannel;
    ChannelType[] channels;

    public Input(String algoType, Random r) {
        this.algoType = algoType;
        w = r.nextInt(200) + 1;
        h = r.nextInt(200) + 1;
        initial = new YearXYLoc(r, w, h);
        goal = new YearXYLoc(r, w, h);
        numChannel = r.nextInt(20);
        channels = new ChannelType[numChannel];
        for (int i = 0; i < numChannel; i++) {
            channels[i] = new ChannelType(r, w, h);
        }
    }

    public void print(PrintStream out) {
        out.println(algoType);
        out.println(w + " " + h);
        out.println(initial);
        out.println(goal);
        out.println(numChannel);
        for (ChannelType c : channels) {
            out.println(c);
        }
    }
}
