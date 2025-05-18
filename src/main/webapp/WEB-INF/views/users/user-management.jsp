<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Management System - User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<!-- Header -->
<header class="navbar navbar-dark bg-primary navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/users">
            <i class="fas fa-tasks me-2"></i>Task Management System
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/tasks"><i class="fas fa-list-check me-1"></i>Tasks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/categories"><i class="fas fa-tag me-1"></i>Categories</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/users"><i class="fas fa-users me-1"></i>Users</a>
                </li>
            </ul>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container py-4">
    <h1 class="mb-4">User Management</h1>

    <!-- Success/Error Messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Button to toggle form canvas -->
    <div class="d-flex justify-content-end mb-3">
        <button type="button" class="btn btn-primary" id="toggleUserFormBtn">
            <i class="fas fa-plus me-2"></i>Add User
        </button>
    </div>

    <!-- Hidden User Form Canvas -->
    <div id="userFormCanvas" class="card p-4 mb-4 d-none">
        <h3 class="mb-3">Add New User</h3>
        <form action="${pageContext.request.contextPath}/users" method="post" class="needs-validation" novalidate>
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                    <div class="invalid-feedback">Please provide a username.</div>
                </div>
                <div class="col-md-6">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                    <div class="invalid-feedback">Please provide a valid email.</div>
                </div>
                <div class="col-md-6">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" required>
                    <div class="invalid-feedback">Please provide a full name.</div>
                </div>
                <div class="col-md-6">
                    <label for="department" class="form-label">Department</label>
                    <input type="text" class="form-control" id="department" name="department" required>
                    <div class="invalid-feedback">Please provide a department.</div>
                </div>
                <div class="col-md-6">
                    <label for="joinDate" class="form-label">Join Date</label>
                    <input type="date" class="form-control" id="joinDate" name="joinDate" required>
                    <div class="invalid-feedback">Please provide a join date.</div>
                </div>
                <div class="col-12 mt-4">
                    <button type="submit" class="btn btn-success me-2">
                        <i class="fas fa-save me-2"></i>Save User
                    </button>
                    <button type="button" class="btn btn-secondary" id="cancelUserFormBtn">
                        <i class="fas fa-times me-2"></i>Cancel
                    </button>
                </div>
            </div>
        </form>
    </div>

    <!-- Users Table -->
    <div class="card">
        <div class="card-header bg-light">
            <h5 class="mb-0">Users List</h5>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover table-striped mb-0">
                    <thead class="table-light">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">Department</th>
                        <th scope="col">Join Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty users}">
                            <tr>
                                <td colspan="6" class="text-center py-4">No users found.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${users}" var="user" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${user.username}</td>
                                    <td>${user.full_name}</td>
                                    <td>${user.email}</td>
                                    <td>${user.department}</td>
                                    <td>
                                        <c:if test="${not empty user.joinDate}">
                                            <fmt:parseDate value="${user.joinDate}" pattern="yyyy-MM-dd" var="parsedDate" type="both" />
                                            <fmt:formatDate value="${parsedDate}" pattern="MMM dd, yyyy" />
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<!-- Footer -->
<footer class="bg-light py-3 mt-auto border-top">
    <div class="container text-center">
        <p class="mb-0">&copy; <%= java.time.Year.now() %> Task Management System. All rights reserved.</p>
    </div>
</footer>

<!-- JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const toggleBtn = document.getElementById("toggleUserFormBtn");
        const cancelBtn = document.getElementById("cancelUserFormBtn");
        const formCanvas = document.getElementById("userFormCanvas");

        toggleBtn?.addEventListener("click", () => {
            formCanvas.classList.toggle("d-none");
        });

        cancelBtn?.addEventListener("click", () => {
            formCanvas.classList.add("d-none");
        });

        // Form validation
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    });
</script>
</body>
</html>