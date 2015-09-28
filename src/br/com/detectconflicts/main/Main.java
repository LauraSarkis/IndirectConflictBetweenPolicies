package br.com.detectconflicts.main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import br.com.detectconflicts.combinations.CombinePolicies;
import br.com.detectconflicts.util.Engine;
import br.com.detectconflicts.util.InsertOnOntology;

public class Main {

	static OWLOntology ontology;
	
	public static void main(String[] args) throws OWLOntologyStorageException, OWLOntologyCreationException, IOException {

		int totalBeforePropagation = 0;
		ontology = loadOntology("D:\\Dropbox\\Dropbox\\Faculdade\\Disciplinas\\TCC I\\Arquivos OWL\\testecompleto2.owl");
		
		ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
		Engine engine = new Engine(ontology);
		
		InsertOnOntology ioo = new InsertOnOntology();
		ontology = ioo.insertOntology(ontology);
		long startTime = System.currentTimeMillis(); //Tempo inicial
		CombinePolicies combine = new CombinePolicies();
		Set<Policie> setOfPolicies = new LinkedHashSet<Policie>();
		setOfPolicies.addAll(combine.combinePoliciesPermitted(ontology));
		setOfPolicies.addAll(combine.combinePoliciesForbidden(ontology));
		setOfPolicies.addAll(combine.combinePoliciesObliged(ontology));

		totalBeforePropagation = setOfPolicies.size();
		int totalConflicts = 0;

		System.out.println("\n******* START PROPAGATION *******\n\n");
		System.out.println("******* CONTEXT PROPAGATION *******");
		ContextPropagation contextPropagation = new ContextPropagation(setOfPolicies, shortFormProvider);
		setOfPolicies = contextPropagation.propagate(engine);
		
		System.out.println("******* ROLE PROPAGATION *******");
		RolePropagation rolePropagation = new RolePropagation(setOfPolicies, shortFormProvider);
		setOfPolicies = rolePropagation.propagate(engine);
		System.out.println("****** ENTITY PROPAGATION ********");
	
		EntityPropagation entityPropagation = new EntityPropagation(setOfPolicies, shortFormProvider);
		setOfPolicies = entityPropagation.propagate(engine);
		
		System.out.println("******* OBJECT COMPOSITION PROPAGATION *******");
		ObjectCompositionPropagation objectCompositionPropagation = new ObjectCompositionPropagation(setOfPolicies, shortFormProvider);
		setOfPolicies = objectCompositionPropagation.propagate(engine);
		System.out.println("*************************************************\n");
		
		System.out.println("\n\n*********** CONFLICTS **********");		
		ConflictChecker conflictChecker = new ConflictChecker(setOfPolicies);
		totalConflicts = conflictChecker.coursePolicies();	
		
		long endTime = System.currentTimeMillis(); //Tempo final
		
		System.out.println("Total Conflicts: "+totalConflicts);
		System.out.println();
		System.out.println("Total Context Propagation: "+contextPropagation.totalContextPropagation);
		System.out.println("Total Role Propagation: "+rolePropagation.totalRolePropagation);
		System.out.println("Total Entity PLAY Propagation: "+entityPropagation.totalPlayPropagation);
		System.out.println("Total Entity OWNERSHIP Propagation: "+entityPropagation.totalOwnershipPropagation);
		System.out.println("Total Object Composition Propagation: "+objectCompositionPropagation.totalObjectCompositionPropagation);
		System.out.println();
		System.out.println("Total Policies Before Propagation: "+totalBeforePropagation);
		System.out.println("Total Policies After propagation: "+setOfPolicies.size());
		System.out.println("Total Propagated Policies: "+(setOfPolicies.size()-totalBeforePropagation));
		System.out.println("\nElapsed Time Processing: "+(endTime-startTime)+" milliseconds.");

		System.out.println("\nTotal policies that conflicted because of Context Propagation: "+conflictChecker.pConflictContext.size());
		System.out.println("Total policies that conflicted because of  Play: "+conflictChecker.pConflictPlay.size());
		System.out.println("Total policies that conflicted because of Ownership: "+conflictChecker.pConflictOwnership.size());
		System.out.println("Total policies that conflicted because of Role Propagation: "+conflictChecker.pConflictRole.size());
		System.out.println("Total policies that conflicted because of Object Composition: "+conflictChecker.pConflictObjectComposition.size());
		System.out.println();
		System.out.println("Total policies that conflicted because of propagations : "+(conflictChecker.pConflictContext.size()+
				conflictChecker.pConflictPlay.size()+
				conflictChecker.pConflictOwnership.size()+
				conflictChecker.pConflictRole.size()+
				conflictChecker.pConflictObjectComposition.size()
				));
		System.out.println("\nTotal policies that conflicted because of Context Propagation: "+conflictChecker.tConflictContext);
		System.out.println("Total policies that conflicted because of Play: "+conflictChecker.tConflictPlay);
		System.out.println("Total policies that conflicted because of Ownership: "+conflictChecker.tConflictOwnership);
		System.out.println("Total policies that conflicted because of Role Propagation: "+conflictChecker.tConflictRole);
		System.out.println("Total policies that conflicted because of Object Composition: "+conflictChecker.tConflictObjectComposition);
		System.out.println();
		
	}

	public static OWLOntology loadOntology(String local) {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File(local);
		OWLOntology ontology = null;

		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		System.out.println("Sucessfully loaded: " + ontology.getOntologyID());
		System.out.println("ииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииии");
		return ontology;
	}
	
}
