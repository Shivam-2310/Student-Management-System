<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>

<head>
    <meta charset="ISO-8859-1">
    <title>Add Students</title>

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script
            src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

</head>

<body>

<div class="container">
    <h1 class="p-3">Add a Student</h1>
    <form:form action="/saveStudents" method="post" modelAttribute="students">



    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3" for="name">Name</label>
            <div class="col-md-6">
                <form:input type="text" path="name" id="name" class="form-control input-sm" required="required"/>
            </div>
    </div>
    </div>


    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3" for="year">Year</label>
            <div class="col-md-6">
                <form:input type="number" path="year" id="year" class="form-control input-sm" required="required"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="college">College<span style="color: red;"></span></label>
        <form:select path="college" class="form-control" required="required">
            <form:option value="" label="Select College" />
            <form:options items="${colleges}" />
        </form:select>
    </div>

    <div class="form-group">
        <label for="checkbox">Club Membership(Active or Inactive)</label>
        <div class="form-check">
            <form:checkbox path="checkboxOption" class="form-check-input" id="checkbox"/>
            <label class="form-check-label" for="checkbox"></label>
        </div>
    </div>

</div>










    <div class="row p-2">
        <div class="col-md-2">
            <label class="col-md-3" for="name"></label>
            <div class="col-md-2">
                <button type="submit" value="Register" class="btn btn-success">Save</button>
            </div>
        </div>
        </form:form>
    </div>


        <script th:inline="javascript">
        window.onload = function() {

        var msg = "${message}";
        console.log(msg);
        if (msg == "Save Failure") {
        Command: toastr["error"]("Something went wrong with the save.")
        }

        toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
        }
        }
        </script>

        </body>

        </html>