package bliareg.organizer.utils.db;

import bliareg.organizer.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bliareg on 10.12.15.
 */

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

    public User findById(Long id);
    public User findBylogin(String login);

}
