<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List books</title>
</head>

<body>
<h2>Books</h2>
<g:each in="${books}">
    <p>${it.author} - ${it.title}</p>
</g:each>
</body>
</html>