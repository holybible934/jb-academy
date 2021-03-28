package platform;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, Long> {
    List<CodeSnippet> findTop10ByOrderByDateDesc();
    CodeSnippet findById(long id);

}