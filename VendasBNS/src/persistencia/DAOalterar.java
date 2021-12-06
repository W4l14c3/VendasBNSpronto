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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Wallace
 */
public class DAOalterar {
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
    
    public int alterar(Alterar telaAlter){
        try {
            pt = conn.prepareStatement("UPDATE produtos SET nome = ?, descricao = ?, categoria = ?, valor = ?, dataAltera = ?  WHERE codigo = ?");// INSERÇÃO DE DADOS USANDO (?) PARAMETROS
            // Abaixo estão as variaveis da classe CadFunc que serão inseridas no lugar parametros(?)
            pt.setString(1, telaAlter.getNome());
            pt.setString(2, telaAlter.getDescricao());
            pt.setString(3, telaAlter.getCategoria());
            pt.setString(4, telaAlter.getValor());
            pt.setString(5, telaAlter.getDatadaAlteração());
            pt.setInt(6, telaAlter.getCodigo());
            pt.execute();// Para executar a Query (Inserção de dados)
            conn.close();//Fecha conexão
            return 1;// retorna 1 se o cadastro foi realizado
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            if(ex.getErrorCode() == 1062){// captura o ero de duplicidade de chave primaria
                return 1062;
            }
            else{return 0;}// retorna 0 se não foi possivel cadastrar
        }

    }
    
     public Alterar consultar(int cod){
        try {
            pt = conn.prepareStatement("SELECT * FROM produtos WHERE codigo = ?");
            pt.setInt(1, cod);
            rs = pt.executeQuery();
            
            if(rs.next()){
                Alterar alterar = new Alterar();
                alterar.setCodigo(rs.getInt("codigo"));
                alterar.setNome(rs.getString("nome"));
                alterar.setDescricao(rs.getString("descricao"));
                alterar.setCategoria(rs.getString("categoria"));
                alterar.setValor(rs.getString("valor"));
                
                return alterar;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR " + ex);
            return null;
        }
     }
     
}
