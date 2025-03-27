package main.backenddesafiobolt.controller;

import main.backenddesafiobolt.service.CsvDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class CsvDownloadController {

    //http://localhost:8080/api/baixar-csv
    private final CsvDownloadService csvDownloadService;

    @Autowired
    public CsvDownloadController(CsvDownloadService csvDownloadService) {
        this.csvDownloadService = csvDownloadService;
    }

    @GetMapping("/baixar-csv")
    public ResponseEntity<String> baixarCsvManualmente() {
        csvDownloadService.baixarEProcessarCsv();
        return ResponseEntity.ok("CSV baixado e processado com sucesso!");
    }
}
