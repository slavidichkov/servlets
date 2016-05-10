<html>
<head>
  <title>${title}</title>
</head>
<body>
  <ul>
    <#list books as book>
    <li>${book.title}</li>
 	<li>${book.publisher}</li>
	<li>${book.date}</li>
        <br>
    </#list>
  </ul>
<form action="books" name="pagePointer" method="post"> 
<input type="submit" name="pagePointer" value="firstPage"> 
<input type="submit" name="pagePointer" value="previousPage"> 
<input type="text" name="pageNumber" value="${currentPageNumber}"  style="width:20px" readonly>
<input type="submit" name="pagePointer" value="nextPage">
<input type="submit" name="pagePointer" value="lastPage">
</form>
</body>
</html> 
