<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="generator"
    content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <meta charset="UTF-8" />
    <title>AccountManager</title>
  </head>
  <body>
    <h1>Users in the system : ${loggedUsers}</h1>
    <br />
    <a href="logout">LOGOUT</a>
    <h1>Balance is : ${balance}</h1>
    <br />
    <form action="balance" name="balanceForm" method="post">
    <input type="text" name="amount" placeholder="amount" /> 
    <span>${errorMessage}</span>
    <br />
    <input type="submit" name="transactionType" value="withdraw" /> 
    <input type="submit" name="transactionType" value="deposit" /></form>
  </body>
</html>
