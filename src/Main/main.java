package Main;
import models.model_peliculas;
import views.view_peliculas;
import controllers.controller_peliculas;
/**
 *
 * @author Sebastián
 */
public class main {
    public static void main(String[] args) {
        model_peliculas model_peliculas =  new model_peliculas();
        view_peliculas view_peliculas = new view_peliculas();
        controller_peliculas controller_peliculas = new controller_peliculas(view_peliculas, model_peliculas);
        
    }
    
}
