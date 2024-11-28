<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Contato" %>
<%
    Contato contato = (Contato) request.getAttribute("contato");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= (contato != null) ? "Editar Contato" : "Adicionar Contato" %></title>
    <link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body>
    <div class="container">
        <h2><%= (contato != null) ? "Editar Contato" : "Adicionar Contato" %></h2>
        <form action="contato?action=<%= (contato != null) ? "update" : "insert" %>" method="post">
            <input type="hidden" name="id" value="<%= (contato != null) ? contato.getId() : "" %>" />
            <label>Nome:</label>
            <input type="text" name="nome" value="<%= (contato != null) ? contato.getNome() : "" %>" required />
            <label>Telefone:</label>
            <input type="text" name="telefone" value="<%= (contato != null) ? contato.getTelefone() : "" %>" required />
            <input type="submit" value="<%= (contato != null) ? "Atualizar" : "Salvar" %>">
            <a href="contato"><button type="button">Cancelar</button></a>
        </form>
    </div>
</body>
</html>
