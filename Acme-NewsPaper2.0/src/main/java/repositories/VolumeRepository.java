package repositories;

import domain.Article;
import domain.NewsPaper;
import domain.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VolumeRepository extends JpaRepository<Volume,Integer> {

    @Query("select n from NewsPaper n join n.volumes nv where (n.modePrivate = false and n.published = true and nv.id=?1)")
    Collection<NewsPaper> findPublishedAndNotPrivateNewsPaperPerVolume(int volumeId);

    @Query("select n from NewsPaper n join n.volumes nv where (n.published = true and nv.id=?1)")
    Collection<NewsPaper> findPublishedNewsPaperPerVolume(int volumeId);


    @Query("select vn from Volume v join v.newsPapersVolume vn where v.id=?1 and vn.modePrivate = true")
    Collection<NewsPaper> findPrivateNewspapersByVolumeId(int id);

    @Query("select s.volume from SubscribeVolume s where s.customer.id=?1")
    Collection<Volume> findVolumeByCustomer(int customerId);

    @Query("select s.volume from SubscribeVolume s where s.customer.id <>?1")
    Collection<Volume> findNotSubscribesVolumes(int id);
}
