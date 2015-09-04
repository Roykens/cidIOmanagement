package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IPersonnelDao;
import com.cid.cidiomanagement.dao.IServiceDao;
import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.entities.Service;
import com.cid.cidiomanagement.service.IPersonnelService;
import com.cid.cidiomanagement.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Transactional
public class PersonnelServiceImpl implements IPersonnelService {

    private IPersonnelDao personnelDao;

    private IServiceDao serviceDao;

    public IPersonnelDao getPersonnelDao() {
        return personnelDao;
    }

    public void setPersonnelDao(IPersonnelDao personnelDao) {
        this.personnelDao = personnelDao;
    }

    public IServiceDao getServiceDao() {
        return serviceDao;
    }

    public void setServiceDao(IServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public Personnel saveOrUpdatePersonnel(Personnel personnel) throws ServiceException {
        try {
            if (personnel.getId() == null) {
                return personnelDao.create(personnel);
            } else {
                return personnelDao.update(personnel);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Personnel findPersonnelById(Long id) throws ServiceException {
        try {
            return personnelDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Personnel> findAllPersonnel() throws ServiceException {
        try {
            return personnelDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deletePersonnel(Long id) throws ServiceException {
        try {
            Personnel personnel = personnelDao.findById(id);
            if (personnel != null) {
               
                personnelDao.delete(personnel);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Personnel> findAllByService(Long idService) throws ServiceException {
        try {
            Service service = serviceDao.findById(idService);
            if (service == null) {
                throw new ServiceException("Service not found");
            }
            return personnelDao.findAllPersonnelByService(service);
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Service saveOrUpdateService(Service service) throws ServiceException {
        System.out.println("Dans le service===================================\n");
        try {
            if (service.getId() == null) {
                System.out.println("J'enregistre =====================");
                return serviceDao.create(service);
            }
            else{
                return serviceDao.update(service);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Service findServiceById(Long id) throws ServiceException {
        try {
            return serviceDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Service> findAllService() throws ServiceException {
        try {
            return serviceDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deleteService(Long id) throws ServiceException {
        try {
            Service service = serviceDao.findById(id);
            if(service == null){
                
                throw new ServiceException("Service not found");
            }
            service.setActive(false);
            serviceDao.update(service);
            List<Personnel> personnels = personnelDao.findAllPersonnelByService(service);
            for (Personnel personnel : personnels) {
                personnel.setActive(false);
                personnelDao.update(personnel);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Service findbyCode(String code) throws ServiceException {
        try {
            return serviceDao.findServiceByCode(code);
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Personnel findPersonnelByNom(String nom) throws ServiceException {
        try {
            return personnelDao.findPersonnelByNom(nom);
        } catch (DataAccessException ex) {
            Logger.getLogger(PersonnelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
