/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
 
 
package org.piangles.backbone.services.config;

import org.piangles.backbone.services.config.dao.ConfigDAO;
import org.piangles.backbone.services.config.dao.ConfigDAOImpl;
import org.piangles.core.dao.DAOException;
import org.piangles.core.services.AuditDetails;
import org.piangles.core.util.Logger;

public class ConfigServiceImpl
{
	private ConfigDAO configDAO = null;

	public ConfigServiceImpl() throws Exception
	{
		configDAO = new ConfigDAOImpl();
	}

	public Configuration getConfiguration(AuditDetails details, String componentId) throws ConfigException
	{
		Configuration configuration = null;
		try
		{
			/**
			 * TODO We should record audit for configuration.
			 * configDAO.recordAudit(context);
			 * 
			 */
			configuration = configDAO.retrieveConfiguration(componentId);
		}
		catch (DAOException e)
		{
			String message = "Unable to getConfiguration for ComponentId: " + componentId;
			Logger.getInstance().error(message + ". Reason: " + e.getMessage(), e);
			throw new ConfigException(message);
		}

		if (configuration == null)
		{
			throw new ConfigException("Unable to find configuration for componentId : " + componentId);
		}

		return configuration;
	}
}
