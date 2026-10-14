package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
public class ClientServiceImpl extends GenericServiceImpl<Client, Long> implements IClientService {

    public ClientServiceImpl(IClientRepository clientRepository) {
        super(clientRepository);
    }

    @Override
    protected String getEntityName() {
        return "Client";
    }

    @Override
    protected Long getId(Client client) {
        return client.getIdClient();
    }

    @Override
    protected void setFields(Client source, Client existing) {
        existing.setNom(source.getNom());
        existing.setPrenom(source.getPrenom());
        existing.setEmail(source.getEmail());
        existing.setTelephone(source.getTelephone());
        existing.setNumPermis(source.getNumPermis());
        existing.setDateInscription(source.getDateInscription());
    }

    @Override
    public boolean check(Client client, boolean isNew) {
        return super.check(client, isNew)
                && Utils.isNotEmpty(client.getNom())
                && Utils.isNotEmpty(client.getPrenom())
                && Utils.isNotEmpty(client.getEmail())
                && Utils.isNotEmpty(client.getNumPermis())
                && client.getDateInscription() != null;
    }
}
