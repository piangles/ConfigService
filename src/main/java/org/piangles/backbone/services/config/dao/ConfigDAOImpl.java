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
 
 
 
package org.piangles.backbone.services.config.dao;

import org.piangles.backbone.services.config.ConfigService;
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
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new CentralConfigProvider(ConfigService.NAME, ConfigService.NAME)));
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
