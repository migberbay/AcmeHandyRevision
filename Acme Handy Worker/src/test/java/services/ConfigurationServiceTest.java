package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;

import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private ConfigurationService configurationService;
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateConfigurations(){
		Configuration configuration;
		super.authenticate("admin");
		configuration = configurationService.create();
		Assert.isNull(configuration.getBanner());
		Assert.isNull(configuration.getDefaultPhoneCode());
		Assert.isNull(configuration.getFinderCacheTime());
		Assert.isNull(configuration.getFinderQueryResults());
		Assert.isNull(configuration.getVatPercentage());
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveConfigurations(){
		Configuration configuration,saved;
		Collection<Configuration> configurations;
		super.authenticate("admin");
		configuration = configurationService.find();
		
		configuration.setDefaultPhoneCode("+66");

		saved = configurationService.save(configuration);

		configurations = configurationService.findAll();

		Assert.isTrue(configurations.contains(saved));
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateConfiguration(){
		Configuration configuration = new Configuration();
		super.authenticate("admin");
		configuration = (Configuration) configurationService.findAll().toArray()[0];

		configuration.setBanner("http://www.pixiv.com");
	
		configurationService.save(configuration);
		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	@Test 
	public void testDeleteConfigurations(){
		Configuration configuration;
		Collection<Configuration> configurations;
		super.authenticate("admin");
		
		configuration = (Configuration) configurationService.findAll().toArray()[0];	

		configurationService.delete(configuration);
		configurations = configurationService.findAll();						
		Assert.isTrue(!configurations.contains(configuration));
		
		super.authenticate(null);
	}
	
	
}