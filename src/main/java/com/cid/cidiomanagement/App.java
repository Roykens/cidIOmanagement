package com.cid.cidiomanagement;

import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Service;
import com.cid.cidiomanagement.service.ICommandeService;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.IPersonnelService;
import com.cid.cidiomanagement.service.IUtilisateurService;
import com.cid.cidiomanagement.service.ServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
     //   Etudiant etudiant = new Etudiant();
        Categorie cat = new Categorie();
        cat.setNomenclatureSommaire("categorie");
        service.saveOrUpdateCategorie(cat);
//        
//        Utilisateur use = serv.findById(1L);
//        use.setEnabled(true);
//        serv.saveOrUpdateUtilisateur(use);
        
//        List <Utilisateur> user = serv.findAll();
//        for (Utilisateur user1 : user) {
//            System.out.println(user1);
//        }
        
//        List<Commande> commandes = commserv.findAllByBon(1L);
//        for (Commande commande : commandes) {
//            System.out.println(commande);
//        }
////        Map<String, Commande> result = commandes.stream().collect(Collectors.toMap(Commande::getCategorie,(c)->c));
////        System.out.println(result);
//        Map<String, Commande> result = nameMap(commandes);
//        System.out.println(result);
         
         Service ser = new Service();
         ser.setCode("SAP");
         ser.setNom("service");
         persserv.saveOrUpdateService(ser);
        
    }
    
    private static Map<String, Commande> nameMap(List<Commande> commandes) {
        final Map<String, Commande> hashMap = new HashMap<>();
        for (final Commande choice : commandes) {
            hashMap.put(choice.getCategorie(), choice);
        }
        return hashMap;
}
}
