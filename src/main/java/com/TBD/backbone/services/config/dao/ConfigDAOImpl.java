package com.TBD.backbone.services.config.dao;

import com.TBD.backbone.services.config.Configuration;
import com.TBD.core.dao.DAOException;
import com.TBD.core.dao.rdbms.AbstractDAO;
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

		super.executeSPQueryProcessIndividual(GET_CONFIGURATION_SP, 1, (call) -> {
			call.setString(1, componentId);
		}, (rs) -> {
			configuration.addNameValue(rs.getString(NAME), rs.getString(VALUE));
		});

		if (configuration != null && !configuration.getProperties().isEmpty())
		{
			retConfig = configuration;
		}
		return retConfig;

	}
}
