import { useUsinas } from "../hooks/useUsinas";
import DownloadCsvButton from '../components/DownloadCsvButton';

const MaioresGeradores = () => {
  const { usinas, error } = useUsinas();

  if (error) return <p className="error">Erro: {error}</p>;

  if (usinas.length === 0) {
    return (
      <div className="container">
        <h1 className="title">Nenhum dado encontrado</h1>
        <p className="no-data-message">
          Nenhum dado foi encontrado no banco. Clique no botão abaixo para atualizar os dados.
        </p>
        <DownloadCsvButton />
      </div>
    );
  }

  return (
    <div className="container">
      <h1 className="title">Top 5 Maiores Geradores do Brasil</h1>
      <DownloadCsvButton />
      <table className="usinas-table">
        <thead>
          <tr>
            <th>#</th>
            <th>Empreendimento</th>
            <th>Potência (kW)</th>
            <th>Estado</th>
            <th>Combustível</th>
            <th>Tipo de geração</th>
            <th>Descrição do tipo de conexão</th>
            <th>Nome da conexão</th>
            <th>Tensão de conexão</th>
            <th>Situação da obra</th>
            <th>Data de geração do conjunto de dados</th>
            <th>Data do RALIE</th>
            <th>Código CEG da usina</th>
            <th>Descrição do regime de participação</th>
          </tr>
        </thead>
        <tbody>
          {usinas.map((usina, index) => (
            <tr key={usina.id}>
              <td>{index + 1}</td>
              <td>{usina.nomEmpreendimento}</td>
              <td>{usina.mdaPotenciaOutorgadaKw}</td>
              <td>{usina.sigUFPrincipal}</td>
              <td>{usina.dscOrigemCombustivel}</td>
              <td>{usina.sigTipoGeracao}</td>
              <td>{usina.dscTipoConexao}</td>
              <td>{usina.nomConexao}</td>
              <td>{usina.mdaTensaoConexao}</td>
              <td>{usina.dscSituacaoObra}</td>
              <td>{usina.datGeracaoConjuntoDados}</td>
              <td>{usina.datRalie}</td>
              <td>{usina.codCEG}</td>
              <td>{usina.dscPropriRegimePariticipacao}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default MaioresGeradores;