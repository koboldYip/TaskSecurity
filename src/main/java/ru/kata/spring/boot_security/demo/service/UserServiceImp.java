package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private UserRepository repository;

    public UserServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(User user) {
        repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void update(User user) {
        User userDb = repository.getById(user.getId());
        user.setPassword(userDb.getPassword());
        repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        user.getRoles().size();
        if (user == null) {
            throw new UsernameNotFoundException("Failed to retrieve user: " + username);
        }
        return user;
    }
}
