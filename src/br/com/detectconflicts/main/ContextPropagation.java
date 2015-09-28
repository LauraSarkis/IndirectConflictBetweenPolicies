package br.com.detectconflicts.main;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.ShortFormProvider;

import br.com.detectconflicts.util.Engine;

public class ContextPropagation {
	
	Set<Policie> setOfPolicies; 
	Set<Policie> setOfNewPolicies = new LinkedHashSet<Policie>(); 
	ShortFormProvider shortFormProvider;
	public static int totalContextPropagation = 0;
	
	ContextPropagation(Set<Policie> setOfPolicies, ShortFormProvider shortFormProvider) {
		this.setOfPolicies = setOfPolicies;
		this.shortFormProvider = shortFormProvider;
	}
	
	public Set<Policie> propagate(Engine engine) {
		
		Iterator<Policie> policiesIterator = setOfPolicies.iterator();
		while(policiesIterator.hasNext()) {	
			int count = 1;
			Policie policie = policiesIterator.next();
			
			if(policie.getSr() == "null") {

				Set<OWLNamedIndividual> setOfHierarchyOrganization = engine.getHierarchyOrganizations(policie.getOrg());	
				
				if(setOfHierarchyOrganization != null && setOfHierarchyOrganization.size() > 0) 
					System.out.println("\n\nOriginal Policie: " +policie+"\n");
				
				//Percorre o Set de todas as organizações que estão em hierarquia
				Iterator<OWLNamedIndividual> organizationIterator = setOfHierarchyOrganization.iterator();
				while(organizationIterator.hasNext()) {
					
					Policie newPolicie = new Policie(policie.getName(), policie.getKp(), policie.getOrg(),policie.getSr(), policie.getAa(), policie.getOv(), policie.getAc(), policie.getDc(), true, null);
					OWLNamedIndividual organization = organizationIterator.next();
					newPolicie.setOrg(shortFormProvider.getShortForm(organization));
					newPolicie.setName(newPolicie.getName()+"."+count);
					Set<String> origins = new LinkedHashSet<String>();
					origins.add("CONTEXT");					
					newPolicie.setOrigin(origins);
					System.out.println("New Policies (HIERARCHY): "+newPolicie);
					setOfNewPolicies.add(newPolicie);		
					totalContextPropagation++;
					count++;
				}	
			}	
		}	
		System.out.println("\n\n");
		setOfPolicies.addAll(setOfNewPolicies);
		return setOfPolicies;
	}	
}