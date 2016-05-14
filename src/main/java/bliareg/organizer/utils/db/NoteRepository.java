package bliareg.organizer.utils.db;

import bliareg.organizer.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by bliareg on 10.12.15.
 */

@Repository
public interface NoteRepository extends CrudRepository<Note,Long>{

 public List<Note> findByuserid(Long userid);



}
