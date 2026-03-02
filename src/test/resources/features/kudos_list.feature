# language: es

Característica: Listado de Kudos
  Como usuario de SofkianOS
  Quiero explorar los kudos otorgados en la organización
  Para ver los reconocimientos realizados

  Antecedentes:
    Dado que navego a la página de listado de kudos

  @smoke @listado
  Escenario: Verificar que la página de listado se carga correctamente
    Entonces el título de la página debe ser "Explorar Kudos"
    Y el formulario de filtros debe estar visible
    Y la tabla de resultados debe estar visible
    Y la tabla debe tener las columnas "De, Para, Categoría, Mensaje, Fecha"

  @listado
  Escenario: Verificar que se muestran kudos en la tabla
    Entonces el contador debe indicar que hay kudos disponibles
    Y la tabla debe tener al menos 1 fila

  @listado
  Escenario: Verificar que el campo de búsqueda tiene placeholder correcto
    Entonces el placeholder del campo de búsqueda debe ser "Buscar en de, para, mensaje..."
