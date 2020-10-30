package org.piangles.backbone.services.config;

import org.piangles.core.email.EmailSupport;
import org.piangles.core.services.Service;
import org.piangles.core.services.remoting.AbstractContainer;
import org.piangles.core.services.remoting.AuditableControllerServiceDelegate;
import org.piangles.core.services.remoting.ContainerException;

public class ConfigServiceContainer extends AbstractContainer
{
	public static void main(String[] args)
	{
		ConfigServiceContainer container = new ConfigServiceContainer();
		try
		{
			container.performSteps();
		}
		catch (ContainerException e)
		{
			EmailSupport.notify(e, e.getMessage());
			System.exit(-1);
		}
	}

	public ConfigServiceContainer()
	{
		super(ConfigService.NAME);
	}
	
	@Override
	protected Object createServiceImpl() throws ContainerException
	{
		Object service = null;
		try
		{
			service = new ConfigServiceImpl();
		}
		catch (Exception e)
		{
			throw new ContainerException(e);
		}
		return service;
	}
	
	@Override
	protected Service createControllerServiceDelegate()
	{
		return new AuditableControllerServiceDelegate(getServiceImpl());
	}
}
