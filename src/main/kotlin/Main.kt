import Dijkstra.Result.*
import com.google.gson.Gson

val gson = Gson()

fun main(args: Array<String>) {

    val graph = createGraph()
    printGraph(graph)

    val result = Dijkstra(graph).execute()

    when (result) {
        is Success -> onResultProcessed(result)
        is NegativeCost -> println(result.message)
        is UnknownTerminal -> println(result.message)
    }
}


private fun onResultProcessed(result: Success) {
    printCostsTable(result)
    printPath(result)
}

private fun printCostsTable(result: Success) {
    val json = gson.toJson(result.costsTable)
    println(json)
}

private fun printPath(result: Success) {
    val end = result.end
    val path = result.costsTable.getPath(end)
        .joinToString(separator = " -> ")

    val totalCost = result.costsTable.getCost(end.name)

    println("Path: $path, Total Cost: ${totalCost!!.cost}")
}

fun printGraph(graph: Graph) {
    val json = gson.toJson(graph)
    println(json)
}

private fun createGraph(): Graph {
    val graphNodes = mutableListOf<GraphNode>()

    val endName = "end"
    val end = GraphNodeDemo(endName, emptyList())
    graphNodes.add(end)

    val aNeighbors = mutableListOf<GraphNode.Neighbor>()
    aNeighbors.add(GraphNode.Neighbor(endName, 1))
    val aName = "a"
    val a = GraphNodeDemo(aName, aNeighbors)
    graphNodes.add(a)

    val bNeighbors = mutableListOf<GraphNode.Neighbor>()
    bNeighbors.add(GraphNode.Neighbor(endName, 5))
    bNeighbors.add(GraphNode.Neighbor(aName, 3))
    val bName = "b"
    val b = GraphNodeDemo(bName, bNeighbors)
    graphNodes.add(b)


    val startNeighbors = mutableListOf<GraphNode.Neighbor>()
    startNeighbors.add(GraphNode.Neighbor(aName, 6))
    startNeighbors.add(GraphNode.Neighbor(bName, 2))
    val startName = "start"
    val start = GraphNodeDemo(startName, startNeighbors)
    graphNodes.add(start)

    val hashMapNodes = createHashMap(graphNodes)

    return Graph(hashMapNodes)
}

private fun createHashMap(graphNodes: MutableList<GraphNode>): HashMap<String, GraphNode> {
    val hashMapNodes = HashMap<String, GraphNode>()
    for (graphNode in graphNodes) {
        hashMapNodes[graphNode.name] = graphNode
    }
    return hashMapNodes
}