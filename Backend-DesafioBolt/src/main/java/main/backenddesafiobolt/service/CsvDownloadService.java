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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CsvDownloadService {

    private final UsinaRepository repository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CsvDownloadService(UsinaRepository repository) {
        this.repository = repository;
    }

    /**
     * Método agendado para executar a cada hora.
     * Faz download de um CSV, processa os dados e persiste no banco.
     */
    @Scheduled(cron = "0 0 * * * ?") // Executa a cada hora
    public void downloadAndProcessCsv() {
        try (BufferedReader reader = getBufferedReader()) {

            // Limpa a tabela antes de inserir novos dados
            repository.deleteAllInBatch();
            repository.resetAutoIncrement();

            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Split considerando aspas no conteúdo
                String[] colunas = linha.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Remove aspas e espaços em branco
                for (int i = 0; i < colunas.length; i++) {
                    colunas[i] = colunas[i].replaceAll("^\"|\"$", "").trim();
                }

                try {
                    Usina usina = getUsina(colunas);
                    saveOrUpdateUsina(usina);
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

    /**
     * Salva ou atualiza um registro de Usina baseado no CNPJ.
     * Ignora registros com CNPJ inválido.
     */
    private void saveOrUpdateUsina(Usina usina) {
        if (usina.getNumCnpjEmpresaConexao() == null || usina.getNumCnpjEmpresaConexao().isEmpty()) {
            System.err.println("[" + LocalDateTime.now().format(DATE_FORMATTER) + "] CNPJ inválido, ignorando registro.");
            return;
        }

        Optional<Usina> existingUsina = repository.findByNumCnpjEmpresaConexao(usina.getNumCnpjEmpresaConexao());

        existingUsina.ifPresent(value -> usina.setId(value.getId()));

        repository.save(usina);
    }

    /**
     * Mapeia um array de strings para um objeto Usina.
     * Usa getValueSafe para evitar IndexOutOfBoundsException.
     */
    private static Usina getUsina(String[] colunas) {
        Usina usina = new Usina();

        usina.setDatGeracaoConjuntoDados(getValueSafe(colunas, 0));
        usina.setDatRalie(getValueSafe(colunas, 1));
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
        usina.setNumCnpjEmpresaConexao(getValueSafe(colunas, 14));
        usina.setDscSituacaoObra(getValueSafe(colunas, 16));

        return usina;
    }

    /**
     * Retorna o valor do array na posição especificada ou null se inválido.
     */
    private static String getValueSafe(String[] colunas, int index) {
        return (index < colunas.length && !colunas[index].isEmpty()) ? colunas[index].trim() : null;
    }

    /**
     * Cria um BufferedReader para ler o CSV remoto com encoding ISO-8859-1.
     */
    private static BufferedReader getBufferedReader() throws IOException {
        URL url = new URL("https://dadosabertos.aneel.gov.br/dataset/57e4b8b5-a5db-40e6-9901-27ca629d0477/resource/4a615df8-4c25-48fa-bbea-873a36a79518/download/ralie-usina.csv");
        return new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));
    }
}



