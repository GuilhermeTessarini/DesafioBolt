import { useState, useEffect } from 'react';
import { getMaioresGeradores } from '../api/usinaService';

export const useUsinas = () => {
  const [usinas, setUsinas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log("Iniciando busca de usinas...");
    const fetchUsinas = async () => {
      try {
        const data = await getMaioresGeradores();
        console.log("Dados recebidos:", data);
        setUsinas(data);
      } catch (err) {
        console.error("Erro detalhado:", err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUsinas();
  }, []);

  return { usinas, loading, error };
};