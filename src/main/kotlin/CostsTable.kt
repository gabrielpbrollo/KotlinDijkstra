class CostsTable {

    private val table = HashMap<String, Cost>()

    fun setCost(nodeName: String, newCost: Cost) {
        table[nodeName] = newCost
    }

    fun getCost(nodeName: String) = table[nodeName]

    inner class Cost(
        val parent: String,
        val cost: Int
    )
}