package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
public class MaintenanceServiceImpl extends GenericServiceImpl<Maintenance, Long> implements IMaintenanceService {

    public MaintenanceServiceImpl(IMaintenanceRepository maintenanceRepository) {
        super(maintenanceRepository);
    }

    @Override
    protected String getEntityName() {
        return "Maintenance";
    }

    @Override
    protected Long getId(Maintenance maintenance) {
        return maintenance.getIdMaintenance();
    }

    @Override
    protected void setFields(Maintenance source, Maintenance existing) {
        existing.setDateDebut(source.getDateDebut());
        existing.setDateFin(source.getDateFin());
        existing.setDescription(source.getDescription());
        existing.setVehicule(source.getVehicule());
    }

    @Override
    public boolean check(Maintenance maintenance, boolean isNew) {
        return super.check(maintenance, isNew)
                && maintenance.getDateDebut() != null
                && (maintenance.getDateFin() == null || !maintenance.getDateFin().isBefore(maintenance.getDateDebut()))
                && Utils.isNotEmpty(maintenance.getDescription());
    }
}
