package controllers;

/**
 *
 * @author Sebastián
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import views.view_peliculas;
import models.model_peliculas;
import bd.Conexion;
import java.sql.PreparedStatement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class controller_peliculas implements ActionListener{
    view_peliculas view_peliculas =  new view_peliculas();
    model_peliculas model_peliculas =  new model_peliculas();
    
    public controller_peliculas (view_peliculas view_peliculas, model_peliculas model_peliculas){
        this.view_peliculas = view_peliculas;
        this.model_peliculas = model_peliculas;
        
        initComponents();
        view_peliculas.jb_add.addActionListener(this);
        view_peliculas.jb_delete.addActionListener(this);
        view_peliculas.jb_modify.addActionListener(this);
        view_peliculas.jb_new.addActionListener(this);
        view_peliculas.jb_search.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view_peliculas.jb_add){
            model_peliculas.setNombre_pelicula(view_peliculas.jtf_name_film.getText());
            model_peliculas.setGenero(view_peliculas.jtf_gender_movie.getText());
            model_peliculas.setPrecio_venta(Double.parseDouble(view_peliculas.jtf_price.getText()));
            model_peliculas.registerMovie();
            
            view_peliculas.jtf_name_film.setText("");
            view_peliculas.jtf_gender_movie.setText("");
            view_peliculas.jtf_price.setText("");
            view_peliculas.jb_delete.setEnabled(true);
            view_peliculas.jb_modify.setEnabled(true);
            view_peliculas.jb_new.setEnabled(true);
            view_peliculas.jb_search.setEnabled(true);
            cargar();
        }else if (e.getSource() == view_peliculas.jb_modify){
            model_peliculas.setNombre_pelicula(view_peliculas.jtf_name_film.getText());
            model_peliculas.setGenero(view_peliculas.jtf_gender_movie.getText());
            model_peliculas.setPrecio_venta(Double.parseDouble(view_peliculas.jtf_price.getText()));
            model_peliculas.modifyMovie();
            
            view_peliculas.jb_delete.setEnabled(true);
            view_peliculas.jb_modify.setEnabled(true);
            view_peliculas.jb_new.setEnabled(true);
            view_peliculas.jb_search.setEnabled(true);
            cargar();            
        }else if (e.getSource() == view_peliculas.jb_search){
              if (view_peliculas.jcb_search.getSelectedItem() == "Nombre") {
                searchByName();
            } else if (view_peliculas.jcb_search.getSelectedItem() == "Genero") {
                searchByGender();
            }   
        }
        else if (e.getSource()==view_peliculas.jb_next){
            jb_next_actionPerformed();
        }
        else if (e.getSource()==view_peliculas.jb_prev){
            jb_prev_actionPerformed();
        }
    }
    
    private Connection conexion;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement pst;
    
    private void initComponents() {
        view_peliculas.setTitle("Peliculas");
        view_peliculas.setLocationRelativeTo(null);
        view_peliculas.setVisible(true);
        cargar();
        setAction();
    }

    private void cargar() {
        DefaultTableModel model;
        String[] titulos = {"ID", "Nombre", "Genero", "Precio"};
        String[] registros = new String[4];
        String sql = "SELECT * FROM peliculas;";

        model = new DefaultTableModel(null, titulos);
        Conexion DataBase = new Conexion();
        Connection con = DataBase.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("id_peliculas");
                registros[1] = rs.getString("nombre_pelicula");
                registros[2] = rs.getString("genero");
                registros[3] = rs.getString("precio_venta");
                model.addRow(registros);

            }
            view_peliculas.jt_movies.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo importar la tabla");
        }
    }
    
      public void jt_movie_MouseClicked() {
        try {
            if (view_peliculas.jt_movies.getSelectedRow() != -1) {
                int i = view_peliculas.jt_movies.getSelectedRow();
                
                view_peliculas.jtf_name_film.setText(view_peliculas.jt_movies.getValueAt(i, 0).toString());
                view_peliculas.jtf_gender_movie.setText(view_peliculas.jt_movies.getValueAt(i, 1).toString());
                view_peliculas.jtf_price.setText(view_peliculas.jt_movies.getValueAt(i, 2).toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay registros.");
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a los registros de la tabla" );
        }

    }
    private void searchByName() {
          try {
            String search_by_name = ("SELECT * FROM peliculas WHERE nombre_pelicula LIKE %?%");
            Conexion DataBase = new Conexion();
            Connection con = DataBase.getConnection();
            pst = (PreparedStatement) con.prepareStatement(search_by_name);
            pst.setString(1, view_peliculas.jtf_search.getText());
            pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se encontró la pelicula");
        }
    }

    private void searchByGender() {
          try {
            String search_by_name = ("SELECT * FROM peliculas WHERE genero LIKE %?%");
            Conexion DataBase = new Conexion();
            Connection con = DataBase.getConnection();
            pst = (PreparedStatement) con.prepareStatement(search_by_name);
            pst.setString(1, view_peliculas.jtf_search.getText());
            pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se encontró el genero");
        }
    
    }

    private void setAction() {
         view_peliculas.jt_movies.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jt_movie_MouseClicked();
            }
        });
    
    }
     private void jb_next_actionPerformed(){
        model_peliculas.moveLastRegister();
    }
     
    private void jb_prev_actionPerformed(){
        model_peliculas.moveFirstRegister();        
    }
    
}
