import api from './axiosConfig';

export const getMaioresGeradores = async () => {
  try {
    console.log("Iniciando requisição...");
    const response = await api.get('/usinas/maiores-geradores');
    console.log("Resposta recebida:", response);
    return response.data;
  } catch (error) {
    console.error('Erro completo:', error.response);
    throw error;
  }
};