import { useState, useEffect } from 'react';
import { getMaioresGeradores } from '../api/usinaService';

export const useUsinas = () => {
  const [usinas, setUsinas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsinas = async () => {
      try {
        const data = await getMaioresGeradores();
        setUsinas(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUsinas();
  }, []);

  return { usinas, loading, error };
};