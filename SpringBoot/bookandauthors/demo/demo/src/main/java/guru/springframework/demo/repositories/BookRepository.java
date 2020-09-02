package guru.springframework.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.demo.domain.Book;

public interface BookRepository extends CrudRepository<Book , Long>{
    
}
    

