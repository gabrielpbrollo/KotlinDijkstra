import com.google.gson.Gson

class Dijkstra(
    private val graph: Graph
) {
    private val costTable = CostsTable()
    private lateinit var end: GraphNode

    fun start() {

        val start = graph.getStart()
        costTable.setCost(start.name, CostsTable.Cost(cost = 0))

        end = graph.getEnd()
        costTable.setCost(end.name, CostsTable.Cost(cost = Int.MAX_VALUE))


        while (true) {
            val lowestCostNode = graph.getLowestCostNotProcessedNode(costTable)

            if (lowestCostNode == null) {
                onAllNodesProcessed()
                return
            }

            for (neighbor in lowestCostNode.neighbors) {
                val currentCost = costTable.getCost(neighbor.name)
                if (currentCost == null) {
                    costTable.setCost(
                        nodeName = neighbor.name,
                        newCost = CostsTable.Cost(lowestCostNode.name, cost = neighbor.cost)
                    )
                    continue
                }

                val parentCost = costTable.getCost(lowestCostNode.name)
                val costToNeighbor = parentCost!!.cost + neighbor.cost

                if (costToNeighbor < currentCost.cost) {
                    costTable.setCost(neighbor.name, CostsTable.Cost(lowestCostNode.name, costToNeighbor))
                }
            }

            lowestCostNode.processed = true
        }
    }

    private fun onAllNodesProcessed() {
        println(Gson().toJson(costTable))
        printPath()
    }

    private fun printPath() {
        val path = costTable.getPath(end)
        val totalCost = costTable.getCost(end.name)
        println("Path: $path, Total Cost: ${totalCost!!.cost}")
    }
}