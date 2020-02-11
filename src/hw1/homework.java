package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

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
            //writeSolution(search(), System.out);
            System.out.println(frontier.size());
            System.out.println(visited.size());
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
        HashMap<Node, Integer> map = new HashMap<>();
        map.put(initial, initial.currentCost);

        Node test = new Node(1242, 246, 289);
        int count = 0;

//        while (!map.isEmpty()) {
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            map.remove(current);

            if (current.equals(goal)) return current;

            visited.add(current);

            for (Node new_node : current.expand(algoType)) {

//                if (current.equals(test)) {
//                    count++;
//                    System.out.println("I am here!!! Here is test: " + test);
//                    System.out.println("I am here!!! Here is current" + current);
//                    System.out.println(count);
//                }

                if (map.containsKey(new_node)) {

                    if (map.get(new_node) > new_node.currentCost) {
                        map.put(new_node, new_node.currentCost);
                        frontier.remove(new_node);

                        if (new_node.equals(test)) {
                            System.out.println(frontier.contains(new_node));
                        }


                        frontier.add(new_node);
                    }
                    // slower version
//                    for (Node n : frontier) {
//                        if (n.equals(new_node) && n.currentCost > new_node.currentCost) {
//
//                            frontier.remove(n);
//                            if (frontier.contains(n)) {
//                                System.out.println("!!");
//                            }
//                            frontier.add(new_node);
//                            break;
//                        }
//                    }
//                    if (visited.contains(new_node)) {
//                        System.out.println(new_node + " is both in frontier and visited. Thinking wrong!!!");
//                        System.err.println(new_node.hashCode());
//                    }
                    continue;
                }
                if (visited.contains(new_node)) continue;

                frontier.add(new_node);
                map.put(new_node, new_node.currentCost);
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
            out.println(cur.year + " " + cur.x + " " + cur.y + " " + (cur.currentCost - preCost));
            preCost = cur.currentCost;
        }
        cur = solution.get(0);
        out.print(cur.year + " " + cur.x + " " + cur.y + " " + (cur.currentCost - preCost));
        return true;
    }
}



class BadInputException extends IOException {
    public BadInputException() {}
}

