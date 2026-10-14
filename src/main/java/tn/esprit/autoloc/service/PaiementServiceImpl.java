package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.math.BigDecimal;

@Service
public class PaiementServiceImpl extends GenericServiceImpl<Paiement, Long> implements IPaiementService {

    public PaiementServiceImpl(IPaiementRepository paiementRepository) {
        super(paiementRepository);
    }

    @Override
    protected String getEntityName() {
        return "Paiement";
    }

    @Override
    protected Long getId(Paiement paiement) {
        return paiement.getIdPaiement();
    }

    @Override
    protected void setFields(Paiement source, Paiement existing) {
        existing.setMontant(source.getMontant());
        existing.setDatePaiement(source.getDatePaiement());
        existing.setModePaiement(source.getModePaiement());
        existing.setContrat(source.getContrat());
    }

    @Override
    public boolean check(Paiement paiement, boolean isNew) {
        return super.check(paiement, isNew)
                && paiement.getMontant() != null
                && paiement.getMontant().compareTo(BigDecimal.ZERO) > 0
                && paiement.getDatePaiement() != null
                && Utils.isNotEmpty(paiement.getModePaiement());
    }

    // Le paiement est en lecture seule : les opérations d'écriture sont interdites

    @Override
    public Paiement create(Paiement paiement) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule");
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule");
    }
}
