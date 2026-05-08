package ai.service.ai_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;

@Service
public class EmbeddingService {
        @Autowired
        private EmbeddingModel embeddingModel;
        @Autowired
        private EmbeddingStore<TextSegment> embeddingStore; // Represents a store for embeddings, also known as a vector
                                                            // database.

        public List<TextSegment> search(String query) {

                var queryEmbedding = embeddingModel.embed(query).content();

                EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                                .queryEmbedding(queryEmbedding)
                                .maxResults(3)
                                .build();

                EmbeddingSearchResult<TextSegment> result = embeddingStore.search(request);

                return result.matches().stream()
                                .map(EmbeddingMatch::embedded)
                                .toList();
        }

        public void store(String text) {

                TextSegment segment = TextSegment.from(text);
                embeddingStore.add(
                                embeddingModel.embed(segment).content(),
                                segment);
        }
}
