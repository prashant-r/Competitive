import java.io.*;
import java.util.*;
import java.math.*;
import java.util.regex.Pattern;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.Socket;
// import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Solution {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

    /**
     * Computes the shortest path between the source vertex <tt>s</tt>
     * and every other vertex in the graph <tt>G</tt>.
     * @param G the graph
     * @param s the source vertex
     */
    public Solution(Graph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);

        assert check(G, s);
    }

    /**
     * Computes the shortest path between any one of the source vertices in <tt>sources</tt>
     * and every other vertex in graph <tt>G</tt>.
     * @param G the graph
     * @param sources the source vertices
     */
    public Solution(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        bfs(G, sources);
    }


    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    // breadth-first search from multiple sources
    private void bfs(Graph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex <tt>s</tt> (or sources) and vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a path, and <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex <tt>s</tt>
     * (or sources) and vertex <tt>v</tt>?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns a shortest path between the source vertex <tt>s</tt> (or sources)
     * and <tt>v</tt>, or <tt>null</tt> if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }


    // check optimality conditions for single source
    private boolean check(Graph G, int s) {

        // check that the distance of s = 0
        if (distTo[s] != 0) {
            StdOut.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }

        // check that for each edge v-w dist[w] <= dist[v] + 1
        // provided v is reachable from s
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("distTo[" + v + "] = " + distTo[v]);
                    StdOut.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
        // provided v is reachable from s
        for (int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                StdOut.println("shortest path edge " + v + "-" + w);
                StdOut.println("distTo[" + v + "] = " + distTo[v]);
                StdOut.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    /**
     * Unit tests the <tt>Solution</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        Solution bfs = new Solution(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
    }














//***********************************

    public static class Graph {
        private static final String NEWLINE = System.getProperty("line.separator");

        private final int V;
        private int E;
        private Bag<Integer>[] adj;

        /**
         * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
         * param V the number of vertices
         *
         * @param  V number of vertices
         * @throws IllegalArgumentException if <tt>V</tt> < 0
         */
        public Graph(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
            this.V = V;
            this.E = 0;
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
        }

        /**
         * Initializes a graph from an input stream.
         * The format is the number of vertices <em>V</em>,
         * followed by the number of edges <em>E</em>,
         * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
         *
         * @param  in the input stream
         * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
         * @throws IllegalArgumentException if the number of vertices or edges is negative
         */
        public Graph(In in) {
            this(in.readInt());
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        }

        /**
         * Initializes a new graph that is a deep copy of <tt>G</tt>.
         *
         * @param  G the graph to copy
         */
        public Graph(Graph G) {
            this(G.V());
            this.E = G.E();
            for (int v = 0; v < G.V(); v++) {
                // reverse so that adjacency list is in same order as original
                Stack<Integer> reverse = new Stack<Integer>();
                for (int w : G.adj[v]) {
                    reverse.push(w);
                }
                for (int w : reverse) {
                    adj[v].add(w);
                }
            }
        }

        /**
         * Returns the number of vertices in this graph.
         *
         * @return the number of vertices in this graph
         */
        public int V() {
            return V;
        }

        /**
         * Returns the number of edges in this graph.
         *
         * @return the number of edges in this graph
         */
        public int E() {
            return E;
        }

        // throw an IndexOutOfBoundsException unless 0 <= v < V
        private void validateVertex(int v) {
            if (v < 0 || v >= V)
                throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
        }

        /**
         * Adds the undirected edge v-w to this graph.
         *
         * @param  v one vertex in the edge
         * @param  w the other vertex in the edge
         * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
         */
        public void addEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            E++;
            adj[v].add(w);
            adj[w].add(v);
        }


        /**
         * Returns the vertices adjacent to vertex <tt>v</tt>.
         *
         * @param  v the vertex
         * @return the vertices adjacent to vertex <tt>v</tt>, as an iterable
         * @throws IndexOutOfBoundsException unless 0 <= v < V
         */
        public Iterable<Integer> adj(int v) {
            validateVertex(v);
            return adj[v];
        }

        /**
         * Returns the degree of vertex <tt>v</tt>.
         *
         * @param  v the vertex
         * @return the degree of vertex <tt>v</tt>
         * @throws IndexOutOfBoundsException unless 0 <= v < V
         */
        public int degree(int v) {
            validateVertex(v);
            return adj[v].size();
        }


        /**
         * Returns a string representation of this graph.
         *
         * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
         *         followed by the <em>V</em> adjacency lists
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " vertices, " + E + " edges " + NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(v + ": ");
                for (int w : adj[v]) {
                    s.append(w + " ");
                }
                s.append(NEWLINE);
            }
            return s.toString();
        }


        /**
         * Unit tests the <tt>Graph</tt> data type.
         */
        public static void main(String[] args) {
            In in = new In(args[0]);
            Graph G = new Graph(in);
            StdOut.println(G);
        }

    }


    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of queue
        private Node<Item> last;     // end of queue
        private int N;               // number of elements on queue

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty queue.
         */
        public Queue() {
            first = null;
            last  = null;
            N = 0;
        }

        /**
         * Returns true if this queue is empty.
         *
         * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this queue.
         *
         * @return the number of items in this queue
         */
        public int size() {
            return N;
        }

        /**
         * Returns the item least recently added to this queue.
         *
         * @return the item least recently added to this queue
         * @throws NoSuchElementException if this queue is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            return first.item;
        }

        /**
         * Adds the item to this queue.
         *
         * @param  item the item to add
         */
        public void enqueue(Item item) {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else           oldlast.next = last;
            N++;
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         *
         * @return the item on this queue that was least recently added
         * @throws NoSuchElementException if this queue is empty
         */
        public Item dequeue() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            N--;
            if (isEmpty()) last = null;   // to avoid loitering
            return item;
        }

        /**
         * Returns a string representation of this queue.
         *
         * @return the sequence of items in FIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this)
                s.append(item + " ");
            return s.toString();
        }

        /**
         * Returns an iterator that iterates over the items in this queue in FIFO order.
         *
         * @return an iterator that iterates over the items in this queue in FIFO order
         */
        public Iterator<Item> iterator()  {
            return new ListIterator<Item>(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class ListIterator<Item> implements Iterator<Item> {
            private Node<Item> current;

            public ListIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext()  { return current != null;                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }


        /**
         * Unit tests the <tt>Queue</tt> data type.
         */
        public static void main(String[] args) {
            Queue<String> q = new Queue<String>();
            while (!StdIn.isEmpty()) {
                String item = StdIn.readString();
                if (!item.equals("-")) q.enqueue(item);
                else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
            }
            StdOut.println("(" + q.size() + " left on queue)");
        }
    }

    public static class Stack<Item> implements Iterable<Item> {
        private Node<Item> first;     // top of stack
        private int N;                // size of the stack

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty stack.
         */
        public Stack() {
            first = null;
            N = 0;
        }

        /**
         * Returns true if this stack is empty.
         *
         * @return true if this stack is empty; false otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this stack.
         *
         * @return the number of items in this stack
         */
        public int size() {
            return N;
        }

        /**
         * Adds the item to this stack.
         *
         * @param  item the item to add
         */
        public void push(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            N++;
        }

        /**
         * Removes and returns the item most recently added to this stack.
         *
         * @return the item most recently added
         * @throws NoSuchElementException if this stack is empty
         */
        public Item pop() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            Item item = first.item;        // save item to return
            first = first.next;            // delete first node
            N--;
            return item;                   // return the saved item
        }


        /**
         * Returns (but does not remove) the item most recently added to this stack.
         *
         * @return the item most recently added to this stack
         * @throws NoSuchElementException if this stack is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            return first.item;
        }

        /**
         * Returns a string representation of this stack.
         *
         * @return the sequence of items in this stack in LIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this)
                s.append(item + " ");
            return s.toString();
        }


        /**
         * Returns an iterator to this stack that iterates through the items in LIFO order.
         *
         * @return an iterator to this stack that iterates through the items in LIFO order
         */
        public Iterator<Item> iterator() {
            return new ListIterator<Item>(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class ListIterator<Item> implements Iterator<Item> {
            private Node<Item> current;

            public ListIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }


        /**
         * Unit tests the <tt>Stack</tt> data type.
         */
        public static void main(String[] args) {
            Stack<String> s = new Stack<String>();
            while (!StdIn.isEmpty()) {
                String item = StdIn.readString();
                if (!item.equals("-")) s.push(item);
                else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
            }
            StdOut.println("(" + s.size() + " left on stack)");
        }
    }



    public static final class StdOut {

        // force Unicode UTF-8 encoding; otherwise it's system dependent
        private static final String CHARSET_NAME = "UTF-8";

        // assume language = English, country = US for consistency with StdIn
        private static final Locale LOCALE = Locale.US;

        // send output here
        private static PrintWriter out;

        // this is called before invoking any methods
        static {
            try {
                out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }
        }

        // don't instantiate
        private StdOut() { }

        /**
          * Closes standard output.
          */
        public static void close() {
            out.close();
        }

        /**
          * Terminates the current line by printing the line-separator string.
          */
        public static void println() {
            out.println();
        }

        /**
          * Prints an object to this output stream and then terminates the line.
          *
          * @param x the object to print
          */
        public static void println(Object x) {
            out.println(x);
        }

        /**
          * Prints a boolean to standard output and then terminates the line.
          *
          * @param x the boolean to print
          */
        public static void println(boolean x) {
            out.println(x);
        }

        /**
          * Prints a character to standard output and then terminates the line.
          *
          * @param x the character to print
          */
        public static void println(char x) {
            out.println(x);
        }

        /**
          * Prints a double to standard output and then terminates the line.
          *
          * @param x the double to print
          */
        public static void println(double x) {
            out.println(x);
        }

        /**
          * Prints an integer to standard output and then terminates the line.
          *
          * @param x the integer to print
          */
        public static void println(float x) {
            out.println(x);
        }

        /**
          * Prints an integer to standard output and then terminates the line.
          *
          * @param x the integer to print
          */
        public static void println(int x) {
            out.println(x);
        }

        /**
          * Prints a long to standard output and then terminates the line.
          *
          * @param x the long to print
          */
        public static void println(long x) {
            out.println(x);
        }

        /**
          * Prints a short integer to standard output and then terminates the line.
          *
          * @param x the short to print
          */
        public static void println(short x) {
            out.println(x);
        }

        /**
          * Prints a byte to standard output and then terminates the line.
          * <p>
          * To write binary data, see {@link BinaryStdOut}.
          *
          * @param x the byte to print
          */
        public static void println(byte x) {
            out.println(x);
        }

        /**
          * Flushes standard output.
          */
        public static void print() {
            out.flush();
        }

        /**
          * Prints an object to standard output and flushes standard output.
          *
          * @param x the object to print
          */
        public static void print(Object x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a boolean to standard output and flushes standard output.
          *
          * @param x the boolean to print
          */
        public static void print(boolean x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a character to standard output and flushes standard output.
          *
          * @param x the character to print
          */
        public static void print(char x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a double to standard output and flushes standard output.
          *
          * @param x the double to print
          */
        public static void print(double x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a float to standard output and flushes standard output.
          *
          * @param x the float to print
          */
        public static void print(float x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints an integer to standard output and flushes standard output.
          *
          * @param x the integer to print
          */
        public static void print(int x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a long integer to standard output and flushes standard output.
          *
          * @param x the long integer to print
          */
        public static void print(long x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a short integer to standard output and flushes standard output.
          *
          * @param x the short integer to print
          */
        public static void print(short x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a byte to standard output and flushes standard output.
          *
          * @param x the byte to print
          */
        public static void print(byte x) {
            out.print(x);
            out.flush();
        }

        /**
          * Prints a formatted string to standard output, using the specified format
          * string and arguments, and then flushes standard output.
          *
          *
          * @param format the <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format string</a>
          * @param args   the arguments accompanying the format string
          */
        public static void printf(String format, Object... args) {
            out.printf(LOCALE, format, args);
            out.flush();
        }

        /**
          * Prints a formatted string to standard output, using the locale and
          * the specified format string and arguments; then flushes standard output.
          *
          * @param locale the locale
          * @param format the <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format string</a>
          * @param args   the arguments accompanying the format string
          */
        public static void printf(Locale locale, String format, Object... args) {
            out.printf(locale, format, args);
            out.flush();
        }

        /**
          * Unit tests some of the methods in <tt>StdOut</tt>.
          */
        public static void main(String[] args) {

            // write to stdout
            StdOut.println("Test");
            StdOut.println(17);
            StdOut.println(true);
            StdOut.printf("%.6f\n", 1.0 / 7.0);
        }

    }


    public static final class StdIn {

        /*** begin: section (1 of 2) of code duplicated from In to StdIn. */

        // assume Unicode UTF-8 encoding
        private static final String CHARSET_NAME = "UTF-8";

        // assume language = English, country = US for consistency with System.out.
        private static final Locale LOCALE = Locale.US;

        // the default token separator; we maintain the invariant that this value
        // is held by the scanner's delimiter between calls
        private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

        // makes whitespace significant
        private static final Pattern EMPTY_PATTERN = Pattern.compile("");

        // used to read the entire input
        private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

        /*** end: section (1 of 2) of code duplicated from In to StdIn. */

        private static Scanner scanner;

        // it doesn't make sense to instantiate this class
        private StdIn() { }

        //// begin: section (2 of 2) of code duplicated from In to StdIn,
        //// with all methods changed from "public" to "public static"

        /**
          * Returns true if standard input is empty (except possibly for whitespace).
          * Use this method to know whether the next call to {@link #readString()},
          * {@link #readDouble()}, etc will succeed.
          *
          * @return <tt>true</tt> if standard input is empty (except possibly
          *         for whitespace); <tt>false</tt> otherwise
          */
        public static boolean isEmpty() {
            return !scanner.hasNext();
        }

        /**
          * Returns true if standard input has a next line.
          * Use this method to know whether the
          * next call to {@link #readLine()} will succeed.
          * This method is functionally equivalent to {@link #hasNextChar()}.
          *
          * @return <tt>true</tt> if standard input is empty;
          *         <tt>false</tt> otherwise
          */
        public static boolean hasNextLine() {
            return scanner.hasNextLine();
        }

        /**
         * Returns true if standard input has more inputy (including whitespace).
         * Use this method to know whether the next call to {@link #readChar()} will succeed.
         * This method is functionally equivalent to {@link #hasNextLine()}.
         *
         * @return <tt>true</tt> if standard input has more input (including whitespace);
         *         <tt>false</tt> otherwise
         */
        public static boolean hasNextChar() {
            scanner.useDelimiter(EMPTY_PATTERN);
            boolean result = scanner.hasNext();
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return result;
        }


        /**
          * Reads and returns the next line, excluding the line separator if present.
          *
          * @return the next line, excluding the line separator if present;
          *         <tt>null</tt> if no such line
          */
        public static String readLine() {
            String line;
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                line = null;
            }
            return line;
        }

        /**
         * Reads and returns the next character.
         *
         * @return the next character
         * @throws NoSuchElementException if standard input is empty
         */
        public static char readChar() {
            scanner.useDelimiter(EMPTY_PATTERN);
            String ch = scanner.next();
            assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
            + " Please contact the authors.";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        }


        /**
          * Reads and returns the remainder of the input, as a string.
          *
          * @return the remainder of the input, as a string
          * @throws NoSuchElementException if standard input is empty
          */
        public static String readAll() {
            if (!scanner.hasNextLine())
                return "";

            String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
            // not that important to reset delimeter, since now scanner is empty
            scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
            return result;
        }


        /**
          * Reads the next token  and returns the <tt>String</tt>.
          *
          * @return the next <tt>String</tt>
          * @throws NoSuchElementException if standard input is empty
          */
        public static String readString() {
            return scanner.next();
        }

        /**
          * Reads the next token from standard input, parses it as an integer, and returns the integer.
          *
          * @return the next integer on standard input
          * @throws NoSuchElementException if standard input is empty
          * @throws InputMismatchException if the next token cannot be parsed as an <tt>int</tt>
          */
        public static int readInt() {
            return scanner.nextInt();
        }

        /**
          * Reads the next token from standard input, parses it as a double, and returns the double.
          *
          * @return the next double on standard input
          * @throws NoSuchElementException if standard input is empty
          * @throws InputMismatchException if the next token cannot be parsed as a <tt>double</tt>
          */
        public static double readDouble() {
            return scanner.nextDouble();
        }

        /**
          * Reads the next token from standard input, parses it as a float, and returns the float.
          *
          * @return the next float on standard input
          * @throws NoSuchElementException if standard input is empty
          * @throws InputMismatchException if the next token cannot be parsed as a <tt>float</tt>
          */
        public static float readFloat() {
            return scanner.nextFloat();
        }

        /**
          * Reads the next token from standard input, parses it as a long integer, and returns the long integer.
          *
          * @return the next long integer on standard input
          * @throws NoSuchElementException if standard input is empty
          * @throws InputMismatchException if the next token cannot be parsed as a <tt>long</tt>
          */
        public static long readLong() {
            return scanner.nextLong();
        }

        /**
          * Reads the next token from standard input, parses it as a short integer, and returns the short integer.
          *
          * @return the next short integer on standard input
          * @throws NoSuchElementException if standard input is empty
          * @throws InputMismatchException if the next token cannot be parsed as a <tt>short</tt>
          */
        public static short readShort() {
            return scanner.nextShort();
        }

        /**
          * Reads the next token from standard input, parses it as a byte, and returns the byte.
          *
          * @return the next byte on standard input
          * @throws NoSuchElementException if standard input is empty
          * @throws InputMismatchException if the next token cannot be parsed as a <tt>byte</tt>
          */
        public static byte readByte() {
            return scanner.nextByte();
        }

        /**
         * Reads the next token from standard input, parses it as a boolean,
         * and returns the boolean.
         *
         * @return the next boolean on standard input
         * @throws NoSuchElementException if standard input is empty
         * @throws InputMismatchException if the next token cannot be parsed as a <tt>boolean</tt>:
         *    <tt>true</tt> or <tt>1</tt> for true, and <tt>false</tt> or <tt>0</tt> for false,
         *    ignoring case
         */
        public static boolean readBoolean() {
            String s = readString();
            if (s.equalsIgnoreCase("true"))  return true;
            if (s.equalsIgnoreCase("false")) return false;
            if (s.equals("1"))               return true;
            if (s.equals("0"))               return false;
            throw new InputMismatchException();
        }

        /**
         * Reads all remaining tokens from standard input and returns them as an array of strings.
         *
         * @return all remaining tokens on standard input, as an array of strings
         */
        public static String[] readAllStrings() {
            // we could use readAll.trim().split(), but that's not consistent
            // because trim() uses characters 0x00..0x20 as whitespace
            String[] tokens = WHITESPACE_PATTERN.split(readAll());
            if (tokens.length == 0 || tokens[0].length() > 0)
                return tokens;

            // don't include first token if it is leading whitespace
            String[] decapitokens = new String[tokens.length - 1];
            for (int i = 0; i < tokens.length - 1; i++)
                decapitokens[i] = tokens[i + 1];
            return decapitokens;
        }

        /**
         * Reads all remaining lines from standard input and returns them as an array of strings.
         * @return all remaining lines on standard input, as an array of strings
         */
        public static String[] readAllLines() {
            ArrayList<String> lines = new ArrayList<String>();
            while (hasNextLine()) {
                lines.add(readLine());
            }
            return lines.toArray(new String[0]);
        }

        /**
         * Reads all remaining tokens from standard input, parses them as integers, and returns
         * them as an array of integers.
         * @return all remaining integers on standard input, as an array
         * @throws InputMismatchException if any token cannot be parsed as an <tt>int</tt>
         */
        public static int[] readAllInts() {
            String[] fields = readAllStrings();
            int[] vals = new int[fields.length];
            for (int i = 0; i < fields.length; i++)
                vals[i] = Integer.parseInt(fields[i]);
            return vals;
        }

        /**
         * Reads all remaining tokens from standard input, parses them as doubles, and returns
         * them as an array of doubles.
         * @return all remaining doubles on standard input, as an array
         * @throws InputMismatchException if any token cannot be parsed as a <tt>double</tt>
         */
        public static double[] readAllDoubles() {
            String[] fields = readAllStrings();
            double[] vals = new double[fields.length];
            for (int i = 0; i < fields.length; i++)
                vals[i] = Double.parseDouble(fields[i]);
            return vals;
        }

        //// end: section (2 of 2) of code duplicated from In to StdIn


        // do this once when StdIn is initialized
        static {
            resync();
        }

        /**
         * If StdIn changes, use this to reinitialize the scanner.
         */
        private static void resync() {
            setScanner(new Scanner(new java.io.BufferedInputStream(System.in), CHARSET_NAME));
        }

        private static void setScanner(Scanner scanner) {
            StdIn.scanner = scanner;
            StdIn.scanner.useLocale(LOCALE);
        }

        /**
          * Reads all remaining tokens, parses them as integers, and returns
          * them as an array of integers.
          * @return all remaining integers, as an array
          * @throws InputMismatchException if any token cannot be parsed as an <tt>int</tt>
          * @deprecated Replaced by {@link #readAllInts()}.
          */
        public static int[] readInts() {
            return readAllInts();
        }

        /**
          * Reads all remaining tokens, parses them as doubles, and returns
          * them as an array of doubles.
          * @return all remaining doubles, as an array
          * @throws InputMismatchException if any token cannot be parsed as a <tt>double</tt>
          * @deprecated Replaced by {@link #readAllDoubles()}.
          */
        public static double[] readDoubles() {
            return readAllDoubles();
        }

        /**
          * Reads all remaining tokens and returns them as an array of strings.
          * @return all remaining tokens, as an array of strings
          * @deprecated Replaced by {@link #readAllStrings()}.
          */
        public static String[] readStrings() {
            return readAllStrings();
        }


        /**
         * Interactive test of basic functionality.
         */
        public static void main(String[] args) {

            StdOut.print("Type a string: ");
            String s = StdIn.readString();
            StdOut.println("Your string was: " + s);
            StdOut.println();

            StdOut.print("Type an int: ");
            int a = StdIn.readInt();
            StdOut.println("Your int was: " + a);
            StdOut.println();

            StdOut.print("Type a boolean: ");
            boolean b = StdIn.readBoolean();
            StdOut.println("Your boolean was: " + b);
            StdOut.println();

            StdOut.print("Type a double: ");
            double c = StdIn.readDouble();
            StdOut.println("Your double was: " + c);
            StdOut.println();

        }

    }





































    public static class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of bag
    private int N;               // number of elements in bag

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty bag.
     */
    public Bag() {
        first = null;
        N = 0;
    }

    /**
     * Returns true if this bag is empty.
     *
     * @return <tt>true</tt> if this bag is empty;
     *         <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this bag.
     *
     * @return the number of items in this bag
     */
    public int size() {
        return N;
    }

    /**
     * Adds the item to this bag.
     *
     * @param  item the item to add to this bag
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }


    /**
     * Returns an iterator that iterates over the items in this bag in arbitrary order.
     *
     * @return an iterator that iterates over the items in this bag in arbitrary order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    /**
     * Unit tests the <tt>Bag</tt> data type.
     */
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }


}













public static final class In {
    
    ///// begin: section (1 of 2) of code duplicated from In to StdIn.
    
    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;

    // the default token separator; we maintain the invariant that this value 
    // is held by the scanner's delimiter between calls
    private static final Pattern WHITESPACE_PATTERN
        = Pattern.compile("\\p{javaWhitespace}+");

    // makes whitespace characters significant 
    private static final Pattern EMPTY_PATTERN
        = Pattern.compile("");

    // used to read the entire input. source:
    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    private static final Pattern EVERYTHING_PATTERN
        = Pattern.compile("\\A");

    //// end: section (1 of 2) of code duplicated from In to StdIn.

    private Scanner scanner;

   /**
     * Initializes an input stream from standard input.
     */
    public In() {
        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
        scanner.useLocale(LOCALE);
    }

   /**
     * Initializes an input stream from a socket.
     *
     * @param  socket the socket
     * @throws IllegalArgumentException if cannot open {@code socket}
     * @throws NullPointerException if {@code socket} is {@code null}
     */
    public In(Socket socket) {
        if (socket == null) throw new NullPointerException("argument is null");
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + socket);
        }
    }

   /**
     * Initializes an input stream from a URL.
     *
     * @param  url the URL
     * @throws IllegalArgumentException if cannot open {@code url}
     * @throws NullPointerException if {@code url} is {@code null}
     */
    public In(URL url) {
        if (url == null) throw new NullPointerException("argument is null");
        try {
            URLConnection site = url.openConnection();
            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + url);
        }
    }

   /**
     * Initializes an input stream from a file.
     *
     * @param  file the file
     * @throws IllegalArgumentException if cannot open {@code file}
     * @throws NullPointerException if {@code file} is {@code null}
     */
    public In(File file) {
        if (file == null) throw new NullPointerException("argument is null");
        try {
            // for consistency with StdIn, wrap with BufferedInputStream instead of use
            // file as argument to Scanner
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + file);
        }
    }


   /**
     * Initializes an input stream from a filename or web page name.
     *
     * @param  name the filename or web page name
     * @throws IllegalArgumentException if cannot open {@code name} as
     *         a file or URL
     * @throws NullPointerException if {@code name} is {@code null}
     */
    public In(String name) {
        if (name == null) throw new NullPointerException("argument is null");
        try {
            // first try to read file from local file system
            File file = new File(name);
            if (file.exists()) {
                // for consistency with StdIn, wrap with BufferedInputStream instead of use
                // file as argument to Scanner
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }

            // next try for files included in jar
            URL url = getClass().getResource(name);

            // or URL from web
            if (url == null) {
                url = new URL(name);
            }

            URLConnection site = url.openConnection();

            // in order to set User-Agent, replace above line with these two
            // HttpURLConnection site = (HttpURLConnection) url.openConnection();
            // site.addRequestProperty("User-Agent", "Mozilla/4.76");

            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + name);
        }
    }

    /**
     * Initializes an input stream from a given {@link Scanner} source; use with 
     * <tt>new Scanner(String)</tt> to read from a string.
     * <p>
     * Note that this does not create a defensive copy, so the
     * scanner will be mutated as you read on. 
     *
     * @param  scanner the scanner
     * @throws NullPointerException if {@code scanner} is {@code null}
     */
    public In(Scanner scanner) {
        if (scanner == null) throw new NullPointerException("argument is null");
        this.scanner = scanner;
    }

    /**
     * Returns true if this input stream exists.
     *
     * @return <tt>true</tt> if this input stream exists; <tt>false</tt> otherwise
     */
    public boolean exists()  {
        return scanner != null;
    }
    
    ////  begin: section (2 of 2) of code duplicated from In to StdIn,
    ////  with all methods changed from "public" to "public static".

   /**
     * Returns true if input stream is empty (except possibly whitespace).
     * Use this to know whether the next call to {@link #readString()}, 
     * {@link #readDouble()}, etc will succeed.
     *
     * @return <tt>true</tt> if this input stream is empty (except possibly whitespace);
     *         <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

   /** 
     * Returns true if this input stream has a next line.
     * Use this method to know whether the
     * next call to {@link #readLine()} will succeed.
     * This method is functionally equivalent to {@link #hasNextChar()}.
     *
     * @return <tt>true</tt> if this input stream is empty;
     *         <tt>false</tt> otherwise
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Returns true if this input stream has more inputy (including whitespace).
     * Use this method to know whether the next call to {@link #readChar()} will succeed.
     * This method is functionally equivalent to {@link #hasNextLine()}.
     * 
     * @return <tt>true</tt> if this input stream has more input (including whitespace);
     *         <tt>false</tt> otherwise   
     */
    public boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }


   /**
     * Reads and returns the next line in this input stream.
     *
     * @return the next line in this input stream; <tt>null</tt> if no such line
     */
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    /**
     * Reads and returns the next character in this input stream.
     *
     * @return the next character in this input stream
     */
    public char readChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        String ch = scanner.next();
        assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
            + " Please contact the authors.";
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return ch.charAt(0);
    }  


   /**
     * Reads and returns the remainder of this input stream, as a string.
     *
     * @return the remainder of this input stream, as a string
     */
    public String readAll() {
        if (!scanner.hasNextLine())
            return "";

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        // not that important to reset delimeter, since now scanner is empty
        scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
        return result;
    }


   /**
     * Reads the next token from this input stream and returns it as a <tt>String</tt>.
     *
     * @return the next <tt>String</tt> in this input stream
     */
    public String readString() {
        return scanner.next();
    }

   /**
     * Reads the next token from this input stream, parses it as a <tt>int</tt>,
     * and returns the <tt>int</tt>.
     *
     * @return the next <tt>int</tt> in this input stream
     */
    public int readInt() {
        return scanner.nextInt();
    }

   /**
     * Reads the next token from this input stream, parses it as a <tt>double</tt>,
     * and returns the <tt>double</tt>.
     *
     * @return the next <tt>double</tt> in this input stream
     */
    public double readDouble() {
        return scanner.nextDouble();
    }

   /**
     * Reads the next token from this input stream, parses it as a <tt>float</tt>,
     * and returns the <tt>float</tt>.
     *
     * @return the next <tt>float</tt> in this input stream
     */
    public float readFloat() {
        return scanner.nextFloat();
    }

   /**
     * Reads the next token from this input stream, parses it as a <tt>long</tt>,
     * and returns the <tt>long</tt>.
     *
     * @return the next <tt>long</tt> in this input stream
     */
    public long readLong() {
        return scanner.nextLong();
    }

   /**
     * Reads the next token from this input stream, parses it as a <tt>short</tt>,
     * and returns the <tt>short</tt>.
     *
     * @return the next <tt>short</tt> in this input stream
     */
    public short readShort() {
        return scanner.nextShort();
    }

   /**
     * Reads the next token from this input stream, parses it as a <tt>byte</tt>,
     * and returns the <tt>byte</tt>.
     * <p>
     * To read binary data, use {@link BinaryIn}.
     *
     * @return the next <tt>byte</tt> in this input stream
     */
    public byte readByte() {
        return scanner.nextByte();
    }

    /**
     * Reads the next token from this input stream, parses it as a <tt>boolean</tt>
     * (interpreting either <tt>"true"</tt> or <tt>"1"</tt> as <tt>true</tt>,
     * and either <tt>"false"</tt> or <tt>"0"</tt> as <tt>false</tt>).
     *
     * @return the next <tt>boolean</tt> in this input stream
     */
    public boolean readBoolean() {
        String s = readString();
        if (s.equalsIgnoreCase("true"))  return true;
        if (s.equalsIgnoreCase("false")) return false;
        if (s.equals("1"))               return true;
        if (s.equals("0"))               return false;
        throw new InputMismatchException();
    }

    /**
     * Reads all remaining tokens from this input stream and returns them as
     * an array of strings.
     *
     * @return all remaining tokens in this input stream, as an array of strings
     */
    public String[] readAllStrings() {
        // we could use readAll.trim().split(), but that's not consistent
        // since trim() uses characters 0x00..0x20 as whitespace
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0)
            return tokens;
        String[] decapitokens = new String[tokens.length-1];
        for (int i = 0; i < tokens.length-1; i++)
            decapitokens[i] = tokens[i+1];
        return decapitokens;
    }

    /**
     * Reads all remaining lines from this input stream and returns them as
     * an array of strings.
     *
     * @return all remaining lines in this input stream, as an array of strings
     */
    public String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[0]);
    }


    /**
     * Reads all remaining tokens from this input stream, parses them as integers,
     * and returns them as an array of integers.
     *
     * @return all remaining lines in this input stream, as an array of integers
     */
    public int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    /**
     * Reads all remaining tokens from this input stream, parses them as doubles,
     * and returns them as an array of doubles.
     *
     * @return all remaining lines in this input stream, as an array of doubles
     */
    public double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }
    
    ///// end: section (2 of 2) of code duplicated from In to StdIn */

   /**
     * Closes this input stream.
     */
    public void close() {
        scanner.close();  
    }

    /**
     * Reads all integers from a file and returns them as
     * an array of integers.
     *
     * @param      filename the name of the file
     * @return     the integers in the file
     * @deprecated Replaced by <tt>new In(filename)</tt>.{@link #readAllInts()}.
     */
    public static int[] readInts(String filename) {
        return new In(filename).readAllInts();
    }

   /**
     * Reads all doubles from a file and returns them as
     * an array of doubles.
     *
     * @param      filename the name of the file
     * @return     the doubles in the file
     * @deprecated Replaced by <tt>new In(filename)</tt>.{@link #readAllDoubles()}.
     */
    public static double[] readDoubles(String filename) {
        return new In(filename).readAllDoubles();
    }

   /**
     * Reads all strings from a file and returns them as
     * an array of strings.
     *
     * @param      filename the name of the file
     * @return     the strings in the file
     * @deprecated Replaced by <tt>new In(filename)</tt>.{@link #readAllStrings()}.
     */
    public static String[] readStrings(String filename) {
        return new In(filename).readAllStrings();
    }

    /**
     * Reads all integers from standard input and returns them
     * an array of integers.
     *
     * @return     the integers on standard input
     * @deprecated Replaced by {@link StdIn#readAllInts()}.
     */
    public static int[] readInts() {
        return new In().readAllInts();
    }

   /**
     * Reads all doubles from standard input and returns them as
     * an array of doubles.
     *
     * @return     the doubles on standard input
     * @deprecated Replaced by {@link StdIn#readAllDoubles()}.
     */
    public static double[] readDoubles() {
        return new In().readAllDoubles();
    }

   /**
     * Reads all strings from standard input and returns them as
     *  an array of strings.
     *
     * @return     the strings on standard input
     * @deprecated Replaced by {@link StdIn#readAllStrings()}.
     */
    public static String[] readStrings() {
        return new In().readAllStrings();
    }
    
   /**
     * Unit tests the <tt>In</tt> data type.
     */
    public static void main(String[] args) {
        In in;
        String urlName = "http://introcs.cs.princeton.edu/stdlib/InTest.txt";

        // read from a URL
        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            System.out.println(in.readAll());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one line at a time from URL
        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one string at a time from URL
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readString();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();


        // read one line at a time from file in current directory
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("./InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();


        // read one line at a time from file using relative path
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("../stdlib/InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        // read one char at a time
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("InTest.txt");
            while (!in.isEmpty()) {
                char c = in.readChar();
                System.out.print(c);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println();

        // read one line at a time from absolute OS X / Linux path
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        in = new In("/n/fs/introcs/www/java/stdlib/InTest.txt");
        try {
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();


        // read one line at a time from absolute Windows path
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("G:\\www\\introcs\\stdlib\\InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

    }

}










}
