package br.com.detectconflicts.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.ShortFormProvider;

import br.com.detectconflicts.util.Engine;

public class RolePropagation {
	
	Set<Policie> setOfPolicies; 
	Set<Policie> setOfNewPolicies = new LinkedHashSet<Policie>(); 
	ShortFormProvider shortFormProvider;
	public static int totalRolePropagation = 0;
	
	RolePropagation(Set<Policie> setOfPolicies, ShortFormProvider shortFormProvider) {
		this.setOfPolicies = setOfPolicies;
		this.shortFormProvider = shortFormProvider;
	}
	
	public Set<Policie> propagate(Engine engine) {
		
		Iterator<Policie> policiesIterator = setOfPolicies.iterator();
		
		while(policiesIterator.hasNext()) {	
			
			int count = 1;
			Policie policie = policiesIterator.next();
			
			if(engine.getTypeObject(policie.getSr()) != null) {
		
				if((engine.getTypeObject(policie.getSr()).equals("Role"))) {

					Set<OWLNamedIndividual> setOfHierarchyRoles = engine.getHierarchyRole(policie.getSr());
			
					if(setOfHierarchyRoles != null && setOfHierarchyRoles.size() > 0) 
						System.out.println("\n\nOriginal Policie: " +policie+"\n");
					
					//Percorre o Set de todas os roles que estão em hierarquia
					Iterator<OWLNamedIndividual> rolesIterator = setOfHierarchyRoles.iterator();
					while(rolesIterator.hasNext()) {
					
						Policie newPolicie = new Policie(policie.getName(), policie.getKp(), policie.getOrg(),policie.getSr(), policie.getAa(), policie.getOv(), policie.getAc(), policie.getDc(), true, null);
						OWLNamedIndividual role = rolesIterator.next();
						newPolicie.setSr(shortFormProvider.getShortForm(role));
						newPolicie.setName(newPolicie.getName()+"."+count);
						Set<String> origins = new LinkedHashSet<String>();
						origins.addAll(policie.getOrigin());
						origins.add("ROLE");
						newPolicie.setOrigin(origins);
						System.out.println("New Policies (ROLE PROPAGATION): "+newPolicie);
						setOfNewPolicies.add(newPolicie);	
						totalRolePropagation++;
						count++;
					}	
				}			
			}
		}	
		setOfPolicies.addAll(setOfNewPolicies);
		return setOfPolicies;
	}	
}