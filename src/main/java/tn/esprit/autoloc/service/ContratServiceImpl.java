package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.math.BigDecimal;

@Service
public class ContratServiceImpl extends GenericServiceImpl<Contrat, Long> implements IContratService {

    public ContratServiceImpl(IContratRepository contratRepository) {
        super(contratRepository);
    }

    @Override
    protected String getEntityName() {
        return "Contrat";
    }

    @Override
    protected Long getId(Contrat contrat) {
        return contrat.getIdContrat();
    }

    @Override
    protected void setFields(Contrat source, Contrat existing) {
        existing.setDateSignature(source.getDateSignature());
        existing.setMontantTotal(source.getMontantTotal());
        existing.setValide(source.isValide());
    }

    @Override
    public boolean check(Contrat contrat, boolean isNew) {
        return super.check(contrat, isNew)
                && contrat.getDateSignature() != null
                && contrat.getMontantTotal() != null
                && contrat.getMontantTotal().compareTo(BigDecimal.ZERO) >= 0;
    }
}
