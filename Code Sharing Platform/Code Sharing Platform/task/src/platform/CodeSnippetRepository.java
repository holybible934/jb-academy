package platform;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, Integer> {
    List<CodeSnippet> findTop10OrderByIdDesc();
    CodeSnippet findById(int id);

}
