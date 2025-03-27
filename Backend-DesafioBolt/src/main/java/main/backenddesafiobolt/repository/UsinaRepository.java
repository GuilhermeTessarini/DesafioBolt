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
    boolean existsByNumCnpjEmpresaConexao(String numCnpjEmpresaConexao);
    Optional<Usina> findByNumCnpjEmpresaConexao(String numCnpjEmpresaConexao);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usinas", nativeQuery = true)
    void deleteAllInBatch();

    // Para PostgreSQL:
    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE usinas_id_seq RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();

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
