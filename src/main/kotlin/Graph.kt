class Graph(
    private val graphNodes: HashMap<String, GraphNode>
) {

    fun getStart(): GraphNode {
        val someoneNeighbors = graphNodes.values
            .flatMap { it.neighbors }
            .map { it.name }
            .distinct()

        return graphNodes.entries.first {
            !someoneNeighbors.contains(it.key)
        }.value
    }

    fun getEnd(): GraphNode {
        return graphNodes
            .values
            .first { !it.hasNeighbors() }
    }
}