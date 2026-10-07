package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class AgenceTests {

    @Autowired
    private AgenceRepositoryMock agenceRepository;

    @Test
    void addAgence() {
        Agence agence = new Agence();
        agence.setAdresse("1 Rue Hedi");
        agence.setNom("Agence ariana");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");

        Vehicule premierVehicule = new Vehicule();
        premierVehicule.setCategorie(CategorieVehicule.SUV);
        premierVehicule.setImmatriculation("78514TU96");
        premierVehicule.setMarque("Isuzu");
        premierVehicule.setModele("DMax");
        premierVehicule.setStatut(StatutVehicule.EN_MAINTENANCE);
        premierVehicule.setTarifJournalier(new BigDecimal("100"));
        premierVehicule.setAgence(agence);

        Vehicule deuxiemeVehicule = new Vehicule();
        deuxiemeVehicule.setCategorie(CategorieVehicule.UTILITAIRE);
        deuxiemeVehicule.setImmatriculation("78541TU95");
        deuxiemeVehicule.setMarque("Toyota");
        deuxiemeVehicule.setModele("Yaris");
        deuxiemeVehicule.setStatut(StatutVehicule.DISPONIBLE);
        deuxiemeVehicule.setTarifJournalier(new BigDecimal("80"));
        deuxiemeVehicule.setAgence(agence);

        agence.getVehicules().add(premierVehicule);
        agence.getVehicules().add(deuxiemeVehicule);

        agenceRepository.save(agence);
    }

    @Test
    void loadAgence() {
        StringBuilder resultat = new StringBuilder();

        agenceRepository.findAll().forEach(agence -> {
            resultat.append("Agence ")
                    .append(agence.getIdAgence())
                    .append(" - ")
                    .append(agence.getNom())
                    .append(" - ")
                    .append(agence.getVehicules().size())
                    .append(" vehicule(s)\n");

            agence.getVehicules().forEach(vehicule -> resultat
                    .append("  Vehicule ")
                    .append(vehicule.getIdVehicule())
                    .append(" - ")
                    .append(vehicule.getImmatriculation())
                    .append('\n'));
        });

        fail(resultat.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}
