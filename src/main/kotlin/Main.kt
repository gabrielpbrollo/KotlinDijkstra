import com.google.gson.Gson

fun main(args: Array<String>) {

    val graph = createGraph()
    printGraph(graph)

    Dijkstra().start(graph)
}

fun printGraph(graph: Graph) {
    println(Gson().toJson(graph))
}

private fun createGraph(): Graph {
    val graphNodes = HashMap<String, GraphNode>()

    val fin = GraphNode(emptyList())
    graphNodes["fin"] = fin

    val aNeighbors = mutableListOf<GraphNode.Neighbor>()
    aNeighbors.add(GraphNode.Neighbor("fin", 1))
    val a = GraphNode(aNeighbors)
    graphNodes["a"] = a

    val bNeighbors = mutableListOf<GraphNode.Neighbor>()
    bNeighbors.add(GraphNode.Neighbor("fin", 5))
    bNeighbors.add(GraphNode.Neighbor("a", 3))
    val b = GraphNode(bNeighbors)
    graphNodes["b"] = b


    val startNeighbors = mutableListOf<GraphNode.Neighbor>()
    startNeighbors.add(GraphNode.Neighbor("a", 6))
    startNeighbors.add(GraphNode.Neighbor("b", 2))
    val start = GraphNode(startNeighbors)
    graphNodes["start"] = start


    return Graph(graphNodes)
}