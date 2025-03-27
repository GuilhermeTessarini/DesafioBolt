package main.backenddesafiobolt.repository;

import main.backenddesafiobolt.entity.Usina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsinaRepository extends JpaRepository<Usina, Long> {

    // Verifica se existe uma Usina com o CNPJ especificado
    boolean existsByNumCnpjEmpresaConexao(String numCnpjEmpresaConexao);

    // Busca uma Usina pelo CNPJ
    Optional<Usina> findByNumCnpjEmpresaConexao(String numCnpjEmpresaConexao);

    // Deleta todos os registros em lote
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usinas", nativeQuery = true)
    void deleteAllInBatch();

    // Reinicia a sequência de autoincremento do ID para 1
    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE usinas_id_seq RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();

    // Consulta as 5 usinas com maior potência outorgada
    // Converte o formato brasileiro (1.234,56) para decimal antes de ordenar
    @Query(nativeQuery = true, value = """
        SELECT * FROM usinas
        WHERE mda_potencia_outorgada_kw IS NOT NULL
        AND mda_potencia_outorgada_kw != ''
        ORDER BY
            CAST(REPLACE(REPLACE(mda_potencia_outorgada_kw, '.', ''), ',', '.') AS DECIMAL(18,2)) DESC
        LIMIT 5
        """)
    List<Usina> findTop5Generators();
}
