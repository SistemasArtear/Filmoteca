package com.artear.filmo.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.common.Empresa;
import com.artear.filmo.bo.common.Empresa.EmpresaColumns;
import com.artear.filmo.daos.interfaces.EmpresaDao;

@Repository
public class JdbcEmpresaDao implements EmpresaDao {

	private JdbcTemplate jdbcTemplate;
	private static String RETRIEVE_BY_ID = "SELECT * FROM LEG_MAEMPRESA WHERE " + EmpresaColumns.MAEMKEMPRESA.name() + " = " ;
	
	@Autowired
	public JdbcEmpresaDao(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Empresa> retrieveById(Integer idEmpresa) {
		List<Empresa> listaEmpresas = new ArrayList<Empresa>();
		listaEmpresas = (List<Empresa>) jdbcTemplate.query(RETRIEVE_BY_ID + idEmpresa , new RowMapper<Empresa>() {
			@Override
			public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Empresa empresa = new Empresa();
				empresa.setIdEmpresa(rs.getShort(EmpresaColumns.MAEMKEMPRESA.name()));
				empresa.setDescripcionCompleta(rs.getString(EmpresaColumns.MAEMDDESCRI.name()));
				empresa.setDescripcionCorta(rs.getString(EmpresaColumns.MAEMADESCORTA.name()));
				empresa.setEstadoEmpresa(rs.getString(EmpresaColumns.MAEMESTADO.name()));
				empresa.setCuit(rs.getString(EmpresaColumns.MAEMCUIT.name()));
				empresa.setRazonSocial(rs.getString(EmpresaColumns.MAEMRAZSOC.name()));
				empresa.setNroIIBB((rs.getString(EmpresaColumns.MAEMNNROIIBB.name())));
				return empresa;
			}
		});
		
		return listaEmpresas;
	}
	
}
