package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation, Long> implements IReservationService {

    public ReservationServiceImpl(IReservationRepository reservationRepository) {
        super(reservationRepository);
    }

    @Override
    protected String getEntityName() {
        return "Reservation";
    }

    @Override
    protected Long getId(Reservation reservation) {
        return reservation.getIdReservation();
    }

    @Override
    protected void setFields(Reservation source, Reservation existing) {
        existing.setDateDebut(source.getDateDebut());
        existing.setDateFin(source.getDateFin());
        existing.setStatut(source.getStatut());
        existing.setClient(source.getClient());
        existing.setVehicule(source.getVehicule());
        existing.setContrat(source.getContrat());
    }

    @Override
    public boolean check(Reservation reservation, boolean isNew) {
        return super.check(reservation, isNew)
                && reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && !reservation.getDateFin().isBefore(reservation.getDateDebut())
                && Utils.isNotEmpty(reservation.getStatut());
    }
}
