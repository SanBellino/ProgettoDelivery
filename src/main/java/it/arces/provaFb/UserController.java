package it.arces.provaFb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplateAutoWired) {
        jdbcTemplate = jdbcTemplateAutoWired;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUser(rs.getString("user"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            return user;
        });
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String showUserDetails(@PathVariable int id, Model model) {
        @SuppressWarnings("deprecation")
        User user = jdbcTemplate.queryForObject("SELECT ID, user, password, email, role FROM users WHERE id = ?",
                new Object[] { id },
                (rs, rowNum) -> {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUser(rs.getString("user"));
                    u.setPassword(rs.getString("password"));
                    u.setEmail(rs.getString("email"));
                    u.setRole(rs.getString("role"));
                    return u;
                });
        model.addAttribute("users", user);
        return "usersView";
    }

    @PostMapping("/users/{id}/edit")
public String editUsers(@PathVariable int id, @RequestParam("user") String user,
                        @RequestParam("password") String password, @RequestParam("email") String email,
                        @RequestParam("role") String role) {
    System.out.println("Received ID: " + id); // Debugging
    System.out.println("Received User: " + user);
    System.out.println("Received Password: " + password);
    System.out.println("Received Email: " + email);
    System.out.println("Received Role: " + role);

    try {
        int rowsAffected = jdbcTemplate.update(
            "UPDATE users SET user = ?, password = ?, email = ?, role = ? WHERE id = ?", 
            user, password, email, role, id
        );

        if (rowsAffected == 0) {
            System.err.println("Errore: Utente con ID " + id + " non trovato.");
            return "redirect:/errorPageNotFound";
        } else {
            System.out.println("Utente modificato con successo!");
            return "redirect:/users";
        }
    } catch (EmptyResultDataAccessException e) {
        System.err.println("Errore durante la modifica dell'utente: " + e.getMessage());
        return "redirect:/errorPageDatabaseError";
    }
}

@PostMapping("/users/{id}/verifyPassword")
public String verifyPassword(@PathVariable int id, @RequestParam("password") String password, Model model) {
    @SuppressWarnings("deprecation")
    User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] { id },
        (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUser(rs.getString("user"));
            u.setPassword(rs.getString("password"));
            u.setEmail(rs.getString("email"));
            u.setRole(rs.getString("role"));
            return u;
        });
    
    if (user != null && user.getPassword().equals(password)) {
        model.addAttribute("user", user);
        return "userDetails";  // Crea un nuovo template per mostrare i dettagli dell'utente
    } else {
        model.addAttribute("error", "Password errata. Riprova.");
        return "redirect:/users";
    }
}

@PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
        return "redirect:/users"; // Reindirizza alla lista dei negozi dopo l'eliminazione
    }  

}