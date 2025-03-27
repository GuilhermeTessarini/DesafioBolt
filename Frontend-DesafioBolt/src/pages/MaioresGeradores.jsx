import { useUsinas } from '../hooks/useUsinas';
import UsinaCard from '../components/UsinaCard';
import LoadingSpinner from '../components/LoadingSpinner';

const MaioresGeradores = () => {
  const { usinas, loading, error } = useUsinas();

  if (loading) return <LoadingSpinner />;
  if (error) return <p className="error">Erro: {error}</p>;

  return (
    <div className="container">
      <h1>Top 5 Maiores Geradores do Brasil</h1>
      <div className="usinas-grid">
        {usinas.map((usina) => (
          <UsinaCard key={usina.id} usina={usina} />
        ))}
      </div>
    </div>
  );
};

export default MaioresGeradores;