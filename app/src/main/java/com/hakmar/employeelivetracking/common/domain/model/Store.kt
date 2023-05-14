package com.hakmar.employeelivetracking.common.domain.model

data class Store(
    val id: String,
    val code: String,
    val name: String,
    var completedTask: Int,
    val taskCount: Int,
    val passedTime: String,
    val longtitude: Double,
    val lattitude: Double,
    val areaCode: String,
    val marketingManager: MarketingManager,
    val distirctManager: DistirctManager,
    val regionalManager: RegionalManager,
    var isStoreShiftEnable: Boolean,
    val address: String?
) {
    override fun equals(other: Any?): Boolean {
        return (other as Store).id == this.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
