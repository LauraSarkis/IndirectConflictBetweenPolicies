package br.com.detectconflicts.combinations;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;

import br.com.detectconflicts.main.Policie;
import br.com.detectconflicts.util.Engine;
public class CombinaPoliticasbackup {

	public Set<Policie> combinaPoliticas(OWLOntology ontology) {
		
		/* COMBINAÇÃO ORGANIZAÇÕES ENTIDADES */
		
		Engine engine = new Engine(ontology);
		Set<String> origins = null;
		
		//List<Organizacao> organizacoes = engine.getOrganizationsObjects();	
		//List<Entidade> entidades = engine.getEntityObjects();
		//entidades.add(new Entidade(null));
		
		List<Organization> organizacoes = new ArrayList<Organization>();
		List<Entity> entidades = new ArrayList<Entity>();

		organizacoes.add(new Organization("Instituição"));
		organizacoes.add(new Organization("Unidade_Administrativa"));

		entidades.add(new Entity("null"));
		entidades.add(new Entity("PROTOCOLIZADOR"));
		
		List<Org_Entity> organizacoesEntidades = new ArrayList<Org_Entity>();
		Iterator iteratorOrg = organizacoes.iterator();
		
		while(iteratorOrg.hasNext()) {
			Organization org;
			org = (Organization) iteratorOrg.next();
			
			Iterator iteratorEnt = entidades.iterator();
			
			while(iteratorEnt.hasNext()) {
				Entity ent;
				ent = (Entity) iteratorEnt.next();
				
				organizacoesEntidades.add(new Org_Entity(org, ent));
				
			}
		}
		
		/* COMBINAÇÃO OBJETOS E AÇÕES */
		
//		List<Objeto> objetos = engine.getObjects();
//		List<Acao> acoes = engine.getActionObjects();
		
		List<Object> objetos = new ArrayList<Object>();
		List<Action> acoes = new ArrayList<Action>();

		objetos.add(new Object("ProcessDespacho"));
		objetos.add(new Object("Processo"));
		
		acoes.add(new Action("MovimentProcess"));
		acoes.add(new Action("Abrir"));
//		acoes.add(new Acao("Fechar"));
		acoes.add(new Action("Anular"));
		acoes.add(new Action("Criar"));
		acoes.add(new Action("Movimentar"));
		acoes.add(new Action("Acessar"));
		acoes.add(new Action("Gravar"));
		acoes.add(new Action("Cancelar"));
		acoes.add(new Action("Gerar"));
//		acoes.add(new Acao("Tramitar"));
		
		
		
		List<Obj_Action> objetosAcoes = new ArrayList<Obj_Action>();
		
		Iterator iteratorObj = objetos.iterator();
		
		while(iteratorObj.hasNext()) {
			Object obj;
			obj = (Object) iteratorObj.next();
			
			Iterator iteratorAcao = acoes.iterator();
			
			while(iteratorAcao.hasNext()) {
				Action acao;
				acao = (Action) iteratorAcao.next();
				
				objetosAcoes.add(new Obj_Action(obj, acao));
				
			}
		}

		/* CRIAÇÃO DAS POLÍTICAS */
		
		Set<Policie> setOfPolicies = new LinkedHashSet<Policie>();
		int count = 1;
		
		Iterator orgsEntidadesIterator = organizacoesEntidades.iterator();
		
		while(orgsEntidadesIterator.hasNext()) {
			Org_Entity orgEntidade = (Org_Entity) orgsEntidadesIterator.next();
			
			Iterator objsAcoesIterator = objetosAcoes.iterator();
			
//			while(objsAcoesIterator.hasNext()) {
//				Obj_Acao objAcao = (Obj_Acao) objsAcoesIterator.next();
//
//				origins = new LinkedHashSet<String>();
//				
//				setOfPolicies.add(new Policie("Policie"+count, "Obliged", orgEntidade.getOrg().getOrganizacao(), orgEntidade.getEntidade().getEntidade(),
//						objAcao.getAcao().getAcao(), objAcao.getObjeto().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
//				count++;
//				setOfPolicies.add(new Policie("Policie"+count, "Forbidden", orgEntidade.getOrg().getOrganizacao(), orgEntidade.getEntidade().getEntidade(),
//						objAcao.getAcao().getAcao(), objAcao.getObjeto().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
//				count++;
//				setOfPolicies.add(new Policie("Policie"+count, "Permitted", orgEntidade.getOrg().getOrganizacao(), orgEntidade.getEntidade().getEntidade(),
//						objAcao.getAcao().getAcao(), objAcao.getObjeto().getObjeto(), "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
//				count++;
//				
//			}
			
		}
		
		origins = new LinkedHashSet<>();
		
		setOfPolicies.add(new Policie("Policie"+(count), "Forbidden", "Instituição", "null", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+1), "Permitted", "NUPS", "null", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+2), "Forbidden", "Instituição", "PROTOCOLIZADOR", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+3), "Permitted", "Instituição", "PROTOCOLIZADOR1", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+4), "Forbidden", "Instituição", "PROTOCOLIZADOR3", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+5), "Forbidden", "Instituição", "MARIA", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+6), "Obliged", "Instituição", "JOSE", "Abrir", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+7), "Forbidden", "Unidade_Administrativa", "null", "Movimentar", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+8), "Obliged", "Instituição", "MARIA", "Criar", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+9), "Obliged", "Instituição", "JOSE", "Fechar", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+10), "Obliged", "Instituição", "PROTOCOLIZADOR3", "Gerar", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+11), "Permitted", "Unidade_Administrativa", "PROTOCOLIZADOR3", "Gravar", "ProcessDespacho", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+12), "Forbidden", "Unidade_Administrativa", "PROTOCOLIZADOR3", "Acessar", "ProcNURCADesp", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		setOfPolicies.add(new Policie("Policie"+(count+13), "Forbidden", "Instituição", "JOSE", "Fechar", "Processo", "2015-08-18T20:06:14^^DateTime", "2015-12-31T20:06:14^^DateTime", false, origins));
		
		
		
		System.out.println("Total de Políticas geradas: "+setOfPolicies.size()+"\n");
		
		for (Policie policie : setOfPolicies) {;
			System.out.println(policie);
		}
		
		
		return setOfPolicies;	
	}	
}
