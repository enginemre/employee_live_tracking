package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model

data class CheckItem(
    val description: String,
    val id: Int,
    var completed: Boolean,
    val type: String,
) {
    override fun equals(other: Any?): Boolean {
        return (other as CheckItem).id == this.id
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + id
        result = 31 * result + completed.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
