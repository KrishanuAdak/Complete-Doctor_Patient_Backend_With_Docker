package ai.service.ai_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ai.service.ai_service.dto.Doctor_Details_Dto;
import ai.service.ai_service.service.EmbeddingService;
import ai.service.ai_service.service.ExcelService;
import ai.service.ai_service.service.RagService;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private RagService ragService;
    @Autowired
    private EmbeddingService embeddingService;
    @Autowired
    private ExcelService excelService;

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String query) throws JsonProcessingException {
        return ragService.searchAndAnswer(query);
    }


    @GetMapping("/doctors")
    public List<Doctor_Details_Dto> test() throws Exception {
        List<Doctor_Details_Dto> doctors = this.excelService.loadDoctors();
        return doctors;
    }

   

}
