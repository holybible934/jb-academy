package platform;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, Long> {
    List<CodeSnippet> findTop10ByOrderByIdDesc();
    CodeSnippet findByUUId(String uuid);
    List<CodeSnippet> findAllByOrderByIdDesc();
}
