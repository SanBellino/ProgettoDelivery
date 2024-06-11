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

public class ProductController{    
    @Autowired
    private final JdbcTemplate jdbcTemplate;


    public ProductController(JdbcTemplate jdbcTemplateAutoWired) {
        jdbcTemplate = jdbcTemplateAutoWired;
    }   

        @GetMapping("/products")
        public String showProducts(Model model) {
            List<Products> products = jdbcTemplate.query("SELECT * FROM products", (rs, rowNum) -> {
                Products product = new Products();
                product.setIdShop(rs.getInt("id_shop"));
                product.setId(rs.getInt("id"));
                product.setNomeProdotto(rs.getString("nome_prodotto"));
                product.setPrezzo(rs.getString("prezzo"));
                return product;
            });
            model.addAttribute("products", products);
            return "products";
        }

    }