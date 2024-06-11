package it.arces.provaFb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import java.util.List;

@SuppressWarnings("unused")
@SpringBootApplication(scanBasePackages = {"it.arces.provaFb"})
@Controller
@EntityScan
@EnableAutoConfiguration
public class ProvaFbApplication {    
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ProvaFbApplication(JdbcTemplate jdbcTemplateAutoWired) {
        jdbcTemplate = jdbcTemplateAutoWired;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProvaFbApplication.class, args);
    }

    @Configuration
    public class ThymeleafConfig {

        @Bean
        public SpringResourceTemplateResolver templateResolver() {
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setPrefix("classpath:/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setCacheable(false);
            return templateResolver;
        }
    }
}

   

    /* @GetMapping("/users")
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
        System.out.println("Funziona");
        return "users";
    }

    @GetMapping("/users/{id}")
    public String showUserDetails(@PathVariable int id, Model model) {
        @SuppressWarnings("deprecation")
        User user = jdbcTemplate.queryForObject("SELECT ID, user, password, email, role FROM users WHERE id = ?",
                new Object[] { id },
                (rs, rowNum) -> {
                    User u = new User();
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
public String editUser(@PathVariable int id, @ModelAttribute User user) {
    System.out.println("Editing user with ID: " + user.getId());
    jdbcTemplate.update("UPDATE users SET user = ?, email = ?, password = ?, role = ? WHERE id = ?", user.getUser(), user.getEmail(), user.getPassword(), user.getRole(), user.getId());
    return "redirect:/users";
}

} */