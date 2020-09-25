package org.piangles.backbone.services.config.dao;

import org.piangles.backbone.services.config.Configuration;
import org.piangles.core.dao.DAOException;

public interface ConfigDAO
{
	public Configuration retrieveConfiguration(String componentId) throws DAOException;
}
