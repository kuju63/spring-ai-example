package io.github.kuju63.spring.ai.service

import org.springframework.ai.document.Document
import org.springframework.ai.reader.ExtractedTextFormatter
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.transformer.KeywordMetadataEnricher
import org.springframework.ai.transformer.SummaryMetadataEnricher
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component


@Component
class EmbeddingServiceImpl(
    private val tokenTextSplitter: TokenTextSplitter,
    private val keywordMetadataEnricher: KeywordMetadataEnricher,
    private val summaryMetadataEnricher: SummaryMetadataEnricher,
    private val vectorStore: VectorStore) : EmbeddingService {
    override fun embedded(fileName: String, resources: Resource): List<Document> {
        val pdfReader = PagePdfDocumentReader(
            resources,
            PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(
                    ExtractedTextFormatter.builder()
                        .withNumberOfTopTextLinesToDelete(0)
                        .build()
                )
                .withPagesPerDocument(1)
                .build()
        )

        val documents = summaryMetadataEnricher.transform(keywordMetadataEnricher.transform(tokenTextSplitter.split(pdfReader.read())))
        vectorStore.add(documents)
        return documents
    }
}