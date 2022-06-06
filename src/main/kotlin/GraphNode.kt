class GraphNode(
    val name: String,
    val neighbors: List<Neighbor>
) {
    var processed: Boolean = false

    fun hasNeighbors() = neighbors.isNotEmpty()

    class Neighbor(
        val name: String,
        val cost: Int
    )
}