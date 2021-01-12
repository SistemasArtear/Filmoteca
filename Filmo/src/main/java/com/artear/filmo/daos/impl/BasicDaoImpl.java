package com.artear.filmo.daos.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.artear.filmo.daos.interfaces.BasicDao;

/**
 * 
 * @author jcertoma
 * 
 */
@Repository("basicDao")
public class BasicDaoImpl implements BasicDao {

	private JdbcTemplate jdbcTemplate;   
	private static final String BASIC_QUERY = " select 1 from dual ";	
	private static final String GET_EMPRESA_DESCRIPCION = " select e.MAEMDDESCRI from LEG_MAEMPRESA e where e.MAEMKEMPRESA = ? ";	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void executeBasicQuery(){
		this.jdbcTemplate.execute(BASIC_QUERY);
	}
	
	public String getEmpresaDescripcion(short codigoEmpresa) {
		return (String) jdbcTemplate.queryForObject(GET_EMPRESA_DESCRIPCION, new Object[] { codigoEmpresa }, String.class);
		
	}

}
