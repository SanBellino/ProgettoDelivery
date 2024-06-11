package it.arces.provaFb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ShopController{    
    @Autowired
    private final JdbcTemplate jdbcTemplate;


    public ShopController(JdbcTemplate jdbcTemplateAutoWired) {
        jdbcTemplate = jdbcTemplateAutoWired;
    }   

        @GetMapping("/shops")
        public String showShops(Model model) {
            List<Shop> shops = jdbcTemplate.query("SELECT * FROM shops", (rs, rowNum) -> {
                Shop shop = new Shop();
                shop.setId(rs.getInt("id"));
                shop.setName(rs.getString("name"));
                shop.setAddress(rs.getString("address"));
                return shop;
            });
            model.addAttribute("shops", shops);
            return "shops";
        }

        @GetMapping("/shops/{id}")
        public String showShopDetails(@PathVariable int id, Model model) {
            @SuppressWarnings("deprecation")
            Shop shop = jdbcTemplate.queryForObject("SELECT id, name, address FROM shops WHERE id = ?",
                    new Object[] { id },
                    (rs, rowNum) -> {
                        Shop s = new Shop();
                        s.setId(rs.getInt("id"));
                        s.setName(rs.getString("name"));
                        s.setAddress(rs.getString("address"));
                        return s;
                    });
            model.addAttribute("shop", shop);
            return "shopView";
        }

    

    @PostMapping("/shops/{id}/editShop") // Assicurati che il percorso sia corretto
    public String editShop(@PathVariable int id, @RequestParam("name") String name,
            @RequestParam("address") String address) {
        System.out.println("Editing shop with ID: " + id); // Stampa l'ID per verifica
        jdbcTemplate.update("UPDATE shops SET name = ?, address = ? WHERE id = ?", name, address, id);
        System.out.println("Negozio modificato con successo!");
        return "redirect:/shops/" + id;

    }

    @GetMapping("/shops/new")
    public String newShop(Model model) {
        model.addAttribute("shop", new Shop()); 
        return "newShop"; 
    }

    @PostMapping("/shops/create")
    public String createShop(@ModelAttribute Shop shop) {
        jdbcTemplate.update("INSERT INTO shops (name, address) VALUES (?, ?)", shop.getName(), shop.getAddress());
        return "redirect:/shops"; // Reindirizza alla lista dei negozi dopo la creazione
    }

    @PostMapping("/shops/{id}/delete")
    public String deleteShop(@PathVariable int id) {
        jdbcTemplate.update("DELETE FROM shops WHERE id = ?", id);
        return "redirect:/shops"; // Reindirizza alla lista dei negozi dopo l'eliminazione
    }  

}