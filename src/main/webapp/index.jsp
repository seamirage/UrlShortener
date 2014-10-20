<!DOCTYPE html>
<html lang="en">


<!-- Custom styles for this template -->
<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<head>
    <script src="lib/jquery/jquery-2.1.1.min.js"></script>
    <script src="lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="scripts/API.js"></script>
</head>
<body>

<div class="container">
<h4 class="form-signin-heading">Paste your long url to shorten it</h4>
     <form class="form-inline" role="form">
         <div class="form-group">
            <input type="text" class="form-control" id="longUrl" autofocus>
         </div>
     </form>
     <button class="btn btn-primary">Shorten</button>
     <form class="form-inline" role="form">
        <div class="form-group">
            <input type="text" class="form-control" id="shortUrl">
        </div>
     </form>
</div>
<script>
    $('button').on('click', function() {
        $.ajax({
          url: API.SHORTEN + $('#longUrl').val()
        }).done(function(a1, isSuccess, response) {
          if(isSuccess === 'success') {
            $('#shortUrl').val(response.responseText);
          } else {
            console.log('error: ' + response);
          }
        });
    });
</script>
</body>
</html>