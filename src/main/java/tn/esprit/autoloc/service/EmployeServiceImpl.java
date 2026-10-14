package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
public class EmployeServiceImpl extends GenericServiceImpl<Employe, Long> implements IEmployeService {

    public EmployeServiceImpl(IEmployeRepository employeRepository) {
        super(employeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Employe";
    }

    @Override
    protected Long getId(Employe employe) {
        return employe.getIdEmploye();
    }

    @Override
    protected void setFields(Employe source, Employe existing) {
        existing.setNom(source.getNom());
        existing.setPrenom(source.getPrenom());
        existing.setRole(source.getRole());
        existing.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Employe employe, boolean isNew) {
        return super.check(employe, isNew)
                && Utils.isNotEmpty(employe.getNom())
                && Utils.isNotEmpty(employe.getPrenom())
                && Utils.isNotEmpty(employe.getRole());
    }
}
