package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class CategoryServiceTest extends AbstractTest{
	
	// Service under test
	
	@Autowired
	private CategoryService catService;
	
	// Tests
	
	@Test
	public void testCreate(){
		
		super.authenticate("admin");
		
		Category res = catService.create();
		
		Assert.isNull(res.getName());
		Assert.isNull(res.getParentCategory());
		
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		
		super.authenticate("admin");

		Category res = catService.create();
		
		res.setName("Furniture");

		res.setParentCategory((Category) catService.findAll().toArray()[2]);


		Category saved = catService.save(res);
		Collection<Category> categories = catService.findAll();
		
		Assert.isTrue(categories.contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		
		super.authenticate("admin");

		//TODO: HAY UN PROBLEMA CON LA ID.
		
		Category res = (Category) catService.findAll().toArray()[4];
		catService.delete(res);
		Assert.isTrue(!catService.findAll().contains(res));
		
		super.authenticate(null);
	}
}
