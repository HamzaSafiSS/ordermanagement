package com.hamza.ordermanagementsystem.service.impl;

import com.hamza.ordermanagementsystem.entity.User;
import com.hamza.ordermanagementsystem.repository.UserRepository;
import com.hamza.ordermanagementsystem.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void testLifecycle() {

        System.out.println("===== TRANSIENT =====");
        User user = new User();
        user.setFirstName("Abel");
        user.setLastName("Tesfaye");
        user.setEmail("abel@example.com");

        // Not saved yet → TRANSIENT

        System.out.println("===== PERSISTENT =====");
        userRepository.save(user);

        // Now managed (persistent)

        System.out.println("===== DIRTY CHECKING =====");
        user.setFirstName("Updated Abel");
        // No save() but will auto update

        System.out.println("===== FLUSH =====");
        entityManager.flush();

        System.out.println("===== DETACH =====");
        entityManager.detach(user);

        user.setFirstName("Detached Change");

        System.out.println("===== CLEAR =====");
        entityManager.clear();

        System.out.println("===== REMOVE =====");
        User managedUser = entityManager.find(User.class, user.getId());
        entityManager.remove(managedUser);
    }
}