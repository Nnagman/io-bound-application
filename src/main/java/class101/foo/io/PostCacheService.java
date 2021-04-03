package class101.foo.io;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PostCacheService {

    private final PostRepository postRepository;
    private Page<Post> firstPostPage;

    public PostCacheService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Scheduled(cron = "* * * * * *")
    public void updateFirstPostPage() {
        firstPostPage = postRepository.findAll(
                PageRequest.of(0, 20, Sort.by("id").descending())
        );
    }

}
