package main.backenddesafiobolt.controller;

import main.backenddesafiobolt.entity.Usina;
import main.backenddesafiobolt.repository.UsinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usinas")
public class UsinaController {

    //http://localhost:8080/usinas/maiores-geradores

    private final UsinaRepository repository;

    @Autowired
    public UsinaController(UsinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/maiores-geradores")
    public List<Usina> getTop5MaioresGeradores() {
        return repository.findTop5MaioresGeradores();
    }
}
