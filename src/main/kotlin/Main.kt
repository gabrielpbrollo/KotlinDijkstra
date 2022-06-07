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

    val pianoName = "piano"
    val piano = GraphNodeDemo(pianoName, emptyList())
    graphNodes.add(piano)

    val bassNeighbors = mutableListOf<GraphNode.Neighbor>()
    bassNeighbors.add(GraphNode.Neighbor(pianoName, 20))
    val bassName = "bass guitar"
    val bass = GraphNodeDemo(bassName, bassNeighbors)
    graphNodes.add(bass)

    val drumSetNeighbors = mutableListOf<GraphNode.Neighbor>()
    drumSetNeighbors.add(GraphNode.Neighbor(pianoName, 10))
    val drumSetName = "drum set"
    val drum = GraphNodeDemo(drumSetName, drumSetNeighbors)
    graphNodes.add(drum)

    val posterNeighbor = mutableListOf<GraphNode.Neighbor>()
    posterNeighbor.add(GraphNode.Neighbor(drumSetName, 35))
    posterNeighbor.add(GraphNode.Neighbor(bassName, 30))
    val posterName = "poster"
    val poster = GraphNodeDemo(posterName, posterNeighbor)
    graphNodes.add(poster)

    val rareLpNeighbors = mutableListOf<GraphNode.Neighbor>()
    rareLpNeighbors.add(GraphNode.Neighbor(bassName, 15))
    rareLpNeighbors.add(GraphNode.Neighbor(drumSetName, 20))
    val rareLpName = "rare lp"
    val rareLp = GraphNodeDemo(rareLpName, rareLpNeighbors)
    graphNodes.add(rareLp)


    val bookNeighbors = mutableListOf<GraphNode.Neighbor>()
    bookNeighbors.add(GraphNode.Neighbor(rareLpName, 5))
    bookNeighbors.add(GraphNode.Neighbor(posterName, 0))
    val bookName = "book"
    val book = GraphNodeDemo(bookName, bookNeighbors)
    graphNodes.add(book)

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