package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Section;
import domain.Tutorial;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class SectionServiceTest extends AbstractTest{
	
	// Service under test
	
		@Autowired
		private SectionService sectionService;
		
		@Autowired
		private TutorialService tutorialService;
		
		// Test
		
		@Test
		public void testCreate(){
			
			super.authenticate("handyworker1");
			
			Section res = sectionService.create();

			Assert.isNull(res.getText());
			Assert.isNull(res.getTitle());
			Assert.isTrue(res.getPictures().isEmpty());
			Assert.isNull(res.getTutorial());
			
			super.authenticate(null);
		}
		
		@Test
		public void testSave(){
			
			super.authenticate("handyworker1");
			Tutorial tutorial = (Tutorial) tutorialService.findAll().toArray()[0];;
			Section res = sectionService.create();
			
			res.setText("Texto de prueba");
			res.setTitle("Título de prueba");
			res.getPictures().add("www.picture.com");
			
			res.setTutorial(tutorial);
			Section saved = sectionService.save(res);
			
			Assert.isTrue(sectionService.findAll().contains(saved));
			
			super.authenticate(null);
		}
		
		@Test
		public void testDelete(){
			
			Section section;
			Collection<Section> sections;
			super.authenticate("handyworker1");


			section = (Section) sectionService.findAll().toArray()[0];
			
			sectionService.delete(section);
			
			sections = sectionService.findAll();	
			Assert.isTrue(!sections.contains(section));
			
			super.authenticate(null);
		}
}
