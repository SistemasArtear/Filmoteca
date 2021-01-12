package com.artear.filmo.daos.impl.pl;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.daos.interfaces.BloqueoContratoDao;

@Repository("bloqueoContratoDao")
public class BloqueoContratoDaoPLImpl implements BloqueoContratoDao {

	private SimpleJdbcCall bloquearContratoCall;
	private SimpleJdbcCall desbloquearContratoCall;
	private SimpleJdbcCall estaBloqueadoCall;

	@Autowired
	public BloqueoContratoDaoPLImpl(DataSource dataSource) {
		super();
		this.bloquearContratoCall = new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_BLOQUEO_CONTRATOS").withProcedureName("PR_BLOQUEAR_CONTRATO");
		this.desbloquearContratoCall = new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_BLOQUEO_CONTRATOS").withProcedureName("PR_DESBLOQUEAR_CONTRATO");
		this.estaBloqueadoCall = new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_BLOQUEO_CONTRATOS").withProcedureName("PR_ESTA_BLOQUEADO");
	}

    @Override
    public boolean estaBloqueado(Integer claveContrato) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", claveContrato);
    
        Map<String, Object> out = this.estaBloqueadoCall.execute(in);

        String estaBloqueado = (String) out.get("P_BLOQUEADO");

        return StringUtils.equals(estaBloqueado, "S");
    }

    @Override
    public void bloquearContrato(Integer claveContrato, String sessionId, String user) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_USUARIO", user)
            .addValue("P_CONTRATO", claveContrato)
            .addValue("P_SESSION_ID", sessionId);
        
        this.bloquearContratoCall.execute(in);
    }

    @Override
    public void desbloquearContrato(Integer claveContrato) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", claveContrato);
        
        this.desbloquearContratoCall.execute(in);
    }

}
