import api from './axiosConfig';

export const getMaioresGeradores = async () => {
  try {
    const response = await api.get('/usinas/maiores-geradores');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const triggerCsvDownload = async () => {
  try {
    const response = await api.get('/usinas/baixar-csv');
    return response.data;
  } catch (error) {
    throw error;
  }
};