package com.TBD.backbone.services.config.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.TBD.backbone.services.config.Configuration;
import com.TBD.core.dao.DAOException;
import com.TBD.core.dao.rdbms.AbstractDAO;
import com.TBD.core.dao.rdbms.IndividualResultSetProcessor;
import com.TBD.core.dao.rdbms.StatementPreparer;
import com.TBD.core.resources.ResourceManager;
import com.TBD.core.util.central.CentralConfigProvider;

public class ConfigDAOImpl extends AbstractDAO implements ConfigDAO
{
	private static final String GET_CONFIGURATION_SP = "Backbone.GetConfiguration";

	private static final String NAME = "Name";
	private static final String VALUE = "Value";

	public ConfigDAOImpl() throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new CentralConfigProvider("ConfigService", "ConfigService")));
	}

	@Override
	public Configuration retrieveConfiguration(String componentId) throws DAOException
	{
		Configuration retConfig = null;
		Configuration configuration = new Configuration();

		super.executeSPQuery(GET_CONFIGURATION_SP, 1, new StatementPreparer()
		{

			@Override
			public void prepare(CallableStatement call) throws SQLException
			{
				call.setString(1, componentId);
			}
		}, new IndividualResultSetProcessor()
		{
			@Override
			public void process(ResultSet rs) throws SQLException
			{
				configuration.addNameValue(rs.getString(NAME), rs.getString(VALUE));
			}
		});

		if (configuration != null && !configuration.getProperties().isEmpty())
		{
			retConfig = configuration;
		}
		return retConfig;

	}
}
