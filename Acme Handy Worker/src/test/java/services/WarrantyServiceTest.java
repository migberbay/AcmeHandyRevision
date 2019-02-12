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
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class WarrantyServiceTest extends AbstractTest{
	
	// Service under test
	
	@Autowired
	private WarrantyService warrantyService;
	
	// Test
	
	@Test
	public void testCreate(){
		
		super.authenticate("admin");
		
		Warranty res = warrantyService.create();

		Assert.isNull(res.getTerms());
		Assert.isNull(res.getTitle());
		Assert.isNull(res.getLaws());
		Assert.isTrue(res.getIsDraft());
		
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		
		super.authenticate("admin");
		
		Warranty res = warrantyService.create();
		
		res.setTerms("hola");
		res.setTitle("Adios");
		res.setLaws("ley1");
		
		Warranty saved = warrantyService.save(res);
		
		Assert.isTrue(warrantyService.findAll().contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		
		super.authenticate("admin");
		
		Collection<Warranty> warranties = warrantyService.findAll();
		Warranty res = null;
		
		for(Warranty w:warranties){
			if(w.getIsDraft()==true){
				res = w;
				break;
			}
		}
		warrantyService.delete(res);
		Assert.isTrue(!warrantyService.findAll().contains(res));
		
		super.authenticate(null);
	}
	
}
