package hw1.test;

import java.util.Random;

public class YearXYLoc {
    int year;
    int x;
    int y;

//    public YearXYLoc(int year, int x, int y) {
//        this.year = year;
//        this.x = x;
//        this.y = y;
//    }

    public YearXYLoc(Random r, int w, int h) {
        this.year = r.nextInt(Integer.MAX_VALUE);
        this.x = r.nextInt(w);
        this.y = r.nextInt(h);
    }

    public String toString() {
        return year + " " + x + " " + y;
    }
}
