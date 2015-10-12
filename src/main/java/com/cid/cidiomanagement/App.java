package com.cid.cidiomanagement;

import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Utilisateur;
import com.cid.cidiomanagement.service.ICommandeService;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.IOrdreSortieService;
import com.cid.cidiomanagement.service.IPersonnelService;
import com.cid.cidiomanagement.service.IUtilisateurService;
import com.cid.cidiomanagement.service.ServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class App {
    
    public static void main(String[] args) throws ServiceException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:config/spring-config.xml");
        IDonneeService service = (IDonneeService) ctx.getBean("IDonneeService");
        IUtilisateurService serv = (IUtilisateurService) ctx.getBean("IUtilisateurService");
        ICommandeService commserv = (ICommandeService) ctx.getBean("ICommandeService");
         IPersonnelService persserv = (IPersonnelService) ctx.getBean("IPersonnelService");
         IOrdreSortieService sortiserv = (IOrdreSortieService) ctx.getBean("IOrdreSortieService");
     //   Etudiant etudiant = new Etudiant();
//        Categorie cat = new Categorie();
//        cat.setNomenclatureSommaire("categorie");
//        service.saveOrUpdateCategorie(cat);
//        
//        Utilisateur use = serv.findById(1L);
//        use.setEnabled(true);
//        serv.saveOrUpdateUtilisateur(use);
        
//        List <Utilisateur> user = serv.findAll();
//        for (Utilisateur user1 : user) {
//            System.out.println(user1);
//        }
        List<Article> articles = service.findAllArticle();
        for (Article article : articles) {
            System.out.println(article);
        }
        
        
        System.out.println("Test de range");
        
         List<Article> articles1 = service.findByRange(1, 3);
        for (Article article : articles1) {
            System.out.println(article);
        }
        
        System.out.println("Test de count");
        
        Long zozo = service.countArticles();
        System.out.println(zozo);
        
        System.out.println("Test de finder");
        Article article = service.findArticleById(0L);
        System.out.println(article);
//         List<Affectation> affectations = sortiserv.findAllAffectationByOrdre(5L);
//         System.out.println(affectations);
//         
//         System.out.println("Les articles");
//         for (Affectation affectation : affectations) {
//             System.out.println(affectation.getArticle());
//        }
         
//         List<Affectation> affectations1 = mergeList(affectations);
//         System.out.println("Les affectations");
//         System.out.println(affectations1);
//         for (Affectation affectation : affectations1) {
//             System.out.println(affectation.getArticle());
//        }
        
//        List<Commande> commandes = commserv.findAllByBon(1L);
       // System.out.println("La taille : " + commandes.size());
       // System.out.println("Les categories :" + getCategories(commandes));
        
        System.out.println("Transformation ================= ");
       // System.out.println(transform(commandes));
        
//        Set<String> cat = new TreeSet<>();
//              cat =  getCategories(commandes);
//        Map<String, List<Commande>> data = transform(commandes);
//        for (String cat1 : cat) {
//            List<Commande> temp = data.get(cat1);
//            System.out.println(" liste 1 : " + temp);
//        }
        
        
//        for (Commande commande : commandes) {
//            System.out.println(commande);
//        }
////        Map<String, Commande> result = commandes.stream().collect(Collectors.toMap(Commande::getCategorie,(c)->c));
////        System.out.println(result);
//        Map<String, Commande> result = nameMap(commandes);
//        System.out.println(result);
        
         
//         Service ser = new Service();
//         ser.setCode("SAP");
//         ser.setNom("service");
//         persserv.saveOrUpdateService(ser);
        
    }
    
    private static Map<String, Commande> nameMap(List<Commande> commandes) {
        final Map<String, Commande> hashMap = new HashMap<>();
        for (final Commande choice : commandes) {
            hashMap.put(choice.getCategorie(), choice);
        }
        return hashMap;
}
    
    private static Set<String> getCategories(List<Commande> commandes){
        Set<String> result = new HashSet<>();
        for (Commande commande : commandes) {
            result.add(commande.getCategorie());
        }
        return result;
    }
    
    private static Map<String, List<Commande>> transform(List<Commande> commandes){
        
        Map<String, List<Commande>> result = new HashMap<>();
        Set<String> cat = getCategories(commandes);
        for (String cat1 : cat) {
            List<Commande> temp = new ArrayList<>();
            for (Commande com : commandes) {
                if(com.getCategorie().equals(cat1)){
                    temp.add(com);
                }
            }
            result.put(cat1, temp);
        }
        return result;
    }
    
     private static List<Affectation> mergeList(List<Affectation> affectations) {
        List<Affectation> result = new ArrayList<>();
        if(affectations.size() > 0){
            result.add(affectations.get(0));
            affectations.remove(0);
        }
        for (Affectation tmp11 : affectations) {
            for (int i = 0; i < result.size(); i++) {
               
                    if (result.get(i).myCompare(tmp11)) {
                        System.out.println("C'est bon");
                        result.get(i).setNombre(tmp11.getNombre() + result.get(i).getNombre());
                        break;
                    } else if(i == result.size()-1){
                        System.out.println("C'est bon2");
                        result.add(tmp11);
                        break;
                    }               
            }
        }
        return result;
    }
}
