package main.backenddesafiobolt.repository;

import main.backenddesafiobolt.entity.Usina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsinaRepository extends JpaRepository<Usina, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM usinas
        WHERE mda_potencia_outorgada_kw IS NOT NULL
        AND mda_potencia_outorgada_kw != ''
        ORDER BY
            CAST(REPLACE(REPLACE(mda_potencia_outorgada_kw, '.', ''), ',', '.') AS DECIMAL(18,2)) DESC
        LIMIT 5
        """)
    List<Usina> findTop5MaioresGeradores();
}
