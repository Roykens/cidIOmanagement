package com.cid.cidiomanagement.service;

import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.entities.Service;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPersonnelService {
 
    public Personnel saveOrUpdatePersonnel(Personnel personnel) throws ServiceException;
    
    public Personnel findPersonnelById(Long id) throws ServiceException;
    
    public Personnel findPersonnelByNom(String nom) throws ServiceException;
    
    public List<Personnel> findAllPersonnel() throws ServiceException;
    
    public void deletePersonnel(Long id) throws ServiceException;
    
    public List<Personnel> findAllByService(Long idService) throws ServiceException;
    
    
    public Service saveOrUpdateService(Service service) throws ServiceException;
    
    public Service findServiceById(Long id) throws ServiceException;
    
    public Service findbyCode(String code) throws ServiceException;
    
    public List<Service> findAllService() throws ServiceException;
    
    public void deleteService(Long id) throws ServiceException;
    
    
}
