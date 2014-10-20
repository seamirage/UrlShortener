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
        <h2 class="form-signin-heading">Please sign in</h2>
        <button class="btn btn-lg btn-primary btn-block">Sign in using Google account</button>

</div>
<script>
    $('button').on('click', function() {
        window.location.href = API.SIGN_IN_BY_GOOGLE;
    });
</script>
</body>
</html>