package services;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class SponsorshipServiceTest extends AbstractTest{

	// Service under test
	
	@Autowired
	private SponsorshipService ssService;
	
	@Autowired
	private TutorialService tutorialService;
	
	// Test
	
	@Test
	public void testCreate(){
		
		super.authenticate("sponsor1");
		
		Sponsorship res = ssService.create();
		
		Assert.isNull(res.getBanner());
		Assert.isNull(res.getCreditCard());
		Assert.isNull(res.getSponsor());
		Assert.isNull(res.getLink());
		Assert.isNull(res.getTutorial());
		
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		
		super.authenticate("sponsor1");
		
		Sponsorship res = ssService.create();
		
		Tutorial tutorial = (Tutorial) tutorialService.findAll().toArray()[1];
		CreditCard creditCard = new CreditCard();
		creditCard.setBrand("afiabfcabf");
		creditCard.setCVV(876);
		creditCard.setExpirationDate(Date.valueOf("2021-03-03"));
		creditCard.setHolder("dwcfbakwcnbf");
		creditCard.setNumber("1234567891234567");
		
		res.setBanner("https://banner1.com");
		res.setCreditCard(creditCard);
		res.setLink("https://link.com");
		res.setTutorial(tutorial);
		
		Sponsorship saved = ssService.save(res);
		
		Assert.isTrue(ssService.findAll().contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		
		super.authenticate("sponsor1");
		
		Sponsorship res = (Sponsorship) ssService.findAll().toArray()[1];
		
		ssService.delete(res);
		Assert.isTrue(!ssService.findAll().contains(res));
		
		super.authenticate(null);
	}
}
