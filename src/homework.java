import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class homework {

    private static String algoType;
    private static Node initial;
    protected static Node goal;
    private static PriorityQueue<Node> frontier;
    private static HashSet<Node> visited;
    protected static ChannelType[] channels;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("input.txt"))) {
            initialState(in);
            writeSolution(search(), new PrintStream(new File("output.txt")));
            System.err.println(frontier.size());
            System.err.println(visited.size());
        } catch (FileNotFoundException e) {
            System.err.println("Warning: File \"input.txt\" does not exist!");
            System.err.println("The program exit.");
        } catch (NoSuchElementException e) {
            System.err.println("Bad input format!");
            System.err.println("The program exit.");

        } catch (BadInputException e) {
            System.err.println("Bad input format!");
            System.err.println("The program exit.");
            e.printStackTrace();
        }
    }

    private static boolean initialState(Scanner in) throws BadInputException {
        algoType = in.next();
        Node.width = in.nextInt();
        Node.height = in.nextInt();
        initial = new Node(in.nextInt(), in.nextInt(), in.nextInt());
        goal = new Node(in.nextInt(), in.nextInt(), in.nextInt());
        channels = new ChannelType[in.nextInt()];
        for (int i = 0; i < channels.length; i++) {
            int year1 = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            int year2 = in.nextInt();
            channels[i] = new ChannelType(year1, x, y, year2);
        }
        frontier = new PriorityQueue<>();
        visited = new HashSet<>();
        if (in.hasNext()) throw new BadInputException();
        return true;
    }

    private static Node search() throws BadInputException {
        frontier.add(initial);
        HashMap<Node, Integer> frontierCost = new HashMap<>();
        frontierCost.put(initial, initial.currentCost);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            frontierCost.remove(current);

            if (current.equals(goal)) return current;
            visited.add(current);

            for (Node child : current.expand(algoType)) {
                if (frontierCost.containsKey(child)) {
                    if (algoType.equals("BFS")) continue;
                    if (frontierCost.get(child) > child.currentCost) {
                        frontierCost.put(child, child.currentCost);
                        frontier.remove(child);
                        frontier.add(child);
                    }
                    continue;
                }
                // 注意此处，A*还是需要check已经visited的结点，若cost变小了，则应在visited中删掉，并放入frontier中
                // 但本次作业中使用的是consistent的heuristic，所以直接continue也可
                if (visited.contains(child)) continue;
                frontier.add(child);
                frontierCost.put(child, child.currentCost);
            }
        }
        return null;
    }

    private static boolean writeSolution(Node cur, PrintStream out) {
        if (cur == null) {
            out.print("FAIL");
            return true;
        }
        out.println(cur.currentCost);
        ArrayList<Node> solution = new ArrayList<>();
        solution.add(cur);
        while(cur.parent != null) {
            cur = cur.parent;
            solution.add(cur);
        }
        out.println(solution.size());
        int preCost = 0;
        for (int i = solution.size() - 1; i > 0; i--) {
            cur = solution.get(i);
            out.println(cur.fingerPrint + " " + (cur.currentCost - preCost));
            preCost = cur.currentCost;
        }
        cur = solution.get(0);
        out.print(cur.fingerPrint + " " + (cur.currentCost - preCost));
        return true;
    }
}



class BadInputException extends IOException {
    public BadInputException() {}
}

