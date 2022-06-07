class CostsTable {

    private val table = HashMap<String, Cost>()

    fun setCost(nodeName: String, newCost: Cost) {
        table[nodeName] = newCost
    }

    fun getCost(nodeName: String) = table[nodeName]

    fun getOrderedByCost() = table.entries.sortedBy { it.value.cost }

    fun getPath(end: GraphNode): List<String> {
        val reversePath = mutableListOf<String>()

        var parent = end.name
        reversePath.add(parent)

        while (true) {
            parent = getParent(parent) ?: break

            reversePath.add(parent)
        }

        return reversePath.reversed()
    }

    private fun getParent(parent: String) = table[parent]?.parent

    class Cost(
        val parent: String? = null,
        val cost: Int
    )
}