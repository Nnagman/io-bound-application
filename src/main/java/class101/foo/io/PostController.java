package class101.foo.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostRepository postRepository;
    private final Producer producer;
    private final ObjectMapper objectMapper;

    public PostController(PostRepository postRepository, Producer producer, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    // 1. 글을 작성한다.
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) throws JsonProcessingException {
        String jsonPost = objectMapper.writeValueAsString(post);
        producer.sendTo(jsonPost);
        return post;
    }

    // 4. 글 내용으로 검색 -> 해당 내용이 포함된 모든 글
    @GetMapping("/search")
    public List<Post> findPostByContent(@RequestParam String content) {
        return postRepository.findByContent(content);
    }
}
