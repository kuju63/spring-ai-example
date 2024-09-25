package io.github.kuju63.spring.ai.controller

import io.github.kuju63.spring.ai.service.EmbeddingService
import org.springframework.ai.document.Document
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class EmbeddingController(private val embeddingService: EmbeddingService) {
    @PostMapping("/ai/embedding", consumes = ["multipart/form-data"])
    fun embedding(uploadFile:MultipartFile): ResponseEntity<List<Document>> {
        if (uploadFile.isEmpty) {
            return ResponseEntity.badRequest().build()
        }

        val fileName = uploadFile.originalFilename ?: "document"
        val resource = uploadFile.resource
        val documents = embeddingService.embedded(fileName, resource)
        return ResponseEntity.ok(documents)
    }
}