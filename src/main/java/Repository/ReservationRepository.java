package Repository;

import com.ebook.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation,Long> {
}
