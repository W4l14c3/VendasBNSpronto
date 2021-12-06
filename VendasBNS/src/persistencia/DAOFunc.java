/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class DAOFunc {
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
    
    public int salvar(CadFunc func){
        try {
            pt = conn.prepareStatement("INSERT INTO CADASTRO (nomeFull, usuario, email, senha) VALUES (?,?,?,?)");// INSERÇÃO DE DADOS USANDO (?) PARAMETROS
            // Abaixo estão as variaveis da classe CadFunc que serão inseridas no lugar parametros(?)
            pt.setString(1, func.getNome());
            pt.setString(2, func.getUsuario());
            pt.setString(3, func.getEmail());
            pt.setString(4, func.getSenha());
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
}
