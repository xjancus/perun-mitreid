<%@tag pageEncoding="UTF-8" %>
<%@ attribute name="js" required="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/common" %>
<jsp:useBean id="date" class="java.util.Date" />
<c:set var="issuer" value="${config.issuer}" />
<c:set var="baseUrl" value="${fn:substringBefore(issuer, 'oidc')}" />
<div id="footer">
    <div style="margin: 0 auto; max-width: 1000px;">
        <div style="float: left;">
            <img src="<c:out value='${baseUrl}'/>proxy/module.php/elixir/res/img/logo_64.png">
        </div>
        <div style="float: left;">
            <p>ELIXIR, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK&nbsp; &nbsp; +44&nbsp;(0)1223&nbsp;492-670&nbsp;&nbsp;
                <a href="mailto:info@elixir-europe.org">info@elixir-europe.org</a>
            </p>
            <p>Copyright &copy; ELIXIR <fmt:formatDate value="${date}" pattern="yyyy" /> |
                <a href="https://www.elixir-europe.org/legal/privacy">Privacy</a> |
                <a href="https://www.elixir-europe.org/legal/cookies">Cookies</a> |
                <a href="https://www.elixir-europe.org/legal/terms-of-use">Terms of use</a>
            </p>
        </div>
    </div>
</div>
<t:scripts />