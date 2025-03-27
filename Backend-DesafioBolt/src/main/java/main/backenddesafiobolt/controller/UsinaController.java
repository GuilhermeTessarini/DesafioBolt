package main.backenddesafiobolt.controller;

import main.backenddesafiobolt.entity.Usina;
import main.backenddesafiobolt.repository.UsinaRepository;
import main.backenddesafiobolt.service.CsvDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usinas")
public class UsinaController {

    private final UsinaRepository repository;
    private final CsvDownloadService csvDownloadService;

    @Autowired
    public UsinaController(UsinaRepository repository, CsvDownloadService csvDownloadService) {
        this.repository = repository;
        this.csvDownloadService = csvDownloadService;
    }

    //http://localhost:8080/usinas/maiores-geradores
    @GetMapping("/maiores-geradores")
    public ResponseEntity<List<Usina>> getTop5Generators() {
        List<Usina> usinas = repository.findTop5Generators();
        if (usinas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usinas);
    }

    //http://localhost:8080/usinas/baixar-csv
    @GetMapping("/baixar-csv")
    public ResponseEntity<String> downloadCsvManually() {
        csvDownloadService.downloadAndProcessCsv();
        return ResponseEntity.ok("CSV baixado e processado com sucesso!");
    }
}
