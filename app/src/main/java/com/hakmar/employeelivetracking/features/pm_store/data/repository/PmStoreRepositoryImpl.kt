package com.hakmar.employeelivetracking.features.pm_store.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.features.pm_store.data.remote.PmStoreApi
import com.hakmar.employeelivetracking.features.pm_store.domain.model.PmStore
import com.hakmar.employeelivetracking.features.pm_store.domain.repository.PmStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PmStoreRepositoryImpl @Inject constructor(
    private val pmStoreApi: PmStoreApi
) : PmStoreRepository {
    override fun getPmStores(): Flow<Resource<List<PmStore>>> {
        return flow {
            try {
                emit(Resource.Loading())
                /* val result = pmStoreApi.getPmStores()
                 if (result.response.success) {
                     emit(Resource.Success(result.data.stores))
                 } else {
                     emit(
                         Resource.Error(
                             message = UiText.DynamicString(result.response.message),
                         )
                     )
                 }*/
                val list = listOf<PmStore>(
                    PmStore(
                        storeCode = "5004",
                        storeName = "Fatih Esenyalı",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    ),
                    PmStore(
                        storeCode = "5024",
                        storeName = "GüzelYalı Pendil",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    ),
                    PmStore(
                        storeCode = "5004",
                        storeName = "Fatih Esenyalı",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    ),
                    PmStore(
                        storeCode = "5024",
                        storeName = "GüzelYalı Pendil",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    ),
                    PmStore(
                        storeCode = "5004",
                        storeName = "Fatih Esenyalı",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    ),
                    PmStore(
                        storeCode = "5024",
                        storeName = "GüzelYalı Pendil",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    ),
                    PmStore(
                        storeCode = "5004",
                        storeName = "Fatih Esenyalı",
                        id = 1,
                        pmId = 2,
                        bsId = 3
                    )
                )
                emit(Resource.Success(list))
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }
}