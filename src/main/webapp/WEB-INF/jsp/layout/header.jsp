<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="js/jquery.js"></script>
	<style media="screen"></style>
</head>
<body>
	
<script type="text/javascript">
function redirect(path) {
	var loc = window.location.href.split("?");
	loc[0] = window.location.origin + path;
	window.location.href = loc.join("?");
}
</script>
		
<header>
<div class="button-area">
	<input type="button" value="Database" onclick="redirect('/h2-console')"/>
	<input type="button" value="Result" onclick="redirect('/result')"/>
</div>
</header>

<div class="container-fluid">