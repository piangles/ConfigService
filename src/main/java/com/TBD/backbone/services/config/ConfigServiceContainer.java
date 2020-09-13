package com.TBD.backbone.services.config;

import com.TBD.core.email.EmailSupport;
import com.TBD.core.services.Service;
import com.TBD.core.services.remoting.AbstractContainer;
import com.TBD.core.services.remoting.AuditableControllerServiceDelegate;
import com.TBD.core.services.remoting.ContainerException;

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
		super("ConfigService");
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
