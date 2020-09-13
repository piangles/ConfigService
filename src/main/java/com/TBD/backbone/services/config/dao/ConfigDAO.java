package com.TBD.backbone.services.config.dao;

import com.TBD.backbone.services.config.Configuration;
import com.TBD.core.dao.DAOException;

public interface ConfigDAO
{
	public Configuration retrieveConfiguration(String componentId) throws DAOException;
}
