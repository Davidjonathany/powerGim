<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 27-03-2025
  Time: 13:49
--%>
</div>
<footer style="
    background-color: #0d6efd;
    color: white;
    padding: 15px 0;
    margin-top: auto;
    position: relative;
    bottom: 0;
    width: 100%;
">
    <div class="container-fluid">
        <div class="row align-items-center">
            <div class="col-md-6 text-center text-md-start">
                <strong style="color: white !important;">© 2025 PowerGim by David</strong> All rights reserved.
            </div>
            <div class="col-md-6 text-center text-md-end">
                <span style="color: white !important;"><b>Version</b> 1.0.0</span>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- Añade este script para ajustar dinámicamente el footer -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const footer = document.querySelector('footer');
        const body = document.body;
        const html = document.documentElement;

        // Calcula la altura del contenido
        const height = Math.max(
            body.scrollHeight, body.offsetHeight,
            html.clientHeight, html.scrollHeight, html.offsetHeight
        );

        // Si el contenido es menor que la ventana, ajusta el margen
        if (height < window.innerHeight) {
            const footerHeight = footer.offsetHeight;
            const contentWrapper = document.querySelector('.content-wrapper') || document.querySelector('main');
            if (contentWrapper) {
                contentWrapper.style.minHeight = `calc(100vh - ${footerHeight + 150}px)`;
            }
        }
    });
</script>
</body>
</html>