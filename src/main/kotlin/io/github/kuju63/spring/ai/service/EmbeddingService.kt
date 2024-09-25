package io.github.kuju63.spring.ai.service

import org.springframework.ai.document.Document
import org.springframework.core.io.Resource

interface EmbeddingService {
    fun embedded(fileName: String, resources: Resource): List<Document>
}