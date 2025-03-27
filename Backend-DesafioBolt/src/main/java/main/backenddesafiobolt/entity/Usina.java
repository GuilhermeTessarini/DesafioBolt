package main.backenddesafiobolt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usinas",
        indexes = @Index(name = "idx_usina_potencia", columnList = "mda_potencia_outorgada_kw"))

public class Usina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único da usina")
    private Long id;

    @Column(name = "dat_geracao_conjunto_dados", length = 50)
    @Comment("Data de geração do conjunto de dados")
    private String datGeracaoConjuntoDados;

    @Column(name = "cod_ceg", length = 21)
    @Comment("Código CEG da usina")
    private String codCEG;

    @Column(name = "sig_uf_principal", length = 2)
    @Comment("Sigla da UF principal")
    private String sigUFPrincipal;

    @Column(name = "dsc_origem_combustivel", length = 50)
    @Comment("Descrição da origem do combustível")
    private String dscOrigemCombustivel;

    @Column(name = "sig_tipo_geracao", length = 5)
    @Comment("Sigla do tipo de geração")
    private String sigTipoGeracao;

    @Column(name = "nom_empreendimento")
    @Comment("Nome do empreendimento")
    private String nomEmpreendimento;

    @Column(name = "mda_potencia_outorgada_kw", length = 50)
    @Comment("Potência outorgada em kW")
    private String mdaPotenciaOutorgadaKw;

    @Column(name = "dsc_propri_regime_pariticipacao", length = 500)
    @Comment("Descrição do regime de participação")
    private String dscPropriRegimePariticipacao;

    @Column(name = "dsc_tipo_conexao", length = 100)
    @Comment("Descrição do tipo de conexão")
    private String dscTipoConexao;

    @Column(name = "nom_conexao", length = 200)
    @Comment("Nome da conexão")
    private String nomConexao;

    @Column(name = "mda_tensao_conexao", length = 50)
    @Comment("Tensão de conexão")
    private String mdaTensaoConexao;

    @Column(name = "dsc_situacao_obra", length = 100)
    @Comment("Descrição da situação da obra")
    private String dscSituacaoObra;
}
