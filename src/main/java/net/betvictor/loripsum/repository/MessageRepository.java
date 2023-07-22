package net.betvictor.loripsum.repository;

import net.betvictor.loripsum.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
