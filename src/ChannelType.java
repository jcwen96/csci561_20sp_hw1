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

    public boolean canJaunt(Node n) {
        if (this.x != n.x) return false;
        if (this.y != n.y) return false;
        return this.year1 == n.year || this.year2 == n.year;
    }

    public Integer jaunt(Node start) {
        if (!canJaunt(start)) return null;
        return start.year == this.year1 ? this.year2 : this.year1;
    }

    public String toString() {
        return year1 + " " + x + " " + y + " " + year2;
    }
}