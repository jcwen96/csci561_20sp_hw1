package hw1;

import java.util.Random;

public class ChannelType {
    int year1;
    int year2;
    int x;
    int y;

    public ChannelType(int year1, int x, int y, int year2) {
        this.year1 = year1;
        this.x = x;
        this.y = y;
        this.year2 = year2;
    }

    public ChannelType(Random r, int w, int h) {
        this.year1 = r.nextInt(Integer.MAX_VALUE);
        this.x = r.nextInt(w);
        this.y = r.nextInt(h);
        this.year2 = r.nextInt(Integer.MAX_VALUE);
    }

    public boolean canJaunt(Node n) {
        if (this.x != n.x) return false;
        if (this.y != n.y) return false;
        if (this.year1 != n.year && this.year2 != n.year) return false;
        return true;
    }

    public Integer jaunt(Node start) {
        if (!canJaunt(start)) return null;
        return start.year == this.year1 ? this.year2 : this.year1;
    }

    public String toString() {
        return year1 + " " + x + " " + y + " " + year2;
    }
}