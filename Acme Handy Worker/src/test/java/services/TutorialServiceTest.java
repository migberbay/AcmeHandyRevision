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
			Assert.notNull(tutorial.getMoment());
			Assert.notNull(tutorial.getHandyWorker());
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
			Tutorial tutorial,saved;
			super.authenticate("handyworker1");						
			tutorial = (Tutorial) tutorialService.findAll().toArray()[0];
			String old = tutorial.getTitle();
			String newT = "Titulo update test";
			tutorial.setTitle(newT);	
			saved = tutorialService.save(tutorial);				
			Assert.isTrue(old != newT && newT.equals(saved.getTitle()));
			super.authenticate(null);
		}
		
		// DELETE ---------------------------------------------------------------------

		@Test 
		public void testDeleteTutorials(){
			Tutorial tutorial;
			Collection<Tutorial> tutorials;
			super.authenticate("handyworker1");								

			tutorial = (Tutorial) tutorialService.findAll().toArray()[0];						

			tutorialService.delete(tutorial);									
			tutorials = tutorialService.findAll();						
			Assert.isTrue(!tutorials.contains(tutorial));						
			
			super.authenticate(null);
		}
}
