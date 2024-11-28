<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Contato" %>
<%@ page import="java.util.List" %>
<%
    List<Contato> listContato = (List<Contato>) request.getAttribute("listContato");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Contatos</title>
    <link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body>
    <div class="container">
        <h2>Lista de Contatos</h2>
        <a href="contato?action=new"><button>Adicionar Contato</button></a>
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Ações</th>
            </tr>
            <%
                if (listContato != null) {
                    for (Contato contato : listContato) {
            %>
            <tr>
                <td><%= contato.getId() %></td>
                <td><%= contato.getNome() %></td>
                <td><%= contato.getTelefone() %></td>
                <td>
                    <a href="contato?action=edit&id=<%= contato.getId() %>">Editar</a> |
                    <a href="contato?action=delete&id=<%= contato.getId() %>" onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</body>
</html>
