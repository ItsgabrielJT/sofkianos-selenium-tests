# language: es

Característica: Filtrado por Fecha de Kudos
  Como usuario de SofkianOS
  Quiero filtrar kudos por rango de fechas
  Para ver reconocimientos de un período específico

  Antecedentes:
    Dado que navego a la página de listado de kudos
    Y verifico que hay kudos disponibles

  @fecha
  Escenario: Filtrar por fecha "desde" y "hasta" con rango válido
    Cuando establezco la fecha desde "2026-03-01"
    Y establezco la fecha hasta "2026-03-31"
    Y aplico los filtros
    Entonces el contador debe reflejar los resultados de la búsqueda

  @fecha
  Escenario: Filtrar por rango de fecha que no contiene kudos
    Cuando establezco la fecha desde "2020-01-01"
    Y establezco la fecha hasta "2020-01-31"
    Y aplico los filtros
    Entonces el contador debe indicar 3 kudos encontrados

  @fecha
  Escenario: Filtrar solo con fecha "desde"
    Cuando establezco la fecha desde "2026-03-01"
    Y aplico los filtros
    Entonces el contador debe reflejar los resultados de la búsqueda

  @fecha
  Escenario: Filtrar solo con fecha "hasta"
    Cuando establezco la fecha hasta "2026-03-31"
    Y aplico los filtros
    Entonces el contador debe reflejar los resultados de la búsqueda

  @fecha @ordenamiento
  Escenario: Cambiar ordenamiento de los resultados
    Entonces el botón de ordenamiento debe mostrar "Más recientes"
    Cuando hago clic en el botón de ordenamiento
    Entonces el botón de ordenamiento debe mostrar "Más antiguos"
    Cuando hago clic en el botón de ordenamiento
    Entonces el botón de ordenamiento debe mostrar "Más recientes"
