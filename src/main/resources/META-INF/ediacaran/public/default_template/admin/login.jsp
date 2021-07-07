<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="https://www.uoutec.com.br/ediacaran/tags/bootstrap4/components" prefix="ec"%>
<ec:setBundle var="sys_messages" locale="${locale}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta property="og:title" content="<fmt:message key="header.title" bundle="${sys_messages}"/>">
	<meta name="description" property="og:description" content="<fmt:message key="header.description" bundle="${sys_messages}"/>">
	<meta name="keywords" content="<fmt:message key="header.keywords" bundle="${sys_messages}"/>">
	<meta name="author" content="<fmt:message key="header.author" bundle="${sys_messages}"/>">
	<meta name="robots" content="noimageindex" />

    <title><fmt:message key="title" bundle="${sys_messages}"/></title>

	<c:import context="/plugins/ediacaran/adm" url="/default_template/admin/includes/header.jsp"/>

	<style type="text/css">
		.has-feedback .form-control-feedback {
		    position: absolute;
		    top: 0;
		    right: 0;
		    z-index: 2;
		    display: block;
		    width: 34px;
		    height: 34px;
		    line-height: 34px;
		    text-align: center;
		    pointer-events: none;
		}
		
		.icheck {
			padding-left: 0px
		}
	</style>
</head>

<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="#"><fmt:message key="box.title" bundle="${sys_messages}"/></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg"><fmt:message key="box.message" bundle="${sys_messages}"/></p>

    <form action="${plugins.ediacaran.adm.login_page}" method="post" dest-content="#result" accept-charset="UTF-8">
      <input type="hidden" name="redirectTo" value="${plugins.ediacaran.adm.adm_page}">
      <div class="form-group has-feedback">
        <input name="username" type="email" class="form-control" placeholder="Email">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input name="password" type="password" class="form-control" placeholder="Password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox"> <fmt:message key="box.remember" bundle="${sys_messages}"/>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat"><fmt:message key="box.sign_in_button" bundle="${sys_messages}"/></button>
        </div>
        <!-- /.col -->
      </div>
      <div class="row">
        <div class="col-xs-12">
	        <div id="result" class="result-check"></div>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <a href="${plugins.ediacaran.adm.forgot_page}"><fmt:message key="box.forgot_pass" bundle="${sys_messages}"/></a><br>
    <a href="${plugins.ediacaran.adm.register_page}" class="text-center"><fmt:message key="box.register" bundle="${sys_messages}"/></a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

	<c:import context="/plugins/ediacaran/adm" url="/${plugins.ediacaran.adm.template}/admin/includes/footer.jsp"/>

	<script type="text/javascript" src="/plugins/ediacaran/adm/default_template/admin/resources/plugins/iCheck/icheck.min.js"     charset="utf-8"></script>
	<script type="text/javascript" src="/plugins/ediacaran/adm/default_template/admin/resources/application/js/regex-patterns.js" charset="utf-8"></script>
	<script type="text/javascript" src="/plugins/ediacaran/basic_security/default_template/admin/js/language/${locale}/login.js"></script>
	<script type="text/javascript" src="/plugins/ediacaran/basic_security/default_template/admin/js/login.js"></script>

</body>
</html>
