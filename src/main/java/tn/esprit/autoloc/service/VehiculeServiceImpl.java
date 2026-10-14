package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    public VehiculeServiceImpl(IVehiculeRepository vehiculeRepository) {
        super(vehiculeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule vehicule) {
        return vehicule.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule source, Vehicule existing) {
        existing.setImmatriculation(source.getImmatriculation());
        existing.setMarque(source.getMarque());
        existing.setModele(source.getModele());
        existing.setCategorie(source.getCategorie());
        existing.setTarifJournalier(source.getTarifJournalier());
        existing.setStatut(source.getStatut());
        existing.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule vehicule, boolean isNew) {
        return super.check(vehicule, isNew)
                && Utils.isNotEmpty(vehicule.getImmatriculation())
                && Utils.isNotEmpty(vehicule.getMarque())
                && Utils.isNotEmpty(vehicule.getModele())
                && vehicule.getCategorie() != null
                && vehicule.getStatut() != null
                && vehicule.getTarifJournalier() != null
                && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) > 0;
    }
}
