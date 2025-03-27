import { triggerCsvDownload } from '../api/usinaService';
import { useState } from 'react';

const DownloadCsvButton = () => {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const handleDownload = async () => {
    setLoading(true);
    setMessage('');
    try {
      const result = await triggerCsvDownload();
      setMessage(result || "Dados atualizados com sucesso!");
    } catch (error) {
      if (error.code === 'ECONNABORTED') {
        setMessage("O download demorou muito tempo. Tente novamente.");
      } else {
        setMessage("Erro ao baixar CSV: " + error.message);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="download-container">
      <button 
        onClick={handleDownload} 
        disabled={loading}
        className="download-button"
      >
        {loading ? 'Processando...' : 'Atualizar Dados'}
      </button>
      {message && <p className="download-message">{message}</p>}
    </div>
  );
};

export default DownloadCsvButton;