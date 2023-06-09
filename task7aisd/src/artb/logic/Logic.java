package artb.logic;

import artb.logic.graph.*;

import java.util.*;

public class Logic {

    public static class Edge {
        int u;
        int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public String toString() {
            return "(" + u + ", " + v + ")";
        }
    }

    public static ArrayList<Edge> findEdges(AdjMatrixGraph graph, int N) {
        int vertices = graph.vertexCount();
        ArrayList<Edge> Edges = new ArrayList<>();
        for (int u = 0; u < vertices; u++) {
            for (int v = 0; v < vertices; v++) {
                if (graph.isEdgeAvailable(u, v)) {
                    graph.removeEdge(u, v);
                    if (!isGraphConnected(graph, N)) {
                        if (u < v) {
                            Edges.add(new Edge(u, v));
                        }
                    }
                    graph.restoreEdge(u, v);

                }
            }
        }
        return Edges;
    }

    public static ArrayList<Integer> findVertices(AdjMatrixGraph graph, int N) {
        int vertices = graph.vertexCount();
        ArrayList<Integer> Vertices = new ArrayList<>();
        for (int vertex = 0; vertex < vertices; vertex++) {
            if (graph.isVertexAvailable(vertex)) {
                graph.removeVertex(vertex);
                if (!isGraphConnected(graph, N)) {
                    Vertices.add(vertex);
                }
                graph.restoreVertex(vertex);
            }
        }

        return Vertices;
    }

    public static boolean isGraphConnected(AdjMatrixGraph graph, int depth) {
        int vertices = graph.vertexCount();
        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            if (graph.isVertexAvailable(i)) {
                Arrays.fill(visited, false); // Сбрасываем флаги посещенных вершин перед каждым обходом
                bfs(i, visited, vertices, depth, graph);

                // Проверяем, все ли вершины были посещены
                for (int k = 0; k < visited.length; k++) {
                    if (!visited[k] && graph.isVertexAvailable(k)) {
                        return false; // Если есть непосещенные вершины, граф не связный
                    }
                }

            }
        }

        return true; // Если все вершины были посещены в каждом обходе, граф связный
    }


    private static void bfs(int startVertex, boolean[] visited, int vertices, int depth, AdjMatrixGraph graph) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startVertex);
        visited[startVertex] = true;

        int[] levels = new int[vertices];
        Arrays.fill(levels, Integer.MAX_VALUE);
        levels[startVertex] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            int currentLevel = levels[currentVertex];

            if (currentLevel >= depth) {
                continue; // Превышена максимальная глубина
            }

            for (int neighbor = 0; neighbor < vertices; neighbor++) {
                if (graph.isEdgeAvailable(currentVertex, neighbor) && !visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    levels[neighbor] = Math.min(levels[neighbor], currentLevel + 1);
                }
            }
        }
    }


}

