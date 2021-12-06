/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Wallace
 */
public class DAOVendas {

     String url = "jdbc:mysql://localhost:3306/vendasbns";
    String user = "root";
    String password = "WALL01101FfX7ss";
    
    private Connection conn; //Declarando variavel conexão
    private PreparedStatement pt;//Para codigos SQL
    private ResultSet rs; //Arrey guarda dados consultados
    
    public boolean conectar(){// Methodo de conexão com o Banco
        try {//tentar conectar
            Class.forName("com.mysql.jdbc.Driver");//Esse é o driver MYSQL CONNECTOR sendo iniciado.
            conn = DriverManager.getConnection(url, user, password);
            //DriverManager
            //Para abrir uma conexão com um banco de dados, precisamos utilizar sempre um driver. 
            //A classe DriverManager é a responsável por se comunicar com todos os drivers que você deixou disponível.
            //Por exemplo o driver de conexão MYSQL-CONNECTOR na biblioteca.

            return true;//se houver conexão retorna verdadeiro
        } catch (ClassNotFoundException | SQLException ex) {//captura o erro do driver sql ou da conexão com o banco
            return false;//se não houver conexão retorna falso
        }
    }

    public Vendas consultar(int cod) {
        try {
            pt = conn.prepareStatement("SELECT * FROM produtos WHERE codigo = ?");
            pt.setInt(1, cod);
            rs = pt.executeQuery();

            if (rs.next()) {
                Vendas vendas = new Vendas();
                vendas.setCodigo(rs.getInt("codigo"));
                vendas.setNome(rs.getString("nome"));
                vendas.setDescricao(rs.getString("descricao"));
                vendas.setCategoria(rs.getString("categoria"));
                vendas.setValor(rs.getString("valor"));

                return vendas;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR " + ex);
            return null;
        }
    }

}
