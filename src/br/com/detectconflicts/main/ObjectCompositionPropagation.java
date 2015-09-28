package br.com.detectconflicts.main;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.ShortFormProvider;

import br.com.detectconflicts.util.Engine;

public class ObjectCompositionPropagation {
	
	Set<Policie> setOfPolicies; 
	Set<Policie> setOfNewPolicies = new LinkedHashSet<Policie>(); 
	ShortFormProvider shortFormProvider;
	public static int totalObjectCompositionPropagation = 0;
	
	ObjectCompositionPropagation(Set<Policie> setOfPolicies, ShortFormProvider shortFormProvider) {
		this.setOfPolicies = setOfPolicies;
		this.shortFormProvider = shortFormProvider;
	}
	
	public Set<Policie> propagate(Engine engine) {
		
		Iterator<Policie> policiesIterator = setOfPolicies.iterator();
		while(policiesIterator.hasNext()) {	
			int count = 1;
			Policie policie = policiesIterator.next();
			
			if((engine.getTypeObject(policie.getOv()).equals("View")) || engine.getTypeObject(policie.getOv()).equals("Object")) {
				
				Set<OWLNamedIndividual> setOfCompositionObject = engine.getCompositionObject(policie.getOv());	
				
				
				if(setOfCompositionObject != null && setOfCompositionObject.size()>0)
					System.out.println("\n\nOriginal Policie: " +policie);
				
				//Percorre o Set de todas as organizações que estão em object composition
				Iterator<OWLNamedIndividual> objectIterator = setOfCompositionObject.iterator();
				while(objectIterator.hasNext()) {
					
					Policie newPolicie = new Policie(policie.getName(), policie.getKp(), policie.getOrg(),policie.getSr(), policie.getAa(), policie.getOv(), policie.getAc(), policie.getDc(), true, null);
					OWLNamedIndividual object = objectIterator.next();
					newPolicie.setOv(shortFormProvider.getShortForm(object));
					newPolicie.setName(newPolicie.getName()+"."+count);
					Set<String> origins = new LinkedHashSet<String>();
					origins.addAll(policie.getOrigin());
					origins.add("OBJECT COMPOSITION");
					newPolicie.setOrigin(origins);
					System.out.println("New Policies (OBJECT COMPOSITION): "+newPolicie);
					setOfNewPolicies.add(newPolicie);	
					totalObjectCompositionPropagation++;
					count++;
				}
			}		
		}	
		
		setOfPolicies.addAll(setOfNewPolicies);
		return setOfPolicies;
	}	
}