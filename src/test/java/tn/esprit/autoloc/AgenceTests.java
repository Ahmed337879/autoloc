package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@EnableJpaRepositories(basePackages = "tn.esprit.autoloc")
@ComponentScan(basePackages = "tn.esprit.autoloc")
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;


    private void addAgence(CrudRepository<Agence, Long> crudRepository) {
        // 7. Créer l'agence
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        int ms = (int) System.currentTimeMillis();
        // 7. Créer les deux véhicules
        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("785414TU96" + ms);
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.EN_MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("785414TU95" + ms);
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setCategorie(CategorieVehicule.UTILITAIRE);
        v2.setStatut(StatutVehicule.DISPONIBLE);
        v2.setTarifJournalier(new BigDecimal("80"));
        v2.setAgence(agence);

        // Lier les véhicules à l'agence
        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        // 8. Persister via save
        crudRepository.save(agence);
    }

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    private void loadAgence(CrudRepository<Agence, Long> repository, String typeDepot) {
        StringBuilder resultat = new StringBuilder("Depot utilise : ")
                .append(typeDepot)
                .append('\n');

        repository.findAll().forEach(agence -> {
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

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "AgenceRepositoryMock (CrudRepository)");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "IAgenceRepository (JpaRepository)");
    }

    @Test
    public void loadSortedAgences() {
        // 12. Charger toutes les agences triées par id décroissant
        List<Agence> agences = fullAgenceRepository.findAll(
                Sort.by("idAgence").descending()
        );

        // 13. Afficher les informations sur les agences (sans les véhicules)
        StringBuilder resultat = new StringBuilder("Agences triées par id décroissant :\n");
        agences.forEach(agence -> resultat
                .append("Agence ")
                .append(agence.getIdAgence())
                .append(" - ")
                .append(agence.getNom())
                .append('\n'));

        //  Vérifier le résultat
        fail(resultat.toString());
    }

    @Test
    public void loadPagedAgences() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("idAgence").descending());
        Page<Agence> page = fullAgenceRepository.findAll(pageable);

        // 17. Afficher le nombre total de pages, la page en cours et les agences
        StringBuilder resultat = new StringBuilder("=== Pagination ===\n");
        resultat.append("Nombre total de pages : ").append(page.getTotalPages()).append('\n');
        resultat.append("Page en cours : ").append(page.getNumber()).append('\n');
        resultat.append("Agences de cette page :\n");

        page.getContent().forEach(agence -> resultat
                .append("Agence ")
                .append(agence.getIdAgence())
                .append(" - ")
                .append(agence.getNom())
                .append('\n'));

        // Vérifier le résultat
        fail(resultat.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}