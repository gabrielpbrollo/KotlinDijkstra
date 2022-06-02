class GraphNode(
    val neighbors: List<Neighbor>
) {
    var processed: Boolean = false

    fun hasNeighbors() = neighbors.isNotEmpty()

    class Neighbor(
        val name: String,
        private val cost: Int
    )
}