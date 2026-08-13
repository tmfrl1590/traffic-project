package com.system.traffic.presentation.fake

import com.system.traffic.domain.model.KeywordModel
import com.system.traffic.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * 테스트용 KeywordRepository fake.
 * 인메모리 리스트로 실제처럼 동작한다:
 * 추가/삭제하면 getKeywordList() Flow가 바뀐 리스트를 방출한다.
 */
class FakeKeywordRepository(
    initialKeywords: List<KeywordModel> = emptyList(),
) : KeywordRepository {

    private val keywords = MutableStateFlow(initialKeywords)
    private var nextId = (initialKeywords.maxOfOrNull { it.id } ?: 0L) + 1

    override suspend fun insertKeyword(keyword: String) {
        keywords.update { list ->
            // 실제 Room의 REPLACE처럼 같은 키워드는 중복 저장하지 않음
            list.filterNot { it.keyword == keyword } + KeywordModel(id = nextId++, keyword = keyword)
        }
    }

    override fun getKeywordList(): Flow<List<KeywordModel>> = keywords

    override suspend fun deleteKeyword(keyword: String) {
        keywords.update { list -> list.filterNot { it.keyword == keyword } }
    }

    override suspend fun clearAllKeyword() {
        keywords.update { emptyList() }
    }
}
