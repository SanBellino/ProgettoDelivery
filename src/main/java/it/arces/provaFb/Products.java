
package it.arces.provaFb;

public class Products {
    
   private int id;
   private String nome_prodotto;
   private String prezzo;
   private int id_shop;

   public Products(){   
   }
   
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getNomeProdotto() {
      return nome_prodotto;
   }
   public void setNomeProdotto(String nome_prodotto) {
      this.nome_prodotto = nome_prodotto;
   }
   public String getPrezzo() {
      return prezzo;
   }
   public void setPrezzo(String prezzo) {
      this.prezzo = prezzo;
   }

   public int getIdShop() {
    return id_shop;
 }
 public void setIdShop(int id_shop) {
    this.id_shop = id_shop;
 }
}