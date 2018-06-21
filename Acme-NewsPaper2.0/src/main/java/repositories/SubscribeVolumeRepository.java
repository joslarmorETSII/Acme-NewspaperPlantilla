package repositories;

import domain.SubscribeVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeVolumeRepository extends JpaRepository<SubscribeVolume,Integer> {

    @Query("select s from SubscribeVolume s where s.volume.id=?1 and s.customer.id=?2")
    SubscribeVolume findSubscriptionToAVolume(int volumeId, int customerId);
}
