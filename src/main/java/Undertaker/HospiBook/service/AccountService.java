package Undertaker.HospiBook.service;

import Undertaker.HospiBook.model.entities.Person;
import Undertaker.HospiBook.model.entities.User;
import Undertaker.HospiBook.repository.PersonRepository;
import Undertaker.HospiBook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final Map<String, Instant> onlineUsers = new ConcurrentHashMap<>();

    public AccountService(UserRepository userRepository, PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    public void incrementFailedAttemps(User user){
        int newFailedAttemps = user.getFailedLoginAttempts() + 1;
        System.out.println("nombre d'échecs" + newFailedAttemps);
        user.setFailedLoginAttempts(newFailedAttemps);

        if (newFailedAttemps >= 5) {
            user.setActive(false);
            user.setLockTime(LocalDateTime.now());
        }
        this.userRepository.save(user);
    }

    public void resetFailedAttempts(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setFailedLoginAttempts(0);
            user.setLockTime(null);
            userRepository.save(user);
        });
    }

    public boolean isAccountLocked(User user){
        if (user.getLockTime() == null) return false;

        LocalDateTime unlockTime = user.getLockTime().plusMinutes(30);
        return LocalDateTime.now().isBefore(unlockTime);
    }

    public void unlockAccount(Long id){
        this.userRepository.findById(id).ifPresent(user -> {
            user.setActive(true);
            user.setFailedLoginAttempts(0);
            user.setLockTime(null);
            this.userRepository.save(user);
        });
    }

    public Person lockOrUnlock(String email, boolean status){
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Person person = this.personRepository.findByUser(user);
        user.setActive(status);
        this.userRepository.save(user);
        return this.personRepository.save(person);
    }

    public void userIsOnline(String email) {
        onlineUsers.put(email, Instant.now());
    }

    public boolean isUserOnline(String email) {
        Instant lastActivity = onlineUsers.get(email);
        if (lastActivity == null) {
            return false;
        }

        return lastActivity.plus(Duration.ofMinutes(5)).isAfter(Instant.now());
    }

    public List<String> getOnlineUsers() {
        Instant now = Instant.now();
        return onlineUsers.entrySet().stream()
                .filter(entry -> entry.getValue().plus(Duration.ofMinutes(5)).isAfter(now))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 300000)
    public void cleanupInactiveUsers () {
        Instant cutoff = Instant.now().minus(Duration.ofMinutes(5));
        onlineUsers.entrySet().removeIf(entry -> entry.getValue().isBefore(cutoff));
    }
}
