import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.quarkus.smallrye.health.runtime.SmallRyeHealthBuildFixedConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Book;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Map;

@ApplicationScoped
public class LibrarianService  {

    @ConfigProperty(name = "quarkus.langchain4j.openai.api-key")
    String key;
    @ConfigProperty(name = "quarkus.langchain4j.openai.chat-model.model-name")
    String modelName;
    @ConfigProperty(name = "quarkus.langchain4j.openai.base-url")
    String baseUrl;

    public String createBook(String topic) {
        PromptTemplate promptTemplate = PromptTemplate
                .from("Create a JSON object with a fictional book information about {{topic}}. Filelds should include title, author, publisher, and any other field a librarian might consider relevant. Return your answer in json format");
        Map<String, Object> variables = Map.of("topic", topic);
        Prompt prompt = promptTemplate.apply(variables);
        ChatModel model = OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(key)
                .modelName(modelName)
                .temperature(0.2)
                .timeout(Duration.of(2, java.time.temporal.ChronoUnit.MINUTES))
                .build();
       return model.chat(prompt.text());
    }




}
