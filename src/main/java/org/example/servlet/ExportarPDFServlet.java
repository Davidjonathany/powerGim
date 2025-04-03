package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 02-04-2025
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.example.modelos.*;
import org.example.services.*;
import org.example.util.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.List;

@WebServlet("/ExportarPDFServlet")
public class ExportarPDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int idUsuario = (int) session.getAttribute("idUsuario");
        boolean esEntrenador = "Entrenador".equals(rol);
        boolean esCliente = "Cliente".equals(rol);

        // Obtener datos filtrados según el rol
        List<ReporteCliente> reportes = obtenerDatosFiltrados(rol, idUsuario);

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=ReporteClientes_" + rol + "_" + idUsuario + ".pdf");

        try (OutputStream out = resp.getOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // Logo
            try {
                Image logo = Image.getInstance(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4jzskVF5G-MAOrP0SpS0Wv36qeN21X4yfslF3eMDryzRon7tjA7XuEdfmATA4NEBI7Fg&usqp=CAU"));
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.err.println("Error al cargar el logo: " + e.getMessage());
            }

            // Título
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph title = new Paragraph("Reporte de Clientes - " + rol, fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Subtítulo con información del usuario
            Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
            Paragraph subtitle = new Paragraph("Generado por: " + session.getAttribute("usuario") + " | " + new java.util.Date(), fontSubtitle);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(15);
            document.add(subtitle);

            // Crear tabla dinámica según rol
            PdfPTable table = crearTablaPDF(esEntrenador);

            // Llenar tabla con datos filtrados
            for (ReporteCliente reporte : reportes) {
                agregarFilaPDF(table, reporte, esEntrenador);
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte PDF");
        }
    }

    private List<ReporteCliente> obtenerDatosFiltrados(String rol, int idUsuario) {
        try (Connection conn = Conexion.getConnection()) {
            ReporteClienteService service = new ReporteClienteServiceImpl(conn);

            switch (rol) {
                case "Administrador":
                    return service.generarReporteClientes();
                case "Entrenador":
                    return service.generarReportePorEntrenador(idUsuario);
                case "Cliente":
                    return service.generarReportePorCliente(idUsuario);
                default:
                    return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo datos filtrados", e);
        }
    }

    private PdfPTable crearTablaPDF(boolean esEntrenador) {
        // Columnas base
        List<String> columnas = new ArrayList<>(Arrays.asList(
                "ID", "Nombre", "Usuario", "Correo"
        ));

        if (!esEntrenador) {
            columnas.addAll(Arrays.asList("Teléfono", "Cédula"));
        }

        columnas.addAll(Arrays.asList(
                "Membresía", "Inicio", "Vencimiento", "Días Restantes",
                "Estado", "Peso Inicial", "Peso Actual", "Progreso",
                "Última Act. Peso", "Total Asistencias", "Última Asistencia",
                "Asistencias Semana", "Asistencias Mes", "Rutinas", "Entrenadores"
        ));

        PdfPTable table = new PdfPTable(columnas.size());
        table.setWidthPercentage(100);

        columnas.forEach(col -> {
            PdfPCell cell = new PdfPCell(new Phrase(col));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        });

        return table;
    }

    private void agregarFilaPDF(PdfPTable table, ReporteCliente reporte, boolean esEntrenador) {
        table.addCell(String.valueOf(reporte.getIdCliente()));
        table.addCell(reporte.getNombre() + " " + reporte.getApellido());
        table.addCell(reporte.getUsuario());
        table.addCell(reporte.getCorreo());

        if (!esEntrenador) {
            table.addCell(reporte.getTelefono());
            table.addCell(reporte.getCedula());
        }

        table.addCell(reporte.getTipoMembresia());
        table.addCell(reporte.getFechaInicio());
        table.addCell(reporte.getFechaVencimiento());
        table.addCell(String.valueOf(reporte.getDiasRestantes()));
        table.addCell(reporte.getEstadoMembresia());
        table.addCell(String.valueOf(reporte.getPesoInicial()));
        table.addCell(String.valueOf(reporte.getPesoActual()));
        table.addCell(reporte.getProgresoPeso() + " %");
        table.addCell(reporte.getUltimaActualizacionPeso());
        table.addCell(String.valueOf(reporte.getTotalAsistencias()));
        table.addCell(reporte.getUltimaAsistencia());
        table.addCell(String.valueOf(reporte.getAsistenciasUltimaSemana()));
        table.addCell(String.valueOf(reporte.getAsistenciasUltimoMes()));
        table.addCell(String.valueOf(reporte.getTotalRutinas()));
        table.addCell(reporte.getEntrenadores());
    }
}