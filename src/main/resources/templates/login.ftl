<#import "layout.ftl" as layout>
<@layout.main "Login" "login">
<p>Login pls</p>
<form action="/confirm_login" method="post">
	Email: <input type="text" name="email"><br>
	Password: <input type="password" name="password"><br>
	<button type="submit">Submit</button>
</form>
</@layout.main>