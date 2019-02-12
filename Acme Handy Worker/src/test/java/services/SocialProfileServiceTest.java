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
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private SocialProfileService socialProfileService;
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateSocialProfile(){
		SocialProfile socialProfile;
		socialProfile = socialProfileService.create();
		Assert.isNull(socialProfile.getLink());
		Assert.isNull(socialProfile.getNick());
		Assert.isNull(socialProfile.getSocialNetwork());
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSocialProfile(){
		SocialProfile socialProfile,saved;
		Collection<SocialProfile> socialProfiles;
		socialProfile = socialProfileService.create();
		
		socialProfile.setLink("http://twitter.com/test");
		socialProfile.setNick("test");
		socialProfile.setSocialNetwork("Twitter");
		
		saved = socialProfileService.save(socialProfile);

		socialProfiles = socialProfileService.findAll();

		Assert.isTrue(socialProfiles.contains(saved));
	}
	

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateSocialProfiles(){
		SocialProfile socialProfile,saved;
		socialProfile = (SocialProfile)socialProfileService.findAll().toArray()[0];
		socialProfile.setNick("test1");

		saved = socialProfileService.save(socialProfile);
		
		Assert.isTrue(saved.getNick().equals("test1"));
	}
	
	// DELETE ---------------------------------------------------------------------

	@Test 
	public void testDeleteSocialProfile(){
		SocialProfile socialProfile;
		Collection<SocialProfile> socialProfiles;
		
		socialProfile = (SocialProfile) socialProfileService.findAll().toArray()[0];

		socialProfileService.delete(socialProfile);									// Eliminamos la nota	
		socialProfiles = socialProfileService.findAll();						
		Assert.isTrue(!socialProfiles.contains(socialProfile));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
		
		super.authenticate(null);
	}
	
	
}