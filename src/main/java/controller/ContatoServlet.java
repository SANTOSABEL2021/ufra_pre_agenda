package controller;

import dao.ContatoDAO;
import model.Contato;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/contato")
public class ContatoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContatoDAO contatoDAO;

    @Override
    public void init() {
        contatoDAO = new ContatoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                listContato(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "insert":
                        insertContato(request, response);
                        break;
                    case "delete":
                        deleteContato(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "update":
                        updateContato(request, response);
                        break;
                    default:
                        listContato(request, response);
                        break;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ContatoServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private void listContato(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ClassNotFoundException {
        List<Contato> listContato = contatoDAO.selectAllContatos();
        request.setAttribute("listContato", listContato);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, ClassNotFoundException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            Contato existingContato = contatoDAO.selectContato(id);
            request.setAttribute("contato", existingContato);
            RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("contato");
        }
    }

    private void insertContato(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException, ServletException {
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        if (nome != null && telefone != null && !nome.isEmpty() && !telefone.isEmpty()) {
            Contato newContato = new Contato();
            newContato.setNome(nome);
            newContato.setTelefone(telefone);
            contatoDAO.insertContato(newContato);
            response.sendRedirect("contato");
        } else {
            request.setAttribute("error", "Nome e telefone são obrigatórios.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void updateContato(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException, ServletException {
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");

        if (idParam != null && nome != null && telefone != null && !nome.isEmpty() && !telefone.isEmpty()) {
            int id = Integer.parseInt(idParam);
            Contato contato = new Contato(id, nome, telefone);
            contatoDAO.updateContato(contato);
            response.sendRedirect("contato");
        } else {
            request.setAttribute("error", "ID, nome e telefone são obrigatórios.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deleteContato(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            contatoDAO.deleteContato(id);
            response.sendRedirect("contato");
        } else {
            response.sendRedirect("contato");
        }
    }
}
