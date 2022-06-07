class Dijkstra(
    private val graph: Graph
) {
    private val costTable = CostsTable()
    private var start: GraphNode? = null
    private var end: GraphNode? = null

    //Manually set the start and end node if it is already known, for best performance
    fun setTerminalNodes(
        start: GraphNode?,
        end: GraphNode?
    ) {
        this.start = start
        this.end = end
    }

    fun execute(): Result {
        try {
            graph.requireNonNegativeCosts()
            loadTerminals()
        } catch (ex: NegativeCostException) {
            return Result.NegativeCost(ex.message!!)
        } catch (ex: UnknownTerminalException) {
            return Result.UnknownTerminal(ex.message!!)
        }

        while (true) {
            val lowestCostNode = graph.getLowestCostNotProcessedNode(costTable)

            if (lowestCostNode == null) {
                return Result.Success(costTable, start!!, end!!)
            }

            for (neighbor in lowestCostNode.neighbors) {
                val currentCost = costTable.getCost(neighbor.name)

                if (currentCost == null) {
                    createCost(neighbor, lowestCostNode)
                    continue
                }

                compareAndSetCost(lowestCostNode, neighbor, currentCost)
            }

            lowestCostNode.processed = true
        }
    }

    private fun createCost(
        neighbor: GraphNode.Neighbor,
        lowestCostNode: GraphNode
    ) {
        costTable.setCost(
            nodeName = neighbor.name,
            newCost = CostsTable.Cost(lowestCostNode.name, cost = neighbor.cost)
        )
    }

    private fun compareAndSetCost(
        parent: GraphNode,
        neighbor: GraphNode.Neighbor,
        currentCost: CostsTable.Cost
    ) {
        val parentCost = costTable.getCost(parent.name)
        val costToNeighbor = parentCost!!.cost + neighbor.cost

        val smallerCost = costToNeighbor < currentCost.cost
        if (smallerCost) {
            costTable.setCost(
                nodeName = neighbor.name,
                newCost = CostsTable.Cost(parent.name, costToNeighbor)
            )
        }
    }

    private fun loadTerminals() {
        if (start == null) start = graph.getStart()

        if (start == null) {
            throw UnknownTerminalException("Unknown start, please set it manually")
        }
        costTable.setCost(start!!.name, CostsTable.Cost(cost = 0))


        if (end == null) end = graph.getEnd()

        if (end == null) {
            throw UnknownTerminalException("Unknown end, please set it manually")
        }
        costTable.setCost(end!!.name, CostsTable.Cost(cost = Int.MAX_VALUE))
    }

    sealed class Result {
        class Success(
            val costsTable: CostsTable,
            val start: GraphNode,
            val end: GraphNode
        ) : Result()

        class NegativeCost(val message: String) : Result()
        class UnknownTerminal(val message: String) : Result()
    }

    class NegativeCostException(message: String) : RuntimeException(message)

    class UnknownTerminalException(message: String) : RuntimeException(message)
}