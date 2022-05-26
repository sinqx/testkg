package ivt.tp.project2.serviceImplementation;

import ivt.tp.project2.entity.Test;
import ivt.tp.project2.entity.User;
import ivt.tp.project2.exception.ObjectNotFoundException;
import ivt.tp.project2.repository.TestRepository;
import ivt.tp.project2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRepository;


    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public Test findById(Long id) {
        return testRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Test with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public List<Test> findAllUserTests(User user) {
        return testRepository.findAllByCreatorUser(user);
    }

    @Override
    public List<Test> getAllTests() {
        try {
            return testRepository.findAll();
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }


    @Override
    public String deleteById(Long id) {
        Test test = findById(id);
        if (test == null) {
            throw new ObjectNotFoundException("Test with id \"" + id + "\" doesn't exist");
        } else {
            testRepository.delete(test);
            return "Test with id \"" + id + "\" is deleted";
        }
    }
}
