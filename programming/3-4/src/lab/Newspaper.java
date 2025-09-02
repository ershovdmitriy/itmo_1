package lab;

import java.util.ArrayList;
import java.util.List;

public class Newspaper {
    private final String name;
    private List<Article> articles = new ArrayList<>();
    protected Newspaper(String name){
        this.name = name;
    }

    public List<Article> getArticles(){
        return articles;
    }
    public String getName(){
        return name;
    }

    public void addArticle(Article article){
        articles.add(article);
    }

    @Override
    public String toString() {
        return name;
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Newspaper)) {
            return false;
        }
        Newspaper other = (Newspaper) obj;
        return this.name == other.name;
    }
}
