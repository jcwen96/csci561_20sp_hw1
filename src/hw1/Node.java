package hw1;

import java.util.ArrayList;

class Node implements Comparable<Node> {

    protected static int width;
    protected static int height;

    protected int year;
    protected int x;
    protected int y;
    protected String fingerPrint;
    protected int currentCost;
    protected int futureCost;
    protected Node parent;

    public Node(int year, int x, int y) {
        this.year = year;
        this.x = x;
        this.y = y;
        fingerPrint = year + ";" + x + ";" + y;
    }

    public Node(int year, int x, int y, int currentCost, Node parent) {
        this(year, x, y);
        this.currentCost = currentCost;
        this.parent = parent;
    }

    public Node(int year, int x, int y, int currentCost, int futureCost, Node parent) {
        this(year, x, y, currentCost, parent);
        this.futureCost = futureCost;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Node)) return false;
        return this.fingerPrint.equals(((Node) obj).fingerPrint);
    }

    public String toString() {
        return "Node: [year, x, y: " + fingerPrint + "; currentCost: " + currentCost + ", futureCost: " + futureCost + "]";
    }

    public int hashCode() {
        return fingerPrint.hashCode();
    }


    public int compareTo(Node n) {
        return (this.currentCost + this.futureCost) - (n.currentCost + n.futureCost);
    }

    public ArrayList<Node> expand(String algoType) throws BadInputException{
        ArrayList<Node> result = new ArrayList<>();

        if (this.x > 0) result.add(
                new Node(this.year, this.x - 1, this.y,
                        this.currentCost + oneMoveCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x - 1, this.y),this)); // move west
        if (this.x < width - 1) result.add(
                new Node(this.year, this.x + 1, this.y,
                        this.currentCost + oneMoveCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x + 1, this.y),this)); // move east
        if (this.y > 0) result.add(
                new Node(this.year, this.x, this.y - 1,
                        this.currentCost + oneMoveCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x, this.y - 1),this)); // move south
        if (this.y < height - 1) result.add(
                new Node(this.year, this.x, this.y + 1,
                        this.currentCost + oneMoveCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x, this.y + 1),this)); // move north
        if (this.x > 0 && this.y > 0) result.add(
                new Node(this.year, this.x - 1, this.y - 1,
                        this.currentCost + diagonalCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x - 1, this.y - 1), this)); // move southwest
        if (this.x > 0 && this.y < height - 1) result.add(
                new Node(this.year, this.x - 1, this.y + 1,
                        this.currentCost + diagonalCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x - 1, this.y + 1), this)); // move northwest
        if (this.x < width - 1 && this.y > 0) result.add(
                new Node(this.year, this.x + 1, this.y - 1,
                        this.currentCost + diagonalCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x + 1, this.y - 1),this)); // move southeast
        if (this.x < width - 1 && this.y < height - 1) result.add(
                new Node(this.year, this.x + 1, this.y + 1,
                        this.currentCost + diagonalCost(algoType),
                        futureCostEstimate(algoType, this.year, this.x + 1, this.y + 1),this)); // move northeast

        for (ChannelType channel : homework.channels) {
            Integer newYear = channel.jaunt(this);
            if (newYear != null)
                result.add(new Node(newYear, this.x, this.y,
                        this.currentCost + jauntCost(algoType, this.year, newYear),
                        futureCostEstimate(algoType, newYear, this.x, this.y) ,this));
        }

        return result;
    }

    private int oneMoveCost(String algoType) throws BadInputException {
        if (algoType.equals("BFS")) return 1;
        if (algoType.equals("UCS") || algoType.equals("A*")) return 10;
        throw new BadInputException();
    }

    private int diagonalCost(String algoType) throws BadInputException {
        if (algoType.equals("BFS")) return 1;
        if (algoType.equals("UCS") || algoType.equals("A*")) return 14;
        throw new BadInputException();
    }

    private int jauntCost(String algoType, int fromYear, int toYear) throws BadInputException{
        if (algoType.equals("BFS")) return 1;
        if (algoType.equals("UCS") || algoType.equals("A*")) return Math.abs(fromYear - toYear);
        throw new BadInputException();
    }

    private int futureCostEstimate(String algoType, int year, int x, int y) throws BadInputException{
        if (algoType.equals("BFS") || algoType.equals("UCS")) return 0;
        if (algoType.equals("A*")) {
            return Math.abs(homework.goal.year - year) + (int) Math.hypot(10 * (homework.goal.x - x), 10 * (homework.goal.y - y));
        }
        throw new BadInputException();
    }
}