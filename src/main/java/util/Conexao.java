package util;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/conexao")
public class Conexao extends HttpServlet {
    //String url = "jdbc:mysql://localhost:3306/suabase?useUnicode=true&characterEncoding=utf8";

    private static final String URL = "jdbc:mysql://localhost:3306/contatos?useUnicode=true&characterEncoding=utf8";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    // Método para obter a conexão com o banco de dados
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");  // Carregar o driver MySQL
        return DriverManager.getConnection(URL, USUARIO, SENHA);  // Retornar a conexão
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            Connection conn = null;
            try {
                // Usar o método getConexao para estabelecer a conexão com o banco
                conn = getConexao();
                
                if (conn != null) {
                    out.println("Conexão com o banco de dados estabelecida com sucesso!");
                } else {
                    out.println("Falha ao estabelecer conexão com o banco de dados.");
                }
            } catch (SQLException | ClassNotFoundException e) {
                out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            } finally {
                // Fechar a conexão
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        out.println("Erro ao fechar a conexão: " + e.getMessage());
                    }
                }
            }
        }
    }
}
