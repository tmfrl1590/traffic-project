package com.system.traffic.presentation.fake

import com.system.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 테스트용 DataStoreRepository fake.
 * 인메모리로 실제처럼 동작한다: set하면 해당 Flow가 바뀐 값을 방출한다.
 * 기본값은 "최초 로그인이 아닌 상태"(초기화 스킵).
 */
class FakeDataStoreRepository(
    private val isFirstLogin: Boolean = false,
    initialFontSize: String = "",
    initialThemeType: String = "",
) : DataStoreRepository {

    private val fontSize = MutableStateFlow(initialFontSize)
    private val themeType = MutableStateFlow(initialThemeType)

    override suspend fun setUpIsFirstLogin() = Unit

    override suspend fun getIsFirstLogin(): Boolean = isFirstLogin

    override suspend fun setAppFontSize(fontSize: String) {
        this.fontSize.value = fontSize
    }

    override fun getAppFontSize(): Flow<String> = fontSize

    override suspend fun setAppThemeType(themeType: String) {
        this.themeType.value = themeType
    }

    override fun getAppThemeType(): Flow<String> = themeType
}
