package com.system.traffic.presentation.fake

import com.system.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * 테스트용 DataStoreRepository fake.
 * 기본값은 "최초 로그인이 아닌 상태"(초기화 스킵)로 동작한다.
 */
class FakeDataStoreRepository(
    private val isFirstLogin: Boolean = false,
) : DataStoreRepository {
    override suspend fun setUpIsFirstLogin() = Unit
    override suspend fun getIsFirstLogin(): Boolean = isFirstLogin
    override suspend fun setAppFontSize(fontSize: String) = Unit
    override fun getAppFontSize(): Flow<String> = flowOf("")
    override suspend fun setAppThemeType(themeType: String) = Unit
    override fun getAppThemeType(): Flow<String> = flowOf("")
}
