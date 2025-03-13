package com.summerizer.blog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String defaultModel; // Default model if not specified

    public String generateSummary(String content) {
        return generateSummary(content, defaultModel);
    }

    public String generateSummary(String content, String model) {
        OpenAiService service = new OpenAiService(openaiApiKey);

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system",
                "You are a helpful assistant that summarizes blog content in 2-3 sentences."));
        messages.add(new ChatMessage("user", "Summarize the following blog content in 2-3 sentences:\n\n" + content));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(model)
                .maxTokens(100)
                .build();

        return service.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }
}