package main.backenddesafiobolt.service;

import main.backenddesafiobolt.entity.Usina;
import main.backenddesafiobolt.repository.UsinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CsvDownloadService {

    private final UsinaRepository repository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CsvDownloadService(UsinaRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 * * * ?") // Executa a cada hora
    public void downloadAndProcessCsv() {
        try (BufferedReader reader = getBufferedReader()) {

            repository.deleteAllInBatch();
            repository.resetAutoIncrement();

            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] colunas = linha.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                for (int i = 0; i < colunas.length; i++) {
                    colunas[i] = colunas[i].replaceAll("^\"|\"$", "").trim();
                }

                try {
                    Usina usina = getUsina(colunas);
                    repository.save(usina);
                } catch (Exception e) {
                    System.err.println("[" + LocalDateTime.now().format(DATE_FORMATTER) + "] Erro ao processar linha: " + linha);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println("[" + LocalDateTime.now().format(DATE_FORMATTER) + "] Erro ao baixar/processar CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Usina getUsina(String[] colunas) {
        Usina usina = new Usina();

        usina.setDatGeracaoConjuntoDados(getValueSafe(colunas, 0));
        usina.setCodCEG(getValueSafe(colunas, 3));
        usina.setSigUFPrincipal(getValueSafe(colunas, 4));
        usina.setDscOrigemCombustivel(getValueSafe(colunas, 5));
        usina.setSigTipoGeracao(getValueSafe(colunas, 6));
        usina.setNomEmpreendimento(getValueSafe(colunas, 7));
        usina.setMdaPotenciaOutorgadaKw(getValueSafe(colunas, 8));
        usina.setDscPropriRegimePariticipacao(getValueSafe(colunas, 9));
        usina.setDscTipoConexao(getValueSafe(colunas, 10));
        usina.setNomConexao(getValueSafe(colunas, 11));
        usina.setMdaTensaoConexao(getValueSafe(colunas, 12));
        usina.setDscSituacaoObra(getValueSafe(colunas, 16));
        return usina;
    }

    private static String getValueSafe(String[] colunas, int index) {
        return (index < colunas.length && !colunas[index].isEmpty()) ?
                colunas[index].trim() : null;
    }

    private static BufferedReader getBufferedReader() throws IOException {
        URL url = new URL("https://dadosabertos.aneel.gov.br/dataset/57e4b8b5-a5db-40e6-9901-27ca629d0477/resource/4a615df8-4c25-48fa-bbea-873a36a79518/download/ralie-usina.csv");
        return new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));
    }
}


