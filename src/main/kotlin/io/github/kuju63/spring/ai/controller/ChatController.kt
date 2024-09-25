package io.github.kuju63.spring.ai.controller

import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ChatController(private val chatModel: ChatModel) {
    @GetMapping("/ai/generate")
    fun generate(
        @RequestParam(value = "message", defaultValue = "Tell me a joke") message: String): ChatResponse {
        val userMessage = UserMessage(message)
        val prompt = Prompt(userMessage)
        return chatModel.call(prompt)
    }

    @GetMapping("/ai/generateStream")
    fun generateStream(
        @RequestParam(value = "message", defaultValue = "Tell me a joke") message: String): Flux<ChatResponse> {
        val prompt = Prompt(message)
        return chatModel.stream(prompt)
    }
}
