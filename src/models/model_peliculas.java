/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import bd.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import views.view_peliculas;
/**
 *
 * @author Sebastián
 */
public class model_peliculas {
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;
    view_peliculas view_peliculas = new view_peliculas();
    
    private String ID;
    private int id_pelicula;
    private String nombre_pelicula;
    private String genero;
    private Double precio_venta;

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the id_pelicula
     */
    public int getId_pelicula() {
        return id_pelicula;
    }

    /**
     * @param id_pelicula the id_pelicula to set
     */
    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    /**
     * @return the nombre_pelicula
     */
    public String getNombre_pelicula() {
        return nombre_pelicula;
    }

    /**
     * @param nombre_pelicula the nombre_pelicula to set
     */
    public void setNombre_pelicula(String nombre_pelicula) {
        this.nombre_pelicula = nombre_pelicula;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the precio_venta
     */
    public Double getPrecio_venta() {
        return precio_venta;
    }

    /**
     * @param precio_venta the precio_venta to set
     */
    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }
    
      public void registerMovie(){
        String insert = ("INSERT INTO peliculas (nombre_pelicula, genero, precio_venta) VALUES (?,?,?);");
        Conexion DataBase = new Conexion();
        Connection con = DataBase.getConnection();
        
        try {
            pst = (PreparedStatement) con.prepareStatement(insert); 
            pst.setString(1, this.getNombre_pelicula());
            pst.setString(2, this.getGenero());
            pst.setDouble(3, this.getPrecio_venta());
 
            if (this.getNombre_pelicula().isEmpty() || 
                this.getGenero().isEmpty() || 
                this.getPrecio_venta().isInfinite())
            {
            JOptionPane.showMessageDialog(null, "Los campos no deben quedar vacíos");
            }else{
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Se insertó el registro");
            }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "No se pudo insertar el registro" +ex);
            }catch (NullPointerException err) {
                System.err.println("NullPointer:  " + err.getMessage());
            }
        }
    public void modifyMovie(){
        String update = ("UPDATE peliculas SET nombre_pelicula = ?, genero = ?, precio_venta = ? WHERE id_peliculas = ? ;");
        Conexion DataBase = new Conexion();
        Connection con = DataBase.getConnection();
        try {
            pst = (PreparedStatement) con.prepareStatement(update);
            pst.setString(1, this.getNombre_pelicula());
            pst.setString(2, this.getGenero());
            pst.setDouble(3, this.getPrecio_venta());
 
            if (this.getNombre_pelicula().isEmpty() || 
                this.getGenero().isEmpty() || 
                this.getPrecio_venta().isInfinite())
            {
            JOptionPane.showMessageDialog(null, "Los campos no deben quedar vacíos");    
            }else{
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Se actualizó el registro");
            }
        }catch(SQLException ex){ 
            JOptionPane.showMessageDialog(null, "No se pudo actualizar");
        }catch (NullPointerException err) {
            JOptionPane.showMessageDialog(null, "NullPointer:  " + err.getMessage());
        }
    }  
     public void deleteMovie(){          
        int eliminar = JOptionPane.showConfirmDialog(null, "Quieres eliminar este registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
        if(eliminar == 0){  
            String delete = ("DELETE FROM proveedores WHERE id_peliculas = ? ;");
            Conexion DataBase = new Conexion();
            Connection con = DataBase.getConnection();
            try{
                pst = (PreparedStatement) con.prepareStatement(delete);
                pst.setString(1, this.getID());
                System.out.println("Eliminando a: " + this.getID());
                if (this.getID() == "0"){
                    JOptionPane.showMessageDialog(null, "No se puede eliminar este registro");
                }else{
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Se eliminó el registro");
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "No se pudo actualizar" + ex.getMessage());
            }
        }                         
    }
     
      public void moveFirstRegister(){
        System.out.println("moverAnteriorRegistro view_peliculas");
        try {
            if (!rs.isFirst()) {
                rs.previous();               
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error model: " + ex.getMessage());
        }
    }
    
    public void moveLastRegister(){
         System.out.println("moverUltimoRegistro view_peliculas");
        try {
            rs.last();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error model:" + ex.getMessage());
        }   
    }
}
