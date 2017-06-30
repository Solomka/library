<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title" >
		<h3>Authentication</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="login" method="POST" role="form">

				<div class="form-group">
					<label for="login">Login</label> <input type="text"
						class="form-control" id="login" name="login" required />
				</div>
				<div class="form-group">
					<label for="pwd">Password</label> <input type="password"
						class="form-control" id="pwd" name="password" required />
				</div>
				<button type="submit" class="btn btn-default" id="submitButton">Submit</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>