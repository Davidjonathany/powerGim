package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 02-04-2025
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.example.modelos.*;
import org.example.services.*;
import org.example.util.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/ExportarExcelServlet")
public class ExportarExcelServlet extends HttpServlet {

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

        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=ReporteClientes_" + rol + "_" + idUsuario + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook();
             OutputStream out = resp.getOutputStream()) {

            Sheet sheet = workbook.createSheet("Clientes");

            // Estilo para encabezados
            CellStyle headerStyle = crearEstiloEncabezado(workbook);
            CellStyle cellStyle = crearEstiloCeldas(workbook);

            // Crear fila de encabezados
            Row headerRow = sheet.createRow(0);
            List<String> columnas = crearListaColumnas(esEntrenador);

            // Llenar encabezados
            for (int i = 0; i < columnas.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas.get(i));
                cell.setCellStyle(headerStyle);
            }

            // Llenar datos
            int rowNum = 1;
            for (ReporteCliente reporte : reportes) {
                Row row = sheet.createRow(rowNum++);
                llenarFilaExcel(row, reporte, esEntrenador, cellStyle);
            }

            // Autoajustar columnas
            for (int i = 0; i < columnas.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte Excel");
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

    private CellStyle crearEstiloEncabezado(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle crearEstiloCeldas(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    private List<String> crearListaColumnas(boolean esEntrenador) {
        List<String> columnas = new ArrayList<>(Arrays.asList(
                "ID", "Nombre Completo", "Usuario", "Correo Electrónico"
        ));

        if (!esEntrenador) {
            columnas.addAll(Arrays.asList("Teléfono", "Cédula"));
        }

        columnas.addAll(Arrays.asList(
                "Tipo Membresía", "Fecha Inicio", "Fecha Vencimiento",
                "Días Restantes", "Estado", "Peso Inicial (kg)",
                "Peso Actual (kg)", "Progreso (%)", "Última Actualización Peso",
                "Total Asistencias", "Última Asistencia", "Asistencias Semana",
                "Asistencias Mes", "Rutinas Asignadas", "Entrenador(es)"
        ));

        return columnas;
    }

    private void llenarFilaExcel(Row row, ReporteCliente reporte, boolean esEntrenador, CellStyle style) {
        int colIndex = 0;

        // ID
        Cell cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getIdCliente());
        cell.setCellStyle(style);

        // Nombre Completo
        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getNombre() + " " + reporte.getApellido());
        cell.setCellStyle(style);

        // Usuario
        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getUsuario());
        cell.setCellStyle(style);

        // Correo
        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getCorreo());
        cell.setCellStyle(style);

        // Campos sensibles (solo para no entrenadores)
        if (!esEntrenador) {
            cell = row.createCell(colIndex++);
            cell.setCellValue(reporte.getTelefono());
            cell.setCellStyle(style);

            cell = row.createCell(colIndex++);
            cell.setCellValue(reporte.getCedula());
            cell.setCellStyle(style);
        }

        // Resto de campos
        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getTipoMembresia());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getFechaInicio());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getFechaVencimiento());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getDiasRestantes());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getEstadoMembresia());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getPesoInicial());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getPesoActual());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getProgresoPeso());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getUltimaActualizacionPeso());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getTotalAsistencias());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getUltimaAsistencia());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getAsistenciasUltimaSemana());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getAsistenciasUltimoMes());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex++);
        cell.setCellValue(reporte.getTotalRutinas());
        cell.setCellStyle(style);

        cell = row.createCell(colIndex);
        cell.setCellValue(reporte.getEntrenadores());
        cell.setCellStyle(style);
    }
}