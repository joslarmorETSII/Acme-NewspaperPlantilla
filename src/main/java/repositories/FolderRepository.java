package repositories;

import domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Integer>{

    @Query("select f from Folder f where f.actor.id=?1 and f.name=?2 and f.system = true")
    Folder findActorAndFolder(int idActor, String nameFolder);


}
