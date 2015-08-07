package com.cid.cidiomanagement;

import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.entities.RoleType;
import com.cid.cidiomanagement.entities.Utilisateur;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.IUtilisateurService;
import com.cid.cidiomanagement.service.ServiceException;
import java.util.List;
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
     //   Etudiant etudiant = new Etudiant();
//        Categorie cat = new Categorie();
//        cat.setNomenclatureSommaire("cat 1");
//        service.saveOrUpdateCategorie(cat);
        
        Utilisateur use = serv.findById(1L);
        use.setEnabled(true);
        serv.saveOrUpdateUtilisateur(use);
        
        List <Utilisateur> user = serv.findAll();
        for (Utilisateur user1 : user) {
            System.out.println(user1);
        }
        
    }
}
