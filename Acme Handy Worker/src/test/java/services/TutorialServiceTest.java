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
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})

@Transactional
public class TutorialServiceTest extends AbstractTest{
	
	// Service under test ---------------------------------------------------------	
		@Autowired
		private TutorialService tutorialService;
		
		// Tests ----------------------------------------------------------------------
		
		// CREATE ---------------------------------------------------------------------
		
		@Test
		public void testCreateTutorials(){
			Tutorial tutorial;
			super.authenticate("handyworker1");
			tutorial = tutorialService.create();
			Assert.isTrue(tutorial.getPictures().isEmpty());
			Assert.isNull(tutorial.getTitle());
			Assert.isNull(tutorial.getSummary());
			Assert.isNull(tutorial.getMoment());
			Assert.isNull(tutorial.getHandyWorker());
			super.authenticate(null);
		}
		
		
		// SAVE -----------------------------------------------------------------------
		
		@Test 
		public void testSaveTutorials(){
			Tutorial tutorial,saved;
			Collection<Tutorial> tutorials;
			super.authenticate("handyworker1");						
			tutorial = tutorialService.create();					
			
			tutorial.setTitle("Título de prueba 1");
			tutorial.setSummary("Esto es un sumario de prueba");
			
			saved = tutorialService.save(tutorial);					

			tutorials = tutorialService.findAll();					
			
			Assert.isTrue(tutorials.contains(saved));
			super.authenticate(null);
		}
		

		// UPDATE ---------------------------------------------------------------------
		
		@Test 
		public void testUpdateTutorials(){
			Tutorial tutorial;
			super.authenticate("handyworker1");						
			tutorial = tutorialService.findOne(15795);				
			tutorial.setTitle("Este es el nuevo título");	

			tutorialService.save(tutorial);				

			super.authenticate(null);
		}
		
		// DELETE ---------------------------------------------------------------------

		@Test 
		public void testDeleteTutorials(){
			Tutorial tutorial;
			Collection<Tutorial> tutorials;
			super.authenticate("handyworker1");								

			tutorial = tutorialService.findOne(15797);						

			tutorialService.delete(tutorial);									
			tutorials = tutorialService.findAll();						
			Assert.isTrue(!tutorials.contains(tutorial));						
			
			super.authenticate(null);
		}
}
