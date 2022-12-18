package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

public  class Stock  {


    private Agence agence;
    private Article article;
    private int quantite ;


    public Stock(Agence agence, Article article, int quantite) {
        this.agence = agence;
        this.article = article;
        this.quantite = quantite;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle_id(Article article) {
        this.article = article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int compareTo(@NonNull Stock o) {
        return 0;
    }

    @Override
    public boolean equals(Object o){
        Stock instance=(Stock) o;
        return agence.getId().equals(instance.getAgence().getId()) && article.getId().equals(instance.getArticle().getId());



    }

}


