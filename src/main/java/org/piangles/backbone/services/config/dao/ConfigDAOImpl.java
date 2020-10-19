package org.piangles.backbone.services.config.dao;

import org.piangles.backbone.services.config.Configuration;
import org.piangles.core.dao.DAOException;
import org.piangles.core.dao.rdbms.AbstractDAO;
import org.piangles.core.resources.ResourceManager;
import org.piangles.core.util.central.CentralConfigProvider;

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

		super.executeSPQueryList(GET_CONFIGURATION_SP, 1, (call) -> {
			call.setString(1, componentId);
		}, (rs, call) -> {
			configuration.addNameValue(rs.getString(NAME), rs.getString(VALUE));
			return null; //Since we already are modifying the list in configuration here
		});

		if (!configuration.getProperties().isEmpty())
		{
			retConfig = configuration;
		}
		return retConfig;

	}
}
