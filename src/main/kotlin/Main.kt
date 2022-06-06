import com.google.gson.Gson

fun main(args: Array<String>) {

    val graph = createGraph()
    printGraph(graph)

    Dijkstra(
        graph
    ).start()
}

fun printGraph(graph: Graph) {
    println(Gson().toJson(graph))
}

private fun createGraph(): Graph {
    val graphNodes = HashMap<String, GraphNode>()

    val finName = "fin"
    val fin = GraphNode(finName, emptyList())
    graphNodes[finName] = fin

    val aNeighbors = mutableListOf<GraphNode.Neighbor>()
    aNeighbors.add(GraphNode.Neighbor(finName, 1))
    val aName = "a"
    val a = GraphNode(aName, aNeighbors)
    graphNodes[aName] = a

    val bNeighbors = mutableListOf<GraphNode.Neighbor>()
    bNeighbors.add(GraphNode.Neighbor(finName, 5))
    bNeighbors.add(GraphNode.Neighbor(aName, 3))
    val bName = "b"
    val b = GraphNode(bName, bNeighbors)
    graphNodes[bName] = b


    val startNeighbors = mutableListOf<GraphNode.Neighbor>()
    startNeighbors.add(GraphNode.Neighbor(aName, 6))
    startNeighbors.add(GraphNode.Neighbor(bName, 2))
    val startName = "start"
    val start = GraphNode(startName, startNeighbors)
    graphNodes[startName] = start


    return Graph(graphNodes)
}