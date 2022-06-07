class GraphNodeDemo(
    override val name: String,
    override val neighbors: List<GraphNode.Neighbor>,
    override var processed: Boolean = false
): GraphNode