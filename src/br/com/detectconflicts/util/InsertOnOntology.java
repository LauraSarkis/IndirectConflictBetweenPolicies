package br.com.detectconflicts.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class InsertOnOntology {

	public static final String ONTOIRI = "http://www.owl-ontologies.com/unnamed.owl";
	
	public OWLOntology insertOntology (OWLOntology ontology) throws OWLOntologyStorageException, OWLOntologyCreationException, IOException {

		OWLOntology ontoBase = ontology;
		Engine engine = new Engine(ontoBase);	
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Do you want to insert INDIVIDUALS in classes? YES - Y / NO - Type other button. ");
		String answer = scan.nextLine();
		
		if(answer.equalsIgnoreCase("y")) {
			System.out.println("\nTo add an individual, enter the individual's name and after the class name you want to insert..");
			int aux = -1;
			
			while(aux!=0) {
				
				System.out.println("Insert Individual's Name: ");
				String individual = scan.nextLine();
				
				System.out.println("Insert Class' Name: ");
				String classe = scan.nextLine();
				
				addIndividualToClass(ontoBase, engine, individual, classe);
				
				System.out.println("Type 1 to CONTINUE adding or 0 to FINISH.");
				aux = scan.nextInt();
				scan.nextLine();
			}
		}
		

		System.out.println("\nDo you want to insert PROPERTIES in individuals? YES - Y / NO - Type other button. ");
		String answer2 = scan.nextLine();
		
		if(answer2.equalsIgnoreCase("y")) {
			
			System.out.println("To add a property, enter the name of the first individual and after the name of the second individual\n"
					+ "Lastly type the name of the property.");
			int auxx = -1;
			
			while(auxx!=0) {
	
				System.out.println("Insert first Individual's name: ");
				String individual = scan.nextLine();
				
				System.out.println("Insert second Individual's name: ");
				String individual2 = scan.nextLine();
				
				System.out.println("Insert the property: ");
				String property = scan.nextLine();
				
				addPropertyToIndividuals(ontoBase, engine, individual, individual2, property);

				System.out.println("Type 1 to CONTINUE adding or 0 to FINISH.");
				auxx = scan.nextInt();
				scan.nextLine();
				
			}
		}
		
		saveOntology(ontoBase);		
		return ontoBase;
		
	}
	
	public static void addIndividualToClass(OWLOntology ontology, Engine engine, String individual, String classe) {
		
		OWLOntology ontoBase = ontology;
		OWLDataFactory df = engine.getFactory();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		OWLClass cls = df.getOWLClass(IRI.create(ONTOIRI+"#"+classe));
		OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(ONTOIRI+"#"+individual));
		OWLClassAssertionAxiom classAssertion = df.getOWLClassAssertionAxiom(cls, ind);
		manager.addAxiom(ontoBase, classAssertion);
	}
	
	public static void addPropertyToIndividuals(OWLOntology ontology, Engine engine, String individual1, String individual2, String property) {

		OWLOntology ontoBase = ontology;
		OWLDataFactory df = engine.getFactory();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		OWLNamedIndividual ind1 = df.getOWLNamedIndividual(IRI.create(ONTOIRI+"#"+individual1));
		OWLNamedIndividual ind2 = df.getOWLNamedIndividual(IRI.create(ONTOIRI+"#"+individual2));
		OWLObjectProperty prop = df.getOWLObjectProperty(IRI.create(ONTOIRI+"#"+property));
		OWLObjectPropertyAssertionAxiom assertion = df.getOWLObjectPropertyAssertionAxiom(prop, ind1, ind2);
		manager.addAxiom(ontoBase, assertion);

	}
	
	public static void saveOntology(OWLOntology ontology) throws OWLOntologyStorageException, OWLOntologyCreationException, IOException {
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology baseOntology = ontology;
		
		File file = new File("D:\\teste\\teste.owl");
		OutputStream os = new FileOutputStream(file);
		manager.saveOntology(baseOntology, new OWLXMLOntologyFormat(), os);
		
	}

	
}
