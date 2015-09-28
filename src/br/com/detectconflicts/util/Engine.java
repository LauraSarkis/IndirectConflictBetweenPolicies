package br.com.detectconflicts.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import br.com.detectconflicts.combinations.Action;
import br.com.detectconflicts.combinations.Entity;
import br.com.detectconflicts.combinations.Object;
import br.com.detectconflicts.combinations.Organization;
import br.com.detectconflicts.main.Policie;

public class Engine {

	public static final String ONTOIRI = "http://www.owl-ontologies.com/unnamed.owl";
	private static OWLOntology ontology;
	private static OWLOntologyManager manager;
	private static OWLDataFactory factory;
	private static OWLReasoner reasoner;
	ShortFormProvider shortFormProvider = new SimpleShortFormProvider();

	public Engine(OWLOntology ontology) {

		this.ontology = ontology;

		/* Instanciando os componentes */

		manager = OWLManager.createOWLOntologyManager();
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		reasoner = reasonerFactory.createReasoner(ontology, config);
		factory = manager.getOWLDataFactory();

	}

	/* GETTERS AND SETTERS */
	
	public OWLDataFactory getFactory() {
		return factory;
	}

	public OWLReasoner getReasoner() {
		return reasoner;
	}

	/* MÉTODO PARA RETORNAR TODAS AS ORGANIZAÇÕES */
	public Set<OWLNamedIndividual> getOrganizations() {

		OWLClass organization = factory.getOWLClass(IRI.create(ONTOIRI+ "#Organization"));
		NodeSet<OWLNamedIndividual> ontoOrganization = reasoner.getInstances(organization, true);
		Set<OWLNamedIndividual> organizations = ontoOrganization.getFlattened();

		return organizations;
	}
	
	/* MÉTODO PARA RETORNAR TODAS AS ORGANIZAÇÕES */
	public List<Organization> getOrganizationsObjects() {

		OWLClass organization = factory.getOWLClass(IRI.create(ONTOIRI+ "#Organization"));
		NodeSet<OWLNamedIndividual> ontoOrganization = reasoner.getInstances(organization, true);
		Set<OWLNamedIndividual> organizations = ontoOrganization.getFlattened();
		List<Organization> listOfOrganizations = new ArrayList<Organization>();
		
		for (OWLNamedIndividual ind : organizations) {
			String s = ind.toString();
			String org = explode(s);
			
			listOfOrganizations.add(new Organization(org));
			
		}
		
		return listOfOrganizations;
	}

	/* MÉTODO PARA RETORNAR TODAS AS AÇÕES */
	public List<Action> getActionObjects() {

		OWLClass action = factory.getOWLClass(IRI.create(ONTOIRI+ "#Activity"));
		NodeSet<OWLNamedIndividual> ontoAction = reasoner.getInstances(action, false);
		Set<OWLNamedIndividual> actions = ontoAction.getFlattened();
		List<Action> listOfActions = new ArrayList<Action>();
		
		for (OWLNamedIndividual ind : actions) {
			String s = ind.toString();
			String act = explode(s);
			
			listOfActions.add(new Action(act));
			
		}
		
		return listOfActions;
	}
	
	/* MÉTODO PARA RETORNAR TODAS AS ENTIDADES */
	public List<Entity> getEntityObjects() {

		OWLClass entity = factory.getOWLClass(IRI.create(ONTOIRI+ "#Entity"));
		NodeSet<OWLNamedIndividual> ontoEntity = reasoner.getInstances(entity,false);
		Set<OWLNamedIndividual> entities = ontoEntity.getFlattened();
		List<Entity> listOfEntities = new ArrayList<Entity>();
		
		for (OWLNamedIndividual ind : entities) {
			String s = ind.toString();
			String ent = explode(s);
			
			listOfEntities.add(new Entity(ent));
		}
		return listOfEntities;
	}

	/* MÉTODO PARA RETORNAR TODAS AS ENTIDADES */
	public List<Object> getObjects() {

		OWLClass object = factory.getOWLClass(IRI.create(ONTOIRI+ "#View"));
		NodeSet<OWLNamedIndividual> ontoObject = reasoner.getInstances(object, false);
		Set<OWLNamedIndividual> objects = ontoObject.getFlattened();
		List<Object> listOfObjects = new ArrayList<Object>();
		
		for (OWLNamedIndividual ind : objects) {
			String s = ind.toString();
			String obj = explode(s);
			
			listOfObjects.add(new Object(obj));
		}
		return listOfObjects;
	}

	

	/* MÉTODO PARA RETORNAR TODAS AS POLÍTICAS */
	public Set<Policie> getPolicies() {

		
		Set<Policie> setOfPolicies = new HashSet<Policie>();
		OWLClass policy = factory.getOWLClass(IRI.create(ONTOIRI + "#Policy"));
		NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(policy, true);
		Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

		for (OWLNamedIndividual ind : individuals) {
			String s = ind.toString();
			String policie = explode(s);
		
			
			Policie p = new Policie(
					policie,
					getTypePolicie(policie),
					getOrganizationPolicie(policie), 
					getEntityPolicie(policie),
					getActionPolicie(policie), 
					getObjectPolicie(policie),
					getActivatePolicie(policie), 
					getDeactivatePolicie(policie),
					false,
					null);
			System.out.println(p);
			setOfPolicies.add(p);

		}

		return setOfPolicies;
	}
	
	/* MÉTODO PARA RETORNAR TODAS AS ORGANIZAÇÕES SUBORDINADAS (hierarchy) */
	public Set<OWLNamedIndividual> getCompositionObject(String o) {

		OWLNamedIndividual object = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + o));

		OWLObjectProperty objectComposition = factory.getOWLObjectProperty(IRI.create(ONTOIRI+ "#Composition_of_object"));
		NodeSet<OWLNamedIndividual> ontoObjectComposition = reasoner.getObjectPropertyValues(object, objectComposition);
		Set<OWLNamedIndividual> values = ontoObjectComposition.getFlattened();

		if (!values.isEmpty()) {
			//Percorre cada instancia encontrada e retorna as instancias hierarquicamente inferiores também.
			for (OWLNamedIndividual value : values) {
				values.addAll(getCompositionObject(shortFormProvider.getShortForm(value)));
			}
		}
		return values;
	}

	/* MÉTODO PARA RETORNAR O OBJECT COMPOSITION */
	public Set<OWLNamedIndividual> getHierarchyOrganizations(String org) {

		OWLNamedIndividual organization = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + org));

		OWLObjectProperty organizationHierarchy = factory.getOWLObjectProperty(IRI.create(ONTOIRI+ "#Organization_Hierarchy"));
		NodeSet<OWLNamedIndividual> ontoOrganizationHierarchy = reasoner.getObjectPropertyValues(organization, organizationHierarchy);
		Set<OWLNamedIndividual> values = ontoOrganizationHierarchy.getFlattened();

		if (!values.isEmpty()) {
			//Percorre cada instancia encontrada e retorna as instancias hierarquicamente inferiores também.
			for (OWLNamedIndividual value : values) {
				values.addAll(getHierarchyOrganizations(shortFormProvider.getShortForm(value)));
			}
		}
		return values;
	}

	/* MÉTODO PARA RETORNAR O HIERARCHY ROLE */ 
	public Set<OWLNamedIndividual> getHierarchyRole (String r) {

		OWLNamedIndividual role = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + r));

		OWLObjectProperty roleHierarchy = factory.getOWLObjectProperty(IRI.create(ONTOIRI+ "#Role_of_Role"));
		NodeSet<OWLNamedIndividual> ontoRoleHierarchy = reasoner.getObjectPropertyValues(role, roleHierarchy);
		Set<OWLNamedIndividual> values = ontoRoleHierarchy.getFlattened();

		if (!values.isEmpty()) {
			//Percorre cada instancia encontrada e retorna as instancias hierarquicamente inferiores também.
			for (OWLNamedIndividual value : values) {
				values.addAll(getHierarchyRole(shortFormProvider.getShortForm(value)));
			}
		}
		return values;
	}
	
	
	/* MÉTODO PARA VER OS SUJEITOS DE UM PAPEL */
	
	public Set<OWLNamedIndividual> getSubjectsOfRole(String r) {

		List<String> arraySubjects = new ArrayList<String>();
		OWLNamedIndividual role = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + r));
		OWLObjectProperty subjectsOfRole = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Role_of_Entity"));
		NodeSet<OWLNamedIndividual> ontoSubjectsOfRole = reasoner.getObjectPropertyValues(role, subjectsOfRole);
		Set<OWLNamedIndividual> values = ontoSubjectsOfRole.getFlattened();
		if(values.toString() != "[]") {
			return values;
		} else {
			return null;
		}
	}	
	
	/* MÉTODO PARA VER OS SUJEITOS DE UMA ORGANIZAÇÃO */
	
	public Set<OWLNamedIndividual> getSubjectsOfOrg(String o) {

		List<String> arraySubjects = new ArrayList<String>();
		OWLNamedIndividual organization = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + o));
		OWLObjectProperty subjectsOfOrganization = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Organizaion_of_subject"));
		NodeSet<OWLNamedIndividual> ontoSubjectsOfRole = reasoner.getObjectPropertyValues(organization, subjectsOfOrganization);
		Set<OWLNamedIndividual> values = ontoSubjectsOfRole.getFlattened();
		if(values.toString() != "[]") {
			return values;
		} else {
			return null;
		}
	}	
	
	/* MÉTODO PAPÉIS DE UMA ORGANIZAÇÃO */
	
	public Set<OWLNamedIndividual> getRolesOfOrganization(String o) {

		List<String> arrayRoles = new ArrayList<String>();
		OWLNamedIndividual organization = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + o));
		OWLObjectProperty rolesOfOrganization = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Role_of_Organization"));
		NodeSet<OWLNamedIndividual> ontoRolesOfOrganization = reasoner.getObjectPropertyValues(organization, rolesOfOrganization);
		Set<OWLNamedIndividual> values = ontoRolesOfOrganization.getFlattened();
		if(values.toString() != "[]") {
			return values;
		} else {
			return null;
		}
	}	
	
	
	/* MÉTODO PARA RETORNAR A ORGANIZAÇÃO DE CADA POLÍTICA */
	public String getOrganizationPolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		OWLObjectProperty organizationPolicie = factory.getOWLObjectProperty(IRI.create(ONTOIRI+ "#Policy_organization"));
		NodeSet<OWLNamedIndividual> ontoOrganizationPolicie = reasoner.getObjectPropertyValues(policie, organizationPolicie);
		Set<OWLNamedIndividual> values = ontoOrganizationPolicie.getFlattened();
		
		if (values.toString() != "[]") {			
			String organizationPolicies = explode(values.toString().replace("]", ""));
			return organizationPolicies;
		} else {
			return null;
		}
	}

	/* MÉTODO PARA RETORNAR A AÇÃO DE CADA POLÍTICA */
	public String getActionPolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		OWLObjectProperty actionPolicie = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Policy_Action"));
		NodeSet<OWLNamedIndividual> ontoActionPolicie = reasoner.getObjectPropertyValues(policie, actionPolicie);
		Set<OWLNamedIndividual> values = ontoActionPolicie.getFlattened();
		String actionPolicies = explode(values.toString().replace("]", ""));

		return actionPolicies;
	}

	/* MÉTODO PARA RETORNAR O OBJETO DE CADA POLÍTICA */
	public String getObjectPolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		OWLObjectProperty objectPolicie = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Policy_object"));
		NodeSet<OWLNamedIndividual> ontoObjectPolicie = reasoner.getObjectPropertyValues(policie, objectPolicie);
		Set<OWLNamedIndividual> values = ontoObjectPolicie.getFlattened();
		String objectPolicies = explode(values.toString().replace("]", ""));

		return objectPolicies;
	}

	/* MÉTODO PARA RETORNAR A ENTIDADE DE CADA POLÍTICA */
	public String getEntityPolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		OWLObjectProperty entityPolicie = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Policy_Entity"));
		NodeSet<OWLNamedIndividual> ontoEntityPolicie = reasoner.getObjectPropertyValues(policie, entityPolicie);
		Set<OWLNamedIndividual> values = ontoEntityPolicie.getFlattened();

		if (values.toString() != "[]") {
			String entityPolicies = explode(values.toString().replace("]", ""));
			return entityPolicies;
		} else {
			return null;
		}
	}

	/* MÉTODO PARA RETORNAR O TIPO DE CADA POLÍTICA */
	public String getTypePolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		String aux = policie.getTypes(ontology).toString();
		String tipo = explodeType(aux);
		return tipo;
	}
	
	/* MÉTODO PARA RETORNAR O TIPO DE CADA OBJETO*/
	public String getTypeObject(String o) {
		
		OWLNamedIndividual object = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + o));
		String aux = object.getTypes(ontology).toString();
		String tipo = "";
		if(aux!= "[]")
			tipo = explodeType(aux).replace("]", "");
		return tipo;
	}
	
	/* MÉTODO PARA RETORNAR ATIVAÇÃO E DESATIVAÇÃO DA POLÍTICA */
	
	public String getActivatePolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		OWLDataProperty activatePolicie = factory.getOWLDataProperty(IRI.create(ONTOIRI + "#Activate_Policy"));
		Set<OWLLiteral> ontoActivatePolicie = reasoner.getDataPropertyValues(policie, activatePolicie);
		
		String values = ontoActivatePolicie.toString();

		return values;
	}
	
	public String getDeactivatePolicie(String p) {

		OWLNamedIndividual policie = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + p));
		OWLDataProperty deactivatePolicie = factory.getOWLDataProperty(IRI.create(ONTOIRI + "#Deactivate_Policy"));
		Set<OWLLiteral> ontoDeactivatePolicie = reasoner.getDataPropertyValues(policie, deactivatePolicie);
	
		String values = ontoDeactivatePolicie.toString();

		return values;
	}
	
	/* MÉTODO PARA VERIFICAR DEPENDENCIA DE DUAS POLÍTICAS */
	
	public String getDependencyAction(String a) {

		OWLNamedIndividual action = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + a));
		OWLObjectProperty dependencyAction = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Dependency"));
		NodeSet<OWLNamedIndividual> ontoDependencyAction = reasoner.getObjectPropertyValues(action, dependencyAction);
		Set<OWLNamedIndividual> values = ontoDependencyAction.getFlattened();
		if(values.toString() != "[]") {
			String actionDependency = explodeProperties(values.toString());
			return actionDependency;
		} else {
			return "";
		}
	}

	/* MÉTODO PARA VERIFICAR A ORTHOGONAL DE DUAS POLÍTICAS */
	
	public String getOrthogonalAction(String a) {

		OWLNamedIndividual action = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + a));
		OWLObjectProperty orthogonalAction = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Orthogonal"));
		NodeSet<OWLNamedIndividual> ontoOrthogonalAction = reasoner.getObjectPropertyValues(action, orthogonalAction);
		Set<OWLNamedIndividual> values = ontoOrthogonalAction.getFlattened();
		if(values.toString() != "[]") {
			String actionDependency = explodeProperties(values.toString());
			return actionDependency;
		} else {
			return "";
		}
	}
	
	/* MÉTODO PARA VERIFICAR A ORTHOGONAL DE DOIS PAPÉIS */
	
	public String getOrthogonalRole(String r) {

		OWLNamedIndividual role = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + r));
		OWLObjectProperty orthogonalRole = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Orthogonal_Role"));
		NodeSet<OWLNamedIndividual> ontoOrthogonalRole = reasoner.getObjectPropertyValues(role, orthogonalRole);
		Set<OWLNamedIndividual> values = ontoOrthogonalRole.getFlattened();
		if(values.toString() != "[]") {
			String actionDependency = explodeProperties(values.toString());
			return actionDependency;
		} else {
			return "";
		}
	}

	/* MÉTODO PARA VERIFICAR A ORTHOGONAL DE DUAS ORGANIZAÇÕES */
	
	public String getOrthogonalOrg(String o) {

		OWLNamedIndividual organization = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + o));
		OWLObjectProperty orthogonalOrg = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Orthogonal_Org"));
		NodeSet<OWLNamedIndividual> ontoOrthogonalOrg = reasoner.getObjectPropertyValues(organization, orthogonalOrg);
		Set<OWLNamedIndividual> values = ontoOrthogonalOrg.getFlattened();
		if(values.toString() != "[]") {
			String actionDependency = explodeProperties(values.toString());
			return actionDependency;
		} else {
			return "";
		}
	}

	/* MÉTODO PARA VERIFICAR A ORTHOGONAL DE DUAS VIEWS */
	
	public String getOrthogonalView(String r) {

		OWLNamedIndividual view = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + r));
		OWLObjectProperty orthogonalView = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Orthogonal_View"));
		NodeSet<OWLNamedIndividual> ontoOrthogonalView = reasoner.getObjectPropertyValues(view, orthogonalView);
		Set<OWLNamedIndividual> values = ontoOrthogonalView.getFlattened();
		if(values.toString() != "[]") {
			String actionDependency = explodeProperties(values.toString());
			return actionDependency;
		} else {
			return "";
		}
	}

	/* MÉTODO PARA VERIFICAR O COMPOSITION CR */
	
	public List<String> getCompositionAction(String a) {

		List<String> arrayCompositions = new ArrayList<String>();
		OWLNamedIndividual action = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + a));
		OWLObjectProperty compositionAction = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Composition"));
		NodeSet<OWLNamedIndividual> ontoCompositionAction = reasoner.getObjectPropertyValues(action, compositionAction);
		Set<OWLNamedIndividual> values = ontoCompositionAction.getFlattened();
		if(values.toString() != "[]") {
			arrayCompositions = explodeComposition(values.toString());
			return arrayCompositions;
		} else {
			return null;
		}
	}	
	
	/* MÉTODO PARA VERIFICAR O OBJECT COMPOSITION CR */
	
	public List<String> getCompositionOfObject(String o) {

		List<String> arrayCompositions = new ArrayList<String>();
		OWLNamedIndividual object = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + o));
		OWLObjectProperty compositionObject = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Composition_of_object"));
		NodeSet<OWLNamedIndividual> ontoCompositionObject = reasoner.getObjectPropertyValues(object, compositionObject);
		Set<OWLNamedIndividual> values = ontoCompositionObject.getFlattened();
		if(values.toString() != "[]") {
			arrayCompositions = explodeComposition(values.toString());
			return arrayCompositions;
		} else {
			return null;
		}
	}	
	
	/* MÉTODO PARA VERIFICAR O REFINEMENT CR */
	
	public List<String> getRefinementAction(String a) {

		List<String> arrayRefinement = new ArrayList<String>();
		OWLNamedIndividual action = factory.getOWLNamedIndividual(IRI.create(ONTOIRI + "#" + a));
		OWLObjectProperty refinementAction = factory.getOWLObjectProperty(IRI.create(ONTOIRI + "#Refinement"));
		NodeSet<OWLNamedIndividual> ontoRefinementAction = reasoner.getObjectPropertyValues(action, refinementAction);
		Set<OWLNamedIndividual> values = ontoRefinementAction.getFlattened();
		if(values.toString() != "[]") {
			arrayRefinement = explodeComposition(values.toString());
			return arrayRefinement;
		} else {
			return null;
		}
	}
	
	/* MÉTODO PARA SABER SE É SUBORG */ 
	
	public boolean isSubOrg(String org1, String org2) {
		
		Set<OWLNamedIndividual> subOrgs = getHierarchyOrganizations(org1);
		List<String> listOfSubOrgs = explodeComposition(subOrgs.toString());
		
		for (String suborg : listOfSubOrgs) {
			if(org2.equals(suborg.trim())) {
				return true;
			}
		}
		return false;
	}

	/* MÉTODO PARA LIMPAR O NOME DA CLASSE */
	public String explode(String input) {
		String[] aux = input.split("#");
		String output = aux[1].replace(">", "");
		return output;
	}
	
	/* MÉTODO PARA LIMPAR O TIPO DA POLÍTICA */
	public String explodeType(String input) {
		String[] aux = input.split(",");
		String output = explode(aux[0]);
		return output;
	}
	
	/* MÉTODO PARA LIMPAR OBJECT PROPERTIES */
	public String explodeProperties(String input) {
		String aux = explode(input);
		String output = aux.replace("]", "");
		return output;
	}
	
	/* MÉTODO PARA SEPARAR OS COMPOSITIONS */ 
	public List<String> explodeComposition(String input){
		List<String> listOfCompositions = new ArrayList<String>();
		String[] aux = input.split(",");
		for(int i=0; i< aux.length; i++) {
			String output = aux[i].replace("[", "");
			output = output.replace("]", "");
			output = output.replace("<", "");
			output = output.replace(">", "");
			output = output.replace("http://www.owl-ontologies.com/unnamed.owl#","");
			listOfCompositions.add(output);
		}
		return listOfCompositions;
	}
}
