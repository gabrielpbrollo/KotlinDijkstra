class Graph(
    private val graphNodes: HashMap<String, GraphNode>
) {

    fun getStart(): GraphNode? {
        val someoneNeighbors = graphNodes.values
            .flatMap { it.neighbors }
            .map { it.name }
            .distinct()

        return graphNodes.entries.firstOrNull {
            !someoneNeighbors.contains(it.key)
        }?.value
    }

    fun getEnd(): GraphNode? {
        return graphNodes
            .values
            .firstOrNull { !it.hasNeighbors() }
    }

    fun getLowestCostNotProcessedNode(costTable: CostsTable): GraphNode? {
        val result = costTable.getOrderedByCost()
            .firstOrNull { graphNodes[it.key]?.processed == false }
            ?: return null

        return graphNodes[result.key]
    }

    fun requireNonNegativeCosts() {
        for (graphNode in graphNodes) {
            val neighborWithNegativeCost = graphNode.value.neighbors.firstOrNull { it.cost < 0 }

            if (neighborWithNegativeCost != null) {
                throw Dijkstra.NegativeCostException(
                    "Cost must not be negative " +
                            "${graphNode.value.name} -> " +
                            "${neighborWithNegativeCost.name} = " +
                            "${neighborWithNegativeCost.cost}"
                )
            }
        }
    }
}