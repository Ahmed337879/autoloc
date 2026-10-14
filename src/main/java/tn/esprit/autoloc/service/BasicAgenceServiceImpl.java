package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicAgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return false;
    }

    @Override
    public Agence create(Agence agence) {
        return null;
    }

    @Override
    public Agence findById(Long id) {
        return null;
    }

    @Override
    public List<Agence> findAll() {
        return null;
    }

    @Override
    public Agence update(Long id, Agence agence) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public boolean hasVehicules(Long id) {
        return false;
    }
}
