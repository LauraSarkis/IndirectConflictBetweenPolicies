package br.com.detectconflicts.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

import com.google.common.collect.Lists;

import br.com.detectconflicts.util.Engine;


public class ConflictChecker {
	
	private Set<Policie> setOfPolicies;
	OWLOntology ontology;
	Engine engine;
	
	private static String O = "Obliged";
	private static String P = "Permitted";
	private static String F = "Forbidden";

	public static int totalExecuctionsRulesRefinment = 0;
	public static int totalExecuctionsRulesDependency = 0;
	public static int totalExecuctionsRulesComposition = 0;
	public static int totalExecuctionsRulesOrthogonal = 0;
	public static int totalExecuctionsRulesDirect = 0;
	public static int totalExecuctionsRulesIndirect = 0;
	public static int totalExecuctionsRulesOrthogonalRole = 0;
	public static int totalExecuctionsRulesOrthogonalOrg = 0;
	public static int totalExecuctionsRulesOrthogonalView = 0;

	public static Set<Policie> pConflictOwnership = new LinkedHashSet<>();
	public static Set<Policie> pConflictPlay = new LinkedHashSet<>();
	public static Set<Policie> pConflictRole = new LinkedHashSet<>();
	public static Set<Policie> pConflictContext = new LinkedHashSet<>();
	public static Set<Policie> pConflictObjectComposition = new LinkedHashSet<>();

	public static int tConflictOwnership = 0;
	public static int tConflictPlay = 0;
	public static int tConflictRole = 0;
	public static int tConflictContext = 0;
	public static int tConflictObjectComposition = 0;
	
	public static Set<String> totalDirect = new LinkedHashSet<>();
	public static Set<String> totalIndirect = new LinkedHashSet<>();
	public static Set<String> totalRefinment = new LinkedHashSet<>();
	public static Set<String> totalComposition = new LinkedHashSet<>();
	public static Set<String> totalOrthogonal = new LinkedHashSet<>();
	public static Set<String> totalDependency = new LinkedHashSet<>();
	public static Set<String> totalRole = new LinkedHashSet<>();
	public static Set<String> totalObjectComposition = new LinkedHashSet<>();
	public static Set<String> totalOrthogonalView = new LinkedHashSet<>();
	public static Set<String> totalOrthogonalOrg = new LinkedHashSet<>();
	
		
	public ConflictChecker(Set<Policie> setOfPolicies) {
		this.setOfPolicies =  setOfPolicies;
		engine = new Engine(Main.ontology);
	}
	
	/* MÉTODO PARA PERCORRER TODAS AS POLÍTICAS */ 
	
	public int coursePolicies() {
		
		Policie p1 = null;
		Policie p2 = null;
		int count = 0;
		int auxPosition = 0;
		List<Policie> listPolicies = Lists.newArrayList(setOfPolicies);
		Iterator<Policie> p2Iterator = null;
	
		
		for(int i=0+auxPosition; i<listPolicies.size(); i++) {
			p1 = listPolicies.get(i);
			count++;
			List<Policie>listPolicies2 = Lists.newArrayList(setOfPolicies);
			
			for(int j=0+auxPosition; j<listPolicies2.size(); j++) {
				
				p2 = listPolicies2.get(j);		
			
				if(detectConflictRoles(p1, p2)) {
					
					if(p1.getOrigin().contains("PLAY") || p2.getOrigin().contains("PLAY")) {
						tConflictPlay++;
					}
					if(p1.getOrigin().contains("OWNERSHIP") || p2.getOrigin().contains("OWNERSHIP")) {
						tConflictOwnership++;
					}
					if(p1.getOrigin().contains("ROLE") || p2.getOrigin().contains("ROLE")) {
						tConflictRole++;
					}
					if(p1.getOrigin().contains("OBJECT COMPOSITION") || p2.getOrigin().contains("OBJECT COMPOSITION")) {
						tConflictObjectComposition++;
					}
					if(p1.getOrigin().contains("CONTEXT") || p2.getOrigin().contains("CONTEXT")) {
						tConflictContext++;
					}
					
					if(p1.isDerivated()) {
						if(p1.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p1);
						}
						if(p1.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p1);
						}
						if(p1.getOrigin().contains("ROLE")) {
							pConflictRole.add(p1);
						}
						if(p1.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p1);
						}
						if(p1.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p1);
						}
					}
					
					if(p2.isDerivated()) {
						if(p2.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p2);
						}
						if(p2.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p2);
						}
						if(p2.getOrigin().contains("ROLE")) {
							pConflictRole.add(p2);
						}
						if(p2.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p2);
						}
						if(p2.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p2);
						}
					}
				}
				
				if(detectConflictViews(p1, p2)) {
					if(p1.isDerivated()) {
						if(p1.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p1);
						}
						if(p1.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p1);
						}
						if(p1.getOrigin().contains("ROLE")) {
							pConflictRole.add(p1);
						}
						if(p1.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p1);
						}
						if(p1.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p1);
						}
					}
					
					if(p2.isDerivated()) {
						if(p2.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p2);
						}
						if(p2.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p2);
						}
						if(p2.getOrigin().contains("ROLE")) {
							pConflictRole.add(p2);
						}
						if(p2.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p2);
						}
						if(p2.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p2);
						}
					}
					
				}
				
				
				if(detectConflictOrgs(p1, p2)) {
					
					if(p1.getOrigin().contains("PLAY") || p2.getOrigin().contains("PLAY")) {
						tConflictPlay++;
					}
					if(p1.getOrigin().contains("OWNERSHIP") || p2.getOrigin().contains("OWNERSHIP")) {
						tConflictOwnership++;
					}
					if(p1.getOrigin().contains("ROLE") || p2.getOrigin().contains("ROLE")) {
						tConflictRole++;
					}
					if(p1.getOrigin().contains("OBJECT COMPOSITION") || p2.getOrigin().contains("OBJECT COMPOSITION")) {
						tConflictObjectComposition++;
					}
					if(p1.getOrigin().contains("CONTEXT") || p2.getOrigin().contains("CONTEXT")) {
						tConflictContext++;
					}
					
					
					if(p1.isDerivated()) {
						if(p1.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p1);
						}
						if(p1.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p1);
						}
						if(p1.getOrigin().contains("ROLE")) {
							pConflictRole.add(p1);
						}
						if(p1.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p1);
						}
						if(p1.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p1);
						}
					}
					
					if(p2.isDerivated()) {
						if(p2.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p2);
						}
						if(p2.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p2);
						}
						if(p2.getOrigin().contains("ROLE")) {
							pConflictRole.add(p2);
						}
						if(p2.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p2);
						}
						if(p2.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p2);
						}
					}
				}
				
				if (detectConflict(p1, p2)) {
					
					
					
					if(p1.isDerivated()) {
						if(p1.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p1);
						}
						if(p1.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p1);
						}
						if(p1.getOrigin().contains("ROLE")) {
							pConflictRole.add(p1);
						}
						if(p1.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p1);
						}
						if(p1.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p1);
						}
					}
					
					if(p2.isDerivated()) {
						if(p2.getOrigin().contains("PLAY")) {
							pConflictPlay.add(p2);
						}
						if(p2.getOrigin().contains("OWNERSHIP")) {
							pConflictOwnership.add(p2);
						}
						if(p2.getOrigin().contains("OBJECT COMPOSITION")) {
							pConflictObjectComposition.add(p2);
						}
						if(p2.getOrigin().contains("CONTEXT")) {
							pConflictContext.add(p2);
						}
					}
				}
			}
			auxPosition = i+1;
		}

		System.out.println("Total of Directs Conflicts: "+totalDirect.size());
		System.out.println("Total of Indirects Conflicts: "+totalIndirect.size());
		System.out.println("Total of Refinment Conflicts:" +totalRefinment.size());
		System.out.println("Total of Composition Conflicts: "+totalComposition.size());
		System.out.println("Total of Orthogonal Conflicts: "+totalOrthogonal.size());
		System.out.println("Total of Dependency Conflicts : "+totalDependency.size());
		System.out.println("Total of Orthogonal-Role Conflicts: "+totalRole.size());
		System.out.println("Total of Orthogonal-View Conflicts: "+totalOrthogonalView.size());
		System.out.println("Total of Orthogonal-Organization Conflicts: "+totalOrthogonalOrg.size());
		System.out.println();
	
		System.out.println("Total times that the rules were excuted:: "+(totalExecuctionsRulesComposition+totalExecuctionsRulesDependency+totalExecuctionsRulesDirect+totalExecuctionsRulesIndirect
				+totalExecuctionsRulesOrthogonal+totalExecuctionsRulesOrthogonalRole+totalExecuctionsRulesRefinment+totalExecuctionsRulesOrthogonalOrg+totalExecuctionsRulesOrthogonalView+"\n"));

		return (totalDirect.size()+totalIndirect.size()+totalRefinment.size()+totalComposition.size()+totalOrthogonal.size()+totalDependency.size()+
				totalRole.size()+totalOrthogonalView.size()+totalOrthogonalOrg.size());
		
	}	
	

	
	/* MÉTODO PARA DETECTAR CONFLITOS ENTRE PAPÉIS*/
	
	public boolean detectConflictRoles(Policie p1, Policie p2) {
		
		boolean conflict = false;
		
		if(!(p1.getName().equals(p2.getName()))) {
			
			//Verifica se as políticas se aplicam a mesma organização, e ao mesmo objeto. (Aqui ele não verifica se os papéis são iguais, pois eles tem que ser diferentes)
			if((p1.getOrg().equals(p2.getOrg())) && (p1.getOv().equals(p2.getOv())) && (p1.getAa().equals(p2.getAa()))) {
							
				//Verifica se o tempo das politicas se interceptam.
				if(intersect(p1, p2)) {
	
					conflict = orthogonalRoleCr(p1, p2);
					
				}
			}
		}
		return conflict;
	}
	
	/* MÉTODO PARA DETECTAR CONFLITOS ENTRE VIEWS*/
	
	public boolean detectConflictViews(Policie p1, Policie p2) {
		
		boolean conflict = false;
		
		if(!(p1.getName().equals(p2.getName()))) {
							
			if((p1.getOrg().equals(p2.getOrg())) && (p1.getAa().equals(p2.getAa())) && (p1.getSr().equals(p2.getSr()))) {
				
				//Verifica se o tempo das politicas se interceptam.
				if(intersect(p1, p2)) {
	
					conflict = orthogonalViewCr(p1, p2);
					
				}
				
			}
		}
		return conflict;
	}
	
	/* MÉTODO PARA DETECTAR CONFLITOS ENTRE ORGS*/
	
	public boolean detectConflictOrgs(Policie p1, Policie p2) {
		
		boolean conflict = false;
		
		if(!(p1.getName().equals(p2.getName()))) {
			
			if((p1.getSr().equals(p2.getSr())) && (p1.getOv().equals(p2.getOv())) && (p1.getAa().equals(p2.getAa()))) {
				
			
				//Verifica se o tempo das politicas se interceptam.
				if(intersect(p1, p2)) {
	
					conflict = orthogonalOrgCr(p1, p2);
				}	
				
			}
		}
		return conflict;
	}
	
	
	
	/* MÉTODO PARA DETECTAR CONFLITOS */
	
	public boolean detectConflict(Policie p1, Policie p2) {

		boolean conflict = false;
		
		if(!(p1.getName().equals(p2.getName()))) {
		
			//Verifica se as políticas se aplicam a mesma organização, a mesma entidade e ao mesmo objeto.
			if(((p1.getOrg().equals(p2.getOrg()))) && (p1.getSr().equals(p2.getSr())) && (p1.getOv().equals(p2.getOv()))) {
				
				//isSubOrg = engine.isSubOrg(p1.getOrg(), p2.getOrg());
				
				//Verifica se o tempo das politicas se interceptam.
				if(intersect(p1, p2)) {
					
					conflict = directConflict(p1,p2)
							|| indirectConflict(p1, p2)
							|| refinementCr(p1, p2, setOfPolicies)
							|| compositionCr(p1,p2, setOfPolicies)
							|| orthogonalCr(p1, p2)
							|| dependencyCr(p1, p2);
					}
				}
			}		
		return conflict;
	}
	
	/* MÉTODO PARA CHECAR SE DOIS PERÍODOS SE INTERCEPTAM */

	public boolean intersect(Policie p1, Policie p2) {
		
		if(p1.getAc().before(p2.getDc()) && (p2.getAc().before(p1.getDc()))) {
			return true;
		} else {
			return false;
		}
	}
	
	/* MÉTODO PARA VERIFICAR RELACIONAMENTOS */
	
	private boolean directConflict(Policie p1, Policie p2) {

		totalExecuctionsRulesDirect++;
		
		if(!p1.isDerivated() && !p2.isDerivated()) {
			if(p1.getAa().equals(p2.getAa())) {
				if(p1.getKp().equals(F) && (p2.getKp().equals(P) || p2.getKp().equals(O))) {
					System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: DIRECT");
					System.out.println(p1);
					System.out.println(p2);
					System.out.println();
					totalDirect.add(p1.getOriginalName()+""+p2.getOriginalName());
					countPropagationConflict(p1, p2);
					return true;
				} else if((p1.getKp().equals(O) || p2.getKp().equals(P)) && p2.getKp().equals(F)) {
					System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: DIRECT");
					System.out.println(p1);
					System.out.println(p2);
					System.out.println();
					totalDirect.add(p1.getOriginalName()+""+p2.getOriginalName());
					countPropagationConflict(p1, p2);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean indirectConflict(Policie p1, Policie p2) {

		totalExecuctionsRulesIndirect++;
		
		if(p1.isDerivated() || p2.isDerivated()) {
			if(p1.getAa().equals(p2.getAa())) {
				if(p1.getKp().equals(F) && (p2.getKp().equals(P) || p2.getKp().equals(O))) {
					System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: INDIRECT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
					System.out.println(p1);
					System.out.println(p2);
					System.out.println();
					totalIndirect.add(p1.getOriginalName()+""+p2.getOriginalName());
					countPropagationConflict(p1, p2);
					return true;
				} else if((p1.getKp().equals(O) || p1.getKp().equals(P)) && p2.getKp().equals(F)) {
					System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: INDIRECT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
					System.out.println(p1);
					System.out.println(p2);
					System.out.println();
					totalIndirect.add(p1.getOriginalName()+""+p2.getOriginalName());
					countPropagationConflict(p1, p2);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean dependencyCr(Policie p1, Policie p2) {
		
		totalExecuctionsRulesDependency++;
		
		boolean conflict = false;
		if((p1.getKp().equals(this.O) || p1.getKp().equals(this.P)) && (p2.getKp().equals(this.F))
				&& depends(p1,p2)) {
			conflict = true;
		}
		
		if((p2.getKp().equals(this.O) || p2.getKp().equals(this.P)) && (p1.getKp().equals(this.F))
				&& depends(p2, p1)) {
			conflict = true;
		} else {	
			conflict = false;;
		}
		return conflict;
	}
	
	private boolean orthogonalCr(Policie p1, Policie p2) {
		
		totalExecuctionsRulesOrthogonal++;
		
		boolean conflict = false;
		if((p1.getKp().equals(this.O)) && ((p2.getKp().equals(this.O) || p2.getKp().equals(this.P)))
			&& orthogonal(p1,p2)) {
				conflict = true;
		} 
		
		if((p2.getKp().equals(this.O)) && ((p1.getKp().equals(this.O) || p1.getKp().equals(this.P)))
				&& orthogonal(p2,p1)) {
					conflict = true;
			} else {
			conflict = false;
		}
		return conflict;
	}
	
	private boolean orthogonalRoleCr(Policie p1, Policie p2) {
		
		totalExecuctionsRulesOrthogonalRole++;
	
			if(p1.getSr() != "null" && p2.getSr() != "null") {
			
				String p1Role = engine.getTypeObject(p1.getSr());
				String p2Role = engine.getTypeObject(p2.getSr());
				
				if(p1.getKp().equals(O) && (p2.getKp().equals(O))) {
					if(p1Role.equals("Role") && p2Role.equals("Role") && orthogonalRole(p1, p2)) {
						System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL ROLE CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
						System.out.println(p1);
						System.out.println(p2);
						totalRole.add(p1.getOriginalName()+""+p2.getOriginalName());
						countPropagationConflict(p1, p2);
						System.out.println();
						return true;
					}
				}
				
				if((p1.getKp().equals(P)) && p2.getKp().equals(P)) {
					if(p1Role.equals("Role") && p2Role.equals("Role") && orthogonalRole(p1, p2)) {
						System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL ROLE CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
						System.out.println(p1);
						System.out.println(p2);
						totalRole.add(p1.getOriginalName()+""+p2.getOriginalName());
						countPropagationConflict(p1, p2);
						System.out.println();
						return true;
					}
				}
				
				return false;
			
			}
		return false;
	}
	
	private boolean orthogonalOrgCr(Policie p1, Policie p2) {
		
		totalExecuctionsRulesOrthogonalOrg++;
			
				if(p1.getOrg() != "null" && p2.getOrg() != "null") {
				
					String p1Org = engine.getTypeObject(p1.getOrg());
					String p2Org = engine.getTypeObject(p2.getOrg());
					
					if(p1.getKp().equals(O) && (p2.getKp().equals(O))) {
						if(p1Org.equals("Organization") && p2Org.equals("Organization") && orthogonalOrg(p1, p2)) {
							System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL ORGANIZATION CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
							System.out.println(p1);
							System.out.println(p2);
							System.out.println();
							totalOrthogonalOrg.add(p1.getOriginalName()+""+p2.getOriginalName());
							countPropagationConflict(p1, p2);
							return true;
						}
					}
					
					if((p1.getKp().equals(P)) && p2.getKp().equals(P)) {
						if(p1Org.equals("Organization") && p2Org.equals("Organization") && orthogonalOrg(p1, p2)) {
							System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL ORGANIZATION CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
							System.out.println(p1);
							System.out.println(p2);
							System.out.println();
							totalOrthogonalOrg.add(p1.getOriginalName()+""+p2.getOriginalName());
							countPropagationConflict(p1, p2);
							return true;
						}
					}	
			}
				return false;				
	}
	
	private boolean orthogonalViewCr(Policie p1, Policie p2) {
		
		totalExecuctionsRulesOrthogonalView++;
		
				if(p1.getOv() != "null" && p2.getOv() != "null") {
				
					String p1View = engine.getTypeObject(p1.getOv());
					String p2View = engine.getTypeObject(p2.getOv());
					
					if(p1.getKp().equals(O) && (p2.getKp().equals(O))) {
						
						if(p1View.equals("View") && p2View.equals("View") && orthogonalView(p1, p2)) {
							System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL VIEW CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
							System.out.println(p1);
							System.out.println(p2);
							System.out.println();
							totalOrthogonalView.add(p1.getOriginalName()+""+p2.getOriginalName());
							countPropagationConflict(p1, p2);
							return true;
						}
					}
					
					if((p1.getKp().equals(P)) && p2.getKp().equals(P)) {
						if(p1View.equals("View") && p2View.equals("View") && orthogonalView(p1, p2)) {
							System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL VIEW CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
							System.out.println(p1);
							System.out.println(p2);
							System.out.println();
							totalOrthogonalView.add(p1.getOriginalName()+""+p2.getOriginalName());
							countPropagationConflict(p1, p2);
							return true;
						}
					}
					
					return false;
				
				}
			return false;
	}
	
	private boolean compositionCr(Policie p1, Policie p2, Set<Policie> setOfPolicies) {
		
		totalExecuctionsRulesComposition++;
		
		List<String> listOfActions = new ArrayList<String>();
		listOfActions = partActionsOf(p1);
		
		if((p1.getKp().equals(this.O) || p1.getKp().equals(this.P)) && (p2.getKp().equals(this.F))
				&& composition(p1,p2)) {
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: COMPOSITION CONFLICT DO TIPO 1 (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalComposition.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;		
		} 
		
		if((p1.getKp().equals(this.F)) && ((p2.getKp().equals(this.O) || p2.getKp().equals(this.P))) 
				&& composition(p1,p2)) {
			
			if(listOfActions != null) {
			
				for (String action : listOfActions) {
					for (Policie policie : setOfPolicies) {
						
						if(policie.getAa().equals(action)
								&& (!(policie.getKp().equals(this.O)) && !(policie.getKp().equals(this.P)))) {
							return false;
						}
					}			
				}
			}
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: COMPOSITION CONFLICT DO TIPO2 (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalComposition.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;
		}
		return false;
	}

	private boolean refinementCr(Policie p1, Policie p2, Set<Policie> setOfPolicies) {
		
		totalExecuctionsRulesRefinment++;
		
		List<String> listOfActions = new ArrayList<String>();
		listOfActions = subActionsOf(p1);
		
		if((p1.getKp().equals(this.F)) && ((p2.getKp().equals(this.O) || p2.getKp().equals(this.P))) 
				&& refinement(p1,p2)) {
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: REFINMENT CONFLICT1 (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalRefinment.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;
		}
		
		if((p1.getKp().equals(this.O) || p1.getKp().equals(this.P)) && (p2.getKp().equals(this.F))
				&& refinement(p1,p2)) {
			

			if(listOfActions != null) {
				for (String action : listOfActions) {
					for (Policie policie : setOfPolicies) {
						
						if(policie.getAa().equals(action)
								&& (!(policie.getKp().equals(this.F)))) {
							return false;
						}
					}			
				}
			}
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: REFINMENT CONFLICT2 (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalRefinment.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;
		}

		return false;
	}
	
	private boolean objectCompositionCr(Policie p1, Policie p2, Set<Policie> setOfPolicies) {
		
		List<String> listOfObjects = new ArrayList<String>();
		listOfObjects = partObjectsOf(p1);
		
		if((p1.getKp().equals(this.O) || p1.getKp().equals(this.P)) && (p2.getKp().equals(this.F))
				&& objectComposition(p1,p2)) {
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: OBJECT COMPOSITION CONFLICT DO TIPO 1 (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalObjectComposition.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;		
		} 
		
		if((p1.getKp().equals(this.F)) && ((p2.getKp().equals(this.O) || p2.getKp().equals(this.P))) 
				&& objectComposition(p1,p2)) {
			
			if(listOfObjects != null) {
			
				for (String action : listOfObjects) {
					for (Policie policie : setOfPolicies) {
						
						if(policie.getAa().equals(action)
								&& (!(policie.getKp().equals(this.O)) && !(policie.getKp().equals(this.P)))) {
							return false;
						}
					}			
				}
			}
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: OBJECT COMPOSITION CONFLICT DO TIPO2 (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalObjectComposition.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;
		}
		return false;
	}

	
	/* MÉTODOS AUXILIARES */
	
	private boolean depends(Policie p1, Policie p2) {
		
		String p1Action = p1.getAa();
		String p2Action = p2.getAa();
		
		if(engine.getDependencyAction(p1Action).equals(p2Action)) {
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: DEPENDENCY CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalDependency.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;
		} else {
			return false;
		}
	}
	
private boolean orthogonal(Policie p1, Policie p2) {
		
		String p1Action = p1.getAa();
		String p2Action = p2.getAa();
		
		if(engine.getOrthogonalAction(p1Action).equals(p2Action)) {
			System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println();
			totalOrthogonal.add(p1.getOriginalName()+""+p2.getOriginalName());
			countPropagationConflict(p1, p2);
			return true;
		} else {
			return false;
		}
	}

private boolean orthogonalRole(Policie p1, Policie p2) {
	
	String p1Role = p1.getSr();
	String p2Role = p2.getSr();
	
	if(engine.getOrthogonalRole(p1Role).contains(p2Role)) {
		return true;
	} else {
		return false;
	}
}

private boolean orthogonalOrg(Policie p1, Policie p2) {
	
	String p1Org = p1.getOrg();
	String p2Org = p2.getOrg();
	
	if(engine.getOrthogonalOrg(p1Org).contains(p2Org)) {
		return true;
	} else {
		return false;
	}
}

private boolean orthogonalView(Policie p1, Policie p2) {
	
	String p1View = p1.getOv();
	String p2View = p2.getOv();
	
	if(engine.getOrthogonalView(p1View).equals(p2View)) {
		return true;
	} else {
		return false;
	}
}

private boolean roleConflict(Policie p1, Policie p2) {
	
	Set<OWLNamedIndividual> p1Subjects = engine.getSubjectsOfRole(p1.getSr());
	Set<OWLNamedIndividual> p2Subjects = engine.getSubjectsOfRole(p2.getSr());
	boolean roleConflict = false;
	List<String> listEqualsSubjects = new ArrayList<String>();

	if(p1Subjects != null) { 
	
		Iterator iteratorP1 = p1Subjects.iterator();
		
		while(iteratorP1.hasNext()) {
			String p1Subject = iteratorP1.next().toString();
			Iterator iteratorP2 = p2Subjects.iterator();
			while(iteratorP2.hasNext()) {
				String p2Subject = iteratorP2.next().toString();
				
				if(p1Subject.equals(p2Subject)) {
					listEqualsSubjects.add(p1Subject);
					roleConflict = true;
				}
			}
		}	
	}
	
	if(roleConflict) {
		System.out.println("Conflicting ["+p1.getOriginalName() +" AND "+p2.getOriginalName()+"] - TYPE: ORTHOGONAL ROLE CONFLICT (PROPAGATION TYPE: P1 -> "+p1.getOrigin()+" | P2 -> "+p2.getOrigin()+")");
		System.out.println(p1);
		System.out.println(p2);
		totalRole.add(p1.getOriginalName()+""+p2.getOriginalName());
		countPropagationConflict(p1, p2);
		for (String subject : listEqualsSubjects) {
			System.out.println("O sujeito: "+engine.explode(subject)+" exerce os papéis orthogonais: "+p1.getSr()+" e "+p2.getSr());
		}
		System.out.println();
		
	}
	
	return roleConflict;
}

	
	private boolean composition(Policie p1, Policie p2) {
		
		String p1Action = p1.getAa();
		String p2Action = p2.getAa();
		
		/* NÃO TENHO CERTEZA DA ORDEM CERTA, SE PEGO A LISTA DE P2 ACTION, OU DE P1 ACTION, VERIFICAR. */
		
		List<String> listComposition = new ArrayList<String>();
		listComposition = engine.getCompositionAction(p2Action);
		
		if(listComposition != null) {
			for (String actionComposition : listComposition) {
				if(actionComposition.equals(p1Action)) {
					return true;
				}
			}
		} else {
		return false;
		}
		return false;
	}
	
private boolean objectComposition(Policie p1, Policie p2) {
		
		String p1Object = p1.getOv();
		String p2Object = p2.getOv();
		
		/* NÃO TENHO CERTEZA DA ORDEM CERTA, SE PEGO A LISTA DE P2 ACTION, OU DE P1 ACTION, VERIFICAR. */
		
		List<String> listComposition = new ArrayList<String>();
		listComposition = engine.getCompositionOfObject(p2Object);
		
		if(listComposition != null) {
			for (String objectComposition : listComposition) {
				if(objectComposition.equals(p1Object)) {
					return true;
				}
			}
		} else {
		return false;
		}
		return false;
	}
	
	private boolean refinement(Policie p1, Policie p2) {
	
		String p1Action = p1.getAa();
		String p2Action = p2.getAa();
		
		List<String> listRefinement = new ArrayList<String>();
		listRefinement = engine.getRefinementAction(p2Action);
		
		if(listRefinement != null) {
			for (String actionRefinement : listRefinement) {
				if(actionRefinement.equals(p1Action)) {
					return true;
				}
			}
		} else {
		return false;
		}
		return false;
	}
	
	private List<String> partActionsOf(Policie p1) {
		return engine.getCompositionAction(p1.getAa());
	}
	
	private List<String> partObjectsOf(Policie p1) {
		return engine.getCompositionOfObject(p1.getAa());
	}
	
	private List<String> subActionsOf(Policie p1) {
		return engine.getRefinementAction(p1.getAa());
	}
	
	private void countPropagationConflict(Policie p1, Policie p2) {
		
		if(p1.getOrigin().contains("PLAY") || p2.getOrigin().contains("PLAY")) {
			tConflictPlay++;
		}
		if(p1.getOrigin().contains("OWNERSHIP") || p2.getOrigin().contains("OWNERSHIP")) {
			tConflictOwnership++;
		}
		if(p1.getOrigin().contains("ROLE") || p2.getOrigin().contains("ROLE")) {
			tConflictRole++;
		}
		if(p1.getOrigin().contains("OBJECT COMPOSITION") || p2.getOrigin().contains("OBJECT COMPOSITION")) {
			tConflictObjectComposition++;
		}
		if(p1.getOrigin().contains("CONTEXT") || p2.getOrigin().contains("CONTEXT")) {
			tConflictContext++;
		}
	}
}

