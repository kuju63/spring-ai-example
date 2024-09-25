package io.github.kuju63.spring.ai

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.transformer.KeywordMetadataEnricher
import org.springframework.ai.transformer.SummaryMetadataEnricher
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.boot.web.client.ClientHttpRequestFactories
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import java.time.Duration

@Configuration
class AIConfiguration {
    @Bean
    fun tokenTextSplitter(): TokenTextSplitter {
        return TokenTextSplitter()
    }

    @Bean
    fun keywordMetadataEnricher(chatModel: ChatModel): KeywordMetadataEnricher {
        return KeywordMetadataEnricher(chatModel, 5)
    }

    @Bean
    fun summaryMetadataEnricher(chatModel: ChatModel): SummaryMetadataEnricher {
        return SummaryMetadataEnricher(chatModel,
            listOf(SummaryMetadataEnricher.SummaryType.PREVIOUS, SummaryMetadataEnricher.SummaryType.CURRENT, SummaryMetadataEnricher.SummaryType.NEXT)
        )
    }

    @Bean
    fun restClient(restClientBuilder: RestClient.Builder): RestClient {
        val clientFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS.withReadTimeout(Duration.ofMinutes(5))
        return restClientBuilder.requestFactory(ClientHttpRequestFactories.get(clientFactorySettings)).build()
    }
}