<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<head>
    <meta charset="ISO-8859-1">
    <title>View Students List</title>

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

    <%--    <link href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css" rel="stylesheet">--%>
    <%--    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>--%>
    <%--    <script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>--%>

    <%--    <script src="./js/jquery.js"></script>--%>
    <%--        <script src="./media/js/jquery.dataTables.min.js"></script>--%>
    <%--        <link href="./media/css/jquery.dataTables.min.css" rel="stylesheet">--%>

    <%--    <script>--%>
    <%--        $(document).ready(function(){--%>
    <%--            $("#myTable").dataTable();--%>
    <%--        });--%>
    <%--    </script>--%>
    <%--    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">--%>
    <%--    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>--%>
    <!-- Include jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Include DataTables CSS and JavaScript -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.js"></script>




    <style>
        a{
            color: white;
        }
        a:hover {
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h1 class="p-3">Students Data</h1>
        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-primary btn-double-width">
                <a href="/addStudents">Add New Student</a>
            </button>
        </div>
    </div>



    <div class="text-right">
        <a href="/exportToPDF" class="btn btn-primary">Export to PDF</a>
        <a href="/exportToExcel" class="btn btn-success">Export to Excel</a>
    </div>

    <form:form>
        <table id="myTable" class="table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Year</th>
                <th>College</th>
                <th>Status</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>

            <c:forEach var="students" items="${studentsList}" varStatus="counter">
                <tr>
                    <td>${counter.index+1}</td>
                    <td>${students.name}</td>
                    <td>${students.year}</td>
                    <td>${students.college}</td>
                    <td>${students.checkboxOption? "Active" : "Inactive"}</td>

                    <td>
                        <button type="button" class="btn btn-success">
                            <a href="/editStudents/${students.id}">Edit</a>
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger">
                            <a href="/deleteStudents/${students.id}">Delete</a>
                        </button>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </form:form>
</div>

<style>
    #printButton {
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border: none;
        cursor: pointer;
    }
    .btn-double-width {
        width: 48%; /* Double the original width */
    }

</style>
</head>


<script th:inline="javascript">
    window.onload = function() {

        var msg = "${message}";
        console.log(msg);
        if (msg == "Save Success") {
            Command: toastr["success"]("Student added successfully!!")
        } else if (msg == "Delete Success") {
            Command: toastr["success"]("Student deleted successfully!!")
        } else if (msg == "Delete Failure") {
            Command: toastr["error"]("Some error occurred, couldn't delete Student")
        } else if (msg == "Edit Success") {
            Command: toastr["success"]("Student updated successfully!!")
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

<script>
    $(document).ready(function() {
        $('#myTable').DataTable({
            "paging": true,
            "ordering": true,
        });
    });
</script>


</body>
</html>