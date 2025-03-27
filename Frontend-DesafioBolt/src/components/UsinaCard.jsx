const UsinaCard = ({ usina }) => {
  return (
    <div className="usina-card">
      <h2>{usina.nomEmpreendimento}</h2>
      <p><strong>Potência:</strong> {usina.mdaPotenciaOutorgadaKw} kW</p>
      <p><strong>Estado:</strong> {usina.sigUFPrincipal}</p>
      <p><strong>Tipo:</strong> {usina.sigTipoGeracao}</p>
    </div>
  );
};
  
  export default UsinaCard;