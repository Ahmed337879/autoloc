package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicAgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public boolean check(Agence agence, boolean isNew) {
        if (agence == null) {
            return false;
        }
        if (!isNew && agence.getIdAgence() == null) {
            return false;
        }
        return Utils.isNotEmpty(agence.getNom())
                && Utils.isNotEmpty(agence.getVille())
                && Utils.isNotEmpty(agence.getAdresse());
    }

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException("L'identifiant de l'agence ne doit pas être renseigné");
        }
        if (!check(agence, true)) {
            throw new IllegalArgumentException("Les données de l'agence ne sont pas valides");
        }
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(id)));
    }

    @Override
    public List<Agence> findAll() {
        return null;
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existante = findById(id);
        agence.setIdAgence(id);
        if (!check(agence, false)) {
            throw new IllegalArgumentException("Les données de l'agence ne sont pas valides");
        }
        existante.setNom(agence.getNom());
        existante.setVille(agence.getVille());
        existante.setAdresse(agence.getAdresse());
        existante.setTelephone(agence.getTelephone());
        return agenceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", String.valueOf(id));
        }
        agenceRepository.deleteById(id);
    }

    @Override
    public boolean hasVehicules(Long id) {
        return false;
    }
}
