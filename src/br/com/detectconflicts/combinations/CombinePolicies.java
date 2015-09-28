package br.com.detectconflicts.combinations;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;

import br.com.detectconflicts.main.Policie;
import br.com.detectconflicts.util.Engine;
public class CombinePolicies {

	public static int count = 1;
	
	public Set<Policie> combinePoliciesForbidden(OWLOntology ontology) {
		
		/* COMBINAÇÃO ORGANIZAÇÕES ENTIDADES */
		
		Engine engine = new Engine(ontology);
		Set<String> origins = null;
		
		//List<Organizacao> organizacoes = engine.getOrganizationsObjects();	
		//List<Entidade> entidades = engine.getEntityObjects();
		//entidades.add(new Entidade(null));
		
		List<Organization> organizations = new ArrayList<Organization>();
		List<Entity> entities = new ArrayList<Entity>();

		organizations.add(new Organization("Institution"));
		organizations.add(new Organization("Administrative_Unit"));

		entities.add(new Entity("null"));
		entities.add(new Entity("PROTOCOLIZADOR"));
//		entities.add(new Entity("PROTOCOLIZADOR3"));
//		entidades.add(new Entidade("MARIA"));
//		entidades.add(new Entidade("JOSE"));
		
		List<Org_Entity> organizationsEntities = new ArrayList<Org_Entity>();
		Iterator iteratorOrg = organizations.iterator();
		
		while(iteratorOrg.hasNext()) {
			Organization org;
			org = (Organization) iteratorOrg.next();
			
			Iterator iteratorEnt = entities.iterator();
			
			while(iteratorEnt.hasNext()) {
				Entity ent;
				ent = (Entity) iteratorEnt.next();
				
				organizationsEntities.add(new Org_Entity(org, ent));
				
			}
		}
		
		/* COMBINAÇÃO OBJETOS E AÇÕES */
		
//		List<Objeto> objetos = engine.getObjects();
//		List<Acao> acoes = engine.getActionObjects();
		
		List<Object> objects = new ArrayList<Object>();
		List<Action> actions = new ArrayList<Action>();

		objects.add(new Object("ProcNURCADesp"));
		objects.add(new Object("Process"));
		objects.add(new Object("ProcessDispatch"));
		objects.add(new Object("BuildDispatch"));
		
		actions.add(new Action("MoveProcess"));
		actions.add(new Action("Open"));
//		acoes.add(new Acao("Close"));
		actions.add(new Action("Annul"));
		actions.add(new Action("Create"));
		actions.add(new Action("Move"));
		actions.add(new Action("Acess"));
		actions.add(new Action("Record"));
		actions.add(new Action("Cancel"));
		actions.add(new Action("Generate"));
//		acoes.add(new Acao("Transact"));
		
		
		
		List<Obj_Action> objectsActions = new ArrayList<Obj_Action>();
		
		Iterator iteratorObj = objects.iterator();
		
		while(iteratorObj.hasNext()) {
			Object obj;
			obj = (Object) iteratorObj.next();
			
			Iterator iteratorAction = actions.iterator();
			
			while(iteratorAction.hasNext()) {
				Action action;
				action = (Action) iteratorAction.next();
				
				objectsActions.add(new Obj_Action(obj, action));
				
			}
		}

		/* CRIAÇÃO DAS POLÍTICAS */
		
		Set<Policie> setOfPolicies = new LinkedHashSet<Policie>();
			
		Iterator orgsEntitiesIterator = organizationsEntities.iterator();
		
		while(orgsEntitiesIterator.hasNext()) {
			Org_Entity orgEntity = (Org_Entity) orgsEntitiesIterator.next();
			
			Iterator objsActionsIterator = objectsActions.iterator();
			
			while(objsActionsIterator.hasNext()) {
				Obj_Action objAction = (Obj_Action) objsActionsIterator.next();

				origins = new LinkedHashSet<String>();

				setOfPolicies.add(new Policie("Policie"+count, "Forbidden", orgEntity.getOrg().getOrganization(), orgEntity.getEntity().getEntity(),
						objAction.getAction().getAction(), objAction.getObject().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
				count++;

				setOfPolicies.add(new Policie("Policie"+count, "Permitted", orgEntity.getOrg().getOrganization(), orgEntity.getEntity().getEntity(),
						objAction.getAction().getAction(), objAction.getObject().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
				count++;
				
				setOfPolicies.add(new Policie("Policie"+count, "Obliged", orgEntity.getOrg().getOrganization(), orgEntity.getEntity().getEntity(),
						objAction.getAction().getAction(), objAction.getObject().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
				count++;
				
			}
			
		}	
		
		
		System.out.println("Total of Generated Policies: "+setOfPolicies.size()+"\n");
		
		for (Policie policie : setOfPolicies) {;
			System.out.println(policie);
		}
		
		
		return setOfPolicies;	
	}	
	
	public Set<Policie> combinePoliciesPermitted(OWLOntology ontology) {
		
		/* COMBINAÇÃO ORGANIZAÇÕES ENTIDADES */
		
		Engine engine = new Engine(ontology);
		Set<String> origins = null;
		
		//List<Organizacao> organizacoes = engine.getOrganizationsObjects();	
		//List<Entidade> entidades = engine.getEntityObjects();
		//entidades.add(new Entidade(null));
		
		List<Organization> organizations = new ArrayList<Organization>();
		List<Entity> entities = new ArrayList<Entity>();

//		organizacoes.add(new Organizacao("Institution"));
		organizations.add(new Organization("Administrative_Unit"));

		entities.add(new Entity("null"));
//		entidades.add(new Entidade("PROTOCOLIZADOR"));
		entities.add(new Entity("PROTOCOLIZADOR3"));
//		entidades.add(new Entidade("PROTOCOLIZADOR2"));
//		entidades.add(new Entidade("MARIA"));
//		entidades.add(new Entidade("JOSE"));
//		entidades.add(new Entidade("Unidade_Administrativa"));
		
		List<Org_Entity> organizationsEntities = new ArrayList<Org_Entity>();
		Iterator iteratorOrg = organizations.iterator();
		
		while(iteratorOrg.hasNext()) {
			Organization org;
			org = (Organization) iteratorOrg.next();
			
			Iterator iteratorEnt = entities.iterator();
			
			while(iteratorEnt.hasNext()) {
				Entity ent;
				ent = (Entity) iteratorEnt.next();
				
				organizationsEntities.add(new Org_Entity(org, ent));
				
			}
		}
		
		
		/* COMBINAÇÃO OBJETOS E AÇÕES */
		
//		List<Objeto> objetos = engine.getObjects();
//		List<Acao> acoes = engine.getActionObjects();
		
		List<Object> objects = new ArrayList<Object>();
		List<Action> actions = new ArrayList<Action>();

		objects.add(new Object("ProcessDispatch"));
		objects.add(new Object("BuildDispatch"));
		objects.add(new Object("Process"));
		
		
//		acoes.add(new Acao("MoveProcess"));
//		acoes.add(new Acao("Open"));
		actions.add(new Action("Close"));
//		acoes.add(new Acao("Annul"));
		actions.add(new Action("Create"));
//		acoes.add(new Acao("Move"));
//		acoes.add(new Acao("Acess"));
		actions.add(new Action("Record"));
//		acoes.add(new Acao("Cancel"));
//		acoes.add(new Acao("Generate"));
//		acoes.add(new Acao("Transact"));

		
		
		List<Obj_Action> objetctsActions = new ArrayList<Obj_Action>();
		
		Iterator iteratorObj = objects.iterator();
		
		while(iteratorObj.hasNext()) {
			Object obj;
			obj = (Object) iteratorObj.next();
			
			Iterator iteratorAction = actions.iterator();
			
			while(iteratorAction.hasNext()) {
				Action action;
				action = (Action) iteratorAction.next();
				
				objetctsActions.add(new Obj_Action(obj, action));
				
			}
		}

		/* CRIAÇÃO DAS POLÍTICAS */
		
		Set<Policie> setOfPolicies = new LinkedHashSet<Policie>();
		
		Iterator orgsEntitiesIterator = organizationsEntities.iterator();
		
		while(orgsEntitiesIterator.hasNext()) {
			Org_Entity orgEntity = (Org_Entity) orgsEntitiesIterator.next();
			
			Iterator objsActionsIterator = objetctsActions.iterator();
			
			while(objsActionsIterator.hasNext()) {
				Obj_Action objAction = (Obj_Action) objsActionsIterator.next();

				origins = new LinkedHashSet<String>();
				
				setOfPolicies.add(new Policie("Policie"+count, "Permitted", orgEntity.getOrg().getOrganization(), orgEntity.getEntity().getEntity(),
						objAction.getAction().getAction(), objAction.getObject().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
				count++;
				
			}
			
		}	
		
		
		System.out.println("Total of Generated Policies: "+setOfPolicies.size()+"\n");
		
		for (Policie policie : setOfPolicies) {;
			System.out.println(policie);
		}
		
		
		return setOfPolicies;	
	}	
	
	public Set<Policie> combinePoliciesObliged(OWLOntology ontology) {
		
		/* COMBINAÇÃO ORGANIZAÇÕES ENTIDADES */
		
		Engine engine = new Engine(ontology);
		Set<String> origins = null;
		
		//List<Organizacao> organizacoes = engine.getOrganizationsObjects();	
		//List<Entidade> entidades = engine.getEntityObjects();
		//entidades.add(new Entidade(null));
		
		List<Organization> organizations = new ArrayList<Organization>();
		List<Entity> entities = new ArrayList<Entity>();

		organizations.add(new Organization("Institution"));
		organizations.add(new Organization("Administrative_Unit"));

		entities.add(new Entity("null"));
		entities.add(new Entity("PROTOCOLIZADOR"));
		entities.add(new Entity("PROTOCOLIZADOR3"));
		entities.add(new Entity("MARY"));
//		entidades.add(new Entidade("JOSE"));
//		
		List<Org_Entity> organizationsEntities = new ArrayList<Org_Entity>();
		Iterator iteratorOrg = organizations.iterator();
		
		while(iteratorOrg.hasNext()) {
			Organization org;
			org = (Organization) iteratorOrg.next();
			
			Iterator iteratorEnt = entities.iterator();
			
			while(iteratorEnt.hasNext()) {
				Entity ent;
				ent = (Entity) iteratorEnt.next();
				
				organizationsEntities.add(new Org_Entity(org, ent));
				
			}
		}
		
		/* COMBINAÇÃO OBJETOS E AÇÕES */
		
//		List<Objeto> objetos = engine.getObjects();
//		List<Acao> acoes = engine.getActionObjects();
		
		List<Object> objects = new ArrayList<Object>();
		List<Action> actions = new ArrayList<Action>();

//		objetos.add(new Objeto("ProcNURCADesp"));
		objects.add(new Object("Process"));
		
		
//		acoes.add(new Acao("MoveProcess"));
		actions.add(new Action("Open"));
//		acoes.add(new Acao("Close"));
//		acoes.add(new Acao("Annul"));
//		acoes.add(new Acao("Create"));
//		acoes.add(new Acao("Move"));
		actions.add(new Action("Acess"));
//		acoes.add(new Acao("Record"));
//		acoes.add(new Acao("Cancel"));
//		acoes.add(new Acao("Generate"));
//		acoes.add(new Acao("Transact"));

		
		
		List<Obj_Action> objectsActions = new ArrayList<Obj_Action>();
		
		Iterator iteratorObj = objects.iterator();
		
		while(iteratorObj.hasNext()) {
			Object obj;
			obj = (Object) iteratorObj.next();
			
			Iterator iteratorAction = actions.iterator();
			
			while(iteratorAction.hasNext()) {
				Action action;
				action = (Action) iteratorAction.next();
				
				objectsActions.add(new Obj_Action(obj, action));
				
			}
		}

		/* CRIAÇÃO DAS POLÍTICAS */
		
		Set<Policie> setOfPolicies = new LinkedHashSet<Policie>();
		
		Iterator orgsEntitiesIterator = organizationsEntities.iterator();
		
		while(orgsEntitiesIterator.hasNext()) {
			Org_Entity orgEntity = (Org_Entity) orgsEntitiesIterator.next();
			
			Iterator objsActionsIterator = objectsActions.iterator();
			
			while(objsActionsIterator.hasNext()) {
				Obj_Action objAction = (Obj_Action) objsActionsIterator.next();

				origins = new LinkedHashSet<String>();
				
				setOfPolicies.add(new Policie("Policie"+count, "Obliged", orgEntity.getOrg().getOrganization(), orgEntity.getEntity().getEntity(),
						objAction.getAction().getAction(), objAction.getObject().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
				count++;
			
			}
			
		}	
		System.out.println("Total of Generated Policies: "+setOfPolicies.size()+"\n");
		
		for (Policie policie : setOfPolicies) {;
			System.out.println(policie);
		}
		
		return setOfPolicies;	
	}
}
