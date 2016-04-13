<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Login</title>
</head>
<body>
	<form action="login" name="loginForm" method="post">
		<label >Email </label>
		<span>${errorMesageWrongEmail}</span> <br>
		<input type="text" name="email" placeholder="Email"><br>
		<label  >Password </label>
		<span>${errorMesageWrongPassword}</span> <br>
		<input type="password" name="password" placeholder="Passwor"><br>
		<p>${errorMesageExistingUser}</p>
	</form>
</body>
</html>
