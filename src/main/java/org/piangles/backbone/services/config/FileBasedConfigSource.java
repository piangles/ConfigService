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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.piangles.core.expt.ServiceRuntimeException;
import org.piangles.core.util.Logger;
import org.piangles.core.util.central.CentralClient;

class FileBasedConfigSource implements ConfigSource
{
	private static final String SANDBOX = "sandbox";
	private static final String CONFIG_STORE = "config.location";
	
	private Map<String, Configuration> componentIdConfigStore = null;
	
	FileBasedConfigSource() throws Exception
	{
		Properties tier1Config = CentralClient.getInstance().tier1Config(ConfigService.NAME);
		
		String configStoreLocation = tier1Config.getProperty(CONFIG_STORE);

		try (BufferedReader br = new BufferedReader(new FileReader(configStoreLocation)))
		{
			componentIdConfigStore = new HashMap<>();

			Configuration configIdProps = null;
			//Environment,ComponentId,ApplicationId,ComponentDescription,Name,Value
			String header = null;
			String line = null;
			String env, componentId, appId, componentDesc, name, value;
			env = componentId = appId = componentDesc = name = value = null;
			
			while ((line = br.readLine()) != null)
			{
				line = StringUtils.trimToNull(line);
				if (line == null)
				{
					continue;
				}
				
				if (header == null)
				{
					header = line;
					Logger.getInstance().info("Header: " + header);
					continue;
				}
				
				// process the line.
				String[] params = line.split(",");
				
				env = params[0];
				componentId = params[1];
				appId = params[2];
				componentDesc = params[3];
				name = params[4];
				value = params[5];
				
				if (!env.equals(SANDBOX))
				{
					throw new ServiceRuntimeException("Invalid environment in Configuration. Only sandbox is supported."); 
				}
				
				configIdProps = componentIdConfigStore.get(componentId);
				if (configIdProps == null)
				{
					Logger.getInstance().info("Creating Configuration for AppId: " + appId + " and ComponentDesc: " + componentDesc);
					configIdProps = new Configuration();
					componentIdConfigStore.put(componentId, configIdProps);
				}
				
				configIdProps.addNameValue(name, value);
			}
		}
		catch (IOException e)
		{
			String message = "FileBasedConfigSource could not be created. Reason: " + e.getMessage();
			Logger.getInstance().fatal(message, e);

			throw new Exception(message, e);
		}
	}
	
	public Configuration retrieveConfiguration(String componentId) throws ConfigException
	{
		return componentIdConfigStore.get(componentId);
	}
}
