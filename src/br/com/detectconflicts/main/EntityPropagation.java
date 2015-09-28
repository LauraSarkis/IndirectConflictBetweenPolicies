package br.com.detectconflicts.main;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.ShortFormProvider;

import br.com.detectconflicts.util.Engine;

public class EntityPropagation {
	
	Set<Policie> setOfPolicies; 
	Set<Policie> setOfNewPolicies = new LinkedHashSet<Policie>(); 
	Set<Policie> setOfNewPoliciesOwnership = new LinkedHashSet<Policie>(); 
	ShortFormProvider shortFormProvider;
	public static int totalPlayPropagation = 0;
	public static int totalOwnershipPropagation = 0;
	
	EntityPropagation(Set<Policie> setOfPolicies, ShortFormProvider shortFormProvider) {
		this.setOfPolicies = setOfPolicies;
		this.shortFormProvider = shortFormProvider;
	}
	
	public Set<Policie> propagate(Engine engine) {
		
		Iterator<Policie> policiesIteratorOwnership = setOfPolicies.iterator();	
		
		while(policiesIteratorOwnership.hasNext()) {	
			
		Policie policie = policiesIteratorOwnership.next();
			if(((engine.getTypeObject(policie.getOrg())).equals("Organization")) && policie.getSr() == "null" ) {
				Set<OWLNamedIndividual> setOfRoles = engine.getRolesOfOrganization(policie.getOrg());
				int countB = 1;
				if(setOfRoles != null && setOfRoles.size()>0)
					System.out.println("\n\nOriginal Policie: " +policie);
				if(setOfRoles != null) {
				
					Iterator<OWLNamedIndividual> rolesIterator = setOfRoles.iterator();
					while(rolesIterator.hasNext()) {
						Policie newPolicie = new Policie(policie.getName(), policie.getKp(), policie.getOrg(),policie.getSr(), policie.getAa(), policie.getOv(), policie.getAc(), policie.getDc(), true, null);
						OWLNamedIndividual role = rolesIterator.next();
						newPolicie.setSr(shortFormProvider.getShortForm(role));
						newPolicie.setName(newPolicie.getName()+"."+countB);
						Set<String> origins = new LinkedHashSet<String>();
						origins.addAll(policie.getOrigin());
						origins.add("OWNERSHIP");
						newPolicie.setOrigin(origins);
						System.out.println("New Policies (OWNERSHIP): "+newPolicie);
						setOfNewPolicies.add(newPolicie);	
						totalOwnershipPropagation++;
						countB++;
					}	
				}	
			}	
		}
		setOfPolicies.addAll(setOfNewPolicies);
		setOfNewPolicies.clear();
		
		Iterator<Policie> policiesIteratorPlay = setOfPolicies.iterator();	
		while(policiesIteratorPlay.hasNext()) {	
			Policie policie = policiesIteratorPlay.next();
			if(policie.getSr() != "null") {
				int countA = 1;
				if((engine.getTypeObject(policie.getSr())).equals("Role")) {
					Set<OWLNamedIndividual> setOfSubjects = engine.getSubjectsOfRole(policie.getSr());
					
					if(setOfSubjects != null && setOfSubjects.size()>0) {
						System.out.println("\n\nOriginal Policie: " +policie);
					
					//Percorre todos os sujeitos criando novas políticas
						Iterator<OWLNamedIndividual> subjectsIterator = setOfSubjects.iterator();
					
						while(subjectsIterator.hasNext()) {
							Policie newPolicie = new Policie(policie.getName(), policie.getKp(), policie.getOrg(),policie.getSr(), policie.getAa(), policie.getOv(), policie.getAc(), policie.getDc(), true, null);
							OWLNamedIndividual subject = subjectsIterator.next();
							newPolicie.setSr(shortFormProvider.getShortForm(subject));
							newPolicie.setName(newPolicie.getName()+"."+countA);
							Set<String> origins = new LinkedHashSet<String>();
							origins.addAll(policie.getOrigin());
							origins.add("PLAY");
							newPolicie.setOrigin(origins);
							System.out.println("New Policies (PLAY):  "+newPolicie);
							setOfNewPolicies.add(newPolicie);
							totalPlayPropagation++;
							countA++;
						}	
					}
				}	
			}
		}	
			
		setOfPolicies.addAll(setOfNewPolicies);
		return setOfPolicies;
	}	
}