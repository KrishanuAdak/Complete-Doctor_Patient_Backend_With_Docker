package ai.service.ai_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
@Service
public class DataLoaderService {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private EmbeddingService embeddingService;

    @PostConstruct
    public void init() throws Exception {
        excelService.loadDoctors().forEach(d->{
            String text= d.getSearchText()+ " " +d.getReviews();
            embeddingService.store(text);
            System.out.println("📌 Stored embedding for doctor: " + text);

        });
    }

}
