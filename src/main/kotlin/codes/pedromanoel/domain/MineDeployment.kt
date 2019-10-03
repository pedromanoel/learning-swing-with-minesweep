package codes.pedromanoel.domain

data class MineDeployment(val deployedPositions: List<Position>) {
    fun contains(position: Position) = deployedPositions.contains(position)

    companion object {
        fun empty() = MineDeployment(emptyList())
    }
}
